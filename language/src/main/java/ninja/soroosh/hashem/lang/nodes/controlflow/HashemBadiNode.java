package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;

/**
 * Implementation of the Hashemi badi statement. We need to unwind an unknown number of interpreter
 * frames that are between this {@link HashemBadiNode} and the {@link HashemTaNode} of the loop we
 * are continuing. This is done by throwing an {@link HashemiBadiException exception} that is caught
 * by the {@link HashemTaNode#executeVoid loop node}.
 */
@NodeInfo(shortName = "badi", description = "The node implementing a continue statement")
public final class HashemBadiNode extends HashemStatementNode {

    @Override
    public void executeVoid(VirtualFrame frame) {
        throw HashemiBadiException.SINGLETON;
    }
}
