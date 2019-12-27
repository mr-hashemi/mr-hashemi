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
                "bebin main() {\n" +
                        "  bede -1;\n" +
                        "}"
        );
        // @formatter:on

        factorial = context.getBindings("hashemi").getMember("main");
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
