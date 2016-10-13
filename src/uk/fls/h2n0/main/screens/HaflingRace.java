package uk.fls.h2n0.main.screens;

import uk.fls.h2n0.main.characters.Race;
import uk.fls.h2n0.main.characters.half.LightFootHalfling;
import uk.fls.h2n0.main.characters.half.StoutHalfling;

public class HaflingRace extends Race {

	public HaflingRace(){
		super("Halfling");
		this.subraces = new Race[2];
		this.subraces[0] = new LightFootHalfling(this);
		this.subraces[1] = new StoutHalfling(this);
		this.minAge = 20;
		this.maxAge = 250;
		this.minSize = 3f;
		this.maxSize = 4f;
		this.speed = 25;
	}
}
