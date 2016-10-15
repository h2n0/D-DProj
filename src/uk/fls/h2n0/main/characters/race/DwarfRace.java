package uk.fls.h2n0.main.characters.race;

import uk.fls.h2n0.main.characters.race.dwarf.HillDwarfRace;
import uk.fls.h2n0.main.characters.race.dwarf.MountainDwarfRace;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class DwarfRace extends Race{

	public DwarfRace() {
		super("Dwarf");
		this.minAge = 50;
		this.maxAge = 350;
		this.minSize = 4f;
		this.maxSize = 5f;
		this.speed = 25;
		this.statMods.add(new StatsModifier(Stat.CON, 2));
		this.subraces = new Race[2];
		this.subraces[0] = new MountainDwarfRace(this);
		this.subraces[1] = new HillDwarfRace(this);
	}

}
