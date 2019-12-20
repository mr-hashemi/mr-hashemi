package ninja.soroosh.hashem.lang.nodes;

import com.oracle.truffle.api.dsl.NodeChild;

/**
 * Utility base class for operations that take two arguments (per convention called "left" and
 * "right"). For concrete subclasses of this class, the Truffle DSL creates two child fields, and
 * the necessary constructors and logic to set them.
 */
@NodeChild("leftNode")
@NodeChild("rightNode")
public abstract class HashemBinaryNode extends HashemExpressionNode {
}
