package uk.fls.h2n0.main.characters;

import uk.fls.h2n0.main.characters.dwarf.HillDwarfRace;
import uk.fls.h2n0.main.characters.dwarf.MountainDwarfRace;

public class DwarfRace extends Race{

	public DwarfRace() {
		super("Dwarf");
		this.subraces = new Race[2];
		this.subraces[0] = new MountainDwarfRace(this);
		this.subraces[1] = new HillDwarfRace(this);
		this.minAge = 50;
		this.maxAge = 350;
		this.minSize = 4f;
		this.maxSize = 5f;
		this.speed = 25;
	}

}
