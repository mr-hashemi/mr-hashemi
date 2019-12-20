
package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.CompilationFinal;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemBebin;
import ninja.soroosh.hashem.lang.runtime.HashemBebinRegistry;

/**
 * Constant literal for a {@link HashemBebin function} value, created when a function name occurs as
 * a literal inHashemisource code. Note that function redefinition can change the {@link CallTarget
 * call target} that is executed when calling the function, but the {@link HashemBebin} for a name
 * never changes. This is guaranteed by the {@link HashemBebinRegistry}.
 */
@NodeInfo(shortName = "bebin")
public final class HashemBebinLiteralNode extends HashemExpressionNode {

    /** The name of the function. */
    private final String functionName;

    /**
     * The resolved function. During parsing (in the constructor of this node), we do not have the
     * {@link HashemContext} available yet, so the lookup can only be done at {@link #executeGeneric
     * first execution}. The {@link CompilationFinal} annotation ensures that the function can still
     * be constant folded during compilation.
     */
    @CompilationFinal private HashemBebin cachedFunction;

    public HashemBebinLiteralNode(String functionName) {
        this.functionName = functionName;
    }

    @Override
    public HashemBebin executeGeneric(VirtualFrame frame) {
        if (cachedFunction == null) {
            /* We are about to change a @CompilationFinal field. */
            CompilerDirectives.transferToInterpreterAndInvalidate();
            /* First execution of the node: lookup the function in the function registry. */
            cachedFunction = lookupContextReference(HashemLanguage.class).get().getFunctionRegistry().lookup(functionName, true);
        }
        return cachedFunction;
    }

}
