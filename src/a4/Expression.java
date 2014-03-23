package a4;

import java.util.ArrayList;
import java.util.Random;

// A critter program expression that has an integer value.
public class Expression implements Node {
	Token tok;
	boolean paren;
	Object head;

	
	/**
	 * Constructor
	 * @param tok
	 */
	public Expression(Token tok) {
		this.tok = tok;
		paren = false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
	/**
	 * 
	 * @return a list of the expresions that extend from this Expression Node
	 */
	public ArrayList<Expression> getExpressions(){
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.add(this);
		return result;
	}
	
	/**
	 * @return a list of Node including and beneath this node.
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(this);
		return result;
	}

	
	/**
	 * 
	 * @return a random token
	 */
	protected Token getRandomToken(){
		if (tok.getType() == Token.NUM){
			Random r = new Random();
			((NumToken) tok).setValue(Integer.MAX_VALUE/r.nextInt());
			return tok;
		} else {
			int i = (int)(Math.random()*10);
			return new Token(10+i, 0);
		}
	}
	
	@Override
	public Node mutate() {
		if (head instanceof Update){
			return mutate1();
		} else if (head instanceof Command){
			return mutate2();
		} else if (head instanceof RelationCondition){
			return mutate3();
		} else {
			return mutate4();
		}
	}
	
	
	/**
	 * helper method for mutate
	 * @return the the mutated Node
	 */
	public Node mutate1(){
		double r = Math.random();
		Update u = (Update) head;
		if (r < 1.0/6.0){
			u.getCommand().removeUpdate(u);
			return getProgram();
		} else if (r < 1.0/3.0){
			return getProgram();
		} else if (r < 0.5){
			Expression temp = getProgram().getRandomExpression();
			if (u.getExpression1().equals(this)){
				u.setExpression1(temp);
			} else {
				u.setExpression2(temp);
			}
			return getProgram();
		} else if (r < 2.0 / 3.0){
			tok = getRandomToken();
			return getProgram();
		} else {
			return getProgram();
		}
	}
	
	
	/**
	 * helper method for mutate
	 * @return the the mutated Node
	 */
	public Node mutate2(){
		double r = Math.random();
		Command c = (Command) head;
		if (r < 1.0/6.0){
			c.removeAction();
			return getProgram();
		} else if (r < 1.0/3.0){
			return getProgram();
		} else if (r < 0.5){
			Expression temp = getProgram().getRandomExpression();
			c.addAction(temp);
			return getProgram();
		} else if (r < 2.0 / 3.0){
			tok = getRandomToken();
			return getProgram();
		} else {
			return getProgram();
		}
	}
	
	
	/**
	 * helper method for mutate
	 * @return the the mutated Node
	 */
	public Node mutate3(){
		double r = Math.random();
		RelationCondition rc = (RelationCondition) head;
		if (r < 1.0/6.0){
			return getProgram();
		} else if (r < 1.0/3.0){
			return getProgram();
		} else if (r < 0.5){
			Expression temp = getProgram().getRandomExpression();
			if (rc.getLeft().equals(this)){
				rc.setLeft(temp);
			} else {
				rc.setRight(temp);
			}
			return getProgram();
		} else if (r < 2.0 / 3.0){
			tok = getRandomToken();
			return getProgram();
		} else {
			return getProgram();
		}
	}
	
	
	/**
	 * helper method for mutate
	 * @return the the mutated Node
	 */
	public Node mutate4(){
		double r = Math.random();
		ExtendedExpression ee = (ExtendedExpression) head;
		if (r < 1.0/6.0){
			return getProgram();
		} else if (r < 1.0/3.0){
			return getProgram();
		} else if (r < 0.5){
			Expression temp = getProgram().getRandomExpression();
			ee.setExpression(temp);
			return getProgram();
		} else if (r < 2.0 / 3.0){
			tok = getRandomToken();
			return getProgram();
		} else {
			return getProgram();
		}
	}
	
	/**
	 * sets the parent node to the value specified
	 * @return the the mutated Node
	 */
	public void setParent(Object o){
		head = o;
	}

	/**
	 * gets the root, Program Node that this Expression stems from
	 * @return the the mutated Node
	 */
	public Program getProgram(){
		if (head instanceof Update){
			return ((Update) head).getProgram();
		} else if (head instanceof Command){
			return ((Command) head).getProgram();
		} else if (head instanceof RelationCondition){
			return ((RelationCondition) head).getProgram();
		} else {
			return ((Expression) head).getProgram();
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		if (paren){
			sb.append(" ( " + tok.toString() + " )");
		}
		else{
			sb.append(" " + tok.toString());
		}
		if(sb.charAt(0)==' ') sb = sb.deleteCharAt(0);
	}
	
	
	/**
	 * sets whether this Expression has parentheses surrounding it or not
	 * @param b
	 */
	public void setParen(boolean b){
		paren = b;
	}
}
