package a4;

/**
 * An ErrorToken is a token containing unrecognized string in the critter
 * language.
 * 
 * @author Chinawat
 * 
 */
public class ErrorToken extends Token {

	protected String value;

	/**
	 * Constructs an ErrorToken
	 * 
	 * @param value the value of the string
	 * @param lineNo the line number
	 */
	public ErrorToken(String value, int lineNo) {
		super(ERROR, lineNo);
		this.value = value;
	}

	/**
	 * @return value of the string
	 */
	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return "[error] " + value;
	}
}
