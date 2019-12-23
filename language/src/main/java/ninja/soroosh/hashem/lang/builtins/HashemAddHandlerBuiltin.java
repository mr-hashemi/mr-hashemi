package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemBebin;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemWebServer;

import java.io.IOException;
import java.net.InetSocketAddress;

/**
 * Builtin function to write a value to the {@link HashemContext#getOutput() standard output}. The
 * different specialization leverage the typed {@code bechap} methods available in Java, i.e.,
 * primitive values are printed without converting them to a {@link String} first.
 * <p>
 * Printing involves a lot of Java code, so we need to tell the optimizing system that it should not
 * unconditionally inline everything reachable from the bechap() method. This is done via the
 * {@link TruffleBoundary} annotations.
 */
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
                    System.out.println(source);
                    callNode.call(source.getCallTarget());
                    exchange.sendResponseHeaders(200, 0);
                    exchange.getResponseBody().close();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
            }
        });
        return "";
    }
}
