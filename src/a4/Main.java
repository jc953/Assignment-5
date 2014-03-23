package a4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args){
		if (args[0].equals("--mutate")){
			int n = Integer.parseInt(args[1]);
			try {
				BufferedReader reader = new BufferedReader(new FileReader(args[2]));
				ParserImpl p = new ParserImpl();
				StringBuffer sb = new StringBuffer();
				Program prog = p.parse(reader);
				for (int i = 0; i < n; i++){
					Node n1= prog.getRandomNode();
					n1.mutate().prettyPrint(sb);
					System.out.print("The ");
					if (n1 instanceof Program){
						System.out.print("Program");
					} else if (n1 instanceof Rule){
						System.out.print("Rule");
					} else if (n1 instanceof Condition){
						System.out.print("Condition");
					} else if (n1 instanceof Expression){
						System.out.print("Expression");
					} else if (n1 instanceof Update){
						System.out.print("Update");
					} 
					System.out.println(" node has been modified.");
					System.out.println(sb);
				}
				
			} catch (FileNotFoundException e) {
				System.out.println("You have not submitted a valid input file");
			}
			
		} else {
			try {
				BufferedReader reader = new BufferedReader(new FileReader(args[2]));
				ParserImpl p = new ParserImpl();
				StringBuffer sb = new StringBuffer();
				p.parse(reader).prettyPrint(sb);
				System.out.println(sb.toString());
			} catch (FileNotFoundException e) {
				System.out.println("You have not submitted a valid input file");
			}
		}
	}
}
