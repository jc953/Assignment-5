package ast;

/**
 * A representation of a binary Boolean condition: 'and' or 'or'
 */
public class Logical extends Binary<Condition, Logical.Op> implements Condition {

	public enum Op {
		OR {
			@Override
			public String toString() {
				return "or";
			}
		},
		AND {
			@Override
			public String toString() {
				return "and";
			}
		};
	}

	public Logical(Condition left, Op op, Condition right) {
		super(left, op, right);
	}
    
    @Override
	public boolean eval(/*Critter c*/) {
		// TODO implement me!
		// returns the boolean value of this condition for Critter c
	}
    
    
	@Override
	public boolean handleRemove() {
		Condition randomArg = Mutation.randomArg(this);
		Mutation.replaceCond(this, randomArg);
		return true;
	}

	@Override
	public Logical dup(RichNode dupParent) {
		Logical dup = new Logical(null, op, null);
		dup.setParent(dupParent);
		dup.left = left.dup(dup);
		dup.right = right.dup(dup);
		return dup;
	}

	@Override
	String leftGroup() {
		return "{";
	}

	@Override
	String rightGroup() {
		return "}";
	}

}
