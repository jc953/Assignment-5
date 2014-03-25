package a5test;

import ast.*;
import parse.*;

import java.io.Reader;
import java.io.StringReader;

import static org.junit.Assert.*;

import org.junit.*;

public class FaultInjectionTest {

	/**
	 * Test cases to make sure mutations do not return same String.
	 * 
	 * FIX THESEESERAFLASDFJASDIOAFLFNASFLAWEJFAEWIFWEADWAS makem pretty
	 */
	@Test
	public void testA() {
		ParserImpl p = new ParserImpl();
		Reader r = new StringReader(
				"3 + 3 < 5 and 3<4 or 34 > 7 --> mem[6] := 12 + 7 forward;");
		StringBuffer sb = new StringBuffer();
		Program prog = p.parse(r);
		for (int i = 0; i < 5; i++) {
			Mutation.mutate(prog);
		}
		prog.prettyPrint(sb);
		System.out.println(sb.toString());
		assertTrue(sb.toString() != "3 + 3 * 5 AND 3<4 OR 34 > 7 --> mem[6] := 12 + 7 forward;");

		r = new StringReader("3 + 3 > 5 or 34 > 7 --> mem[6] := 12 + 7 wait;");
		sb = new StringBuffer();
		prog = p.parse(r);
		for (int i = 0; i < 5; i++) {
			Mutation.mutate(prog);
		}
		prog.prettyPrint(sb);
		System.out.println(sb.toString());
		assertTrue(sb.toString() != "3 + 3 * 5 OR 34 > 7 --> mem[6] := 12 + 7 wait");
	}
}