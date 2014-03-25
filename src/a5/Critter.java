package a5;
import ast.*;

public class Critter {
	CritterWorld critterworld;
	Program program;
	int[] mem;
	int direction;
	int column;
	int row;
	
	public Critter(String file, int direction, CritterWorld critterworld){
		//give rules to the critter 
		//parseFile();
		this.direction = direction;
		this.critterworld = critterworld;
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
	}
	
	public void eat(){
		if (mem[4] + critterworld.hexes[column][row].food > Constants.ENERGY_PER_SIZE*mem[3]){
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
	
	
}
