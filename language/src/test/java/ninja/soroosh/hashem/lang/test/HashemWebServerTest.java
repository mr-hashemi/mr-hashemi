package ninja.soroosh.hashem.lang.test;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class HashemWebServerTest {

    private Context context;
    private Value factorial;

    @Before
    public void initEngine() throws Exception {
        context = Context.newBuilder().allowCreateThread(true).allowAllAccess(true).build();

        // @formatter:off
        context.eval("hashemi",
                "bebin server() {\n" +
                        "  server1 = webserver(9091);" +
                        "  addHandler(server1,x);" +
                        "  start(server1);" +
                        "}\n" +
                        "bebin x() {" +
                        "bechap(called);" +
                        " javab  = jadid(); " +
                        " javab.status = 202;" +
                        " javab.body= \"Dorood Jahan\";" +
                        " bede javab;" +
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
        context.close();
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
                        " start(server1); " +
                        "}\n" +
                        "bebin x() {" +
                        "bechap(called);" +
                        "}"
        );
        // @formatter:on
        factorial.execute();

        context.close();

    }

    @Test
    public void factorialOf1() throws Exception {
        Number ret = factorial.execute(1).as(Number.class);
        context.close();
    }
}
