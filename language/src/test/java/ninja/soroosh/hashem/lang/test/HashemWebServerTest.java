package ninja.soroosh.hashem.lang.test;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.IntNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;


//@Ignore
public class HashemWebServerTest {

    private Context context;
    private Value server;

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

        server = context.getBindings("hashemi").getMember("server");
    }

    @After
    public void dispose() {
        context.close();
    }

//    @Test
//    public void factorialOf5() throws Exception {
//        Value value = server.execute();
//        System.out.println(value);
//        context.close();
//    }

    @Test
    public void factorialOf3() throws Exception {
        context = Context.newBuilder().allowCreateThread(true).allowAllAccess(true).out(System.out).build().create();
        ObjectMapper mapper = new ObjectMapper();
        final HashMap jsonNode = mapper.readValue("{\"person\":{\"name\":\"soroosh\"}}", JsonMap.class);


        // @formatter:off
        context.eval("hashemi",
                "bebin server(request) {\n" +
                        "  bechap(\"req is \"+ request.get(\"person\"));" +
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

        server = context.getBindings("hashemi").getMember("server");
        System.out.println(server.execute(jsonNode));

        context.close();
    }

    //    @Test
//    public void factorialOf1() throws Exception {
//        Number ret = server.execute(1).as(Number.class);
//        context.close();
//    }
//implements ProxyObject
    public static class HttpReq {
        @HostAccess.Export
        public String getMethod() {
            return "POST";
        }

        @HostAccess.Export
        public String method = "POST";
    }

    public static class JsonMapDeserializer extends StdDeserializer<JsonMap> {

        public JsonMapDeserializer() {
            this(null);
        }

        public JsonMapDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public JsonMap deserialize(JsonParser jp, DeserializationContext ctxt)
                throws IOException, JsonProcessingException {
            JsonNode node = jp.getCodec().readTree(jp);
            if (node instanceof ObjectNode) {
                ctxt.des
                ((ObjectNode) node).elements();

            }


            return new JsonMap();
        }
    }

    @JsonDeserialize(using = JsonMapDeserializer.class)
    public static class JsonMap<K, V> extends HashMap<K, V> {
        @HostAccess.Export
        @Override
        public V get(Object key) {
            return super.get(key);
        }
    }


}

