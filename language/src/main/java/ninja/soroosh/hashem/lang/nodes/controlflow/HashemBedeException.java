package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link HashemBedeNode return statement} and caught by the
 * {@link HashemFunctionBodyNode function body}. The exception transports the return value in its
 * {@link #result} field.
 */
public final class HashemBedeException extends ControlFlowException {

    private static final long serialVersionUID = 4073191346281369231L;

    private final Object result;

    public HashemBedeException(Object result) {
        this.result = result;
    }

    public Object getResult() {
        return result;
    }
}
