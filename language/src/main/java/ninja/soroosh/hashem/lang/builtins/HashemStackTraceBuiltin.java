
package ninja.soroosh.hashem.lang.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives.TruffleBoundary;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.Frame;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.FrameInstance;
import com.oracle.truffle.api.frame.FrameInstance.FrameAccess;
import com.oracle.truffle.api.frame.FrameInstanceVisitor;
import com.oracle.truffle.api.frame.FrameSlot;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.nodes.RootNode;

/**
 * Returns a string representation of the current stack. This includes the {@link CallTarget}s and
 * the contents of the {@link Frame}.
 */
@NodeInfo(shortName = "poshte")
public abstract class HashemStackTraceBuiltin extends HashemBuiltinNode {

    @Specialization
    public String trace() {
        return createStackTrace();
    }

    @TruffleBoundary
    private static String createStackTrace() {
        final StringBuilder str = new StringBuilder();

        Truffle.getRuntime().iterateFrames(new FrameInstanceVisitor<Integer>() {
            private int skip = 1; // skip stack trace builtin

            @Override
            public Integer visitFrame(FrameInstance frameInstance) {
                if (skip > 0) {
                    skip--;
                    return null;
                }
                CallTarget callTarget = frameInstance.getCallTarget();
                Frame frame = frameInstance.getFrame(FrameAccess.READ_ONLY);
                RootNode rn = ((RootCallTarget) callTarget).getRootNode();
                // ignore internal or interop stack frames
                if (rn.isInternal() || rn.getLanguageInfo() == null) {
                    return 1;
                }
                if (str.length() > 0) {
                    str.append(System.getProperty("line.separator"));
                }
                str.append("Frame: ").append(rn.toString());
                FrameDescriptor frameDescriptor = frame.getFrameDescriptor();
                for (FrameSlot s : frameDescriptor.getSlots()) {
                    str.append(", ").append(s.getIdentifier()).append("=").append(frame.getValue(s));
                }
                return null;
            }
        });
        return str.toString();
    }
}
