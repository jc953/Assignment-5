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
		cw.step(); 
		assert cw.hexes[column][arrayRow].critter.mem[4] == 1179;
		assert cw.hexes[column][arrayRow].critter.direction == 1;
		cw.step();
		assert cw.hexes[column][arrayRow].critter == null;
		assert cw.hexes[column-1][arrayRow].critter != null;
		assert cw.hexes[column-1][arrayRow].critter.mem[4] == Constants.INITIAL_ENERGY;
		System.out.println("Bud works");
	}
}
	
	
