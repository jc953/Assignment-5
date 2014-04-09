package a5;

public class Hex {
	int food = 0;
	boolean rock = false;
	Critter critter = null;
	
	public boolean isFree(){
		return (!rock && critter==null);
	}
	
	public int determineContents(boolean ignoreCritter){
		if (critter==null && !rock && food==0){
			return 0;
		}
		if(rock) return -1;
		else if(critter != null && !ignoreCritter) return critter.mem[7];
		else return -(food+1);
	}
	
	public String getWorldInfo(){
		if (rock){
			return "# ";
		} else if (critter != null && food > 0){
			return "G" + critter.direction;
		} else if (critter != null){
			return "C" + critter.direction;
		} else if (food > 0){
			return "F ";
		} else {
			return "- ";
		}
	}
	
	public void getInfo(){
		if (rock){
			System.out.println("This hex contains a rock.");
			return;
		}
		if (critter != null) {
			critter.getInfo();
		}
		if (food > 0){
			System.out.println("This hex contains " + food + " food.");
		} else {
			System.out.println("This hex does not contain food.");
		}
	}
}
