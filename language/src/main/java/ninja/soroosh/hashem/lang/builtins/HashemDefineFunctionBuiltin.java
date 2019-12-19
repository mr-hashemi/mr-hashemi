package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.Source;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;

/**
 * Builtin function to define (or redefine) functions. The provided source code is parsed the same
 * way as the initial source of the script, so the same syntax applies.
 */
@NodeInfo(shortName = "defineFunction")
public abstract class HashemDefineFunctionBuiltin extends HashemBuiltinNode {

    @TruffleBoundary
    @Specialization
    public String defineFunction(String code, @CachedContext(HashemLanguage.class) HashemContext context) {
        // @formatter:off
        Source source = Source.newBuilder(HashemLanguage.ID, code, "[defineFunction]").
            build();
        // @formatter:on
        /* The same parsing code as for parsing the initial source. */
        context.getFunctionRegistry().register(source);

        return code;
    }
}
