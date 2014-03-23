package a4;

import java.util.ArrayList;

/**
 * An interface representing a Boolean condition in a critter program.
 * 
 */
public abstract class Condition implements Node {
	Rule rhead;
	boolean brace;
	Token tok;
	Condition head;
	
	/**
	 * Sets truth of brace to b.
	 * @param b the truth
	 */
	public void setBrace(boolean b){
		brace = b;
	}
	
	/**
	 * Sets parent to rule
	 * @param r the rule
	 */
	public void setParent(Rule r){
		rhead = r;
	}
	
	/**
	 * 
	 * @param c
	 */
	public void setParent(Condition c){
		head = c;
	}
	
	/**
	 * @return Object on right
	 */
	public Object getRight(){
		return null;
	}
	
	/**
	 * @return Object on left
	 */
	public Object getLeft(){
		return null;
	}
	
	/**
	 * Sets object on right to o 
	 * @param o object to be set
	 */
	public void setRight(Object o){
	}
	
	/**
	 * Sets object on left to o 
	 * @param o object to be set
	 */
	public void setLeft(Object o){
	}
	
	/**
	 * @return Program by looking to the header class
	 */
	public Program getProgram(){
		if (rhead != null){
			return rhead.getProgram();
		}
		return head.getProgram();
	}
	
	/**
	 * @return random token
	 */
	public Token getRandomToken() {
		return null;
	}
	
	/**
	 * @return list containing this and all conditions below this
	 */
	public ArrayList<Condition> getConditions(){
		ArrayList<Condition> result = new ArrayList<Condition>();
		result.add(this);
		return result;
	}
	
	/**
	 * @return list containing this and all binary conditions below this
	 */
	public ArrayList<BinaryCondition> getBinaryConditions(){
		return null;
	}
	
	/**
	 * @return list containing all expressions below this
	 */
	public ArrayList<Expression> getExpressions(){
		return null;
	}
	
	/**
	 * @return list of all nodes including and below this node
	 */
	public ArrayList<Node> getNodes(){
		return null;
	}
}
