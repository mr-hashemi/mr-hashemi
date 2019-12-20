
package ninja.soroosh.hashem.lang.nodes.expression;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.NodeChild;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.NodeInfo;
import ninja.soroosh.hashem.lang.nodes.HashemExpressionNode;
import ninja.soroosh.hashem.lang.nodes.util.HashemToMemberNode;
import ninja.soroosh.hashem.lang.runtime.HashemUndefinedNameException;

/**
 * The node for writing a property of an object. When executed, this node:
 * <ol>
 * <li>evaluates the object expression on the left hand side of the object access operator</li>
 * <li>evaluates the property name</li>
 * <li>evaluates the value expression on the right hand side of the assignment operator</li>
 * <li>writes the named property</li>
 * <li>returns the written value</li>
 * </ol>
 */
@NodeInfo(shortName = ".=")
@NodeChild("receiverNode")
@NodeChild("nameNode")
@NodeChild("valueNode")
public abstract class HashemWritePropertyNode extends HashemExpressionNode {

    static final int LIBRARY_LIMIT = 3;

    @Specialization(guards = "arrays.hasArrayElements(receiver)", limit = "LIBRARY_LIMIT")
    protected Object write(Object receiver, Object index, Object value,
                    @CachedLibrary("receiver") InteropLibrary arrays,
                    @CachedLibrary("index") InteropLibrary numbers) {
        try {
            arrays.writeArrayElement(receiver, numbers.asLong(index), value);
        } catch (UnsupportedMessageException | UnsupportedTypeException | InvalidArrayIndexException e) {
            // read was not successful. InHashemiwe only have basic support for errors.
            throw HashemUndefinedNameException.undefinedProperty(this, index);
        }
        return value;
    }

    @Specialization(limit = "LIBRARY_LIMIT")
    protected Object write(Object receiver, Object name, Object value,
                    @CachedLibrary("receiver") InteropLibrary objectLibrary,
                    @Cached HashemToMemberNode asMember) {
        try {
            objectLibrary.writeMember(receiver, asMember.execute(name), value);
        } catch (UnsupportedMessageException | UnknownIdentifierException | UnsupportedTypeException e) {
            // write was not successful. InHashemiwe only have basic support for errors.
            throw HashemUndefinedNameException.undefinedProperty(this, name);
        }
        return value;
    }

}
