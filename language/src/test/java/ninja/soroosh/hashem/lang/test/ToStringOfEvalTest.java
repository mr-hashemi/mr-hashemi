package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ToStringOfEvalTest {
    Context context;

    @Before
    public void initialize() {
        context = Context.create();
    }

    @After
    public void dispose() {
        context.close();
    }

    @Test
    public void checkToStringOnAFunction() {
        context.eval("hashemi", "bebin checkName() {}");
        Value value1 = context.getBindings("hashemi").getMember("checkName");
        Value value2 = context.getBindings("hashemi").getMember("checkName");

        assertNotNull("Symbol is not null", value1);
        assertNotNull("Symbol is not null either", value2);

        assertFalse("Symbol is not null", value1.isNull());
        assertFalse("Symbol is not null either", value2.isNull());

        assertTrue("Contans checkName text: " + value2, value2.toString().contains("checkName"));
    }
}
