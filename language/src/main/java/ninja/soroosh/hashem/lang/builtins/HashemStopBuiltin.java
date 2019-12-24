package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.dsl.CachedContext;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemContext;
import ninja.soroosh.hashem.lang.runtime.HashemWebServer;

@NodeInfo(shortName = "stop")
public abstract class HashemStopBuiltin extends HashemBuiltinNode {
    @Specialization
    public String stop(HashemWebServer server, @CachedContext(HashemLanguage.class) HashemContext context) {
        server.stop();
        return "";
    }
}
