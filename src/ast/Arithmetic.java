package ast;

public class Arithmetic extends Binary<Expression, Arithmetic.Op> implements
		Expression {

	public enum Op {
		PLUS {
			@Override
			public String toString() {
				return "+";
			}
		},
		MINUS {
			@Override
			public String toString() {
				return "-";
			}
		},
		MUL {
			@Override
			public String toString() {
				return "*";
			}
		},
		DIV {
			@Override
			public String toString() {
				return "/";
			}
		},
		MOD {
			@Override
			public String toString() {
				return "mod";
			}
		};
	}

	public Arithmetic(Expression left, Op op, Expression right) {
		super(left, op, right);
	}
            
    @Override
    public int eval() {
        // TODO implement me!
        // returns the boolean value of this expression for Critter c
    }
            
	@Override
	public boolean handleRemove() {
		Expression randomArg = Mutation.randomArg(this);
		Mutation.replaceExpr(this, randomArg);
		return true;
	}

	@Override
	public Arithmetic dup(RichNode dupParent) {
		Arithmetic dup = new Arithmetic(null, op, null);
		dup.setParent(dupParent);
		dup.left = left.dup(dup);
		dup.right = right.dup(dup);
		return dup;
	}

	@Override
	String leftGroup() {
		return "(";
	}

	@Override
	String rightGroup() {
		return ")";
	}

}
