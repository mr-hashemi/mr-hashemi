package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.nodes.HashemRootNode;
import ninja.soroosh.hashem.lang.runtime.HashemPooch;

/**
 * The body of a user-defined function in Hashemi. This is the node referenced by a {@link HashemRootNode} for
 * user-defined functions. It handles the return value of a function: the {@link HashemBedeNode return
 * statement} throws an {@link HashemBedeException exception} with the return value. This node catches
 * the exception. If the method ends without an explicit {@code return}, return the
 * {@link HashemPooch#SINGLETON default null value}.
 */
@NodeInfo(shortName = "body")
public final class HashemFunctionBodyNode extends HashemExpressionNode {

    /** The body of the function. */
    @Child private HashemStatementNode bodyNode;

    /**
     * Profiling information, collected by the interpreter, capturing whether the function had an
     * {@link HashemBedeNode explicit return statement}. This allows the compiler to generate better
     * code.
     */
    private final BranchProfile exceptionTaken = BranchProfile.create();
    private final BranchProfile nullTaken = BranchProfile.create();

    public HashemFunctionBodyNode(HashemStatementNode bodyNode) {
        this.bodyNode = bodyNode;
        addRootTag();
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        try {
            /* Execute the function body. */
            bodyNode.executeVoid(frame);

        } catch (HashemBedeException ex) {
            /*
             * In the interpreter, record profiling information that the function has an explicit
             * return.
             */
            exceptionTaken.enter();
            /* The exception transports the actual return value. */
            return ex.getResult();
        }

        /*
         * In the interpreter, record profiling information that the function ends without an
         * explicit return.
         */
        nullTaken.enter();
        /* Return the default null value. */
        return HashemPooch.SINGLETON;
    }
}
