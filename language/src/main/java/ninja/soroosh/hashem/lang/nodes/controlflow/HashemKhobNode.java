package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;

/**
 * Implementation of the Hashemi break statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link HashemKhobNode} and the {@link HashemTaNode} of the loop we are
 * breaking out. This is done by throwing an {@link HashemKhobException exception} that is caught by
 * the {@link HashemTaNode#executeVoid loop node}.
 */
@NodeInfo(shortName = "khob", description = "The node implementing a break statement")
public final class HashemKhobNode extends HashemStatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
        throw HashemKhobException.SINGLETON;
    }
}
