
/*
 *
 *  * Copyright (c) 2012, 2018, Oracle and/or its affiliates. All rights reserved.
 *  * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *  *
 *  * The Universal Permissive License (UPL), Version 1.0
 *  *
 *  * Subject to the condition set forth below, permission is hereby granted to any
 *  * person obtaining a copy of this software, associated documentation and/or
 *  * data (collectively the "Software"), free of charge and under any and all
 *  * copyright rights in the Software, and any and all patent rights owned or
 *  * freely licensable by each licensor hereunder covering either (i) the
 *  * unmodified Software as contributed to or provided by such licensor, or (ii)
 *  * the Larger Works (as defined below), to deal in both
 *  *
 *  * (a) the Software, and
 *  *
 *  * (b) any piece of software and/or hardware listed in the lrgrwrks.txt file if
 *  * one is included with the Software each a "Larger Work" to which the Software
 *  * is contributed by such licensors),
 *  *
 *  * without restriction, including without limitation the rights to copy, create
 *  * derivative works of, display, perform, and distribute the Software and make,
 *  * use, sell, offer for sale, import, export, have made, and have sold the
 *  * Software and the Larger Work(s), and to sublicense the foregoing rights on
 *  * either these or other terms.
 *  *
 *  * This license is subject to the following condition:
 *  *
 *  * The above copyright notice and either this complete permission notice or at a
 *  * minimum a reference to the UPL must be included in all copies or substantial
 *  * portions of the Software.
 *  *
 *  * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *  * SOFTWARE.
 *
 */

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
    private final ExecutorService executorService;

    public HashemWebServer(HttpServer server) {
        this.server = server;

        executorService = Executors
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

    @TruffleBoundary
    public void stop() {
        server.stop(5);
        executorService.shutdown();
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
