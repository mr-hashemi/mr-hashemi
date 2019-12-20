
package ninja.soroosh.hashem.lang.nodes.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import ninja.soroosh.hashem.lang.nodes.HashemTypes;
import ninja.soroosh.hashem.lang.runtime.HashemBigNumber;

/**
 * The node to normalize any value to anHashemivalue. This is useful to reduce the number of values
 * expression nodes need to expect.
 */
@TypeSystemReference(HashemTypes.class)
@GenerateUncached
public abstract class HashemToMemberNode extends Node {

    static final int LIMIT = 5;

    public abstract String execute(Object value) throws UnknownIdentifierException;

    @Specialization
    protected static String fromString(String value) {
        return value;
    }

    @Specialization
    protected static String fromBoolean(boolean value) {
        return String.valueOf(value);
    }

    @Specialization
    @TruffleBoundary
    protected static String fromLong(long value) {
        return String.valueOf(value);
    }

    @Specialization
    @TruffleBoundary
    protected static String fromBigNumber(HashemBigNumber value) {
        return value.toString();
    }

    @Specialization(limit = "LIMIT")
    protected static String fromInterop(Object value, @CachedLibrary("value") InteropLibrary interop) throws UnknownIdentifierException {
        try {
            if (interop.fitsInLong(value)) {
                return longToString(interop.asLong(value));
            } else if (interop.isString(value)) {
                return interop.asString(value);
            } else if (interop.isNumber(value) && value instanceof HashemBigNumber) {
                return bigNumberToString((HashemBigNumber) value);
            } else {
                throw error(value);
            }
        } catch (UnsupportedMessageException e) {
            CompilerDirectives.transferToInterpreter();
            throw new AssertionError();
        }
    }

    @TruffleBoundary
    private static UnknownIdentifierException error(Object value) {
        return UnknownIdentifierException.create(value.toString());
    }

    @TruffleBoundary
    private static String bigNumberToString(HashemBigNumber value) {
        return value.toString();
    }

    @TruffleBoundary
    private static String longToString(long longValue) {
        return String.valueOf(longValue);
    }

}
