package a4;

import java.io.Reader;
public class ParserImpl implements Parser {

	/** The tokenizer from which input is read. */
	Tokenizer tokenizer;
	Token tok;

	public Program parse(Reader r) {
		tokenizer = new Tokenizer(r);
		try {
			return parseProgram();
		} catch (SyntaxError e) {
			System.out.println("Error");
		}
		return null;

	}

	/**
	 * Parses a program from the stream of tokens provided by the Tokenizer,
	 * consuming tokens representing the program. All following methods with a
	 * name "parseX" have the same spec except that they parse syntactic form X.
	 * 
	 * @return the created AST
	 * @throws SyntaxError
	 *             if there the input tokens have invalid syntax
	 */
	public Program parseProgram() throws SyntaxError {
		Program result = new Program();
		while (tokenizer.hasNext()) {
			result.addRule(parseRule());	
		}
		return result;
	}
	
	/**
	 * helper method for parseProgram()
	 * @return a correctly parsed Rule
	 * @throws SyntaxError
	 */
	public Rule parseRule() throws SyntaxError {
		Rule result = new Rule(parseCondition(), parseCommand());
		result.getCondition().setParent(result);
		result.getCommand().setParent(result);
		return result;
	}
	
	/**
	 * helper method for parseRule()
	 * @return a correctly parsed Condition
	 * @throws SyntaxError
	 */
	public Condition parseCondition() throws SyntaxError {
		Condition temp = parseRelation();
		if (!tokenizer.hasNext()) return temp;
		if (tokenizer.peek().getType() == Token.ARR) {
			tok = tokenizer.next();
			return temp;
		}
		tok = tokenizer.next();
		return new BinaryCondition(temp, tok, parseCondition());
	}
	
	/**
	 * helper method for parseCondition()
	 * @return a partially parsed Condition
	 * @throws SyntaxError
	 */
	public Condition parseRelation() throws SyntaxError {
		if (!tokenizer.hasNext()) return null;
		if (tokenizer.peek().getType() == Token.LBRACE) {
			tokenizer.next();
			Condition result = parseCondition();
			tok = tokenizer.next();
			return result;
		}
		Expression e = parseExpression();
		tok = tokenizer.next();
		return new RelationCondition(e, tok,
				parseExpression());
	}

	/**
	 * helper method for parseRule()
	 * @return a correctly parsed Command
	 * @throws SyntaxError
	 */
	public Command parseCommand() throws SyntaxError {
		Command c = new Command();
		if (!tokenizer.hasNext()){
			c.addAction(new Expression(tok));
			return c;
		}
		while (tokenizer.peek().getType() == Token.MEM) {
			c.addUpdate(parseUpdate());
		}
		if (tokenizer.peek().isAction()) {
			c.addAction(parseExpression());
		}
		return c;
	}
	
	/**
	 * helper method for parseCommand()
	 * @return a correctly parsed Update
	 * @throws SyntaxError
	 */
	public Update parseUpdate() throws SyntaxError {
		tokenizer.next();
		tokenizer.next();
		Expression e1 = parseExpression();
		tokenizer.next();
		tokenizer.next();
		Expression e2 = parseExpression();
		return new Update(e1, e2);
	}

	
	/**
	 * helper method for parsing
	 * @return a correctly parsed Expression
	 * @throws SyntaxError
	 */
	public Expression parseExpression() throws SyntaxError {
		Expression e = parseFactor();
		if (!tokenizer.hasNext()) return e;
		if (tokenizer.peek().isMulOp() || tokenizer.peek().isAddOp()) {
			return new BinaryOp(e, tokenizer.next(), parseExpression());
		}
		return e;
	}

	
	/**
	 * helper method for parseExpression()
	 * @return a partially parsed Expression
	 * @throws SyntaxError
	 */
	public Expression parseFactor() throws SyntaxError {
		if (!tokenizer.hasNext()) return null;
		if (tokenizer.peek().getType() == Token.NUM) {
			return new Expression(tokenizer.next().toNumToken());
		} else if (tokenizer.peek().getType() == Token.LPAREN) {
			tokenizer.next();
			Expression e = parseExpression();
			e.setParen(true);
			tokenizer.next();
			return e;
		} else if(tokenizer.peek().isAction() || tokenizer.peek().isSensor() || tokenizer.peek().getType() == Token.MEM){
			Expression e1 = new Expression(tokenizer.next());
			if (!tokenizer.hasNext()) return e1;
			tokenizer.next();
			Expression e = parseExpression();
			tokenizer.next();
			return new ExtendedExpression(e1.tok, e);
		}
		return null;
	}
	
	

	/**
	 * Consumes a token of the expected type. Throws a SyntaxError if the wrong
	 * kind of token is encountered.
	 */
	public void consume(int tokenType) throws SyntaxError {
		throw new UnsupportedOperationException();
	}

}
