package a5;
import ast.*;

public class Critter {
	CritterWorld critterworld;
	Program program;
	int[] mem;
	int direction;
	int column;
	int row;
	int nextColumn;
	int nextRow;
	
	public Critter(String file, int direction, int column, int row, CritterWorld critterworld){
		//give rules to the critter 
		//parseFile();
		this.direction = direction;
		this.critterworld = critterworld;
		this.column = column;
		this.row = row;
		switch(direction){
			case 0: nextColumn = column; nextRow = row +1;
			case 1: nextColumn = column; nextRow = row +1;
			case 2: nextColumn = column; nextRow = row +1;
			case 3: nextColumn = column; nextRow = row +1;
			case 4: nextColumn = column; nextRow = row +1;
			case 5: nextColumn = column; nextRow = row +1;
		}
	}
	
	public void step(){
		
	}
	
	public void waitTurn(){
		mem[4] = mem[4] + Constants.SOLAR_FLUX;
	}
	
	public void move(){
		//move
		//take energy
	}
	
	public void turn(int n){
		assert (n == 1 || n == -1);
		direction = direction + n;
		//change nextColumn and nextRow
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
	
	public boolean grow(){
		if (Constants.GROW_COST>=mem[4]){
			return false;
		}
		else{
			mem[3]++;
			mem[4] -= Constants.GROW_COST;
			return true;
		}
	}
	
	public boolean bud(){
		//figure out the coordinates of the hex behind
		if (Constants.BUD_COST>=mem[4]){ //|| hex behind.isFree()){
			return false;
		}
		else{
			mem[4] -= Constants.BUD_COST;
			//initialize a new critter in the space 
			//behind. Make sure to add it to critterworld.hexes
			return true;
		}
	}
	
	public boolean mate(){
		if (direction + critterworld.hexes[nextColumn][nextRow].getCritter().direction == 5){
		
		}
	}
}
