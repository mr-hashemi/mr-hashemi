
package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HashemJavaInteropTest {

    private Context context;
    private ByteArrayOutputStream os;

    @Before
    public void create() {
        os = new ByteArrayOutputStream();
        context = Context.newBuilder().allowHostAccess(HostAccess.ALL).out(os).build();
    }

    @After
    public void dispose() {
        context.close();
    }

    @Test
    public void asFunction() throws Exception {
        String scriptText = "bebin test() {\n" + "    bechap(\"Called!\");\n" + "}\n";
        context.eval("hashemi", scriptText);
        Value main = lookup("test");
        Runnable runnable = main.as(Runnable.class);
        runnable.run();

        assertEquals("Called!\n", toUnixString(os));
    }

    private Value lookup(String symbol) {
        return context.getBindings("hashemi").getMember(symbol);
    }

    @Test
    public void asFunctionWithArg() throws Exception {
        String scriptText = "bebin values(a, b) {\n" + //
                        "  bechap(\"Called with \" + a + \" and \" + b);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");
        PassInValues valuesIn = fn.as(PassInValues.class);
        valuesIn.call("OK", "Fine");

        assertEquals("Called with OK and Fine\n", toUnixString(os));
    }

    private static void assertNumber(double exp, Object real) {
        if (real instanceof Number) {
            assertEquals(exp, ((Number) real).doubleValue(), 0.1);
        } else {
            fail("Expecting a number, but was " + real);
        }
    }

    @FunctionalInterface
    public interface PassInValues {
        void call(Object a, Object b);
    }

    @Test
    public void asFunctionWithArr() throws Exception {
        String scriptText = "bebin values(a, b) {\n" + //
                        "  bechap(\"Called with \" + a[0] + a[1] + \" and \" + b);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");
        PassInArray valuesIn = fn.as(PassInArray.class);
        valuesIn.call(new Object[]{"OK", "Fine"});
        assertEquals("Called with OKFine and POOCH\n", toUnixString(os));
    }

    @Test
    public void asFunctionWithVarArgs() throws Exception {
        String scriptText = "bebin values(a, b) {\n" + //
                        "  bechap(\"Called with \" + a + \" and \" + b);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");
        PassInVarArg valuesIn = fn.as(PassInVarArg.class);

        valuesIn.call("OK", "Fine");
        assertEquals("Called with OK and Fine\n", toUnixString(os));
    }

    @Test
    public void asFunctionWithArgVarArgs() throws Exception {
        String scriptText = "bebin values(a, b, c) {\n" + //
                        "  bechap(\"Called with \" + a + \" and \" + b + c);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");
        PassInArgAndVarArg valuesIn = fn.as(PassInArgAndVarArg.class);

        valuesIn.call("OK", "Fine", "Well");
        assertEquals("Called with OK and FineWell\n", toUnixString(os));
    }

    @Test
    public void sumPairs() {
        String scriptText = "bebin values(sum, k, v) {\n" + //
                        "  obj = jadid();\n" + //
                        "  obj.key = k;\n" + //
                        "  obj.value = v;\n" + //
                        "  bede sum.sum(obj);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");

        Sum javaSum = new Sum();
        Object sum = javaSum;
        Object ret1 = fn.execute(sum, "one", 1).asHostObject();
        Object ret2 = fn.execute(sum, "two", 2).as(Object.class);
        Sum ret3 = fn.execute(sum, "three", 3).as(Sum.class);

        assertEquals(6, javaSum.sum);
        assertSame(ret1, ret2);
        assertSame(ret3, ret2);
        assertSame(sum, ret2);
    }

    @Test
    public void sumPairsFunctionalInterface() {
        String scriptText = "bebin values(sum, k, v) {\n" + //
                        "  obj = jadid();\n" + //
                        "  obj.key = k;\n" + //
                        "  obj.value = v;\n" + //
                        "  bede sum.sum(obj);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Values fn = lookup("values").as(Values.class);

        Sum sum = new Sum();
        Object ret1 = fn.values(sum, "one", 1);
        Object ret2 = fn.values(sum, "two", 2);
        Object ret3 = fn.values(sum, "three", 3);

        assertEquals(6, sum.sum);
        assertSame(ret1, ret2);
        assertSame(ret3, ret2);
        assertSame(sum, ret2);
    }

    @Test
    public void sumPairsFunctionalRawInterface() {
        String scriptText = "bebin values(sum, k, v) {\n" + //
                        "  obj = jadid();\n" + //
                        "  obj.key = k;\n" + //
                        "  obj.value = v;\n" + //
                        "  bede sum.sum(obj);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        ValuesRaw fn = lookup("values").as(ValuesRaw.class);

        Sum sum = new Sum();
        Object ret1 = fn.values(sum, "one", 1);
        Object ret2 = fn.values(sum, "two", 2);
        Object ret3 = fn.values(sum, "three", 3);

        assertEquals(6, sum.sum);
        assertSame(ret1, ret2);
        assertSame(ret3, ret2);
        assertSame(sum, ret2);
    }

    @Test
    public void sumPairsIndirect() {
        String scriptText = "bebin values(sum, k, v) {\n" + //
                        "  obj = jadid();\n" + //
                        "  obj.key = k;\n" + //
                        "  obj.value = v;\n" + //
                        "  bede sum.sum(obj);\n" + //
                        "}\n" + //
                        "bebin create() {\n" + //
                        "  obj = jadid();\n" + //
                        "  obj.doSum1 = values;\n" + //
                        "  obj.doSum2 = values;\n" + //
                        "  bede obj;\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        DoSums fn = lookup("create").execute().as(DoSums.class);

        Sum sum = new Sum();
        Object ret1 = fn.doSum1(sum, "one", 1);
        Sum ret2 = fn.doSum2(sum, "two", 2);
        Object ret3 = fn.doSum1(sum, "three", 3);

        assertEquals(6, sum.sum);
        assertSame(ret1, ret2);
        assertSame(ret3, ret2);
        assertSame(sum, ret2);
    }

    @Test
    public void sumPairsInArray() {
        String scriptText = "bebin values(sum, arr) {\n" + //
                        "  sum.sumArray(arr);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");

        Sum javaSum = new Sum();

        PairImpl[] arr = {
                        new PairImpl("one", 1),
                        new PairImpl("two", 2),
                        new PairImpl("three", 3),
        };
        fn.execute(javaSum, arr);
        assertEquals(6, javaSum.sum);
    }

    @Test
    public void sumPairsInArrayOfArray() {
        String scriptText = "bebin values(sum, arr) {\n" + //
                        "  sum.sumArrayArray(arr);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");

        Sum javaSum = new Sum();

        PairImpl[][] arr = {
                        new PairImpl[]{
                                        new PairImpl("one", 1),
                        },
                        new PairImpl[]{
                                        new PairImpl("two", 2),
                                        new PairImpl("three", 3),
                        }
        };
        fn.execute(javaSum, arr);
        assertEquals(6, javaSum.sum);
    }

    @Test
    public void sumMapInArrayOfArray() {
        String scriptText = "bebin values(sum, arr) {\n" + //
                        "  sum.sumArrayMap(arr);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");

        Sum javaSum = new Sum();

        PairImpl[][] arr = {
                        new PairImpl[]{
                                        new PairImpl("one", 1),
                        },
                        new PairImpl[]{
                                        new PairImpl("two", 2),
                                        new PairImpl("three", 3),
                        }
        };
        fn.execute(javaSum, arr);
        assertEquals(6, javaSum.sum);
    }

    @Test
    public void sumPairInMapOfArray() {
        String scriptText = "bebin values(sum, arr) {\n" + //
                        "  sum.sumMapArray(arr);\n" + //
                        "}\n"; //
        context.eval("hashemi", scriptText);
        Value fn = lookup("values");

        Sum javaSum = new Sum();

        TwoPairsImpl groups = new TwoPairsImpl(
                        new PairImpl[]{
                                        new PairImpl("one", 1),
                        },
                        new PairImpl[]{
                                        new PairImpl("two", 2),
                                        new PairImpl("three", 3),
                        });
        fn.execute(javaSum, groups);
        assertEquals(6, javaSum.sum);
    }

    @Test
    public void accessJavaMap() {
        String scriptText = "bebin write(map, key, value) {\n" +
                        "  map.put(key, value);\n" +
                        "}\n" +
                        "bebin read(map, key) {\n" +
                        "  bede map.get(key);\n" +
                        "}\n";
        context.eval("hashemi", scriptText);
        Value read = lookup("read");
        Value write = lookup("write");

        Map<Object, Object> map = new HashMap<>();
        map.put("a", 42);

        Object b = read.execute(map, "a").as(Object.class);
        assertNumber(42L, b);

        write.execute(map, "a", 33);

        Object c = read.execute(map, "a").as(Object.class);
        assertNumber(33L, c);
    }

    /**
     * Converts a {@link ByteArrayOutputStream} content into UTF-8 String with UNIX line ends.
     */
    static String toUnixString(ByteArrayOutputStream stream) {
        try {
            return stream.toString("UTF-8").replace("\r\n", "\n");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException(e);
        }
    }

    @FunctionalInterface
    public interface Values {
        Sum values(Sum sum, String key, int value);
    }

    @FunctionalInterface
    public interface ValuesRaw {
        Object values(Object sum, String key, int value);
    }

    public interface DoSums {
        Object doSum1(Sum sum, String key, int value);

        Sum doSum2(Sum sum, String key, Integer value);
    }

    @FunctionalInterface
    public interface PassInArray {
        void call(Object[] arr);
    }

    @FunctionalInterface
    public interface PassInVarArg {
        void call(Object... arr);
    }

    @FunctionalInterface
    public interface PassInArgAndVarArg {
        void call(Object first, Object... arr);
    }

    public interface Pair {
        String key();

        int value();
    }

    public static final class PairImpl {
        public final String key;
        public final int value;

        PairImpl(String key, int value) {
            this.key = key;
            this.value = value;
        }
    }

    public static final class TwoPairsImpl {
        public final PairImpl[] one;
        public final PairImpl[] two;

        TwoPairsImpl(PairImpl[] one, PairImpl[] two) {
            this.one = one;
            this.two = two;
        }
    }

    public static class Sum {
        int sum;

        @HostAccess.Export
        public Sum sum(Pair p) {
            sum += p.value();
            return this;
        }

        @HostAccess.Export
        public void sumArray(List<Pair> pairs) {
            Object[] arr = pairs.toArray();
            assertNotNull("Array created", arr);
            for (Pair p : pairs) {
                sum(p);
            }
        }

        @HostAccess.Export
        public void sumArrayArray(List<List<Pair>> pairs) {
            Object[] arr = pairs.toArray();
            assertNotNull("Array created", arr);
            assertEquals("Two lists", 2, arr.length);
            for (List<Pair> list : pairs) {
                sumArray(list);
            }
        }

        @HostAccess.Export
        public void sumArrayMap(List<List<Map<String, Integer>>> pairs) {
            Object[] arr = pairs.toArray();
            assertNotNull("Array created", arr);
            assertEquals("Two lists", 2, arr.length);
            for (List<Map<String, Integer>> list : pairs) {
                for (Map<String, Integer> map : list) {
                    Integer value = map.get("value");
                    sum += value;
                }
            }
        }

        @HostAccess.Export
        public void sumMapArray(Map<String, List<Pair>> pairs) {
            assertEquals("Two elements", 2, pairs.size());
            Object one = pairs.get("one");
            assertNotNull(one);
            Object two = pairs.get("two");
            assertNotNull(two);

            sumArray(pairs.get("two"));
            sumArray(pairs.get("one"));
        }
    }
}
