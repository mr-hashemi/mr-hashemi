package ninja.soroosh.hashem.lang.builtins;

import java.io.BufferedReader;
import java.io.IOException;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.runtime.HashemContext;

/**
 * Builtin function that reads a String from the {@link HashemContext#getInput() standard input}.
 */
@NodeInfo(shortName = "bekhoon")
public abstract class HashemBekhoonBuiltin extends HashemBuiltinNode {

    @Specialization
    public String bekhoon(@CachedContext(HashemLanguage.class) HashemContext context) {
        String result = doRead(context.getInput());
        if (result == null) {
            /*
             * We do not have a sophisticated end of file handling, so returning an empty string is
             * a reasonable alternative. Note that the Java null value should never be used, since
             * it can interfere with the specialization logic in generated source code.
             */
            result = "";
        }
        return result;
    }

    @TruffleBoundary
    private String doRead(BufferedReader in) {
        try {
            return in.readLine();
        } catch (IOException ex) {
            throw new HashemException(ex.getMessage(), this);
        }
    }
}
