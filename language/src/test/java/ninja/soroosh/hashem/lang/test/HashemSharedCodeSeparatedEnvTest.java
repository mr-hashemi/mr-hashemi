
package ninja.soroosh.hashem.lang.test;

import static ninja.soroosh.hashem.lang.test.HashemJavaInteropTest.toUnixString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import ninja.soroosh.hashem.lang.HashemLanguage;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Instrument;
import org.graalvm.polyglot.PolyglotAccess;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.oracle.truffle.api.instrumentation.EventBinding;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;

@Ignore
public class HashemSharedCodeSeparatedEnvTest {

    private ByteArrayOutputStream osRuntime;
    private ByteArrayOutputStream os1;
    private ByteArrayOutputStream os2;
    private Engine engine;
    private Context e1;
    private Context e2;

    @Before
    public void initializeEngines() {
        osRuntime = new ByteArrayOutputStream();
        engine = Engine.newBuilder().out(osRuntime).err(osRuntime).build();

        os1 = new ByteArrayOutputStream();
        os2 = new ByteArrayOutputStream();

        int instances = HashemLanguage.counter;
        // @formatter:off
        e1 = Context.newBuilder("hashemi").engine(engine).out(os1).allowPolyglotAccess(PolyglotAccess.ALL).build();
        e1.getPolyglotBindings().putMember("extra", 1);
        e2 = Context.newBuilder("hashemi").engine(engine).out(os2).allowPolyglotAccess(PolyglotAccess.ALL).build();
        e2.getPolyglotBindings().putMember("extra", 2);
        e1.initialize("hashemi");
        e2.initialize("hashemi");
        assertEquals("One SLLanguage instance created", instances + 1, HashemLanguage.counter);
    }

    @After
    public void closeEngines() {
        engine.close();
    }

    @Test
    public void shareCodeUseDifferentOutputStreams() throws Exception {

        String sayHello =
            "bebin main() {\n" +
            "  bekhoon(\"Ahoj\" + import(\"extra\"));" +
            "}";
        // @formatter:on

        e1.eval("hashemi", sayHello);
        assertEquals("Ahoj1\n", toUnixString(os1));
        assertEquals("", toUnixString(os2));

        e2.eval("hashemi", sayHello);
        assertEquals("Ahoj1\n", toUnixString(os1));
        assertEquals("Ahoj2\n", toUnixString(os2));
    }

    @Test
    public void instrumentsSeeOutputOfBoth() throws Exception {
        Instrument outInstr = e2.getEngine().getInstruments().get("captureOutput");
        ByteArrayOutputStream outConsumer = outInstr.lookup(ByteArrayOutputStream.class);
        assertNotNull("Stream capturing is ready", outConsumer);

        String sayHello = "bebin main() {\n" +
                        "  bekhoon(\"Ahoj\" + import(\"extra\"));" +
                        "}";
        // @formatter:on

        e1.eval("hashemi", sayHello);
        assertEquals("Ahoj1\n", toUnixString(os1));
        assertEquals("", toUnixString(os2));

        e2.eval("hashemi", sayHello);
        assertEquals("Ahoj1\n", toUnixString(os1));
        assertEquals("Ahoj2\n", toUnixString(os2));

        engine.close();

        assertEquals("Output of both contexts and instruments is capturable",
                        "initializingOutputCapture\n" +
                                        "Ahoj1\n" +
                                        "Ahoj2\n" +
                                        "endOfOutputCapture\n",
                        toUnixString(outConsumer));

        assertEquals("Output of instrument goes not to os runtime if specified otherwise",
                        "initializingOutputCapture\n" + "endOfOutputCapture\n",
                        toUnixString(osRuntime));
    }

    @TruffleInstrument.Registration(id = "captureOutput", services = ByteArrayOutputStream.class)
    public static class CaptureOutput extends TruffleInstrument {
        private EventBinding<ByteArrayOutputStream> binding;

        @Override
        protected void onCreate(final TruffleInstrument.Env env) {
            final ByteArrayOutputStream capture = new ByteArrayOutputStream() {
                @Override
                public void write(byte[] b) throws IOException {
                    super.write(b);
                }

                @Override
                public synchronized void write(byte[] b, int off, int len) {
                    super.write(b, off, len);
                }

                @Override
                public synchronized void write(int b) {
                    super.write(b);
                }
            };
            binding = env.getInstrumenter().attachOutConsumer(capture);
            env.registerService(capture);
            try {
                env.out().write("initializingOutputCapture\n".getBytes("UTF-8"));
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
        }

        @Override
        protected void onDispose(Env env) {
            try {
                env.out().write("endOfOutputCapture\n".getBytes("UTF-8"));
            } catch (IOException ex) {
                throw new IllegalStateException(ex);
            }
            binding.dispose();
        }
    }
}
