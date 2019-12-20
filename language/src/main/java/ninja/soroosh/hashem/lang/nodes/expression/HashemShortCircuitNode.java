
package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

/**
 * Logical operations i nHashemi use short circuit evaluation: if the evaluation of the left operand
 * already decides the result of the operation, the right operand must not be executed. This is
 * expressed in using this base class for {@link HashemLogicalAndNode} and {@link HashemLogicalOrNode}.
 */
public abstract class HashemShortCircuitNode extends HashemExpressionNode {

    @Child private HashemExpressionNode left;
    @Child private HashemExpressionNode right;

    /**
     * Short circuits might be used just like a conditional statement it makes sense to profile the
     * branch probability.
     */
    private final ConditionProfile evaluateRightProfile = ConditionProfile.createCountingProfile();

    public HashemShortCircuitNode(HashemExpressionNode left, HashemExpressionNode right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public final Object executeGeneric(VirtualFrame frame) {
        return executeBoolean(frame);
    }

    @Override
    public final boolean executeBoolean(VirtualFrame frame) {
        boolean leftValue;
        try {
            leftValue = left.executeBoolean(frame);
        } catch (UnexpectedResultException e) {
            throw HashemException.typeError(this, e.getResult(), null);
        }
        boolean rightValue;
        try {
            if (evaluateRightProfile.profile(isEvaluateRight(leftValue))) {
                rightValue = right.executeBoolean(frame);
            } else {
                rightValue = false;
            }
        } catch (UnexpectedResultException e) {
            throw HashemException.typeError(this, leftValue, e.getResult());
        }
        return execute(leftValue, rightValue);
    }

    /**
     * This method is called after the left child was evaluated, but before the right child is
     * evaluated. The right child is only evaluated when the return value is {code true}.
     */
    protected abstract boolean isEvaluateRight(boolean leftValue);

    /**
     * Calculates the result of the short circuit operation. If the right node is not evaluated then
     * <code>false</code> is provided.
     */
    protected abstract boolean execute(boolean leftValue, boolean rightValue);

}
