package a5;

import java.io.*;
import java.util.ArrayList;

public class CritterWorld {
	Hex[][] hexes;
	ArrayList<Critter> critters;
	int steps;
	
	public CritterWorld(String file) throws FileNotFoundException{
		hexes = new Hex[Constants.MAX_COLUMN][Constants.MAX_ROW-Constants.MAX_COLUMN/2];
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void createRock(String line){
		String[] str = line.split(" ");
		if (str.length != 3){
			System.out.println("Please enter a file with correct syntax.");
			throw new RuntimeException();
		}
		int row = Integer.parseInt(str[1]);
		int column = Integer.parseInt(str[2]);
		int arrayRow = row - ((column+1)/2);
		hexes[column][arrayRow].setRock();
	}
	
	public void createCritter(String line){
		String[] str = line.split(" ");
		if (str.length != 5){
			System.out.println("Please enter a file with correct syntax.");
			throw new RuntimeException();
		}
		int row = Integer.parseInt(str[2]);
		int column = Integer.parseInt(str[3]);
		int arrayRow = row - ((column+1)/2);
		Critter c = new Critter(str[1], Integer.parseInt(str[4]), column, arrayRow, this);
		hexes[column][arrayRow].setCritter(c);
		critters.add(c);
	}
	
	public void step(){
		for (Critter c : critters){
			c.step();
		}
		steps++;
	}
	
	public void info(){
		System.out.println(steps + " steps have elapsed.");
		System.out.println(critters.size() + " critters are alive.");
		if (hexes.length == 1){
			for (int i = hexes[0].length-1; i >= 0; i--){
				System.out.println(hexes[0][i].getInfo()+"\n");
			}
		}
		for (int i = hexes[0].length-1; i >= 0; i--){
			for (int j = 1; j < hexes.length; j += 2){
				System.out.print("  "+hexes[j][i].getInfo());
			}
			System.out.println();
			for (int j = 0; j < hexes.length; j += 2){
				System.out.print(hexes[j][i].getInfo()+"  ");
			}
			System.out.println();
		}
	}
	
	public void kill(Critter c){
		int column = c.column;
		int row = c.row;
		critters.remove(c);
		hexes[column][row].setCritter(null);
		hexes[column][row].setFood(c.mem[3]*Constants.FOOD_PER_SIZE);
		
	}
}
