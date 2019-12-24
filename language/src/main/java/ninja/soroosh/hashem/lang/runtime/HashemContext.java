
package ninja.soroosh.hashem.lang.runtime;

import com.oracle.truffle.api.*;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleLanguage.Env;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.Layout;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.Source;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.builtins.*;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.nodes.HashemRootNode;
import ninja.soroosh.hashem.lang.nodes.local.HashemReadArgumentNode;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The run-time state of Hashemi during execution. The context is created by the {@link HashemLanguage}. It
 * is used, for example, by {@link HashemBuiltinNode#getContext() builtin functions}.
 * <p>
 * It would be an error to have two different context instances during the execution of one script.
 * However, if two separate scripts run in one Java VM at the same time, they have a different
 * context. Therefore, the context is not a singleton.
 */
public final class HashemContext {

    private static final Source BUILTIN_SOURCE = Source.newBuilder(HashemLanguage.ID, "", "SL builtin").build();
    static final Layout LAYOUT = Layout.createLayout();

    private final Map<Integer, HashemWebServer> portServers = new HashMap<>();

    private final Env env;
    private final BufferedReader input;
    private final PrintWriter output;
    private final HashemBebinRegistry functionRegistry;
    private final Shape emptyShape;
    private final HashemLanguage language;
    private final AllocationReporter allocationReporter;
    private final Iterable<Scope> topScopes; // Cache the top scopes

    public HashemContext(HashemLanguage language, TruffleLanguage.Env env, List<NodeFactory<? extends HashemBuiltinNode>> externalBuiltins) {
        this.env = env;
        this.input = new BufferedReader(new InputStreamReader(env.in()));
        this.output = new PrintWriter(env.out(), true);
        this.language = language;
        this.allocationReporter = env.lookup(AllocationReporter.class);
        this.functionRegistry = new HashemBebinRegistry(language);
        this.topScopes = Collections.singleton(Scope.newBuilder("global", functionRegistry.getFunctionsObject()).build());
        installBuiltins();
        for (NodeFactory<? extends HashemBuiltinNode> builtin : externalBuiltins) {
            installBuiltin(builtin);
        }
        this.emptyShape = LAYOUT.createShape(HashemObjectType.SINGLETON);
    }

    /**
     * Return the current Truffle environment.
     */
    public Env getEnv() {
        return env;
    }

    /**
     * Returns the default input, i.e., the source for the {@link HashemBekhoonBuiltin}. To allow unit
     * testing, we do not use {@link System#in} directly.
     */
    public BufferedReader getInput() {
        return input;
    }

    /**
     * The default default, i.e., the output for the {@link HashemBechapBuiltin}. To allow unit
     * testing, we do not use {@link System#out} directly.
     */
    public PrintWriter getOutput() {
        return output;
    }

    /**
     * Returns the registry of all functions that are currently defined.
     */
    public HashemBebinRegistry getFunctionRegistry() {
        return functionRegistry;
    }

    public Iterable<Scope> getTopScopes() {
        return topScopes;
    }

    /**
     * Adds all builtin functions to the {@link HashemBebinRegistry}. This method lists all
     * {@link HashemBuiltinNode builtin implementation classes}.
     */
    private void installBuiltins() {
        installBuiltin(HashemBekhoonBuiltinFactory.getInstance());
        installBuiltin(HashemAdadBekhoonBuiltinFactory.getInstance());
        installBuiltin(HashemBechapBuiltinFactory.getInstance());
        installBuiltin(HashemNanoTimeBuiltinFactory.getInstance());
        installBuiltin(HashemDefineFunctionBuiltinFactory.getInstance());
        installBuiltin(HashemStackTraceBuiltinFactory.getInstance());
        installBuiltin(HashemNewObjectBuiltinFactory.getInstance());
        installBuiltin(HashemEvalBuiltinFactory.getInstance());
        installBuiltin(HashemImportBuiltinFactory.getInstance());
        installBuiltin(HashemGetSizeBuiltinFactory.getInstance());
        installBuiltin(HashemHasSizeBuiltinFactory.getInstance());
        installBuiltin(HashemIsExecutableBuiltinFactory.getInstance());
        installBuiltin(HashemIsPoochBuiltinFactory.getInstance());
        installBuiltin(HashemWrapPrimitiveBuiltinFactory.getInstance());
        installBuiltin(HashemWebServerBuiltinFactory.getInstance());
        installBuiltin(HashemStartBuiltinFactory.getInstance());
        installBuiltin(HashemStopBuiltinFactory.getInstance());
        installBuiltin(HashemAddHandlerBuiltinFactory.getInstance());
    }

    public void installBuiltin(NodeFactory<? extends HashemBuiltinNode> factory) {
        /*
         * The builtin node factory is a class that is automatically generated by the Truffle DSL.
         * The signature returned by the factory reflects the signature of the @Specialization
         *
         * methods in the builtin classes.
         */
        int argumentCount = factory.getExecutionSignature().size();
        HashemExpressionNode[] argumentNodes = new HashemExpressionNode[argumentCount];
        /*
         * Builtin functions are like normal functions, i.e., the arguments are passed in as an
         * Object[] array encapsulated in SLArguments. A SLReadArgumentNode extracts a parameter
         * from this array.
         */
        for (int i = 0; i < argumentCount; i++) {
            argumentNodes[i] = new HashemReadArgumentNode(i);
        }
        /* Instantiate the builtin node. This node performs the actual functionality. */
        HashemBuiltinNode builtinBodyNode = factory.createNode((Object) argumentNodes);
        builtinBodyNode.addRootTag();
        /* The name of the builtin function is specified via an annotation on the node class. */
        String name = lookupNodeInfo(builtinBodyNode.getClass()).shortName();
        builtinBodyNode.setUnavailableSourceSection();

        /* Wrap the builtin in a RootNode. Truffle requires all AST to start with a RootNode. */
        HashemRootNode rootNode = new HashemRootNode(language, new FrameDescriptor(), builtinBodyNode, BUILTIN_SOURCE.createUnavailableSection(), name);

        /* Register the builtin function in our function registry. */
        getFunctionRegistry().register(name, Truffle.getRuntime().createCallTarget(rootNode));
    }

    public static NodeInfo lookupNodeInfo(Class<?> clazz) {
        if (clazz == null) {
            return null;
        }
        NodeInfo info = clazz.getAnnotation(NodeInfo.class);
        if (info != null) {
            return info;
        } else {
            return lookupNodeInfo(clazz.getSuperclass());
        }
    }

    /*
     * Methods for object creation / object property access.
     */
    public AllocationReporter getAllocationReporter() {
        return allocationReporter;
    }

    /**
     * Allocate an empty object. All new objects initially have no properties. Properties are added
     * when they are first stored, i.e., the store triggers a shape change of the object.
     */
    public DynamicObject createObject(AllocationReporter reporter) {
        DynamicObject object = null;
        reporter.onEnter(null, 0, AllocationReporter.SIZE_UNKNOWN);
        object = emptyShape.newInstance();
        reporter.onReturnValue(object, 0, AllocationReporter.SIZE_UNKNOWN);
        return object;
    }

    public static boolean isSLObject(Object value) {
        /*
         * LAYOUT.getType() returns a concrete implementation class, i.e., a class that is more
         * precise than the base class DynamicObject. This makes the type check faster.
         */
        return LAYOUT.getType().isInstance(value) && LAYOUT.getType().cast(value).getShape().getObjectType() == HashemObjectType.SINGLETON;
    }

    /*
     * Methods for language interoperability.
     */

    public static Object fromForeignValue(Object a) {
        if (a instanceof Long || a instanceof HashemBigNumber || a instanceof String || a instanceof Boolean) {
            return a;
        } else if (a instanceof Character) {
            return String.valueOf(a);
        } else if (a instanceof Number) {
            return fromForeignNumber(a);
        } else if (a instanceof TruffleObject) {
            return a;
        } else if (a instanceof HashemContext) {
            return a;
        }
        CompilerDirectives.transferToInterpreter();
        throw new IllegalStateException(a + " is not a Truffle value");
    }

    @TruffleBoundary
    private static long fromForeignNumber(Object a) {
        return ((Number) a).longValue();
    }

    public CallTarget parse(Source source) {
        return env.parsePublic(source);
    }

    /**
     * Returns an object that contains bindings that were exported across all used languages. To
     * read or write from this object the {@link TruffleObject interop} API can be used.
     */
    public TruffleObject getPolyglotBindings() {
        return (TruffleObject) env.getPolyglotBindings();
    }

    public static HashemContext getCurrent() {
        return HashemLanguage.getCurrentContext();
    }

    public void addWebServer(long port, HashemWebServer server) {
        this.portServers.putIfAbsent((int) port, server);
    }

    public HashemWebServer getWebServer(long port) {
        return portServers.get(port);
    }
}
