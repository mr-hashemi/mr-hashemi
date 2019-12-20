package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.nodes.HashemBinaryNode;
import ninja.soroosh.hashem.lang.runtime.HashemBigNumber;

/**
 * This class is similar to the extensively documented {@link HashemAddNode}. Divisions by 0 throw the
 * same {@link ArithmeticException exception} as in Java,Hashemihas no special handling for it to keep
 * the code simple.
 */
@NodeInfo(shortName = "/")
public abstract class HashemDivNode extends HashemBinaryNode {

    @Specialization(rewriteOn = ArithmeticException.class)
    protected long div(long left, long right) throws ArithmeticException {
        long result = left / right;
        /*
         * The division overflows if left is Long.MIN_VALUE and right is -1.
         */
        if ((left & right & result) < 0) {
            throw new ArithmeticException("long overflow");
        }
        return result;
    }

    @Specialization
    @TruffleBoundary
    protected HashemBigNumber div(HashemBigNumber left, HashemBigNumber right) {
        return new HashemBigNumber(left.getValue().divide(right.getValue()));
    }

    @Fallback
    protected Object typeError(Object left, Object right) {
        throw HashemException.typeError(this, left, right);
    }
}
