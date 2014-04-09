package a5;

import java.io.*;

public class Constants {

	static int BASE_DAMAGE;
	static double DAMAGE_INC;
	static int ENERGY_PER_SIZE;
	static int FOOD_PER_SIZE;
	static int MAX_SMELL_DISTANCE;
	static int ROCK_VALUE;
	static int MAX_COLUMN;
	static int MAX_ROW;
	static int MAX_ARRAY_ROW;
	static int MAX_RULES_PER_TURN;
	static int SOLAR_FLUX;
	static int MOVE_COST;
	static int ATTACK_COST;
	static int GROW_COST;
	static int BUD_COST;
	static int MATE_COST;
	static int RULE_COST;
	static int ABILITY_COST;
	static int INITIAL_ENERGY;
	static int MIN_MEMORY;
	
	public static void read(String file){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = br.readLine();
			while(line != null){
				String constant = "";
				int i=0;
				for(;line.charAt(i) != ' ';i++){
					constant = constant + line.charAt(i);
				}
				i++;
				String value = "";
				for(;line.charAt(i) != ' ';i++){
					value = value + line.charAt(i);
				}
				switch(constant){
				case "BASE_DAMAGE": BASE_DAMAGE = Integer.parseInt(value);
					break;
				case "DAMAGE_INC": DAMAGE_INC = Double.parseDouble(value);
					break;
				case "ENERGY_PER_SIZE": ENERGY_PER_SIZE = Integer.parseInt(value);
					break;
				case "FOOD_PER_SIZE": FOOD_PER_SIZE = Integer.parseInt(value);
					break;
				case "MAX_SMELL_DISTANCE": MAX_SMELL_DISTANCE = Integer.parseInt(value);
					break;
				case "ROCK_VALUE": ROCK_VALUE = Integer.parseInt(value);
					break;
				case "MAX_COLUMN": MAX_COLUMN = Integer.parseInt(value);
					break;
				case "MAX_ROW": MAX_ROW = Integer.parseInt(value);
					break;
				case "MAX_RULES_PER_TURN": MAX_RULES_PER_TURN = Integer.parseInt(value);
					break;
				case "SOLAR_FLUX": SOLAR_FLUX = Integer.parseInt(value);
					break;
				case "MOVE_COST": MOVE_COST = Integer.parseInt(value);
					break;
				case "ATTACK_COST": ATTACK_COST = Integer.parseInt(value);
					break;
				case "GROW_COST": GROW_COST = Integer.parseInt(value);
					break;
				case "BUD_COST": BUD_COST = Integer.parseInt(value);
					break;
				case "MATE_COST": MATE_COST = Integer.parseInt(value);
					break;
				case "RULE_COST": RULE_COST = Integer.parseInt(value);
					break;
				case "ABILITY_COST": ABILITY_COST = Integer.parseInt(value);
					break;
				case "INITIAL_ENERGY": INITIAL_ENERGY = Integer.parseInt(value);
					break;
				case "MIN_MEMORY": MIN_MEMORY = Integer.parseInt(value);
					break;
				default: throw new FileNotFoundException();
				}
				
				line = br.readLine();
			}
			MAX_ARRAY_ROW = MAX_ROW-MAX_COLUMN/2;
			br.close();
		}
		catch(FileNotFoundException fnfe){
			System.out.println("No such file");
		}
		catch(IOException e){
			System.out.println("Error");
		}
	}
	
}
