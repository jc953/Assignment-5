package a4;

import java.util.ArrayList;
import java.util.Random;

public class ExtendedExpression extends Expression {
	Expression e;

	public ExtendedExpression(Token tok, Expression e) {
		super(tok);
		this.e = e;
		this.e.setParent(this);
	}
	
	public void setExpression(Expression e){
		this.e = e;
	}
	
	protected Token getRandomToken(){
		if (tok.isSensor()){
			int i = (int)(Math.random()*3);
			return new Token(80+i, 0);
		} else if (tok.isAction()){
			int i = (int)(Math.random()*2);
			return new Token(19+i, 0);
		} else {
			return new Token(0, 0);
		}
	}
	
	public ArrayList<Expression> getExpressions(){
		ArrayList<Expression> result = new ArrayList<Expression>();
		result.add(this);
		ArrayList<Expression> temp = e.getExpressions();
		for (Expression e : temp){
			result.add(e);
		}
		return result;
	}
	
	public ArrayList<Node> getNodes(){
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(this);
		ArrayList<Node> temp1 = e.getNodes();
 		for (Node n : temp1){
			result.add(n);
		}
		return result;
	}
	
	@Override
	public void prettyPrint(StringBuffer sb) {
		super.prettyPrint(sb);
		sb.append(" [");
		e.prettyPrint(sb);
		sb.append(" ]");
		if(sb.charAt(0)==' ') sb = sb.deleteCharAt(0);
	}
}
