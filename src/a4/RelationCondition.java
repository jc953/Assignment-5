package a4;

import java.util.ArrayList;
/**
 * A RelationCondition represents a Critter Language feature which compares two expressions
 * with a logical operator (!,=,<,> and so on) 
 * @author Ishaan
 *
 */
public class RelationCondition extends Condition {
	protected Expression left, right;

	/**
	 * Constructor
	 * @param left
	 * @param tok
	 * @param right
	 */
	public RelationCondition(Expression left, Token tok, Expression right) {
		this.left = left;
		this.tok = tok;
		this.right = right;
		this.left.setParent(this);
		this.right.setParent(this);
	}
	
	/**
	 * @return the right side Expression of the RelationCondition
	 */
	public Expression getRight(){
		return right;
	}
	
	
	/**
	 * @return the left side Expression of the RelationCondition
	 */
	public Expression getLeft(){
		return left;
	}
	
	
	/**
	 * set the right side Expression of the RelationCondition
	 * @param e
	 */
	public void setRight(Expression e){
		right = e;
		e.setParent(this);
	}
	
	
	/**
	 * set the left side Expression of the RelationCondition
	 * @param e
	 */
	public void setLeft(Expression e){
		left = e;
		e.setParent(this);
	}
	
	
	/**
	 * @return the head of the RelationCondition
	 */
	public Object getHead(){
		if (rhead != null){
			return rhead;
		}
		return head;
	}
	
	
	/**
	 * set the head of the RelationCondition
	 * @param o
	 */
	public void setHead(Object o){
		if (o instanceof Rule){
			rhead = (Rule) o;
			head = null;
		} else {
			head = (Condition) o;
			rhead = null;
		}
	}

	
	/**
	 * @return a random Token
	 */
	public Token getRandomToken(){
		int i = (int)(Math.random()*6);
		return new Token(32+i, 0);
	}
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	/**
	 * @return a list of the expressions that extend from this RelationCondition
	 */
	public ArrayList<Expression> getExpressions(){
		ArrayList<Expression> result = new ArrayList<Expression>();
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

	@Override
	public Node mutate() {
		double r = Math.random();
		if (r < 1.0/6.0){
			Object o = getHead();
			if (o instanceof Rule){
				getProgram().removeRule((Rule) o);
				return getProgram();
			} else {
				BinaryCondition header = (BinaryCondition) o;
				if (header.getHead() instanceof Rule){
					Rule temp = (Rule) header.getHead();
					if (header.getLeft().equals(this)){
						temp.setCondition(header.getRight());
					} else {
						temp.setCondition(header.getLeft());
					}
					return getProgram();
				} else {
					BinaryCondition doubleheader = (BinaryCondition) header.getHead();
					Condition temp;
					if (header.getLeft().equals(this)){
						temp = header.getRight();
					} else {
						temp = header.getLeft();
					}
					if (doubleheader.getLeft().equals(header)){
						doubleheader.setLeft(temp);
					} else {
						doubleheader.setRight(temp);
					}
					return getProgram();
				}
			}
		} else if (r < 1.0/3.0){
			Expression e = left;
			left = right;
			right = e;
			return getProgram();
		} else if (r < 0.5){
			Condition temp = getProgram().getRandomCondition();
			if (getHead() instanceof Rule){
				((Rule) getHead()).setCondition(temp);
			} else {
				BinaryCondition bc = (BinaryCondition) getHead();
				if ((bc.getLeft()).equals(this)){
					bc.setLeft(temp);
				} else {
					bc.setRight(temp);
				}
			}
			return getProgram();
		} else if (r < 2.0 / 3.0){
			tok = getRandomToken();
			return getProgram();
		} else if (r < 5.0 / 6.0){
			Token tempTok = getRandomToken();
			BinaryCondition temp = getProgram().getRandomBinaryCondition();
			Object o = getHead();
			BinaryCondition temp2 = new BinaryCondition(this, tempTok, temp);
			setHead(temp2);
			if (o instanceof BinaryCondition){
				BinaryCondition header = (BinaryCondition) o;
				temp2.setHead(header);
				if (header.getLeft().equals(this)){
					header.setLeft(temp2);
				} else {
					header.setRight(temp2);
				}
				return getProgram();
			} else {
				Rule header = (Rule) o;
				temp2.setHead(header);
				header.setCondition(temp2);
				return getProgram();
			}
		} else {
			return getProgram();
		}
	}
	
	
	/**
	 * @return a list of the nodes thats extend from this RelationCondition
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

	@Override
	public void prettyPrint(StringBuffer sb) {
		if(brace){
			sb.append(" {");
		}
		left.prettyPrint(sb);
		sb.append(" " + tok.toString());
		right.prettyPrint(sb);
		if(brace){
			sb.append(" }");
		}
		if(sb.charAt(0)==' ') sb = sb.deleteCharAt(0);
	}

}
