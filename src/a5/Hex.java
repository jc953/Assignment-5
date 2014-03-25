package a5;

public class Hex {
	int food = 0;
	boolean rock = false;
	Critter critter = null;
	
	public Critter getCritter(){
		return critter;
	}
	
	public void setRock(){
		rock = true;
	}
	
	public void setCritter(Critter c){
		critter = c;
	}
	
	public boolean isFree(){
		return (!rock && critter==null);
	}
	
	
	public String getInfo(){
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
}
