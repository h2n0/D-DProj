package uk.fls.h2n0.main.characters;

import uk.fls.h2n0.main.characters.elf.HighElfRace;
import uk.fls.h2n0.main.characters.elf.WoodElfRace;

public class ElfRace extends Race {
	
	public ElfRace() {
		super("Elf");
		this.subraces = new Race[2];
		this.subraces[0] = new HighElfRace(this);
		this.subraces[1] = new WoodElfRace(this);
		this.minAge = 100;
		this.maxAge = 750;
		this.minSize = 5f;
		this.maxSize = 6f;
		this.speed = 30;
	}

}
