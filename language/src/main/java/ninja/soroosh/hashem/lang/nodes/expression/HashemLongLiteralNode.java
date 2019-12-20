package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

/**
 * Constant literal for a primitive {@code long} value. The unboxed value can be returned when the
 * parent expects a long value and calls {@link HashemLongLiteralNode#executeLong}. In the generic case,
 * the primitive value is automatically boxed by Java.
 */
@NodeInfo(shortName = "const")
public final class HashemLongLiteralNode extends HashemExpressionNode {

    private final long value;

    public HashemLongLiteralNode(long value) {
        this.value = value;
    }

    @Override
    public long executeLong(VirtualFrame frame) throws UnexpectedResultException {
        return value;
    }

    @Override
    public Object executeGeneric(VirtualFrame frame) {
        return value;
    }
}
