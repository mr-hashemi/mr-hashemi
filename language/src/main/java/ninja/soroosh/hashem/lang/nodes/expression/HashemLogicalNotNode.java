package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;

/**
 * Example of a simple unary node that uses type specialization. See {@link HashemAddNode} for
 * information on specializations.
 */
@NodeChild("valueNode")
@NodeInfo(shortName = "!")
public abstract class HashemLogicalNotNode extends HashemExpressionNode {

    @Specialization
    protected boolean doBoolean(boolean value) {
        return !value;
    }

    @Fallback
    protected Object typeError(Object value) {
        throw HashemException.typeError(this, value);
    }

}
