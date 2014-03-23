package a4;

/**
 * A NumToken is a token containing a number.
 * 
 * @author Chinawat
 * 
 */
public class NumToken extends Token {

	protected int value;

	
	/**
	 * Constructor
	 * @param value
	 * @param lineNo
	 */
	public NumToken(int value, int lineNo) {
		super(NUM, lineNo);
		this.value = value;
	}

	
	/**
	 * 
	 * @return the value associated with this NumToken
	 */
	public int getValue() {
		return value;
	}
	
	
	/**
	 * sets the value to the parameterhelper method for mutate
	 * @param i
	 */
	public void setValue(int i){
		value = i;
	}

	@Override
	public String toString() {
		return Integer.toString(value);
	}
}
