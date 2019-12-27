
package ninja.soroosh.hashem.lang.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.Value;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

public class PassItselfBackViaContextTest {
    private Context context;
    private MyObj myObj;
    private Value myObjWrapped;
    private CallWithValue myObjCall;

    @Test
    public void callbackWithParamTen() {
        myObjWrapped.execute(10);
        assertEquals("Assigned to ten", 10, myObj.value);
    }

    @Test
    public void callbackWithParamTruffleObject() {
        myObjWrapped.execute(myObjWrapped);
        assertEquals("Assigned to itself", myObj, myObj.value);
    }

    @Test
    public void callbackWithValueTen() {
        myObjCall.call(10);
        assertEquals("Assigned to ten", 10, myObj.value);
    }

    @Test
    public void callbackWithValueTruffleObject() {
        myObjCall.call(myObjWrapped);
        assertEquals("Assigned to itself", myObj, myObj.value);
    }

    @Before
    public void prepareSystem() {
        myObj = new MyObj();
        context = Context.newBuilder().allowPolyglotAccess(PolyglotAccess.ALL).build();
        context.getPolyglotBindings().putMember("myObj", myObj);
        context.eval("hashemi", "bebin azinja() {\n" + "  bede import(\"myObj\");\n" + "}\n");
        myObjWrapped = context.getBindings("hashemi").getMember("azinja").execute();
        assertFalse(myObjWrapped.isNull());
        myObjCall = myObjWrapped.as(CallWithValue.class);
    }

    @After
    public void disposeSystem() {
        context.close();
    }

    @ExportLibrary(InteropLibrary.class)
    static final class MyObj implements TruffleObject {
        private Object value;

        @ExportMessage
        Object execute(Object[] arguments) {
            value = arguments[0];
            return "";
        }

        @SuppressWarnings("static-method")
        @ExportMessage
        boolean isExecutable() {
            return true;
        }

    }

    abstract static class MyLang extends TruffleLanguage<Object> {
    }

    @FunctionalInterface
    interface CallWithValue {
        void call(Object value);
    }
}
