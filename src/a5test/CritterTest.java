package a5test;

import org.junit.Test;

import a5.*;

public class CritterTest {
	
	//wait
	@Test
	public void testA(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int row = 5;
		int column = 2;
		int arrayRow = row - (column+1)/2;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 495;
		Critter c = cw.hexes[column][arrayRow].critter;
		int d = cw.hexes[column][arrayRow].critter.direction;
		for(int i=0;i<5;i++){
			cw.step();
		}
		assert cw.hexes[column][arrayRow].critter.mem[4] == 500;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.mem[4] == 500;
		assert cw.hexes[column][arrayRow].critter.direction == d;
		assert cw.hexes[column][arrayRow].critter.equals(c);
	}
	
	//move + turn + moving to rock
	@Test
	public void testB(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 3;
		int row = 4;
		int arrayRow = row - ((column+1)/2);
		Critter c = cw.hexes[column][arrayRow].critter;
		int d = cw.hexes[column][arrayRow].critter.direction;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 500;
		cw.step();
		assert cw.hexes[column+1][arrayRow+1].critter.equals(c);
		assert cw.hexes[column+1][arrayRow+1].critter.direction == d;
		assert cw.hexes[column+1][arrayRow+1].critter.mem[4] == 497;
		cw.step();
		assert cw.hexes[column+1][arrayRow+1].critter.equals(c);
		assert cw.hexes[column+1][arrayRow+1].critter.direction == 0;
		assert cw.hexes[column+1][arrayRow+1].critter.mem[4] == 496;
		cw.step();
		assert cw.hexes[column+1][arrayRow+1].critter.equals(c);
		assert cw.hexes[column+1][arrayRow+1].critter.direction == 0;
		assert cw.hexes[column+1][arrayRow+1].critter.mem[4] == 493;
	}
	
	//grow
	@Test
	public void testC(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int row = 4;
		int column = 4;
		int arrayRow = row - ((column+1)/2);
		Critter c = cw.hexes[column][arrayRow].critter;
		cw.step();
		assert cw.hexes[column][arrayRow].critter.equals(c);
		assert cw.hexes[column][arrayRow].critter.mem[3] == 2;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 230;
		cw.step();
		assert cw.hexes[column][arrayRow].critter == null;
		assert cw.hexes[column][arrayRow].food == 400;
	}
	
	//attack
	@Test
	public void testD(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 10;
		int row = 11;
		int arrayRow = row - ((column+1)/2);
		cw.step();
		assert cw.hexes[column][arrayRow].critter.mem[4] == 2475;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 1373;
	}
	
	//bud successful, bud where parent dies but kid still comes out.
	@Test
	public void testE(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 1;
		int row = 7;
		int arrayRow = row - ((column+1)/2);
		assert cw.hexes[column][arrayRow].critter.mem[4] == 2363;
		assert cw.hexes[column][arrayRow-1].critter == null;
		cw.step(); 
		assert cw.hexes[column][arrayRow-1].critter != null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 1184;
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
	}
	
	//bud unsuccessful due to rock - no baby but parent still loses energy
		@Test
		public void testF(){
			Constants.read("src/constants.txt");
			CritterWorld cw = new CritterWorld("src/world.txt");
			int column = 1;
			int row = 7;
			int arrayRow = row - ((column+1)/2);
			cw.hexes[column][arrayRow-1].rock = true;
			assert cw.hexes[column][arrayRow].critter.mem[4] == 2363;
			assert cw.hexes[column][arrayRow-1].critter == null;
			cw.step(); 
			assert cw.hexes[column][arrayRow-1].critter == null;
			assert cw.hexes[column][arrayRow].critter.mem[4] == 1184;
		}
	
	//mate
	@Test
	public void testG(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 6;
		int row = 5;
		int arrayRow = row - ((column+1)/2);
		assert cw.hexes[column][arrayRow-1].critter == null;
		assert cw.hexes[column][arrayRow+2].critter == null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 3359;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 3359;
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
	}
	
	//mate with one rock
	@Test
	public void testH(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 6;
		int row = 5;
		int arrayRow = row - ((column+1)/2);
		cw.hexes[column][arrayRow-1].rock = true;
		assert cw.hexes[column][arrayRow+2].critter == null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 3359;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 3359;
		cw.step(); 
		assert cw.hexes[column][arrayRow+2].critter != null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 2724;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2474;
		Critter baby = cw.hexes[column][arrayRow+2].critter;
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
	}
	
	//mate with 2 rocks
	@Test
	public void testI(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 6;
		int row = 5;
		int arrayRow = row - ((column+1)/2);
		cw.hexes[column][arrayRow-1].rock = true;
		cw.hexes[column][arrayRow+2].rock = true;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 3359;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 3359;
		cw.step(); 
		assert cw.hexes[column][arrayRow+2].critter == null && cw.hexes[column][arrayRow-1].critter == null;
		assert cw.hexes[column][arrayRow].critter.mem[4] == 3352;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 3352;
	}
	
	//eat tag serve
	@Test
	public void testJ(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 8;
		int row = 7;
		int arrayRow = row - ((column+1)/2);
		cw.step();
		assert cw.hexes[column][arrayRow+1].critter.mem[6] == 47;
		assert cw.hexes[column][arrayRow+1].food == 34;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2320;
		cw.step();
		assert cw.hexes[column][arrayRow+1].food == 0;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2349;
		cw.hexes[column][arrayRow+1].food = 1000;
		cw.step();
		assert cw.hexes[column][arrayRow+1].food == 844;
		assert cw.hexes[column][arrayRow+1].critter.mem[4] == 2500;
		
	}
	/*
	 * Test for updates: nearby, ahead and random
	 */
	@Test
	public void testK(){
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		int column = 4;
		int row = 3;
		int arrayRow = row - ((column+1)/2);
		System.out.println(cw.hexes[column][arrayRow].getWorldInfo());
		cw.step();
		System.out.println(cw.hexes[column][arrayRow].critter.mem[8]);// == 8;
		//assert cw.hexes[column][arrayRow+1].critter.mem[3] == 2;
		
		
	}
}
