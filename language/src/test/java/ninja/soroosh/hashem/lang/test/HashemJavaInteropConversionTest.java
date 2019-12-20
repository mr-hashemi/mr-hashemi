
package ninja.soroosh.hashem.lang.test;

import static org.hamcrest.CoreMatchers.hasItems;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import ninja.soroosh.hashem.lang.HashemLanguage;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.Test;

import org.graalvm.polyglot.HostAccess;

public class HashemJavaInteropConversionTest {
    public static class Validator {
        @HostAccess.Export
        @SuppressWarnings("unchecked")
        public int validateObject(Object value1, Value value2) {
            assertThat(value1, instanceOf(Map.class));
            assertTrue(!((Map<?, ?>) value1).isEmpty());
            assertThat(((Map<String, ?>) value1).keySet(), hasItems("a", "b"));
            assertThat(value2, instanceOf(Value.class));
            assertTrue(value2.hasMembers());
            assertThat(value2.getMemberKeys(), hasItems("a", "b"));
            return 42;
        }

        @HostAccess.Export
        public int validateMap(Map<String, Object> map1, Map<String, Value> map2) {
            assertEquals(2, map1.size());
            assertThat(map1.keySet(), hasItems("a", "b"));
            for (Object value : map1.values()) {
                assertThat(value, instanceOf(Map.class));
            }

            assertEquals(2, map2.size());
            assertThat(map2.keySet(), hasItems("a", "b"));
            for (Object value : map2.values()) {
                assertThat(value, instanceOf(Value.class));
            }
            return 42;
        }

        @HostAccess.Export
        public int validateList(List<Object> list1, List<Value> list2) {
            assertEquals(2, list1.size());
            for (Object value : list1) {
                assertThat(value, instanceOf(Map.class));
            }

            assertEquals(2, list2.size());
            for (Object value : list2) {
                assertThat(value, instanceOf(Value.class));
            }
            return 42;
        }
    }

    @Test
    public void testGR7318Object() throws Exception {
        String sourceText = "bebin test(validator) {\n" +
                "  obj = jadid();\n" +
                "  obj.a = jadid();\n" +
                "  obj.b = jadid();\n" +
                "  bede validator.validateObject(obj, obj);\n" +
                "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            Value res = test.execute(new Validator());
            assertTrue(res.isNumber() && res.asInt() == 42);
        }
    }

    @Test
    public void testGR7318Map() throws Exception {
        String sourceText = "bebin test(validator) {\n" +
                "  obj = jadid();\n" +
                "  obj.a = jadid();\n" +
                "  obj.b = jadid();\n" +
                "  bede validator.validateMap(obj, obj);\n" +
                "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            Value res = test.execute(new Validator());
            assertTrue(res.isNumber() && res.asInt() == 42);
        }
    }

    @Test
    public void testGR7318List() throws Exception {
        String sourceText = "bebin test(validator, array) {\n" +
                "  array[0] = jadid();\n" +
                "  array[1] = jadid();\n" +
                "  bede validator.validateList(array, array);\n" +
                "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).allowHostAccess(HostAccess.ALL).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            Value res = test.execute(new Validator(), new Object[2]);
            assertTrue(res.isNumber() && res.asInt() == 42);
        }
    }
}
