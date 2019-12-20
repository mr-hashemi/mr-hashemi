
package ninja.soroosh.hashem.lang.runtime;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.nodes.Node;
import ninja.soroosh.hashem.lang.HashemException;

public final class HashemUndefinedNameException extends HashemException {

    private static final long serialVersionUID = 1L;

    @TruffleBoundary
    public static HashemUndefinedNameException undefinedBebin(Node location, Object name) {
        throw new HashemUndefinedNameException("Undefined bebin: " + name, location);
    }

    @TruffleBoundary
    public static HashemUndefinedNameException undefinedProperty(Node location, Object name) {
        throw new HashemUndefinedNameException("Undefined property: " + name, location);
    }

    private HashemUndefinedNameException(String message, Node node) {
        super(message, node);
    }
}
