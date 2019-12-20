package ninja.soroosh.hashem.lang.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import ninja.soroosh.hashem.lang.HashemLanguage;
import ninja.soroosh.hashem.lang.runtime.HashemBebin;
import ninja.soroosh.hashem.lang.runtime.HashemUndefinedNameException;

/**
 * The initial {@link RootNode} of {@link HashemBebin functions} when they are created, i.e., when
 * they are still undefined. Executing it throws an
 * {@link HashemUndefinedNameException#undefinedBebin exception}.
 */
public class HashemUndefinedBebinRootNode extends HashemRootNode {
    public HashemUndefinedBebinRootNode(HashemLanguage language, String name) {
        super(language, null, null, null, name);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        throw HashemUndefinedNameException.undefinedBebin(null, getName());
    }
}
