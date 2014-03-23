package a4;

import java.util.ArrayList;

/** Represents +, -, *, /, mod
 * 
 * @author Ishaan and Jonathan
 *
 */
public class BinaryOp extends Expression { // need not be abstract
	private Expression left, right;

	/**
	 * Constructor
	 * @param left left expression
	 * @param tok operator
	 * @param right right expression
	 */
	public BinaryOp(Expression left, Token tok, Expression right) {
		super(tok);
		this.left = left;
		this.right = right;
		this.left.setParent(this);
		this.right.setParent(this);
	}
	
	/**
	 * @return a list of all Expressions extending from this BinaryOp node 
	 */
	public ArrayList<Expression> getExpressions(){
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.add(this);
		ArrayList<Expression> temp1 = left.getExpressions();
		ArrayList<Expression> temp2 = right.getExpressions();
		for (Expression e : temp1){
			result.add(e);
		}
		for (Expression e : temp2){
			result.add(e);
		}
		return result;
	}
	
	/**
	 * @return a list of Node including and beneath this node.
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(this);
		ArrayList<Node> temp1 = left.getNodes();
		ArrayList<Node> temp2 = right.getNodes();
 		for (Node n : temp1){
			result.add(n);
		}
 		for (Node n : temp2){
			result.add(n);
		}
		return result;
	}
	
	/**
	 * @return a random Token 
	 */
	public Token getRandomToken(){
		int i = (int)(Math.random()*5);
		if (i < 2){
			return new Token(50 + i, 0);
		} else{
			return new Token(58 + i, 0);
		}
	}
	
	@Override
	public void prettyPrint(StringBuffer sb) {
		if(paren){
			sb.append(" (");
		}
		left.prettyPrint(sb);
		sb.append(" " + tok.toString());
		right.prettyPrint(sb);
		if(paren){
			sb.append(" )");
		}
		if(sb.charAt(0)==' ') sb = sb.deleteCharAt(0);
	}
	
	/**
	 * helper method for mutate
	 * @return the mutated node
	 */
	public Node mutate1(){
		double r = Math.random();
		Update u = (Update) head;
		if (r < 1.0/6.0){
			Expression e;
			if (Math.random() < 0.5){
				e = left;
			} else{
				e = right;
			}
			if (u.getExpression1().equals(this)){
				u.setExpression1(e);
			} else {
				u.setExpression2(e);
			}
			return getProgram();
		} else if (r < 1.0/3.0){
			Expression temp = left;
			left = right;
			right = temp;
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
	 * @return the mutated node
	 */
	public Node mutate2(){
		double r = Math.random();
		Command c = (Command) head;
		if (r < 1.0/6.0){
			Expression e;
			if (Math.random() < 0.5){
				e = left;
			} else{
				e = right;
			}
			c.addAction(e);
			return getProgram();
		} else if (r < 1.0/3.0){
			Expression temp = left;
			left = right;
			right = temp;
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
	 * @return the mutated node
	 */
	public Node mutate3(){
		double r = Math.random();
		RelationCondition rc = (RelationCondition) head;
		if (r < 1.0/6.0){
			return getProgram();
		} else if (r < 1.0/3.0){
			Expression temp = left;
			left = right;
			right = temp;
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
	 * @return the mutated node
	 */
	public Node mutate4(){
		double r = Math.random();
		ExtendedExpression ee = (ExtendedExpression) head;
		if (r < 1.0/6.0){
			return getProgram();
		} else if (r < 1.0/3.0){
			Expression temp = left;
			left = right;
			right = temp;
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
}
