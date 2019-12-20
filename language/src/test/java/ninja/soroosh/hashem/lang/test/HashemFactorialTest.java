package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertEquals;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class HashemFactorialTest {

    private Context context;
    private Value factorial;

    @Before
    public void initEngine() throws Exception {
        context = Context.create();
        // @formatter:off
        context.eval("hashemi", "\n" +
                "bebin fac(n) {\n" +
                "  age (n <= 1) bood {\n" +
                "    bede 1;\n" +
                "  }\n" +
                "  prev = fac(n - 1);\n" +
                "  bede prev * n;\n" +
                "}\n"
        );
        // @formatter:on
        factorial = context.getBindings("hashemi").getMember("fac");
    }

    @After
    public void dispose() {
        context.close();
    }

    @Test
    public void factorialOf5() throws Exception {
        Number ret = factorial.execute(5).as(Number.class);
        assertEquals(120, ret.intValue());
    }

    @Test
    public void factorialOf3() throws Exception {
        Number ret = factorial.execute(3).as(Number.class);
        assertEquals(6, ret.intValue());
    }

    @Test
    public void factorialOf1() throws Exception {
        Number ret = factorial.execute(1).as(Number.class);
        assertEquals(1, ret.intValue());
    }
}
