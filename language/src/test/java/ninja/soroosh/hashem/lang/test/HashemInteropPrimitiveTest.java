
package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemInteropPrimitiveTest {
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
    public void testBoolean() {
        final Source src = Source.newBuilder("hashemi", "bebin testBoolean(a,b) {bede a == b;} bebin main() {bede testBoolean;}", "testBoolean.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        Assert.assertTrue(fnc.canExecute());
        fnc.execute(true, false);
    }

    @Test
    public void testChar() {
        final Source src = Source.newBuilder("hashemi", "bebin testChar(a,b) {bede a == b;} bebin main() {bede testChar;}", "testChar.hashem").buildLiteral();
        final Value fnc = context.eval(src);
        Assert.assertTrue(fnc.canExecute());
        fnc.execute('a', 'b');
    }
}
