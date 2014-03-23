package a4;

import java.util.ArrayList;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 * 
 */
public class BinaryCondition extends Condition {
	protected Condition left, right;

	/**
	 * Create an AST representation of l op r.
	 * 
	 * @param l
	 * @param op
	 * @param r
	 */
	public BinaryCondition(Condition left, Token tok, Condition right) {
		this.left = left;
		this.tok = tok;
		this.right = right;
	}
	
	
	/**
	 * @return the right side of the BinaryCondition
	 */
	public Condition getRight(){
		return right;
	}
	
	
	/**
	 * @return the left side of the BinaryCondition
	 */
	public Condition getLeft(){
		return left;
	}
	
	/**
	 * sets the right side of the BinaryCondition to c
	 * @param c 
	 */
	public void setRight(Condition c){
		right = c;
		c.setParent(this);
	}
	
	/**
	 * sets the left side of the BinaryCondition to c
	 * @param c 
	 */
	public void setLeft(Condition c){
		left = c;
		c.setParent(this);
	}

	
	/**
	 * get the head of this BinaryCondition 
	 * @return a rule or a condition 
	 */
	public Object getHead(){
		if (rhead != null){
			return rhead;
		}
		return head;
	}
	
	
	/**
	 * set the head of this BinaryCondition 
	 * @param o the head 
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
	 * removes the left condition of this BinaryCondition
	 * @param c
	 */
	public void remove(Condition c){
		if (left.equals(c)){
			left = null;
		}
	}
	
	/**
	 * returns all the expressions that arise from this BinaryCondition node
	 * @return the list of expressions
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
	
	/**
	 * returns all the BinaryConditions that arise from this BinaryCondition node
	 * @return the list of BinaryConditions
	 */
	public ArrayList<BinaryCondition> getBinaryConditions(){
		ArrayList<BinaryCondition> result = new ArrayList<BinaryCondition>();
		if (left instanceof BinaryCondition){
			BinaryCondition tempLeft = (BinaryCondition)left;
			result.add(tempLeft);
			ArrayList<BinaryCondition> temp = tempLeft.getBinaryConditions();
			for (BinaryCondition bc : temp){
				result.add(bc);
			}
		} 
		if (right instanceof BinaryCondition){
			BinaryCondition tempRight = (BinaryCondition)right;
			result.add(tempRight);
			ArrayList<BinaryCondition> temp = tempRight.getBinaryConditions();
			for (BinaryCondition bc : temp){
				result.add(bc);
			}
		} 
		return result;
	}
	
	/**
	 * returns all the conditions that arise from this BinaryCondition node
	 * @return the list of conditions
	*/
	public ArrayList<Condition> getConditions(){
		ArrayList<Condition> result = new ArrayList<Condition>();
		if (left instanceof RelationCondition){
			result.add((RelationCondition)left);
		} else {
			BinaryCondition temp = (BinaryCondition)left;
			result.add(temp);
			ArrayList<BinaryCondition> temp1 = temp.getBinaryConditions();
			for (BinaryCondition bc : temp1){
				result.add(bc);
			}
		}
		if (right instanceof RelationCondition){
			result.add((RelationCondition)right);
		} else {
			BinaryCondition temp = (BinaryCondition)right;
			result.add(temp);
			ArrayList<BinaryCondition> temp1 = temp.getBinaryConditions();
			for (BinaryCondition bc : temp1){
				result.add(bc);
			}
		}
		return result;
	}
	
	/**
	 * @return a random Token 
	 */
	public Token getRandomToken(){
		int i = (int)(Math.random()*2);
		return new Token(30+i, 0);
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
	
	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	@Override
	public Node mutate() {
		double r = Math.random();
		if (r < 1.0/6.0){
			double i = Math.random();
			Condition c1;
			if (i<0.5){
				c1 = left;
			} else {
				c1 = right;
			}
			Object o = getHead();
			if (o instanceof BinaryCondition){
				BinaryCondition c2 = (BinaryCondition)o;
				if ((c2.getLeft()).equals(this)){
					c2.setLeft(c1);
				} else {
					c2.setRight(c1);
				}
			} else {
				Rule rule = (Rule)o;
				rule.setCondition(c1);
			}
			return getProgram();
		} else if (r < 1.0/3.0){
			Condition c = left;
			left = right;
			right = c;
			return getProgram();
		} else if (r < 0.5){
			BinaryCondition temp = getProgram().getRandomBinaryCondition();
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
