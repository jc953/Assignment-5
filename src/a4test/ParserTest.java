package a4test;
import a4.*;
import java.io.Reader;
import java.io.StringReader;
import java.util.*;
import static org.junit.Assert.*;
import org.junit.*;

public class ParserTest {
	/**
	 * Test cases for Expressions
	 */
	public void testA(){
		ParserImpl p = new ParserImpl(); 
		Reader r = new StringReader("3 < 5 --> forward");
		StringBuffer sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(sb.equals("3 < 5 --> forward"));
		
		r = new StringReader("3 * 3 > 8 + 9 --> tag [ 3 ]");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 * 3 > 8 + 9 --> tag [ 3 ]");
		
		r = new StringReader("3 + 3 * 5 OR 34 > 7 --> mem[6] := 12 + 7 wait");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 OR 34 > 7 --> mem[6] := 12 + 7 wait");
		
		r = new StringReader("3 + 3 * 5 OR 34 > 7 --> mem[6] := 12 + 7 wait");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 AND 3<4 OR 34 > 7 --> mem[6] := 12 + 7 forward");
		
		r = new StringReader("3 + 3 * 5 OR 34 > 7 --> mem[6] := 12 + 7 wait");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="(3 + 3 * 5) < 4 AND (9) > random[4+5] --> bud");
		
		r = new StringReader("3 + 3 * 5 AND 3<4 OR 34 > 7 --> mem[6] := 12 + 7 forward");
		sb = new StringBuffer();
		p.parse(r).prettyPrint(sb);
		assertTrue(p.parse(r).toString()=="3 + 3 * 5 AND 3<4 OR 34 > 7 --> mem[6] := 12 + 7 forward");
		
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