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
	
	public Constants(String file){
		try{
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = br.readLine();
			while(line != null){
				String constant = "";
				int i=0;
				for(;line.charAt(i) != ' ';i++){
					constant = constant + line.charAt(i);
				}
				String value = "";
				for(;line.charAt(i) != ' ';i++){
					value = value + line.charAt(i);
				}
				switch(constant){
				case "BASE_DAMAGE": BASE_DAMAGE = new Integer(value);
					break;
				case "DAMAGE_INC": DAMAGE_INC = new Double(value);
					break;
				case "ENERGY_PER_SIZE": ENERGY_PER_SIZE = new Integer(value);
					break;
				case "FOOD_PER_SIZE": FOOD_PER_SIZE = new Integer(value);
					break;
				case "MAX_SMELL_DISTANCE": MAX_SMELL_DISTANCE = new Integer(value);
					break;
				case "ROCK_VALUE": ROCK_VALUE = new Integer(value);
					break;
				case "MAX_COLUMN": MAX_COLUMN = new Integer(value);
					break;
				case "MAX_ROW": MAX_ROW = new Integer(value);
					break;
				case "MAX_RULES_PER_TURN": MAX_RULES_PER_TURN = new Integer(value);
					break;
				case "SOLAR_FLUX": SOLAR_FLUX = new Integer(value);
					break;
				case "MOVE_COST": MOVE_COST = new Integer(value);
					break;
				case "ATTACK_COST": ATTACK_COST = new Integer(value);
					break;
				case "GROW_COST": GROW_COST = new Integer(value);
					break;
				case "BUD_COST": BUD_COST = new Integer(value);
					break;
				case "MATE_COST": MATE_COST = new Integer(value);
					break;
				case "RULE_COST": RULE_COST = new Integer(value);
					break;
				case "ABILITY_COST": ABILITY_COST = new Integer(value);
					break;
				case "INITIAL_ENERGY": INITIAL_ENERGY = new Integer(value);
					break;
				case "MIN_MEMORY": MIN_MEMORY = new Integer(value);
					break;
				default: throw new FileNotFoundException();
				}
			}
		}
		catch(FileNotFoundException fnfe){
			System.out.println("No such file");
		}
		catch(IOException e){
			System.out.println("Error");
		}
	}
	
}
