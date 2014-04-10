package a5;
import ast.*;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) throws FileNotFoundException, InterruptedException{
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int row = 5;
		int column = 2;
		int arrayRow = row - (column+1)/2;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 500;
		Program program = cw.hexes[column][arrayRow].critter.program;
		int d = cw.hexes[column][arrayRow].critter.direction;
		for(int i=0;i<5;i++){
			cw.step();
		}
		assert cw.hexes[column][arrayRow].critter.mem[4] == 505;// to check its only absorbed energy and hasnt moved
		assert cw.hexes[column][arrayRow].critter.direction == d;
		assert cw.hexes[column][arrayRow].critter.program.equals(program);// assuming the program uniquely identitfies a critter
		System.out.println("Wait works");
		test2();
		test3();
		test4();
		test5();
		test6();
		test7();
		test8();
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
		cw.step();
		assert program == cw.hexes[column][arrayRow].critter.program;
		assert d == cw.hexes[column][arrayRow].critter.direction;
		assert cw.hexes[column][arrayRow].critter.mem[3] == 2;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 230;
		cw.step();
		assert cw.hexes[column][arrayRow].critter == null;
		System.out.println("Grow works");
	
	}
	
	public static void test4() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 10;
		int row = 11;
		int arrayRow = row - ((column+1)/2);
		cw.step();
		assert cw.hexes[column][arrayRow].critter.mem[4] == 2495;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2192;
		System.out.println("Attack works");
	
	}
	
	public static void test5() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 1;
		int row = 1;
		int arrayRow = row - ((column+1)/2);
		assert cw.hexes[column][arrayRow].critter.direction == 0;
		cw.step(); 
		assert cw.hexes[column][arrayRow].critter.direction == 5;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.direction == 0;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 314;
		System.out.println("Turn works");
	}
	
	
	public static void test6() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 1;
		int row = 7;
		int arrayRow = row - ((column+1)/2);
		assert cw.hexes[column][arrayRow-1].critter == null;
		cw.step(); 
		assert cw.hexes[column][arrayRow-1].critter != null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 1180;
		assert cw.hexes[column][arrayRow-1].critter.mem[4] == Constants.INITIAL_ENERGY;
		assert cw.hexes[column][arrayRow-1].critter.mem[3] == 1;
		assert cw.hexes[column][arrayRow-1].critter.mem[6] == 0;
		assert cw.hexes[column][arrayRow-1].critter.mem[7] == 0;
		cw.step(); 
		assert cw.hexes[column][arrayRow].critter.mem[4] == 1179;
		assert cw.hexes[column][arrayRow].critter.direction == 1;
		cw.step();
		assert cw.hexes[column][arrayRow].critter == null;
		assert cw.hexes[column-1][arrayRow].critter != null;
		assert cw.hexes[column-1][arrayRow].critter.mem[4] == Constants.INITIAL_ENERGY;
		System.out.println("Bud works");
	}
	
	public static void test7() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 6;
		int row = 5;
		int arrayRow = row - ((column+1)/2);
		assert cw.hexes[column][arrayRow-1].critter == null;
		assert cw.hexes[column][arrayRow+2].critter == null;
		cw.step(); 
		assert cw.hexes[column][arrayRow-1].critter != null || cw.hexes[column][arrayRow+2].critter != null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 2724;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2474;
		Critter baby = cw.hexes[column][arrayRow-1].critter;
		if (baby == null) baby = cw.hexes[column][arrayRow+2].critter;
		assert baby.mem[3] == 1;
		assert baby.mem[4] == Constants.INITIAL_ENERGY;
		assert baby.mem[6] == 0;
		assert baby.mem[7] == 0;
		for(int  t=0;t<3;t++){
			boolean a = Math.abs(baby.mem[t] - cw.hexes[column][arrayRow].critter.mem[t]) <= 1;
			boolean b = Math.abs(baby.mem[t] - cw.hexes[column][arrayRow+1].critter.mem[t]) <= 1;
			assert a || b; //Note: with very low probability this assertion will fail, in cases where
						   //there are 3 or more mutations that each exclusively add to or subtract
						   //from an attribute
		}
		int r = baby.row;
		int c = baby.column;
		cw.kill(baby);
		assert cw.hexes[c][r].critter == null;
		cw.hexes[c][r].rock = true;
		cw.step();
		assert cw.hexes[c][r].rock;
		assert cw.hexes[column][arrayRow-1].critter != null || cw.hexes[column][arrayRow+2].critter != null;
		System.out.println("Mate works");
	}
	
	public static void test8() throws FileNotFoundException, InterruptedException{
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 8;
		int row = 7;
		int arrayRow = row - ((column+1)/2);
		cw.step();
		assert cw.hexes[column][arrayRow+1].critter.mem[6] == 47;
		assert cw.hexes[column][arrayRow+1].food == 34;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2324;
		System.out.println(cw.hexes[column][arrayRow+1].critter.mem[4]);
		cw.step();
		System.out.println(cw.hexes[column][arrayRow+1].critter.mem[4]);// == 2358;
		System.out.println(cw.hexes[column][arrayRow+1].food);// == 0;
		System.out.println("Tag, Eat and Serve work");
	}
	
	
}
	
	

