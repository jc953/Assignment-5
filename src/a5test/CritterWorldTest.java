package a5test;

import org.junit.*;

import a5.*;

/**
 * Test cases for the CritterWorld class.
 */
public class CritterWorldTest {

	/**
	 * Tests the construction of a CritterWorld using a text file.
	 * Also tests certain methods that the construction uses.
	 */
	@Test
	public void testA() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		assert cw.hexes.length == Constants.MAX_COLUMN;
		assert cw.hexes[0].length == Constants.MAX_ARRAY_ROW;
		assert cw.hexes[2][1].rock;
		assert cw.hexes[2][4].critter != null;
		assert !cw.hexes[20][10].rock;
		cw.createRock("rock 20 20");
		assert cw.hexes[20][10].rock;
		assert cw.hexes[30][5].critter == null;
		cw.createCritter("critter src/wait-critter.txt 20 30 0");
		assert cw.hexes[30][5].critter != null;
	}

	/**
	 * Tests the creation of a CritterWorld without a text file.
	 * Ensures that there are the correct number of rocks.
	 */
	@Test
	public void testB() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld();
		int numberRocks = cw.hexes.length * cw.hexes[0].length / 10;
		int count = 0;
		for (int i = 0; i < cw.hexes.length; i++) {
			for (int j = 0; j < cw.hexes[0].length; j++) {
				if (cw.hexes[i][j].rock) {
					count++;
				}
			}
		}
		assert numberRocks == count;
		assert cw.critters.size() == 0;
		cw.addRandomCritter("src/wait-critter.txt");
		assert cw.critters.size() == 1;
	}

	/**
	 * Tests the methods addCritter and kill.
	 */
	@Test
	public void testC() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		assert cw.hexes[20][20].critter == null;
		Critter c = new Critter("src/wait-critter.txt", 0, 20, 20, cw);
		cw.addCritter(c, 20, 20);
		assert cw.hexes[20][20].critter == c;
		cw.kill(c);
		assert cw.hexes[20][20].critter == null;
	}
}
