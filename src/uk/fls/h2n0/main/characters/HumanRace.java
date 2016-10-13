package uk.fls.h2n0.main.characters;

public class HumanRace extends Race{

	public HumanRace(){
		super("Human");
		this.minAge = 18;
		this.maxAge = 95;
		this.minSize = 5f;
		this.maxSize = 6f;
		this.speed = 30;
	}
}
