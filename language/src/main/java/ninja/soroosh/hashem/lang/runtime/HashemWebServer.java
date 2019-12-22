
package ninja.soroosh.hashem.lang.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.sun.net.httpserver.HttpServer;
import org.graalvm.polyglot.Value;
import org.graalvm.polyglot.proxy.ProxyObject;

import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
public final class HashemWebServer implements TruffleObject, ProxyObject {
    private final HttpServer server;

    public HashemWebServer(HttpServer server) {
        this.server = server;
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
