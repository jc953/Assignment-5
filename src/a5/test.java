package a5;

import java.io.FileNotFoundException;

public class test {
	public static void main(String[] args) throws FileNotFoundException{
		Constants.read("constants.txt");
		CritterWorld cw = new CritterWorld("C:\\Users\\one80_000\\world.txt");
		cw.info();
	}
}
