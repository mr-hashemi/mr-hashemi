
package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemInteropOperatorTest {
    private Context context;

    @Before
    public void setUp() {
        context = Context.create("hashemi");
    }

    @After
    public void tearDown() {
        context = null;
    }

    @Test
    public void testAdd() {
        final Source src = Source.newBuilder("hashemi", "bebin testAdd(a,b) {bede a + b;} bebin azinja() {bede testAdd;}", "testAdd.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        Assert.assertTrue(fnc.canExecute());
        final Value res = fnc.execute(1, 2);
        Assert.assertTrue(res.isNumber());
        Assert.assertEquals(3, res.asInt());
    }

    @Test
    public void testSub() {
        final Source src = Source.newBuilder("hashemi", "bebin testSub(a,b) {bede a - b;} bebin azinja() {bede testSub;}", "testSub.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        final Value res = fnc.execute(1, 2);
        Assert.assertTrue(res.isNumber());
        Assert.assertEquals(-1, res.asInt());
    }
}
