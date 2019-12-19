package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;

/**
 * Built-in function that queries if the foreign object is executable. See
 * <link>Messages.IS_EXECUTABLE</link>.
 */
@NodeInfo(shortName = "isExecutable")
public abstract class HashemIsExecutableBuiltin extends HashemBuiltinNode {

    @Specialization(limit = "3")
    public boolean isExecutable(Object obj, @CachedLibrary("obj") InteropLibrary executables) {
        return executables.isExecutable(obj);
    }
}
