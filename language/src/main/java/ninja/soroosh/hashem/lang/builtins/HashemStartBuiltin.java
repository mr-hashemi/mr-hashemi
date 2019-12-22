package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.sun.net.httpserver.HttpServer;
import ninja.soroosh.hashem.lang.HashemLanguage;
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
@NodeInfo(shortName = "start")
public abstract class HashemStartBuiltin extends HashemBuiltinNode {
    @Specialization
    public String start(HashemWebServer server, @CachedContext(HashemLanguage.class) HashemContext context) {
        server.start();
        return "";
    }
//
//    @TruffleBoundary
//    private HashemWebServer doBuildServer(long port) {
//        InetSocketAddress address = new InetSocketAddress((int) port);
//        HttpServer server = null;
//        try {
//            server = HttpServer.create(address, 0);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        return new HashemWebServer(server);
//    }
}
