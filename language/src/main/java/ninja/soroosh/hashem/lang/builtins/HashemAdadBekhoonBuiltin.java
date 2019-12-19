package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemException;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * Builtin function that reads a String from the {@link HashemContext#getInput() standard input}.
 */
@NodeInfo(shortName = "adadBekhoon")
public abstract class HashemAdadBekhoonBuiltin extends HashemBuiltinNode {

    @Specialization
    public Integer adadBekhoon(@CachedContext(HashemLanguage.class) HashemContext context) {
        String input = doRead(context.getInput());
        int result = 0;
        if (input == null) {
            /*
             * We do not have a sophisticated end of file handling, so returning an empty string is
             * a reasonable alternative. Note that the Java null value should never be used, since
             * it can interfere with the specialization logic in generated source code.
             */
            result = 0;
        }
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new HashemException(String.format("%s is not a number!", input), this);
        }
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
