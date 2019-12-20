package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

/**
 * Logical disjunction node with short circuit evaluation.
 */
@NodeInfo(shortName = "||")
public final class HashemLogicalOrNode extends HashemShortCircuitNode {

    public HashemLogicalOrNode(HashemExpressionNode left, HashemExpressionNode right) {
        super(left, right);
    }

    @Override
    protected boolean isEvaluateRight(boolean left) {
        return !left;
    }

    @Override
    protected boolean execute(boolean left, boolean right) {
        return left || right;
    }

}
