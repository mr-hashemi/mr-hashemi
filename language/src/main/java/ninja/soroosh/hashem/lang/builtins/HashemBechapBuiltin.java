package ninja.soroosh.hashem.lang.builtins;

import java.io.PrintWriter;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;

/**
 * Builtin function to write a value to the {@link HashemContext#getOutput() standard output}. The
 * different specialization leverage the typed {@code bechap} methods available in Java, i.e.,
 * primitive values are printed without converting them to a {@link String} first.
 * <p>
 * Printing involves a lot of Java code, so we need to tell the optimizing system that it should not
 * unconditionally inline everything reachable from the bechap() method. This is done via the
 * {@link TruffleBoundary} annotations.
 */
@NodeInfo(shortName = "bechap")
public abstract class HashemBechapBuiltin extends HashemBuiltinNode {

    @Specialization
    public long bechap(long value, @CachedContext(HashemLanguage.class) HashemContext context) {
        doPrint(context.getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, long value) {
        out.println(value);
    }

    @Specialization
    public boolean bechap(boolean value, @CachedContext(HashemLanguage.class) HashemContext context) {
        doPrint(context.getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, boolean value) {
        out.println(value);
    }

    @Specialization
    public String bechap(String value, @CachedContext(HashemLanguage.class) HashemContext context) {
        doPrint(context.getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, String value) {
        out.println(value);
    }

    @Specialization
    public Object bechap(Object value, @CachedContext(HashemLanguage.class) HashemContext context) {
        doPrint(context.getOutput(), value);
        return value;
    }

    @TruffleBoundary
    private static void doPrint(PrintWriter out, Object value) {
        out.println(value);
    }
}
