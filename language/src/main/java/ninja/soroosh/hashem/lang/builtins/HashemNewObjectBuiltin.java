package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemPooch;
import ninja.soroosh.hashem.lang.runtime.HashemUndefinedNameException;

/**
 * Built-in function to create a new object. Objects in Hashemi are simply made up of name/value pairs.
 */
@NodeInfo(shortName = "jadid")
public abstract class HashemNewObjectBuiltin extends HashemBuiltinNode {

    @Specialization
    @SuppressWarnings("unused")
    public Object newObject(HashemPooch o, @CachedContext(HashemLanguage.class) HashemContext context,
                            @Cached("context.getAllocationReporter()") AllocationReporter reporter) {
        return context.createObject(reporter);
    }

    @Specialization(guards = "!values.isNull(obj)", limit = "3")
    public Object newObject(Object obj, @CachedLibrary("obj") InteropLibrary values) {
        try {
            return values.instantiate(obj);
        } catch (UnsupportedTypeException | ArityException | UnsupportedMessageException e) {
            /* Foreign access was not successful. */
            throw HashemUndefinedNameException.undefinedBebin(this, obj);
        }
    }
}
