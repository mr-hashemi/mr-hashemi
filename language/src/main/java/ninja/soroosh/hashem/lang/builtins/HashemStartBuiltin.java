package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemWebServer;

@NodeInfo(shortName = "start")
public abstract class HashemStartBuiltin extends HashemBuiltinNode {
    @Specialization
    public String start(HashemWebServer server, @CachedContext(HashemLanguage.class) HashemContext context) {
        server.start();
        return "";
    }
}
