
package ninja.soroosh.hashem.lang.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.sun.net.httpserver.HttpServer;
import ninja.soroosh.hashem.lang.HashemLanguage;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

@ExportLibrary(InteropLibrary.class)
public final class HashemWebServer implements TruffleObject, ProxyObject {
    private final HttpServer server;

    public HashemWebServer(HttpServer server) {
        this.server = server;

        ExecutorService executorService = Executors
                .newFixedThreadPool(1,
                        r -> HashemLanguage.getCurrentContext().getEnv().createThread(r));

        executorService.submit(() -> { //initialize thread pool
                }
        );

        server.setExecutor(executorService);
    }


    public HttpServer getValue() {
        return server;
    }


    @Override
    @TruffleBoundary
    public String toString() {
        return server.toString();
    }

    @Override
    @TruffleBoundary
    public boolean equals(Object obj) {
        return false;
    }

    @Override
    public int hashCode() {
        return server.hashCode();
    }

    @TruffleBoundary
    public void start() {
        server.start();
    }

    @Override
    public Object getMember(String key) {
        Runnable start = server::start;
        if (key.equals("start")) {
            return start;
        }
        return null;
    }

    @Override
    public Object getMemberKeys() {
        return new String[]{"start"};
    }

    @Override
    public boolean hasMember(String key) {
        return false;
    }

    @Override
    public void putMember(String key, Value value) {

    }
}
