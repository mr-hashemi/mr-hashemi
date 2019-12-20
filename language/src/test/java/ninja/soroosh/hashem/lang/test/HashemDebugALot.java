
package ninja.soroosh.hashem.lang.test;

import java.io.ByteArrayOutputStream;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Source;
import org.junit.Assert;
import org.junit.Test;

/**
 * Basic test of debug-a-lot instrument applied to simple language.
 */
public class HashemDebugALot {

    private final Source slCode = Source.create("hashemi", "bebin main() {\n" +
                    "  n = 2;\n" +
                    "  return types(n);\n" +
                    "}\n" +
                    "bebin doNull() {}\n" +
                    "bebin compute(n, l) {\n" +
                    "  z = new();\n" +
                    "  z.a = n + l;\n" +
                    "  z.b = z;\n" +
                    "  z.c = n - l;\n" +
                    "  return z;\n" +
                    "}\n" +
                    "bebin types(n) {\n" +
                    "  a = 1;\n" +
                    "  b = n + a;\n" +
                    "  c = \"string\";\n" +
                    "  d = doNull();\n" +
                    "  e = 10 == 10;\n" +
                    "  f = new();\n" +
                    "  f.p1 = 1;\n" +
                    "  f.p2 = new();\n" +
                    "  f.p2.p21 = 21;\n" +
                    "  g = doNull;\n" +
                    "  i = 0;\n" +
                    "  while (i < n) {\n" +
                    "    b = b * i;\n" +
                    "    l = b + i;\n" +
                    "    z = compute(n, l);\n" +
                    "    a = a + z.a;\n" +
                    "    i = i + 1;\n" +
                    "  }\n" +
                    "  return n * a;\n" +
                    "}\n");

    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    private final ByteArrayOutputStream err = new ByteArrayOutputStream();

    @Test
    public void test() {
        try (Engine engine = Engine.newBuilder().out(out).err(err).allowExperimentalOptions(true).option("debugalot", "true").build()) {
            try (Context context = Context.newBuilder().engine(engine).build()) {
                context.eval(slCode);
            }
        }
        String log = out.toString();
        String successMessage = "Executed successfully:";
        int index = log.lastIndexOf(successMessage);
        Assert.assertTrue(log, index > 0);
        String success = log.substring(index + successMessage.length()).trim();
        Assert.assertEquals(log, "TRUE", success);
    }
}
