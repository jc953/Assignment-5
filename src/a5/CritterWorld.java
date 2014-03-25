package a5;

import java.io.*;
import ast.*;
import parse.*;

public class CritterWorld {
	Hex[][] hexes;
	
	public CritterWorld(String file) throws FileNotFoundException{
		hexes = new Hex[Constants.MAX_COLUMN][Constants.MAX_ROW-Constants.MAX_COLUMN/2];
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
		
	}
	
	public void step(){
		for(int i=0;i<hexes.length;i++){
			for(int j=0;j<hexes[i].length;j++){
				if (hexes[i][j].getCritter()!=null){
					hexes[i][j].getCritter().step();
				}
			}
		}
	}
}
