package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function that queries if the foreign object has a size. See
 * <link>Messages.HAS_SIZE</link>.
 */
@NodeInfo(shortName = "hasSize")
public abstract class HashemHasSizeBuiltin extends HashemBuiltinNode {

    @Specialization(limit = "3")
    public boolean hasSize(Object obj, @CachedLibrary("obj") InteropLibrary arrays) {
        return arrays.hasArrayElements(obj);
    }
}
