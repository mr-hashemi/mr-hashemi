
package ninja.soroosh.hashem.lang.runtime;

import java.util.logging.Level;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.utilities.CyclicAssumption;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.nodes.HashemUndefinedBebinRootNode;

/**
 * Represents a Hashemi bebin. On the Truffle level, a callable element is represented by a
 * {@link RootCallTarget call target}. This class encapsulates a call target, and adds version
 * support: functions in Hashemi can be redefined, i.e. changed at run time. When a function is
 * redefined, the call target managed by this function object is changed (and {@link #callTarget} is
 * therefore not a final field).
 * <p>
 * Function redefinition is expected to be rare, therefore optimized call nodes want to speculate
 * that the call target is stable. This is possible with the help of a Truffle {@link Assumption}: a
 * call node can keep the call target returned by {@link #getCallTarget()} cached until the
 * assumption returned by {@link #getCallTargetStable()} is valid.
 * <p>
 * The {@link #callTarget} can be {@code null}. To ensure that only one {@link HashemBebin} instance
 * per name exists, the {@link HashemBebinRegistry} creates an instance also when performing name
 * lookup. A function that has been looked up, i.e., used, but not defined, has a call target that
 * encapsulates a {@link HashemUndefinedBebinRootNode}.
 */
@ExportLibrary(InteropLibrary.class)
public final class HashemBebin implements TruffleObject {

    public static final int INLINE_CACHE_SIZE = 2;

    private static final TruffleLogger LOG = TruffleLogger.getLogger(HashemLanguage.ID, HashemBebin.class);

    /** The name of the function. */
    private final String name;

    /** The current implementation of this function. */
    private RootCallTarget callTarget;

    /**
     * Manages the assumption that the {@link #callTarget} is stable. We use the utility class
     * {@link CyclicAssumption}, which automatically creates a new {@link Assumption} when the old
     * one gets invalidated.
     */
    private final CyclicAssumption callTargetStable;

    protected HashemBebin(HashemLanguage language, String name) {
        this.name = name;
        this.callTarget = Truffle.getRuntime().createCallTarget(new HashemUndefinedBebinRootNode(language, name));
        this.callTargetStable = new CyclicAssumption(name);
    }

    public String getName() {
        return name;
    }

    protected void setCallTarget(RootCallTarget callTarget) {
        this.callTarget = callTarget;
        /*
         * We have a new call target. Invalidate all code that speculated that the old call target
         * was stable.
         */
        LOG.log(Level.FINE, "Installed call target for: {0}", name);
        callTargetStable.invalidate();
    }

    public RootCallTarget getCallTarget() {
        return callTarget;
    }

    public Assumption getCallTargetStable() {
        return callTargetStable.getAssumption();
    }

    /**
     * This method is, e.g., called when using a function literal in a string concatenation. So
     * changing it has an effect onHashemiprograms.
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * {@link HashemBebin} instances are always visible as executable to other languages.
     */
    @SuppressWarnings("static-method")
    public SourceSection getDeclaredLocation() {
        return getCallTarget().getRootNode().getSourceSection();
    }

    /**
     * {@link HashemBebin} instances are always visible as executable to other languages.
     */
    @SuppressWarnings("static-method")
    @ExportMessage
    boolean isExecutable() {
        return true;
    }

    /**
     * We allow languages to execute this function. We implement the interop execute message that
     * forwards to a function dispatch.
     */
    @ExportMessage
    abstract static class Execute {

        /**
         * Inline cached specialization of the dispatch.
         *
         * <p>
         * SinceHashemiis a quite simple language, the benefit of the inline cache seems small: after
         * checking that the actual function to be executed is the same as the cachedFuntion, we can
         * safely execute the cached call target. You can reasonably argue that caching the call
         * target is overkill, since we could just retrieve it via {@code function.getCallTarget()}.
         * However, caching the call target and using a {@link DirectCallNode} allows Truffle to
         * perform method inlining. In addition, in a more complex language the lookup of the call
         * target is usually much more complicated than in SL.
         * </p>
         *
         * <p>
         * {@code limit = "INLINE_CACHE_SIZE"} Specifies the limit number of inline cache
         * specialization instantiations.
         * </p>
         * <p>
         * {@code guards = "function.getCallTarget() == cachedTarget"} The inline cache check. Note
         * that cachedTarget is a final field so that the compiler can optimize the check.
         * </p>
         * <p>
         * {@code assumptions = "callTargetStable"} Support for function redefinition: When a
         * function is redefined, the call target maintained by the SLFunction object is changed. To
         * avoid a check for that, we use an Assumption that is invalidated by the SLFunction when
         * the change is performed. Since checking an assumption is a no-op in compiled code, the
         * assumption check performed by the DSL does not add any overhead during optimized
         * execution.
         * </p>
         *
         * @see Cached
         * @see Specialization
         *
         * @param function the dynamically provided function
         * @param cachedFunction the cached function of the specialization instance
         * @param callNode the {@link DirectCallNode} specifically created for the
         *            {@link CallTarget} in cachedFunction.
         */
        @Specialization(limit = "INLINE_CACHE_SIZE", //
                        guards = "function.getCallTarget() == cachedTarget", //
                        assumptions = "callTargetStable")
        @SuppressWarnings("unused")
        protected static Object doDirect(HashemBebin function, Object[] arguments,
                                         @Cached("function.getCallTargetStable()") Assumption callTargetStable,
                                         @Cached("function.getCallTarget()") RootCallTarget cachedTarget,
                                         @Cached("create(cachedTarget)") DirectCallNode callNode) {

            /* Inline cache hit, we are safe to execute the cached call target. */
            Object returnValue = callNode.call(arguments);
            return returnValue;
        }

        /**
         * Slow-path code for a call, used when the polymorphic inline cache exceeded its maximum
         * size specified in <code>INLINE_CACHE_SIZE</code>. Such calls are not optimized any
         * further, e.g., no method inlining is performed.
         */
        @Specialization(replaces = "doDirect")
        protected static Object doIndirect(HashemBebin function, Object[] arguments,
                                           @Cached IndirectCallNode callNode) {
            /*
             *Hashemihas a quite simple call lookup: just ask the function for the current call target,
             * and call it.
             */
            return callNode.call(function.getCallTarget(), arguments);
        }
    }

}
