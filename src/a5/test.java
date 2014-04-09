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
		
		
	
	}
	
}
