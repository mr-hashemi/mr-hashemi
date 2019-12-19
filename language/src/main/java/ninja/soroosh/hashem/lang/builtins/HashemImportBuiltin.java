package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemNull;

/**
 * Built-in function that goes through to import a symbol from the polyglot bindings.
 */
@NodeInfo(shortName = "import")
public abstract class HashemImportBuiltin extends HashemBuiltinNode {

    @Specialization
    public Object importSymbol(String symbol,
                    @CachedLibrary(limit = "3") InteropLibrary arrays,
                    @CachedContext(HashemLanguage.class) HashemContext context) {
        try {
            return arrays.readMember(context.getPolyglotBindings(), symbol);
        } catch (UnsupportedMessageException | UnknownIdentifierException e) {
            return HashemNull.SINGLETON;
        } catch (SecurityException e) {
            throw new HashemException("No polyglot access allowed.", this);
        }
    }

}
