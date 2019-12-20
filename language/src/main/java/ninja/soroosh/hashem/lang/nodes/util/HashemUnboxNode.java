package ninja.soroosh.hashem.lang.nodes.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.nodes.HashemTypes;
import ninja.soroosh.hashem.lang.runtime.HashemBebin;
import ninja.soroosh.hashem.lang.runtime.HashemBigNumber;
import ninja.soroosh.hashem.lang.runtime.HashemPooch;

/**
 * The node to normalize any value to anHashemivalue. This is useful to reduce the number of values
 * expression nodes need to expect.
 */
@TypeSystemReference(HashemTypes.class)
@NodeChild
public abstract class HashemUnboxNode extends HashemExpressionNode {

    static final int LIMIT = 5;

    @Specialization
    protected static String fromString(String value) {
        return value;
    }

    @Specialization
    protected static boolean fromBoolean(boolean value) {
        return value;
    }

    @Specialization
    protected static long fromLong(long value) {
        return value;
    }

    @Specialization
    protected static HashemBigNumber fromBigNumber(HashemBigNumber value) {
        return value;
    }

    @Specialization
    protected static HashemBebin fromFunction(HashemBebin value) {
        return value;
    }

    @Specialization
    protected static HashemPooch fromFunction(HashemPooch value) {
        return value;
    }

    @Specialization(limit = "LIMIT")
    public static Object fromForeign(Object value, @CachedLibrary("value") InteropLibrary interop) {
        try {
            if (interop.fitsInLong(value)) {
                return interop.asLong(value);
            } else if (interop.fitsInDouble(value)) {
                return (long) interop.asDouble(value);
            } else if (interop.isString(value)) {
                return interop.asString(value);
            } else if (interop.isBoolean(value)) {
                return interop.asBoolean(value);
            } else {
                return value;
            }
        } catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreter();
            throw new AssertionError();
        }
    }

}
