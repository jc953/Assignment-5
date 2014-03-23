package a4;

import java.util.ArrayList;

/**
 * A representation of a critter rule.
 */
public class Rule implements Node {
	private Program program;
	private Condition condition;
	private Command command;

	public Rule(Condition condition, Command command) {
		this.condition = condition;
		this.command = command;
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
			program.removeRule(this);
			return program;
		} else if (r < 1.0/3.0){
			int i = (int) (Math.random()*(program.numRules()-1));
			Rule temp = program.getRule(i);
			this.condition = temp.getCondition();
			this.command = temp.getCommand();
			return program;
		} else {
			return program; //counts for mutations 2 4 5 and 6
		}
	}

	/**
	 * set the parent Program of this Rule to the parameter
	 * @param p
	 */
	protected void setProgram(Program p){
		program = p;
	}
	
	/**
	 * @return the condition associated with this Rule
	 */
	protected Condition getCondition(){
		return condition;
	}
	
	
	/** set the condition associated with this Rule
	 * @param c
	 */	
	protected void setCondition(Condition c){
		condition = c;
	}
	
	
	/**
	 * @return the command associated with this Rule
	 */
	protected Command getCommand(){
		return command;
	}
	
	
	/**
	 * @return the parent command associated with this Rule
	 */
	protected Program getProgram(){
		return program;
	}
	
	
	/**
	 * @return a list of the nodes associated with this Rule Node
	 */
	protected ArrayList<Node> getNodes(){
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(this);
		ArrayList<Node> temp1 = condition.getNodes();
		ArrayList<Node> temp2 = command.getNodes();
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
		condition.prettyPrint(sb);
		sb.append(" -->");
		command.prettyPrint(sb);
	}

}
