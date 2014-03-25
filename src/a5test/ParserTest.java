package a5test;
import ast.*;
import parse.*;
import java.io.Reader;
import java.io.StringReader;
import static org.junit.Assert.*;
import org.junit.*;

public class ParserTest {
	/**
	 * Test cases for Expressions
	 */
	@Test
	public void testA(){
		ParserImpl p = new ParserImpl(); 
		Reader r = new StringReader("3 < 5 --> forward");
		StringBuffer sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(sb.toString().equals("3 < 5 --> forward ;"));
		
		r = new StringReader("3 * 3 > 8 + 9 --> tag [ 3 ]");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		//System.out.println(sb);
		assertTrue(sb.toString().equals("3 * 3 > 8 + 9 --> tag [ 3 ] ;"));
		
		r = new StringReader("3 + 3 * 5 or 34 > 7 --> mem[6] := 12 + 7 wait");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 or 34 > 7 --> mem[6] := 12 + 7 wait");
		
		r = new StringReader("3 + 3 * 5 and 3<4 or 34 > 7 --> mem[6] := 12 + 7 forward");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 and 3 < 4 or 34 > 7 --> mem[6] := 12 + 7 forward");
		
		r = new StringReader("(3 + 3 * 5) < 4 and (9) > random[4+5] --> bud");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="(3 + 3 * 5) < 4 and (9) > random[4+5] --> bud");
		
		r = new StringReader("3 + 3 * 5 and 3<4 or 34 > 7 --> mem[6] := 12 + 7 forward");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 and 3<4 or 34 > 7 --> mem[6] := 12 + 7 forward");
		
		r = new StringReader("3 < 4 --> forward ; 3 < 4 --> backward");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 < 4 --> forward ; 3 < 4 --> backward");
		
		r = new StringReader("{3<4} < 5 --> bud");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="{3<4} < 5 --> bud");
	}
}