package uk.fls.h2n0.main.characters.race.dwarf;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class MountainDwarfRace extends Race {
	
	public MountainDwarfRace(Race parent){
		super("Mountain Dwarf");
		setParent(parent);
		this.statMods.add(new StatsModifier(Stat.STR, 2));
	}
}
