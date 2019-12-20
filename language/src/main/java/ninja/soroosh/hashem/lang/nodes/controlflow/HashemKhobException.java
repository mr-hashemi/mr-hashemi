package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link HashemKhobNode break statement} and caught by the {@link HashemTaNode
 * loop statement}. Since the exception is stateless, i.e., has no instance fields, we can use a
 * {@link #SINGLETON} to avoid memory allocation during interpretation.
 */
public final class HashemKhobException extends ControlFlowException {

    public static final HashemKhobException SINGLETON = new HashemKhobException();

    private static final long serialVersionUID = -91013036379258890L;

    /* Prevent instantiation from outside. */
    private HashemKhobException() {
    }
}
