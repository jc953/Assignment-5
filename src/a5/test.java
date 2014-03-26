package a5;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) throws FileNotFoundException{
		Constants.read("src/constants.txt");
		CritterWorld cw = new CritterWorld("src/world.txt");
		cw.info();
	}
}
