
package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyInstantiable;
import org.graalvm.polyglot.proxy.ProxyObject;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class HashemInteropObjectTest {
    private Context context;

    @Before
    public void setUp() {
        context = Context.create("hashemi");
    }

    @After
    public void tearDown() {
        context.close();
        context = null;
    }

    @Test
    public void testObject() {
        final Source src = Source.newBuilder("hashemi", "bebin azinja() {o = jadid(); o.a = 10; o.b = \"B\"; bede o;}", "testObject.hashem").buildLiteral();
        final Value obj = context.eval(src);
        Assert.assertTrue(obj.hasMembers());

        Value a = obj.getMember("a");
        Assert.assertNotNull(a);
        Assert.assertTrue(a.isNumber());
        Assert.assertEquals(10, a.asInt());

        Value b = obj.getMember("b");
        Assert.assertNotNull(b);
        Assert.assertTrue(b.isString());
        Assert.assertEquals("B", b.asString());

        obj.putMember("a", b);
        a = obj.getMember("a");
        Assert.assertTrue(a.isString());
        Assert.assertEquals("B", a.asString());

        obj.removeMember("a");
        Assert.assertFalse(obj.hasMember("a"));

        Assert.assertEquals("[b]", obj.getMemberKeys().toString());
    }

    @Test
    public void testJadidForeign() {
        final Source src = Source.newBuilder("hashemi", "bebin getValue(type) {o = jadid(type); o.a = 10; bede o.value;}", "testObject.hashem").buildLiteral();
        context.eval(src);
        Value getValue = context.getBindings("hashemi").getMember("getValue");
        Value ret = getValue.execute(new TestType());
        Assert.assertEquals(20, ret.asLong());
    }

    private static class TestType implements ProxyInstantiable {
        @Override
        public Object newInstance(Value... arguments) {
            return new TestObject();
        }

    }

    private static class TestObject implements ProxyObject {

        private long value;

        @Override
        public Object getMember(String key) {
            if ("value".equals(key)) {
                return 2 * value;
            }
            return 0;
        }

        @Override
        public Object getMemberKeys() {
            return new String[]{"a", "value"};
        }

        @Override
        public boolean hasMember(String key) {
            switch (key) {
                case "a":
                case "value":
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void putMember(String key, Value v) {
            value += v.asLong();
        }

    }
}
