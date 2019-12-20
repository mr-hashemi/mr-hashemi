
package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

@NodeInfo(shortName = "ta", description = "The node implementing a ta loop")
public final class HashemTaNode extends HashemStatementNode {

    @Child private LoopNode loopNode;

    public HashemTaNode(HashemExpressionNode conditionNode, HashemStatementNode bodyNode) {
        this.loopNode = Truffle.getRuntime().createLoopNode(new HashemTaRepeatingNode(conditionNode, bodyNode));
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        loopNode.execute(frame);
    }

}
