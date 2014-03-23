package a4;

import java.util.ArrayList;
/**
 * Class to handle the commands in the language, relating them to the appropriate
 * nodes in the Abstract Syntax Tree
 * @authors Ishaan and Jonathan
 *
 */
public class Command implements Node {
	Rule rule;
	ArrayList<Update> updates;
	Expression action;

	/**
	 * Constructor
	 */
	public Command() {
		updates = new ArrayList<Update>();
		action = null;
	}

	/**
	 * method to add Update Nodes to the Command Node
	 * @param u the update node to add
	 */
	public void addUpdate(Update u) {
		updates.add(u);
		u.addCommand(this);
	}

	/**
	 * removes Update Nodes from this Command Node
	 * @param u the update node to remove
	 */
	public void removeUpdate(Update u){
		updates.remove(u);
	}
	
	/**
	 * method to add an action Nodes to the Command Node
	 * @param e the action node to add
	 */
	public void addAction(Expression e) {
		action = e;
		action.setParent(this);
	}
	
	
	/**
	 * method to remove an action Nodes from this Command Node
	 * @param e the action node to remove
	 */	
	public void removeAction(){
		if (action != null){
			action = null;
		}
	}
	/**
	 * 
	 * @return the Update Nodes in this Command Node
	 */
	public ArrayList<Update> getUpdates(){
		return updates;
	}
	
	/**
	 * @return the Action node in this Command Node, if any
	 */
	public Expression getAction(){
		return action;
	}
	
	/**
	 * sets the parent rule to r
	 * @param r
	 */
	public void setParent(Rule r){
		rule = r;
	}
	
	/**
	 * 
	 * @return the parent Program Node
	 */
	public Program getProgram(){
		return rule.getProgram();
	}
	
	
	/**
	 * 
	 * @return a list of the expressions extending from this Command Node
	 */
	public ArrayList<Expression> getExpressions(){
		ArrayList<Expression> result = new ArrayList<Expression>();
		if (action != null) result.add(action);
		for (Update u : updates){
			result.add(u.getExpression1());
			result.add(u.getExpression2());
		}
		return result;
	}
	
	/**
	 * @return a list of Node including and beneath this node.
	 */
	public ArrayList<Node> getNodes(){
		ArrayList<Node> result = new ArrayList<Node>();
		result.add(this);
		if (action != null){
			ArrayList<Node> temp1 = action.getNodes();
			for (Node n : temp1){
				result.add(n);
			}
		}
		for (Update u : updates){
			ArrayList<Node> temp2 = u.getNodes();
			for (Node n : temp2){
				result.add(n);
			}
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
			getProgram().removeRule(rule);
			return getProgram();
		} else if (r < 1.0/3.0){
			int len = updates.size();
			int index1 = (int) (Math.random()*(len-1));
			int index2 = (int) (Math.random()*(len-1));
			Update temp = updates.get(index1);
			updates.set(index1, updates.get(index2));
			updates.set(index2, temp);
			return getProgram();
		} else if (r < 0.5){
			int i = (int)(getProgram().numRules() * Math.random());
			Rule random = getProgram().getRule(i);
			Command com = random.getCommand();
			this.updates = com.getUpdates();
			this.action = com.getAction();
			return getProgram();
		} else if (r < 5.0 / 6.0){
			return getProgram();
		} else {
			int len = updates.size();
			int index = (int) (Math.random()*(len-1));
			updates.add(updates.get(index));
			return getProgram();
		}
	}

	@Override
	public void prettyPrint(StringBuffer sb) {
		for(Update u: updates){
			u.prettyPrint(sb);			
		}
		if (action != null){
			action.prettyPrint(sb);
		}

	}
}
