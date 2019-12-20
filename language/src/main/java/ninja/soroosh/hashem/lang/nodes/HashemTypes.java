package ninja.soroosh.hashem.lang.nodes;

import java.math.BigInteger;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.ImplicitCast;
import com.oracle.truffle.api.dsl.TypeCast;
import com.oracle.truffle.api.dsl.TypeCheck;
import com.oracle.truffle.api.dsl.TypeSystem;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemBigNumber;
import ninja.soroosh.hashem.lang.runtime.HashemPooch;

/**
 * The type system of Hashemi, as explained in {@link HashemLanguage}. Based on the {@link TypeSystem}
 * annotation, the Truffle DSL generates the subclass {@link HashemTypesGen} with type test and type
 * conversion methods for some types. In this class, we only cover types where the automatically
 * generated ones would not be sufficient.
 */
@TypeSystem({long.class, boolean.class})
public abstract class HashemTypes {

    /**
     * Example of a manually specified type check that replaces the automatically generated type
     * check that the Truffle DSL would generate. For {@link HashemPooch}, we do not need an
     * {@code instanceof} check, because we know that there is only a {@link HashemPooch#SINGLETON
     * singleton} instance.
     */
    @TypeCheck(HashemPooch.class)
    public static boolean isPooch(Object value) {
        return value == HashemPooch.SINGLETON;
    }

    /**
     * Example of a manually specified type cast that replaces the automatically generated type cast
     * that the Truffle DSL would generate. For {@link HashemPooch}, we do not need an actual cast,
     * because we know that there is only a {@link HashemPooch#SINGLETON singleton} instance.
     */
    @TypeCast(HashemPooch.class)
    public static HashemPooch asPooch(Object value) {
        assert isPooch(value);
        return HashemPooch.SINGLETON;
    }

    /**
     * Informs the Truffle DSL that a primitive {@code long} value can be used in all
     * specializations where a {@link HashemBigNumber} is expected. This models the semantic of Hashemi: It
     * only has an arbitrary precision Number type (implemented as {@link HashemBigNumber}, and
     * {@code long} is only used as a performance optimization to avoid the costly
     * {@link HashemBigNumber} arithmetic for values that fit into a 64-bit primitive value.
     */
    @ImplicitCast
    @TruffleBoundary
    public static HashemBigNumber castBigNumber(long value) {
        return new HashemBigNumber(BigInteger.valueOf(value));
    }
}
