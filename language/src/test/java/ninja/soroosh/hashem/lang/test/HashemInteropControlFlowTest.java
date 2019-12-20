
package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemInteropControlFlowTest {
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
    public void testWhile() {
        final Source src = Source.newBuilder("hashemi", "bebin testTa(a) {ta(a) bood {khob;}} bebin main() {bede testTa;}", "testTa.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        Assert.assertTrue(fnc.canExecute());
        fnc.execute(false);
    }

    @Test
    public void testIf() {
        final Source src = Source.newBuilder("hashemi", "bebin testIf(a) {age (a) bood {bede 1;} na? {bede 0;}} bebin main() {bede testIf;}", "testIf.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        fnc.execute(false);
    }
}
