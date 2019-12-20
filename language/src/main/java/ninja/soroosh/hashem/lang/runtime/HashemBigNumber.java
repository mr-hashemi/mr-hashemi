
package ninja.soroosh.hashem.lang.runtime;

import java.math.BigInteger;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

@ExportLibrary(InteropLibrary.class)
public final class HashemBigNumber implements TruffleObject, Comparable<HashemBigNumber> {

    private static final long LONG_MAX_SAFE_DOUBLE = 9007199254740991L; // 2 ** 53 - 1
    private static final int INT_MAX_SAFE_FLOAT = 16777215; // 2 ** 24 - 1

    private static boolean inSafeDoubleRange(long l) {
        return l >= -LONG_MAX_SAFE_DOUBLE && l <= LONG_MAX_SAFE_DOUBLE;
    }

    private static boolean inSafeFloatRange(int i) {
        return i >= -INT_MAX_SAFE_FLOAT && i <= INT_MAX_SAFE_FLOAT;
    }

    private final BigInteger value;

    public HashemBigNumber(BigInteger value) {
        this.value = value;
    }

    public HashemBigNumber(long value) {
        this.value = BigInteger.valueOf(value);
    }

    public BigInteger getValue() {
        return value;
    }

    @TruffleBoundary
    public int compareTo(HashemBigNumber o) {
        return value.compareTo(o.getValue());
    }

    @Override
    @TruffleBoundary
    public String toString() {
        return value.toString();
    }

    @Override
    @TruffleBoundary
    public boolean equals(Object obj) {
        if (obj instanceof HashemBigNumber) {
            return value.equals(((HashemBigNumber) obj).getValue());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

    @SuppressWarnings("static-method")
    @ExportMessage
    boolean isNumber() {
        return fitsInLong();
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInByte() {
        return value.bitLength() < 8;
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInShort() {
        return value.bitLength() < 16;
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInFloat() {
        return fitsInInt() && inSafeFloatRange(value.intValue());
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInLong() {
        return value.bitLength() < 64;
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInInt() {
        return value.bitLength() < 32;
    }

    @ExportMessage
    @TruffleBoundary
    boolean fitsInDouble() {
        return fitsInLong() && inSafeDoubleRange(value.longValue());
    }

    @ExportMessage
    @TruffleBoundary
    double asDouble() throws UnsupportedMessageException {
        if (fitsInDouble()) {
            return value.doubleValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @TruffleBoundary
    long asLong() throws UnsupportedMessageException {
        if (fitsInLong()) {
            return value.longValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @TruffleBoundary
    byte asByte() throws UnsupportedMessageException {
        if (fitsInByte()) {
            return value.byteValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @TruffleBoundary
    int asInt() throws UnsupportedMessageException {
        if (fitsInInt()) {
            return value.intValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @TruffleBoundary
    float asFloat() throws UnsupportedMessageException {
        if (fitsInFloat()) {
            return value.floatValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

    @ExportMessage
    @TruffleBoundary
    short asShort() throws UnsupportedMessageException {
        if (fitsInShort()) {
            return value.shortValue();
        } else {
            throw UnsupportedMessageException.create();
        }
    }

}
