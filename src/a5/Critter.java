package a5;

import java.io.*;
import java.util.Arrays;

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
	boolean matePossible;

	public Critter(String file, int direction, int column, int row, CritterWorld critterworld) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(file)));
			int[] memNums = new int[6];
			for (int i = 0; i < memNums.length; i++){
				String line = br.readLine();
				String[] str = line.split(" ");
				if (str.length != 2) throw new FileNotFoundException();
				memNums[i] = Integer.parseInt(str[1]);
			}
			mem = new int[memNums[0]];
			for (int i = 0; i < memNums.length - 1; i++){
				mem[i] = memNums[i];
			}
			mem[5] = 0;
			mem[6] = 0;
			mem[7] = memNums[5];
			for (int i = 8; i < mem[0]; i++){
				mem[i] = 0;
			}
			ParserImpl p = new ParserImpl();
			program = p.parse(br);
			this.direction = direction;
			this.critterworld = critterworld;
			this.column = column;
			this.row = row;
			lastRule = null;
			matePossible = false;
			br.close();
		} catch (FileNotFoundException e) {
			System.out.println("Error in producing a Critter.");
		} catch (IOException e) {
			System.out.println("Error in producing a Critter.");
		}
	}

	public Critter(Program program, int[] mem, int direction, int column, int row, CritterWorld critterworld) {
		this.mem = mem;
		mem[3] = 1;
		mem[4] = Constants.INITIAL_ENERGY;
		for (int i = 6; i < mem[0]; i++){
			mem[i] = 0;
		}
		this.program = program;
		this.direction = direction;
		this.column = column;
		this.row = row;
		this.critterworld = critterworld;
		lastRule = null;
		matePossible = false;
	}

	public void step() {
		mem[5] = 1;

	}

	/**
	 * 
	 * returns int array [nextColumn, nextRow, prevColumn, prevRow]
	 * 
	 * @param column
	 * @param row
	 * @return
	 */
	public int[] getAdjacentPositions(int column, int row) {
		int[] result = new int[4];
		if (column % 2 == 0) {
			switch (direction) {
			case 0:
				result[0] = column;
				result[1] = row + 1;
				result[2] = column;
				result[3] = row - 1;
				break;
			case 1:
				result[0] = column+1;
				result[1] = row;
				result[2] = column-1;
				result[3] = row - 1;
				break;
			case 2:
				result[0] = column+1;
				result[1] = row-1;
				result[2] = column-1;
				result[3] = row;
				break;
			case 3:
				result[0] = column;
				result[1] = row-1;
				result[2] = column;
				result[3] = row+1;
				break;
			case 4:
				result[0] = column-1;
				result[1] = row-1;
				result[2] = column+1;
				result[3] = row;
				break;
			case 5:
				result[0] = column-1;
				result[1] = row;
				result[2] = column+1;
				result[3] = row-1;
				break;
			}
		} else {
			switch (direction) {
			case 0:
				result[0] = column;
				result[1] = row + 1;
				result[2] = column;
				result[3] = row - 1;
				break;
			case 1:
				result[0] = column+1;
				result[1] = row + 1;
				result[2] = column-1;
				result[3] = row;
				break;
			case 2:
				result[0] = column+1;
				result[1] = row;
				result[2] = column-1;
				result[3] = row+1;
				break;
			case 3:
				result[0] = column;
				result[1] = row-1;
				result[2] = column;
				result[3] = row+1;
				break;
			case 4:
				result[0] = column-1;
				result[1] = row;
				result[2] = column+1;
				result[3] = row+1;
				break;
			case 5:
				result[0] = column-1;
				result[1] = row+1;
				result[2] = column+1;
				result[3] = row;
				break;
			}
		}
		return result;
	}
	
	public int getComplexity(){
		int a = program.rules.size() * Constants.RULE_COST;
		int b = (mem[1]+mem[2]) * Constants.ABILITY_COST;
		return a+b;
	}

	public void waitTurn() {
		mem[4] = mem[4] + Constants.SOLAR_FLUX;
	}
	
	public void move(int forOrBack) {
		assert (forOrBack == -1 || forOrBack == 1);
		if (Constants.MOVE_COST >= mem[4]) {
			critterworld.kill(this);
			return;
		}
		mem[4] -= Constants.MOVE_COST;
		int[] pos = getAdjacentPositions(column, row);
		if (forOrBack == -1) {
			if (!critterworld.hexes[pos[2]][pos[3]].isFree()) {
				return;
			} else {
				critterworld.hexes[column][row].critter = null;
				critterworld.hexes[pos[2]][pos[3]].critter = this;
				column = pos[2];
				row = pos[3];
				return;
			}
		} else {
			if (!critterworld.hexes[pos[0]][pos[1]].isFree()) {
				return;
			} else {
				critterworld.hexes[column][row].critter = null;
				critterworld.hexes[pos[0]][pos[1]].critter = this;
				column = pos[0];
				row = pos[1];
				return;
			}
		}
	}

	public void turn(int n) {
		assert (n == 1 || n == -1);
		direction = direction + n;
		mem[4] -= mem[3];
		if (mem[4] <= 0){
			critterworld.kill(this);
		}
	}

	public void eat() {
		mem[4] -= mem[3];
		if (mem[4] + critterworld.hexes[column][row].food > Constants.ENERGY_PER_SIZE * mem[3]) {
			int difference = Constants.ENERGY_PER_SIZE * mem[3] - mem[4];
			mem[4] = Constants.ENERGY_PER_SIZE * mem[3];
			critterworld.hexes[column][row].food = critterworld.hexes[column][row].food - difference;
		} else {
			mem[4] = mem[4] + critterworld.hexes[column][row].food;
			critterworld.hexes[column][row].food = 0;
		}
		if (mem[4] <= 0){
			critterworld.kill(this);
		}
	}

	public void serve(int amountServed) {
		if (amountServed < mem[4]) {
			mem[4] -= amountServed;
			critterworld.hexes[column][row].food += amountServed + mem[3];
		} else {
			critterworld.hexes[column][row].food += mem[4];
			critterworld.kill(this);
		}
	}

	public void attack() {
		int[] pos = getAdjacentPositions(column, row);
		if (critterworld.hexes[pos[0]][pos[1]].getCritter() != null) {
			critterworld.hexes[pos[0]][pos[1]].getCritter().attacked(this);
			mem[4] -= mem[3] * Constants.ATTACK_COST;
		}
	}

	public void attacked(Critter attacker) {
		double x = Constants.DAMAGE_INC * ((attacker.mem[3] * attacker.mem[2])-(mem[3]*mem[1]));
		double p = 1.0 / (1.0 + Math.pow(Math.E, -x));
		double damage = Constants.BASE_DAMAGE * attacker.mem[3] * p;
		if (damage >= mem[4]) {
			critterworld.kill(this);
		} else {
			mem[4] = mem[4] - (int)(damage);
		}
	}

	public void tag(int tagNumber) {
		assert (tagNumber <= 99 && tagNumber >= 0);
		mem[4] -= mem[3];
		int[] pos = getAdjacentPositions(column, row);
		if (critterworld.hexes[pos[0]][pos[1]].getCritter() != null) {
			critterworld.hexes[pos[0]][pos[1]].getCritter().mem[6] = tagNumber;
		}
	}

	public void grow() {
		if (mem[3]*getComplexity()*Constants.GROW_COST >= mem[4]) {
			critterworld.kill(this);
			return;
		} else {
			mem[3]++;
			mem[4] -= mem[3]*getComplexity()*Constants.GROW_COST;
		}
	}

	public boolean bud() {
		mem[4] -= Constants.BUD_COST * getComplexity();
		if (mem[4] < 0){
			critterworld.kill(this);
			return false;
		}
		int[] pos = getAdjacentPositions(column, row);
		if (!critterworld.hexes[pos[2]][pos[3]].isFree()) {
			return false;
		} else {
			Program tempProg = program.dup(program);
			int[] tempMem = mem;
			while (Math.random() < 0.25){
				if (Math.random() < 0.5) {
					Mutation.mutate(tempProg);
				} else {
					tempMem = mutateAttributes(mem);
				}
			}
			critterworld.hexes[pos[2]][pos[3]].setCritter(new Critter(tempProg, tempMem, direction, pos[2], pos[3], critterworld));
			if (mem[4] == 0)
				critterworld.kill(this);
			return true;
		}
	}


	public boolean mate(){
		int [] pos = getAdjacentPositions(column, row);
		boolean mate = false;
		switch (direction){
			case 0: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 3 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
			case 1: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 4 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
			case 2: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 5 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
			case 3: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 0 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
			case 4: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 1 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
			case 5: 
				if(critterworld.hexes[pos[0]][pos[1]].getCritter().direction == 2 
				&& critterworld.hexes[pos[0]][pos[1]].getCritter().matePossible == true) mate = true;
				break;
		}
		
		if (!mate){
			matePossible = true;
			return false;//Ask Jon
		}
		//do the energy mate cost thing here
		int [] tempMem;
		if (Math.random()<0.5){
			tempMem = new int[mem[0]];
		}
		else{
			tempMem = new int[critterworld.hexes[pos[0]][pos[1]].getCritter().mem[0]];
		}
		
		while (Math.random() < 0.25){
			if (Math.random() < 0.5) {
				Mutation.mutate(tempProg);
			} else {
				tempMem = mutateAttributes(mem);
			}
		}
		
	}
	
	public int[] mutateAttributes(int[] mem){
		int[] result = Arrays.copyOf(mem, mem.length);
		double i = Math.random();
		if (i < 1.0/3){
			if (result[0] == 8){
				result[0]++;
				result = Arrays.copyOf(result, result[0]);
				return result;
			}
			if (Math.random() < 0.5){
				result[0]++;
			} else {
				result[0]--;
			}
			result = Arrays.copyOf(result, result[0]);
			return result;
		} else if (i < 2.0 / 3){
			if (result[1] == 1){
				result[1]++;
				return result;
			}
			if (Math.random() < 0.5){
				result[1]++;
			} else {
				result[1]--;
			}
			return result;
		} else {
			if (result[2] == 1){
				result[2]++;
				return result;
			}
			if (Math.random() < 0.5){
				result[2]++;
			} else {
				result[2]--;
			}
			return result;
		}
	}

	public int nearby(int dir) {
		int originalDir = direction;
		direction = (direction + dir) % 6;
		int pos[] = getAdjacentPositions(column, row);
		int ans = critterworld.hexes[pos[0]][pos[1]].determineContents(false);
		direction = originalDir;
		return ans;
	}

	public int ahead(int dist) {
		if (dist == 0)
			return this.mem[7];
		if (dist == -1)
			return critterworld.hexes[column][row].determineContents(true);
		if (dist < -1)
			dist = -dist - 1;
		int tempCol = column;
		int tempRow = row;
		for (int i = 1; i < Math.abs(dist); i++) {
			int[] pos = getAdjacentPositions(tempCol, tempRow);
			tempCol = pos[0];
			tempRow = pos[1];
		}
		int ans = critterworld.hexes[tempCol][tempRow].determineContents(false);
		return ans;
	}

	public void getInfo() {
		System.out.println("This hex contains a critter.");
		System.out.println("MEMSIZE : " + mem[0]);
		System.out.println("DEFENSE : " + mem[1]);
		System.out.println("OFFENSE : " + mem[2]);
		System.out.println("SIZE : " + mem[3]);
		System.out.println("ENERGY : " + mem[4]);
		System.out.println("PASS : " + mem[5]);
		System.out.println("TAG : " + mem[6]);
		System.out.println("POSTURE : " + mem[7]);
		for (int i = 8; i < mem.length; i++) {
			System.out.println("mem[" + i + "] : " + mem[i]);
		}
		StringBuffer sb = new StringBuffer();
		program.prettyPrint(sb);
		System.out.println(sb);
		if (lastRule != null){
			lastRule.prettyPrint(sb);
			System.out.println(sb);
		} else {
			System.out.println("This critter has not performed a rule yet.");
		}
	}
}
