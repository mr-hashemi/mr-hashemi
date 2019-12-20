
package ninja.soroosh.hashem.lang;

import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.TruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.source.SourceSection;
import ninja.soroosh.hashem.lang.runtime.HashemContext;

/**
 * Hashemi does not need a sophisticated error checking and reporting mechanism, so all unexpected
 * conditions just abort execution. This exception class is used when we abort from within the SL
 * implementation.
 */
public class HashemException extends RuntimeException implements TruffleException {

    private static final long serialVersionUID = -6799734410727348507L;

    private final Node location;

    @TruffleBoundary
    public HashemException(String message, Node location) {
        super(message);
        this.location = location;
    }

    @SuppressWarnings("sync-override")
    @Override
    public final Throwable fillInStackTrace() {
        return this;
    }

    public Node getLocation() {
        return location;
    }

    /**
     * Provides a user-readable message for run-time type errors.Hashemiis strongly typed, i.e., there
     * are no automatic type conversions of values.
     */
    @TruffleBoundary
    public static HashemException typeError(Node operation, Object... values) {
        StringBuilder result = new StringBuilder();
        result.append("Type error");

        if (operation != null) {
            SourceSection ss = operation.getEncapsulatingSourceSection();
            if (ss != null && ss.isAvailable()) {
                result.append(" at ").append(ss.getSource().getName()).append(" line ").append(ss.getStartLine()).append(" col ").append(ss.getStartColumn());
            }
        }

        result.append(": operation");
        if (operation != null) {
            NodeInfo nodeInfo = HashemContext.lookupNodeInfo(operation.getClass());
            if (nodeInfo != null) {
                result.append(" \"").append(nodeInfo.shortName()).append("\"");
            }
        }

        result.append(" not defined for");

        String sep = " ";
        for (int i = 0; i < values.length; i++) {
            Object value = values[i];
            result.append(sep);
            sep = ", ";
            if (value == null || InteropLibrary.getFactory().getUncached().isNull(value)) {
                result.append(HashemLanguage.toString(value));
            } else {
                result.append(HashemLanguage.getMetaObject(value));
                result.append(" ");
                if (InteropLibrary.getFactory().getUncached().isString(value)) {
                    result.append("\"");
                }
                result.append(HashemLanguage.toString(value));
                if (InteropLibrary.getFactory().getUncached().isString(value)) {
                    result.append("\"");
                }
            }
        }
        return new HashemException(result.toString(), operation);
    }

}
