package a5test;

import org.junit.Test;

import a5.*;

/**
 * Second set of test cases for the Critter class.
 */
public class CritterTest2 {

	/**
	 * Test a basic update method. Makes sure that if a rule only contains 
	 * updates, it will continue until an action is performed.
	 */
	@Test
	public void testA() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world2.txt");
		int column = 2;
		int row = 10;
		int arrayRow = row - ((column + 1) / 2);
		assert cw.hexes[column][arrayRow].critter.mem[8] == 0;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 300;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.mem[8] == 5;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 301;
		assert cw.hexes[column][arrayRow].critter.mem[5] == 2;
		
	}

	/**
	 * Test the nearby method for a rock, critter, food, and empty space.
	 */
	@Test
	public void testB() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world2.txt");
		int column = 2;
		int row = 2;
		int arrayRow = row - ((column + 1) / 2);
		assert cw.hexes[column][arrayRow + 1].rock;
		assert cw.hexes[column][arrayRow].critter.direction == 0;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.direction == 1;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.direction == 2;
		cw.hexes[column + 1][arrayRow - 1].food = 5;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.direction == 3;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.direction == 3;
		assert cw.hexes[column][arrayRow].critter.mem[5] == 999;
	}

	/**
	 * Test the ahead method for a critter, empty space, and food.
	 */
	@Test
	public void testC() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world2.txt");
		int column = 2;
		int row = 30;
		int arrayRow = row - ((column + 1) / 2);
		assert cw.hexes[column][arrayRow].critter.direction == 3;
		cw.step();
		assert cw.hexes[column][arrayRow - 1].critter.direction == 3;
		cw.step();
		assert cw.hexes[column][arrayRow - 1].critter.direction == 2;
		cw.hexes[column][arrayRow - 1].food = 4;
		cw.step();
		assert cw.hexes[column][arrayRow - 1].critter.direction == 3;
	}

	/**
	 * Tests the random method will randomly assign the value.
	 * Was run multiple times to see it was changing.
	 */
	@Test
	public void testD() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world2.txt");
		int column = 3;
		int row = 3;
		int arrayRow = row - ((column + 1) / 2);
		assert cw.hexes[column][arrayRow].critter.mem[8] == 0;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.mem[8] >= 0;
		assert cw.hexes[column][arrayRow].critter.mem[8] < 8;
	}
}
