package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class HashemWebServerTest {

    private Context context;
    private Value factorial;

    @Before
    public void initEngine() throws Exception {
        context = Context.newBuilder().allowAllAccess(true).build().create();

        // @formatter:off
        context.eval("hashemi",
                "bebin server() {\n" +
                        "  server1 = webserver(9091);" +
//                        "  server2 = webserver(9092);" +
                        "  start(server1);" +
//                        "  start(server2);" +
                        "  addHandler(server1,x);" +
                        "  bekhoon();" +
                        "}\n" +
                        "bebin x() {" +
                        "bechap(called);" +
                        "}"
        );
        // @formatter:on

        factorial = context.getBindings("hashemi").getMember("server");
    }

    @After
    public void dispose() {
        context.close();
    }

    @Test
    public void factorialOf5() throws Exception {
        Value value = factorial.execute();
        System.out.println(value);
    }

    @Test
    public void factorialOf3() throws Exception {
        context = Context.newBuilder().allowAllAccess(true).build().create();

        // @formatter:off
        context.eval("hashemi",
                "bebin server() {\n" +
                        "  server1 = webserver(9091);" +
//                        "  server2 = webserver(9092);" +
                        "  start(server1);" +
//                        "  start(server2);" +
                        "  addHandler(server1,x);" +
                        "  bekhoon();" +
                        "}\n" +
                        "bebin x() {" +
                        "bechap(called);" +
                        "}"
        );
        // @formatter:on
        factorial.execute();

    }

    @Test
    public void factorialOf1() throws Exception {
        Number ret = factorial.execute(1).as(Number.class);
        assertEquals(1, ret.intValue());
    }
}
