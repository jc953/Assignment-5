package a5;
import ast.*;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		assert cw.hexes[4][2].critter.mem[4] == 500;
		Program program = cw.hexes[4][2].critter.program;
		int d = cw.hexes[4][2].critter.direction;
		for(int i=0;i<5;i++){
			cw.step();
		}
		assert cw.hexes[4][2].critter.mem[4] == 505;// to check its only absorbed energy and hasnt moved
		assert cw.hexes[4][2].critter.direction == d;
		assert cw.hexes[4][2].critter.program.equals(program);// assuming the program uniquely identitfies a critter
		System.out.println("Wait works");
		test2();
		test3();
	}
	
	public static void test2() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int row = 4;
		int column = 3;
		int arrayRow = row - ((column+1)/2);
		Program program = cw.hexes[column][arrayRow].critter.program;
		int d = cw.hexes[column][arrayRow].critter.direction;
		cw.step();
		assert program == cw.hexes[4][3].critter.program;
		assert d == cw.hexes[4][3].critter.direction;
		System.out.println("Move works");
	
	}
	
	public static void test3() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int row = 4;
		int column = 4;
		int arrayRow = row - ((column+1)/2);
		Program program = cw.hexes[column][arrayRow].critter.program;
		int d = cw.hexes[column][arrayRow].critter.direction;
		int s = cw.hexes[column][arrayRow].critter.mem[3];
		cw.step();
		cw.step();
		assert program == cw.hexes[column][arrayRow].critter.program;
		assert d == cw.hexes[column][arrayRow].critter.direction;
		assert cw.hexes[column][arrayRow].critter.mem[3] == 3;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 1825;
		//Even though the rules specifies it should grow 5 times, it only grows once per step
		//is this an issue?
		System.out.println("Grow works");
	
	}
	
}
	
	
