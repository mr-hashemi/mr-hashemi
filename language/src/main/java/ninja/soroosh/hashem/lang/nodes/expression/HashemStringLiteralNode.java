package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

/**
 * Constant literal for a String value.
 */
@NodeInfo(shortName = "const")
public final class HashemStringLiteralNode extends HashemExpressionNode {

    private final String value;

    public HashemStringLiteralNode(String value) {
        this.value = value;
    }

    @Override
    public String executeGeneric(VirtualFrame frame) {
        return value;
    }
}
