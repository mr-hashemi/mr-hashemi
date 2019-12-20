package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.runtime.HashemNull;

/**
 * Implementation of the Hashemi bede statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link HashemBedeNode} and the {@link HashemFunctionBodyNode} of the
 * method we are exiting. This is done by throwing an {@link HashemBedeException exception} that is
 * caught by the {@link HashemFunctionBodyNode#executeGeneric function body}. The exception transports
 * the returned value.
 */
@NodeInfo(shortName = "bede", description = "The node implementing a bede statement")
public final class HashemBedeNode extends HashemStatementNode {

    @Child private HashemExpressionNode valueNode;

    public HashemBedeNode(HashemExpressionNode valueNode) {
        this.valueNode = valueNode;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        Object result;
        if (valueNode != null) {
            result = valueNode.executeGeneric(frame);
        } else {
            /*
             * Return statement that was not followed by an expression, so return the Hashemi null value.
             */
            result = HashemNull.SINGLETON;
        }
        throw new HashemBedeException(result);
    }
}
