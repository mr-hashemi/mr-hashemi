package ninja.soroosh.hashem.lang.nodes.expression;

import java.math.BigInteger;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.runtime.HashemBigNumber;

/**
 * Constant literal for a arbitrary-precision number that exceeds the range of
 * {@link HashemLongLiteralNode}.
 */
@NodeInfo(shortName = "const")
public final class HashemBigIntegerLiteralNode extends HashemExpressionNode {

    private final HashemBigNumber value;

    public HashemBigIntegerLiteralNode(BigInteger value) {
        this.value = new HashemBigNumber(value);
    }

    @Override
    public HashemBigNumber executeGeneric(VirtualFrame frame) {
        return value;
    }
}
