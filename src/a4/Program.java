package a4;

import java.util.ArrayList;

/**
 * A representation of a critter program.
 * represents the top of the Abstract Syntax Tree for Parsing the Critter Language
 */
public class Program implements Node {

	private ArrayList<Rule> rules;

	/**
	 * Constructor
	 */
	public Program() {
		rules = new ArrayList<Rule>();
	}

	
	/**
	 * adds a rule node to this program
	 * @param rule
	 */
	protected void addRule(Rule rule) {
		rules.add(rule);
		rule.setProgram(this);
	}
	
	/**
	 * removes a rule node from this program
	 * @param rule
	 */
	protected void removeRule(Rule rule){
		rules.remove(rule);
	}
	
	/**
	 * get a particular rule node
	 * @param i
	 * @return the rule node
	 */
	protected Rule getRule(int i){
		return rules.get(i);
	}

	/**
	 * 
	 * @return number of rule nodes in this program
	 */
	protected int numRules(){
		return rules.size();
	}
	
	
	/**
	 * @return a random condition
	 */
	protected Condition getRandomCondition(){
		ArrayList<Condition> conditions = new ArrayList<Condition>();
		for (Rule r : rules){
			ArrayList<Condition> con = r.getCondition().getConditions();
			for (Condition c : con){
				conditions.add(c);
			}
		}
		return conditions.get((int) (Math.random()*(conditions.size()-1)));
	}
	
	
	/**
	 * @return a random BinaryCondition
	 */
	protected BinaryCondition getRandomBinaryCondition(){
		ArrayList<BinaryCondition> conditions = new ArrayList<BinaryCondition>();
		for (Rule r : rules){
			ArrayList<BinaryCondition> con = r.getCondition().getBinaryConditions();
			for (BinaryCondition c : con){
				conditions.add(c);
			}
		}
		return conditions.get((int) (Math.random()*(conditions.size()-1)));
	}
	
	
	/**
	 * @return a random Update
	 */
	protected Update getRandomUpdate(){
		int i = (int)(Math.random()*rules.size());
		Command temp = rules.get(i).getCommand();
		ArrayList<Update> temp2 = temp.getUpdates();
		return temp2.get((int)(Math.random()*(temp2.size()-1)));
	}
	
	
	/**
	 * @return a random Expression
	 */
	protected Expression getRandomExpression(){
		ArrayList<Expression> expressions = new ArrayList<Expression>();
		for (Rule r : rules){
			ArrayList<Expression> comExpr = r.getCommand().getExpressions();
			for (Expression e : comExpr){
				expressions.add(e);
			}
			ArrayList<Expression> conExpr = r.getCondition().getExpressions();
			for (Expression e : conExpr){
				expressions.add(e);
			}
		}
		return expressions.get((int) (Math.random()*(expressions.size()-1)));
	}
	
	@Override
	public int size() {
		return rules.size() + 1;
	}

	@Override
	public Node mutate() {
		double r = Math.random();
		if (r < 1.0/6.0){
			return new Program();
		} else if (r < 1.0/3.0){
			int len = rules.size();
			int index1 = (int) (Math.random()*(len-1));
			int index2 = (int) (Math.random()*(len-1));
			Rule temp = rules.get(index1);
			rules.set(index1, rules.get(index2));
			rules.set(index2, temp);
			return this;
		} else if (r < 5.0 / 6.0){
			return this;//This is counting for mutation 3 and 4 as well
		} else {
			int len = rules.size();
			int index = (int) (Math.random()*(len-1));
			rules.add(rules.get(index));
			return this;
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		for(Rule r: rules){
			r.prettyPrint(sb);
			sb.append(" ;");
		}
	}
	
	
	/**
	 * @return a random Node
	 */
	public Node getRandomNode(){
		ArrayList<Node> nodes = new ArrayList<Node>();
		nodes.add(this);
		for (Rule r : rules){
			ArrayList<Node> temp = r.getNodes();
			for (Node n : temp){
				nodes.add(n);
			}
		}
		return nodes.get((int) (Math.random()*(nodes.size()-1)));
	}

}
