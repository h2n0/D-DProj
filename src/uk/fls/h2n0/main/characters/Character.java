package uk.fls.h2n0.main.characters;

public class Character {

	private String name;
	private Race r;
	
	public Character(){
		
	}
	
	public void setRace(Race r){
		this.r = r;
	}
	
	public Race getRace(){
		return this.r;
	}
	
	public String getName(){
		return this.name;
	}
}
