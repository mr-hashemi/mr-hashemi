
package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotException;
import org.graalvm.polyglot.Source;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemParseErrorTest {
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
    public void testParseError() {
        try {
            final Source src = Source.newBuilder("hashemi", "bebin testSyntaxError(a) {break;} function main() {return testSyntaxError;}", "testSyntaxError.hashem").buildLiteral();
            context.eval(src);
            Assert.assertTrue("Should not reach here.", false);
        } catch (PolyglotException e) {
            Assert.assertTrue("Should be a syntax error.", e.isSyntaxError());
            Assert.assertNotNull("Should have source section.", e.getSourceLocation());
        }
    }

    @Test
    public void testParseErrorEmpty() {
        try {
            final Source src = Source.newBuilder("hashemi", "", "testSyntaxErrorEmpty.hashem").buildLiteral();
            context.eval(src);
            Assert.assertTrue("Should not reach here.", false);
        } catch (PolyglotException e) {
            Assert.assertTrue("Should be a syntax error.", e.isSyntaxError());
            Assert.assertNotNull("Should have source section.", e.getSourceLocation());
        }
    }

    @Test
    public void testParseErrorEOF1() {
        try {
            final Source src = Source.newBuilder("hashemi", "bebin azinja", "testSyntaxErrorEOF1.hashem").buildLiteral();
            context.eval(src);
            Assert.assertTrue("Should not reach here.", false);
        } catch (PolyglotException e) {
            Assert.assertTrue("Should be a syntax error.", e.isSyntaxError());
            Assert.assertNotNull("Should have source section.", e.getSourceLocation());
        }
    }

    @Test
    public void testParseErrorEOF2() {
        try {
            final Source src = Source.newBuilder("hashemi", "function\n", "testSyntaxErrorEOF2.hashem").buildLiteral();
            context.eval(src);
            Assert.assertTrue("Should not reach here.", false);
        } catch (PolyglotException e) {
            Assert.assertTrue("Should be a syntax error.", e.isSyntaxError());
            Assert.assertNotNull("Should have source section.", e.getSourceLocation());
        }
    }
}
