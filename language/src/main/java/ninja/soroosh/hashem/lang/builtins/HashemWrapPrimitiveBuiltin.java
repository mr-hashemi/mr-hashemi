package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Builtin function to wrap primitive values in order to increase coverage of the Truffle TCK test.
 */
@NodeInfo(shortName = "wrapPrimitive")
@SuppressWarnings("unused")
public abstract class HashemWrapPrimitiveBuiltin extends HashemBuiltinNode {

    @TruffleBoundary
    @Specialization
    public Object doDefault(Object value) {
        if (value instanceof PrimitiveValueWrapper) {
            return value;
        } else {
            return new PrimitiveValueWrapper(value);
        }
    }

    @ExportLibrary(ReflectionLibrary.class)
    static final class PrimitiveValueWrapper implements TruffleObject {

        final Object delegate;

        PrimitiveValueWrapper(Object delegate) {
            this.delegate = delegate;
        }

        @ExportMessage
        Object send(Message message, Object[] args,
                        @CachedLibrary("this.delegate") ReflectionLibrary reflection) throws Exception {
            return reflection.send(this.delegate, message, args);
        }

    }

}
