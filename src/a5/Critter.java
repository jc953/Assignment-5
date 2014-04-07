package a5;
import java.io.*;

import parse.*;
import ast.*;

public class Critter {
	CritterWorld critterworld;
	Program program;
	Rule lastRule;
	public int[] mem;
	int direction;
	int column;
	int row;
	int nextColumn;
	int nextRow;
	int previousColumn;
	int previousRow;
	int appearance;
	
	public Critter(String file, int direction, int column, int row, CritterWorld critterworld){
		try {
			BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			ParserImpl p = new ParserImpl();
			program = p.parse(br);
			this.direction = direction;
			this.critterworld = critterworld;
			setPosition(column,row);
			
		
		} catch (FileNotFoundException e) {
			System.out.println("Error");
		}
	}
	
	public Critter(){
		
	}
	
	public void step(){
		mem[5]=1;
		
		
	}
	
	public void waitTurn(){
		mem[4] = mem[4] + Constants.SOLAR_FLUX;
	}
	
	public void setPosition(int column, int row){
		this.column = column;
		this.row = row;
		if(column%2==0){
			switch(direction){
				case 0: nextColumn = column; nextRow = row+1; 
					previousColumn = column-1; previousRow = row; break;
				case 1: nextColumn = column+1; nextRow = row;
					previousColumn = column-1; previousRow = row-1; break;
				case 2:	nextColumn = column+1; nextRow = row-1;
					previousColumn = column-1; previousRow = row; break;
				case 3: nextColumn = column; nextRow = row-1; 
					previousColumn = column; previousRow = row+1; break;
				case 4: nextColumn = column-1; nextRow = row-1;
					previousColumn = column+1; previousRow = row; break;
				case 5: nextColumn = column-1; nextRow = row;
					previousColumn = column+1; previousRow = row-1; break;
			}
			
		}
		else{
			switch(direction){
				case 0: nextColumn = column; nextRow = row+1;
					previousColumn = column-1; previousRow = row+1; break;
				case 1: nextColumn = column+1; nextRow = row+1;
					previousColumn = column-1; previousRow = row; break;
				case 2:	nextColumn = column+1; nextRow = row;
					previousColumn = column-1; previousRow = row+1; break;
				case 3: nextColumn = column; nextRow = row-1;
					previousColumn = column; previousRow = row+1; break;
				case 4: nextColumn = column-1; nextRow = row;
					previousColumn = column+1; previousRow = row+1; break;
				case 5: nextColumn = column-1; nextRow = row+1;
					previousColumn = column+1; previousRow = row; break;
			}
		}
	}
	
	public void move(int forOrBack){
		assert (forOrBack==-1 || forOrBack==1);
		if(Constants.MOVE_COST>=mem[4]){
			critterworld.kill(this);
			return;
		}
		mem[4] -= Constants.MOVE_COST;
		if (forOrBack==-1){
			if(!critterworld.hexes[previousColumn][previousRow].isFree()){
				return;
			}
			else {
				setPosition(previousColumn, previousRow);
				critterworld.hexes[previousColumn][previousRow].setCritter(this);
				return;
			}
		}
		else{
			if(!critterworld.hexes[nextColumn][nextRow].isFree()){
				return;
			}
			else {
				setPosition(nextColumn, nextRow);
				critterworld.hexes[previousColumn][previousRow].setCritter(this);
				return;
			}
		}
	}
	
	public void turn(int n){
		assert (n == 1 || n == -1);
		direction = direction + n;
		setPosition(column,row);
	}
	
	public void eat(){
		if (mem[4] + critterworld.hexes[column][row].food >= Constants.ENERGY_PER_SIZE*mem[3]){
			mem[4] = Constants.ENERGY_PER_SIZE*mem[3];
			critterworld.hexes[column][row].food = critterworld.hexes[column][row].food - mem[4]; 
		}
		else{
			mem[4] = mem[4] + critterworld.hexes[column][row].food;
			critterworld.hexes[column][row].food = 0;
		}
	}
	
	public void serve(int amountServed){
		if(amountServed<mem[4]){
			mem[4]-=amountServed;
			critterworld.hexes[column][row].food += amountServed;
		}
		else{
			critterworld.hexes[column][row].food += mem[4];
			critterworld.kill(this);
		}
	}
	
	
	public void attack(){
		if (critterworld.hexes[nextColumn][nextRow].getCritter() != null){
			critterworld.hexes[nextColumn][nextRow].getCritter().attacked(this);
		}
	}
	
	
	public void attacked(Critter attacker){
		double p = 1.0/(1.0+Math.pow(Math.E, -Constants.DAMAGE_INC*(attacker.mem[3]*attacker.mem[2]-mem[3]*mem[1])));
		if (Constants.BASE_DAMAGE*attacker.mem[3]*p>=mem[4]){
			critterworld.kill(this);
		}
		else{
			mem[4] = mem[4] - (int)(Constants.BASE_DAMAGE*attacker.mem[3]*p);
		}
	}
	
	public void tag(int tagNumber){
		assert (tagNumber <= 99 && tagNumber >= 0 );
		if (critterworld.hexes[nextColumn][nextRow].getCritter() != null){
			critterworld.hexes[nextColumn][nextRow].getCritter().mem[6]=tagNumber;
		}
	}
	
	public void grow(){
		if (Constants.GROW_COST>=mem[4]){
			critterworld.kill(this);
			return;
		}
		else{
			mem[3]++;
			mem[4] -= Constants.GROW_COST;
		}
	}
	
	public boolean bud(){
		//if its greater kill it without child
		//if its less, normal
		//if its equal kill it with child
		if (Constants.BUD_COST>=mem[4]||!critterworld.hexes[previousColumn][previousRow].isFree()){
			return false;
		}
		else{
			mem[4] -= Constants.BUD_COST;
				Program temp = //dup;
			Mutation.mutate(temp);
			critterworld.hexes[previousColumn][previousRow].setCritter(new Critter());
			return true;
		}
	}
	
	public boolean mate(){
		if (direction + critterworld.hexes[nextColumn][nextRow].getCritter().direction == 5){
		
		}
	}
	
	
	public int nearby(int dir){
		dir = dir%6;
		int ans=0;
		for(int i=0;i<Math.abs(dir);i++){
			turn(dir/(Math.abs(dir)));
		}
		Hex hex = critterworld.hexes[nextColumn][nextRow];
		if (hex.critter==null && !hex.rock && hex.food==0){
			ans = 0;
		}
		if(hex.rock) ans = -1;
		if(hex.food>0) ans = -(hex.food+1);
		if(hex.critter != null) ans = hex.critter.appearance;
		for(int i=0;i<Math.abs(dir);i++){
			turn(-dir/(Math.abs(dir)));
		}
		return ans;
	}
	
	public int ahead(int dist){
		
		
	}
	public void getInfo(){
		System.out.println("This hex contains a critter.");
		System.out.println("MEMSIZE : " + mem[0]);
		System.out.println("DEFENSE : " + mem[1]);
		System.out.println("OFFENSE : " + mem[2]);
		System.out.println("SIZE : " + mem[3]);
		System.out.println("ENERGY : " + mem[4]);
		System.out.println("PASS : " + mem[5]);
		System.out.println("TAG : " + mem[6]);
		System.out.println("POSTURE : " + mem[7]);
		for (int i = 8; i < mem.length; i++){
			System.out.println("mem["+i+"] : "+ mem[i]);
		}
		StringBuffer sb = new StringBuffer();
		program.prettyPrint(sb);
		System.out.println(sb);
		lastRule.prettyPrint(sb);
		System.out.println(sb);
	}
}
