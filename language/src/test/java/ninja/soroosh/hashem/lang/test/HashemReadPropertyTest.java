package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertNull;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemReadPropertyTest {

    private Context ctx;
    private Value hashemObject;

    @Before
    public void setUp() {
        this.ctx = Context.create("hashemi");
        this.hashemObject = ctx.eval("hashemi", "bebin createObject() {\n" +
                        "obj1 = jadid();\n" +
                        "obj1.number = 42;\n" +
                        "bede obj1;\n" +
                        "}\n" +
                        "bebin azinja() {\n" +
                        "bede createObject;\n" +
                        "}").execute();
    }

    @After
    public void tearDown() {
        this.ctx.close();
    }

    @Test
    public void testRead() {
        Assert.assertEquals(42, hashemObject.getMember("number").asInt());
        assertNull(hashemObject.getMember("nonexistent"));
    }
}
