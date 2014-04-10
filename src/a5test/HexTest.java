package a5test;

import org.junit.*;

import a5.*;

/**
 * Test cases for the Hex class.
 */
public class HexTest {

	/**
	 * Tests the Hex that contains a rock.
	 */
	@Test
	public void testA() {
		Hex h = new Hex();
		h.rock = true;
		assert !h.isFree();
		assert h.determineContents(false) == -1;
		assert h.getWorldInfo().equals("# ");
	}

	/**
	 * Tests the Hex that contains a critter.
	 */
	@Test
	public void testB() {
		Hex h = new Hex();
		h.food = 9001;
		assert h.isFree();
		assert h.determineContents(false) == -9002;
		assert h.getWorldInfo().equals("F ");
	}

	/**
	 * Tests the Hex that contains food.
	 */
	@Test
	public void testC() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		Hex h = cw.hexes[2][4];
		assert h.critter != null;
		assert !h.isFree();
		assert h.determineContents(false) == 1173;
		assert h.getWorldInfo().equals("C3");
	}

	/**
	 * Tests the Hex that contains a critter and food.
	 */
	@Test
	public void testD() {
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		Hex h = cw.hexes[2][4];
		h.food = 9001;
		assert h.critter != null;
		assert !h.isFree();
		assert h.determineContents(false) == 1173;
		assert h.determineContents(true) == -9002;
		assert h.getWorldInfo().equals("G3");
	}
}
