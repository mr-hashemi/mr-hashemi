package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.DynamicObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemBebin;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemWebServer;

import java.io.IOException;

@NodeInfo(shortName = "addHandler")
public abstract class HashemAddHandlerBuiltin extends HashemBuiltinNode {
    @Specialization
    public String addHandler(HashemWebServer server,
                             HashemBebin source,
                             @CachedContext(HashemLanguage.class) HashemContext context,
                             @Cached() IndirectCallNode callNode) {
        return doAddHandler(server, source, context, callNode);
    }

    @TruffleBoundary
    public String doAddHandler(HashemWebServer server, HashemBebin source, HashemContext context, IndirectCallNode callNode) {
        server.getValue().createContext("/", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                try {
                    DynamicObject answer = (DynamicObject) callNode.call(source.getCallTarget());
                    int rCode = ((Long) answer.get("status", 200)).intValue();
                    String body = (String) answer.get("body", "");
                    byte[] response = body.getBytes();
                    exchange.sendResponseHeaders(rCode, response.length);
                    exchange.getResponseBody().write(response);
                    exchange.getResponseBody().close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        return "";
    }
}
