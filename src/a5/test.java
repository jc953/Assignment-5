package a5;
import ast.*;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		while(true) cw.step();
	}
	
}
	
	

