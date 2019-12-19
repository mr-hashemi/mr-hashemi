package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.nodes.HashemStatementNode;
import ninja.soroosh.hashem.lang.nodes.util.HashemUnboxNodeGen;

@NodeInfo(shortName = "age", description = "The node implementing a condional statement")
public final class HashemAgeNode extends HashemStatementNode {

    /**
     * The condition of the {@code age}. This in a {@link HashemExpressionNode} because we require a
     * result value. We do not have a node type that can only return a {@code boolean} value, so
     * {@link #evaluateCondition executing the condition} can lead to a type error.
     */
    @Child private HashemExpressionNode conditionNode;

    /** Statement (or {@link HashemBlockNode block}) executed when the condition is true. */
    @Child private HashemStatementNode thenPartNode;

    /** Statement (or {@link HashemBlockNode block}) executed when the condition is false. */
    @Child private HashemStatementNode elsePartNode;

    private final ConditionProfile condition = ConditionProfile.createCountingProfile();

    public HashemAgeNode(HashemExpressionNode conditionNode, HashemStatementNode thenPartNode, HashemStatementNode elsePartNode) {
        this.conditionNode = HashemUnboxNodeGen.create(conditionNode);
        this.thenPartNode = thenPartNode;
        this.elsePartNode = elsePartNode;
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        /*
         * In the interpreter, record profiling information that the condition was executed and with
         * which outcome.
         */
        if (condition.profile(evaluateCondition(frame))) {
            /* Execute the then-branch. */
            thenPartNode.executeVoid(frame);
        } else {
            /* Execute the else-branch (which is optional according to theHashemisyntax). */
            if (elsePartNode != null) {
                elsePartNode.executeVoid(frame);
            }
        }
    }

    private boolean evaluateCondition(VirtualFrame frame) {
        try {
            /*
             * The condition must evaluate to a boolean value, so we call the boolean-specialized
             * execute method.
             */
            return conditionNode.executeBoolean(frame);
        } catch (UnexpectedResultException ex) {
            /*
             * The condition evaluated to a non-boolean result. This is a type error in the SL
             * program.
             */
            throw HashemException.typeError(this, ex.getResult());
        }
    }
}
