
package ninja.soroosh.hashem.lang.test;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import ninja.soroosh.hashem.lang.HashemLanguage;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.Assert;
import org.junit.Test;

import org.graalvm.polyglot.HostAccess;

public class HashemJavaInteropExceptionTest {
    public static class Validator {
        @HostAccess.Export
        public int validateException() {
            throw new NoSuchElementException();
        }

        @HostAccess.Export
        public void validateNested() throws Exception {
            String sourceText = "bebin test(validator) {\n" +
                            "  bede validator.validateException();\n" +
                            "}";
            try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
                context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
                Value test = context.getBindings(HashemLanguage.ID).getMember("test");
                test.execute(Validator.this);
            }
        }

        @HostAccess.Export
        public long validateFunction(Supplier<Long> function) {
            return function.get();
        }

        @HostAccess.Export
        public void validateMap(Map<String, Object> map) {
            Assert.assertNull(map.get(null));
        }
    }

    @Test
    public void testGR7284() throws Exception {
        String sourceText = "bebin test(validator) {\n" +
                        "  bede validator.validateException();\n" +
                        "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            try {
                test.execute(new Validator());
                fail("expected a PolyglotException but did not throw");
            } catch (PolyglotException ex) {
                assertTrue("expected HostException", ex.isHostException());
                assertThat(ex.asHostException(), instanceOf(NoSuchElementException.class));
                assertNoJavaInteropStackFrames(ex);
            }
        }
    }

    @Test
    public void testGR7284GuestHostGuestHost() throws Exception {
        String sourceText = "bebin test(validator) {\n" +
                        "  bede validator.validateNested();\n" +
                        "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            try {
                test.execute(new Validator());
                fail("expected a PolyglotException but did not throw");
            } catch (PolyglotException ex) {
                assertTrue("expected HostException", ex.isHostException());
                assertThat(ex.asHostException(), instanceOf(NoSuchElementException.class));
                assertNoJavaInteropStackFrames(ex);
            }
        }
    }

    private static void assertNoJavaInteropStackFrames(PolyglotException ex) {
        String javaInteropPackageName = "com.oracle.truffle.api.interop.java";
        assertFalse("expected no java interop stack trace elements", Arrays.stream(ex.getStackTrace()).anyMatch(ste -> ste.getClassName().startsWith(javaInteropPackageName)));
    }

    @Test
    public void testFunctionProxy() throws Exception {
        String javaMethod = "validateFunction";
        String sourceText = "" +
                        "bebin supplier() {\n" +
                        "  bede error();\n" +
                        "}\n" +
                        "bebin test(validator) {\n" +
                        "  bede validator." + javaMethod + "(supplier);\n" +
                        "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            try {
                test.execute(new Validator());
                fail("expected a PolyglotException but did not throw");
            } catch (PolyglotException ex) {
                StackTraceElement last = null;
                boolean found = false;
                for (StackTraceElement curr : ex.getStackTrace()) {
                    if (curr.getMethodName().contains(javaMethod)) {
                        assertNotNull(last);
                        assertThat("expected Proxy stack frame", last.getClassName(), containsString("Proxy"));
                        found = true;
                        break;
                    }
                    last = curr;
                }
                assertTrue(javaMethod + " not found in stack trace", found);
            }
        }
    }

    @Test
    public void testTruffleMap() throws Exception {
        String javaMethod = "validateMap";
        String sourceText = "" +
                        "bebin test(validator) {\n" +
                        "  bede validator." + javaMethod + "(jadid());\n" +
                        "}";
        try (Context context = Context.newBuilder(HashemLanguage.ID).build()) {
            context.eval(Source.newBuilder(HashemLanguage.ID, sourceText, "Test").build());
            Value test = context.getBindings(HashemLanguage.ID).getMember("test");
            test.execute(new Validator());
        }
    }
}
