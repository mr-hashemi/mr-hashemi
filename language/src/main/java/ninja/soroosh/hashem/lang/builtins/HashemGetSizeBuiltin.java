package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemException;

/**
 * Built-in function that queries the size property of a foreign object. See
 * <link>Messages.GET_SIZE</link>.
 */
@NodeInfo(shortName = "getSize")
public abstract class HashemGetSizeBuiltin extends HashemBuiltinNode {

    @Specialization(limit = "3")
    public Object getSize(Object obj, @CachedLibrary("obj") InteropLibrary arrays) {
        try {
            return arrays.getArraySize(obj);
        } catch (UnsupportedMessageException e) {
            throw new HashemException("Element is not a valid array.", this);
        }
    }
}
