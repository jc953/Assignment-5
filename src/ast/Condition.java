package ast;

/**
 * An interface representing a Boolean condition in a critter program.
 */
public interface Condition extends RichNode {

	/**
	 * Evaluates the Boolean value of this condition.
	 * 
	 * @param c The critter to be evaluated for
	 * @return The Boolean value of this condition
	 */
	boolean eval();

	Condition dup(RichNode dupParent);

}
