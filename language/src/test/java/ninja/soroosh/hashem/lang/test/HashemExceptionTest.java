package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess.Export;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.PolyglotException.StackFrame;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyExecutable;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemExceptionTest {

    private Context ctx;

    @Before
    public void setUp() {
        this.ctx = Context.create("hashemi");
    }

    @After
    public void tearDown() {
        this.ctx.close();
    }

    @Test
    public void testExceptions() {
        assertException(true, "bebin azinja() { x = 1 / (1 == 1); }", "azinja");
        assertException(true, "bebin foo() { bede 1 / \"foo\"; } bebin azinja() { foo(); }", "foo", "azinja");
        assertException(true, "bebin foo() { bar(); } bebin bar() { bede 1 / \"foo\"; } bebin azinja() { foo(); }", "bar", "foo", "azinja");
        assertException(true, "bebin foo() { bar1(); bar2(); } bebin bar1() { bede 1; } bebin bar2() { bede \"foo\" / 1; } bebin azinja() { foo(); }", "bar2", "foo", "azinja");
    }

    @Test
    public void testNonMain() {
        assertException(false, "bebin foo(z) { x = 1 / (1==1); } bebin azinja() { bede foo; }", "foo");
    }

    @Test
    public void testThroughProxy() {
        assertException(false, "bebin bar() { x = 1 / (1==1); } bebin foo(z) { z(bar); } bebin azinja() { bede foo; }", "bar", null, null, "foo");
    }

    @Test
    public void testHostException() {
        assertHostException("bebin foo(z) { z(1); } bebin azinja() { bede foo; }", null, "foo");
    }

    private void assertException(boolean failImmediately, String source, String... expectedFrames) {
        boolean initialExecute = true;
        try {
            Value value = ctx.eval("hashemi", source);
            initialExecute = false;
            if (failImmediately) {
                Assert.fail("Should not reach here.");
            }
            ProxyExecutable proxy = (args) -> args[0].execute();
            value.execute(proxy);
            Assert.fail("Should not reach here.");
        } catch (PolyglotException e) {
            Assert.assertEquals(failImmediately, initialExecute);
            assertFrames(failImmediately, e, expectedFrames);
        }
    }

    private static void assertFrames(boolean isEval, PolyglotException e, String... expectedFrames) {
        int i = 0;
        boolean firstHostFrame = false;
        // Expected exception
        for (StackFrame frame : e.getPolyglotStackTrace()) {
            if (i < expectedFrames.length && expectedFrames[i] != null) {
                Assert.assertTrue(frame.isGuestFrame());
                Assert.assertEquals("hashemi", frame.getLanguage().getId());
                Assert.assertEquals(expectedFrames[i], frame.getRootName());
                Assert.assertTrue(frame.getSourceLocation() != null);
                firstHostFrame = true;
            } else {
                Assert.assertTrue(frame.isHostFrame());
                if (firstHostFrame) {
                    Assert.assertEquals(isEval ? "org.graalvm.polyglot.Context.eval" : "org.graalvm.polyglot.Value.execute", frame.getRootName());
                    firstHostFrame = false;
                }
            }
            i++;
        }
    }

    private void assertHostException(String source, String... expectedFrames) {
        boolean initialExecute = true;
        RuntimeException[] exception = new RuntimeException[1];
        try {
            Value value = ctx.eval("hashemi", source);
            initialExecute = false;
            ProxyExecutable proxy = (args) -> {
                throw exception[0] = new RuntimeException();
            };
            value.execute(proxy);
            Assert.fail("Should not reach here.");
        } catch (PolyglotException e) {
            Assert.assertFalse(initialExecute);
            Assert.assertTrue(e.asHostException() == exception[0]);
            assertFrames(false, e, expectedFrames);
        }
    }

    @Test
    public void testGuestLanguageError() {
        try {
            String source = "bebin bar() { x = 1 / \"asdf\"; }\n" +
                    "bebin foo() { bede bar(); }\n" +
                    "bebin azinja() { foo(); }";
            ctx.eval(Source.newBuilder("hashemi", source, "script.hashemi").buildLiteral());
            fail();
        } catch (PolyglotException e) {
            assertTrue(e.isGuestException());

            Iterator<StackFrame> frames = e.getPolyglotStackTrace().iterator();
            assertGuestFrame(frames, "hashemi", "bar", "script.hashemi", 18, 28);
            assertGuestFrame(frames, "hashemi", "foo", "script.hashemi", 51, 56);
            assertGuestFrame(frames, "hashemi", "azinja", "script.hashemi", 77, 82);
            assertHostFrame(frames, Context.class.getName(), "eval");
            assertHostFrame(frames, HashemExceptionTest.class.getName(), "testGuestLanguageError");

            // only host frames trailing
            while (frames.hasNext()) {
                assertTrue(frames.next().isHostFrame());
            }
        }
    }

    private static class TestProxy implements ProxyExecutable {
        private int depth;
        private final Value f;
        final List<PolyglotException> seenExceptions = new ArrayList<>();

        RuntimeException thrownException;

        TestProxy(int depth, Value f) {
            this.depth = depth;
            this.f = f;
        }

        public Object execute(Value... t) {
            depth--;
            if (depth > 0) {
                try {
                    return f.execute(this);
                } catch (PolyglotException e) {
                    assertProxyException(this, e);
                    seenExceptions.add(e);
                    throw e;
                }
            } else {
                thrownException = new RuntimeException("Error in proxy test.");
                throw thrownException;
            }
        }
    }

    @Test
    public void testProxyGuestLanguageStack() {
        Value bar = ctx.eval("hashemi", "bebin foo(f) { f(); } bebin bar(f) { bede foo(f); } bebin azinja() { bede bar; }");

        TestProxy proxy = new TestProxy(3, bar);
        try {
            bar.execute(proxy);
            fail();
        } catch (PolyglotException e) {
            assertProxyException(proxy, e);

            for (PolyglotException seenException : proxy.seenExceptions) {
                // exceptions are unwrapped and wrapped again
                assertNotSame(e, seenException);
                assertSame(e.asHostException(), seenException.asHostException());
            }
        }
    }

    private static void assertProxyException(TestProxy proxy, PolyglotException e) {
        assertTrue(e.isHostException());
        if (e.asHostException() instanceof AssertionError) {
            throw (AssertionError) e.asHostException();
        }
        assertSame(proxy.thrownException, e.asHostException());

        Iterator<StackFrame> frames = e.getPolyglotStackTrace().iterator();
        assertHostFrame(frames, TestProxy.class.getName(), "execute");
        for (int i = 0; i < 2; i++) {
            assertGuestFrame(frames, "hashemi", "foo", "Unnamed", 15, 18);
            assertGuestFrame(frames, "hashemi", "bar", "Unnamed", 42, 48);

            assertHostFrame(frames, Value.class.getName(), "execute");
            assertHostFrame(frames, TestProxy.class.getName(), "execute");
        }

        assertGuestFrame(frames, "hashemi", "foo", "Unnamed", 15, 18);
        assertGuestFrame(frames, "hashemi", "bar", "Unnamed", 42, 48);

        assertHostFrame(frames, Value.class.getName(), "execute");
        assertHostFrame(frames, HashemExceptionTest.class.getName(), "testProxyGuestLanguageStack");

        while (frames.hasNext()) {
            // skip unit test frames.
            assertTrue(frames.next().isHostFrame());
        }
    }

    private static void assertHostFrame(Iterator<StackFrame> frames, String className, String methodName) {
        assertTrue(frames.hasNext());
        StackFrame frame = frames.next();
        assertTrue(frame.isHostFrame());
        assertFalse(frame.isGuestFrame());
        assertEquals("host", frame.getLanguage().getId());
        assertEquals("Host", frame.getLanguage().getName());
        assertEquals(className + "." + methodName, frame.getRootName());
        assertNull(frame.getSourceLocation());
        assertNotNull(frame.toString());

        StackTraceElement hostFrame = frame.toHostFrame();
        assertEquals(className, hostFrame.getClassName());
        assertEquals(methodName, hostFrame.getMethodName());
        assertNotNull(hostFrame.toString());
        assertTrue(hostFrame.equals(hostFrame));
        assertNotEquals(0, hostFrame.hashCode());
    }

    private static void assertGuestFrame(Iterator<StackFrame> frames, String languageId, String rootName, String fileName, int charIndex, int endIndex) {
        assertTrue(frames.hasNext());
        StackFrame frame = frames.next();
        assertTrue(frame.toString(), frame.isGuestFrame());
        assertEquals(languageId, frame.getLanguage().getId());
        assertEquals(rootName, frame.getRootName());
        assertNotNull(frame.getSourceLocation());
        assertNotNull(frame.getSourceLocation().getSource());
        assertEquals(fileName, frame.getSourceLocation().getSource().getName());
        assertEquals(charIndex, frame.getSourceLocation().getCharIndex());
        assertEquals(endIndex, frame.getSourceLocation().getCharEndIndex());

        StackTraceElement hostFrame = frame.toHostFrame();
        assertEquals("<" + languageId + ">", hostFrame.getClassName());
        assertEquals(rootName, hostFrame.getMethodName());
        assertEquals(frame.getSourceLocation().getStartLine(), hostFrame.getLineNumber());
        assertEquals(fileName, hostFrame.getFileName());
        assertNotNull(hostFrame.toString());
        assertTrue(hostFrame.equals(hostFrame));
        assertNotEquals(0, hostFrame.hashCode());
    }

    @Export
    public String methodThatTakesFunction(Function<String, String> s) {
        return s.apply("t");
    }

    @Test
    public void testGuestOverHostPropagation() {
        Context context = Context.newBuilder("hashemi").allowAllAccess(true).build();
        String code = "" +
                "bebin other(x) {" +
                "   bede invalidFunction();" +
                "}" +
                "" +
                "bebin f(test) {" +
                "test.methodThatTakesFunction(other);" +
                "}";

        context.eval("hashemi", code);
        try {
            context.getBindings("hashemi").getMember("f").execute(this);
            fail();
        } catch (PolyglotException e) {
            assertFalse(e.isHostException());
            assertTrue(e.isGuestException());
            Iterator<StackFrame> frames = e.getPolyglotStackTrace().iterator();
            assertTrue(frames.next().isGuestFrame());
            assertGuestFrame(frames, "hashemi", "other", "Unnamed", 24, 41);
            assertHostFrame(frames, "com.oracle.truffle.polyglot.PolyglotFunction", "apply");
            assertHostFrame(frames, "ninja.soroosh.hashem.lang.test.HashemExceptionTest", "methodThatTakesFunction");
            assertGuestFrame(frames, "hashemi", "f", "Unnamed", 58, 93);

            // rest is just unit test host frames
            while (frames.hasNext()) {
                assertTrue(frames.next().isHostFrame());
            }
        }
    }

}
