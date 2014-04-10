package a5;

import java.io.*;
import java.util.ArrayList;

/**
 * The CritterWorld class is a representation of a critter world. This contains
 * a 2D array representing every hex in the world and keeps track of the critters
 * currently alive.
 */
public class CritterWorld {
	public Hex[][] hexes;
	public ArrayList<Critter> critters;
	int steps;
	
	/**
	 * Uses a text file to create a critter world. It will read in the rocks 
	 * and critters and create them using the createRock and createCritter 
	 * helper methods. 
	 * 
	 * @param file the text file that will be used to create the critter world.
	 */
	public CritterWorld(String file){
		hexes = new Hex[Constants.MAX_COLUMN][Constants.MAX_ARRAY_ROW];
		for (int i = 0; i < hexes.length; i++){
			for (int j = 0; j < hexes[0].length; j++){
				hexes[i][j] = new Hex();
			}
		}
		critters = new ArrayList<Critter>();
		steps = 0;
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = br.readLine();
			while (line != null){
				if (line.charAt(0)=='r'){
					createRock(line);
				} else if (line.charAt(0)=='c'){
					createCritter(line);
				} else {
					System.out.println("Please enter a file with correct syntax.");
					throw new RuntimeException();
				}
				line = br.readLine();
			}
			br.close();
		} catch (IOException e) {
			System.out.println("No file found");
		}
	}
	
	/**
	 * Creates a critter world with randomized rocks. The number of randomly
	 * generated rocks is determined by the total number of hexes in the world.
	 */
	public CritterWorld(){
		hexes = new Hex[Constants.MAX_COLUMN][Constants.MAX_ARRAY_ROW];
		for (int i = 0; i < hexes.length; i++){
			for (int j = 0; j < hexes[0].length; j++){
				hexes[i][j] = new Hex();
			}
		}
		critters = new ArrayList<Critter>();
		steps = 0;
		int numberRocks = Constants.MAX_COLUMN*Constants.MAX_ARRAY_ROW/10;
		for (int i = 0; i < numberRocks; i++){
			int col = (int)(Math.random() * hexes.length);
			int row = (int)(Math.random() * hexes[0].length);
			while (hexes[col][row].rock){
				col = (int)(Math.random() * hexes.length);
				row = (int)(Math.random() * hexes[0].length);
			}
			hexes[col][row].rock = true;
		}
	}
	
	/**
	 * Uses a text file to create a rock. Creates a rock at the chosen column 
	 * and row, as long as they are within the boundaries.
	 * 
	 * @param line the text file that will be used to create the rock.
	 */
	public void createRock(String line){
		String[] str = line.split(" ");
		if (str.length != 3){
			System.out.println("Please enter a file with correct syntax.");
			throw new RuntimeException();
		}
		int row = Integer.parseInt(str[1]);
		int column = Integer.parseInt(str[2]);
		int arrayRow = row - ((column+1)/2);
		if (column < 0 || column >= Constants.MAX_COLUMN || arrayRow < 0 || arrayRow >= Constants.MAX_ARRAY_ROW){
			System.out.println("A rock you tried to create breached the boundaries.");
			return;
		}
		hexes[column][arrayRow].rock = true;
	}
	
	/**
	 * Uses a text file to create a critter. Creates a critter with a program,
	 * direction, column, and row, all specified by the text, as long as they
	 * are within the boundaries.
	 * 
	 * @param line the text file that will be used to create the critter.
	 */
	public void createCritter(String line){
		String[] str = line.split(" ");
		if (str.length != 5){
			System.out.println("Please enter a file with correct syntax.");
			throw new RuntimeException();
		}
		int row = Integer.parseInt(str[2]);
		int column = Integer.parseInt(str[3]);
		int arrayRow = row - ((column+1)/2);
		if (column < 0 || column >= Constants.MAX_COLUMN || arrayRow < 0 || arrayRow >= Constants.MAX_ARRAY_ROW){
			System.out.println("A critter you tried to create breached the boundaries.");
			return;
		}
		if (hexes[column][arrayRow].isFree()){
			Critter c = new Critter(str[1], Integer.parseInt(str[4]), column, arrayRow, this);
			hexes[column][arrayRow].critter = c;
			critters.add(c);
		}
	}
	
	/**
	 * Uses a text file to create a critter on a randomly chosen free hex.
	 * 
	 * @param filename the text file that will be used to create the critter.
	 */
	public void addRandomCritter(String filename){
		int col = (int)(Math.random() * hexes.length);
		int row = (int)(Math.random() * hexes[0].length);
		while (!hexes[col][row].isFree()){
			col = (int)(Math.random() * hexes.length);
			row = (int)(Math.random() * hexes[0].length);
		}
		Critter c = new Critter(filename, 0, col, row, this);
		hexes[col][row].critter = c;
		critters.add(c);
	}

	/**
	 * Adds a Critter to the critter list at the given column and row. Mainly
	 * used when a Critter buds or mates.
	 * 
	 * @param c the Critter that is added.
	 * @param column the column that the Critter is located in.
	 * @param arrayRow the row that the Critter is located in.
	 */
	public void addCritter(Critter c, int column, int arrayRow){
		if (column < 0 || column >= Constants.MAX_COLUMN ||arrayRow < 0 || arrayRow >= Constants.MAX_ARRAY_ROW){
			System.out.println("A critter you tried to create breached the boundaries.");
			return;
		}
		if (hexes[column][arrayRow].isFree()){
			hexes[column][arrayRow].critter = c;
			critters.add(c);
		}
	}
	
	/**
	 * Steps through each critter in the order that they were created. Increases 
	 * the step count by one.
	 */
	public void step(){
		ArrayList<Critter> tempCrits = new ArrayList<Critter>(critters);
		for (Critter c : tempCrits){
			if (c!=null){
				c.step();
			}
		}
		steps++;
	}
	
	/**
	 * Removes a critter from the critters list and from the hex. Also creates
	 * food on the hex where the critter died.
	 * 
	 * @param c the Critter to be killed.
	 */
	public void kill(Critter c){
		int column = c.column;
		int row = c.row;
		critters.remove(c);
		hexes[column][row].critter = null;
		hexes[column][row].food = c.mem[3]*Constants.FOOD_PER_SIZE;
	}
	
	/**
	 * Prints the info of this critter world to the console. Contains information
	 * on the time steps elapsed and number of critters alive. Portrays an ASCII 
	 * map of the world using the information of each Hex. Since the world is
	 * a hexagonal grid, each column is staggered by one line.
	 */
	public void info(){
		System.out.println(steps==1 ? steps + " step has elapsed.": steps + " steps have elapsed.");
		System.out.println(critters.size()==1 ?critters.size() + " critter is alive." : critters.size() + " critters are alive");
		if (hexes.length == 1){
			for (int i = hexes[0].length-1; i >= 0; i--){
				System.out.println(hexes[0][i].getWorldInfo()+"\n");
			}
		}
		for (int i = hexes[0].length-1; i >= 0; i--){
			for (int j = 1; j < hexes.length; j += 2){
				System.out.print("  "+hexes[j][i].getWorldInfo());
			}
			System.out.println();
			for (int j = 0; j < hexes.length; j += 2){
				System.out.print(hexes[j][i].getWorldInfo()+"  ");
			}
			System.out.println();
		}
	}
}
