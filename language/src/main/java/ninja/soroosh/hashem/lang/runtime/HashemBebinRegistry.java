package ninja.soroosh.hashem.lang.runtime;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.source.Source;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.parser.HashemLanguageParser;

/**
 * Manages the mapping from function names to {@link HashemBebin function objects}.
 */
public final class HashemBebinRegistry {

    private final HashemLanguage language;
    private final FunctionsObject functionsObject = new FunctionsObject();

    public HashemBebinRegistry(HashemLanguage language) {
        this.language = language;
    }

    /**
     * Returns the canonical {@link HashemBebin} object for the given name. If it does not exist yet,
     * it is created.
     */
    public HashemBebin lookup(String name, boolean createIfNotPresent) {
        HashemBebin result = functionsObject.functions.get(name);
        if (result == null && createIfNotPresent) {
            result = new HashemBebin(language, name);
            functionsObject.functions.put(name, result);
        }
        return result;
    }

    /**
     * Associates the {@link HashemBebin} with the given name with the given implementation root
     * node. If the function did not exist before, it defines the function. If the function existed
     * before, it redefines the function and the old implementation is discarded.
     */
    public HashemBebin register(String name, RootCallTarget callTarget) {
        HashemBebin function = lookup(name, true);
        function.setCallTarget(callTarget);
        return function;
    }

    public void register(Map<String, RootCallTarget> newFunctions) {
        for (Map.Entry<String, RootCallTarget> entry : newFunctions.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
    }

    public void register(Source newFunctions) {
        register(HashemLanguageParser.parseHashemiLang(language, newFunctions));
    }

    public HashemBebin getFunction(String name) {
        return functionsObject.functions.get(name);
    }

    /**
     * Returns the sorted list of all functions, for printing purposes only.
     */
    public List<HashemBebin> getFunctions() {
        List<HashemBebin> result = new ArrayList<>(functionsObject.functions.values());
        Collections.sort(result, new Comparator<HashemBebin>() {
            public int compare(HashemBebin f1, HashemBebin f2) {
                return f1.toString().compareTo(f2.toString());
            }
        });
        return result;
    }

    public TruffleObject getFunctionsObject() {
        return functionsObject;
    }

}
