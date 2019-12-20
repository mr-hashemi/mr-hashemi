package ninja.soroosh.hashem.lang.nodes.controlflow;

import com.oracle.truffle.api.nodes.ControlFlowException;

/**
 * Exception thrown by the {@link HashemBadiNode continue statement} and caught by the
 * {@link HashemTaNode loop statement}. Since the exception is stateless, i.e., has no instance
 * fields, we can use a {@link #SINGLETON} to avoid memory allocation during interpretation.
 */
public final class HashemiBadiException extends ControlFlowException {

    public static final HashemiBadiException SINGLETON = new HashemiBadiException();

    private static final long serialVersionUID = 5329687983726237188L;

    /* Prevent instantiation from outside. */
    private HashemiBadiException() {
    }
}
