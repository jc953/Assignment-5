package a4;

/**
 * A factory that gives access to instances of parser.
 */
public class ParserFactory {

	/**
	 * Return a parser object for parsing a critter program.
	 * 
	 * @return
	 */
	public static Parser getParser() {
		return new ParserImpl();
	}
}
