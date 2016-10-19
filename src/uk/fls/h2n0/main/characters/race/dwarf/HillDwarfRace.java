package uk.fls.h2n0.main.characters.race.dwarf;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class HillDwarfRace extends Race {
	
	public HillDwarfRace(Race parent){
		super("Hill Dwarf");
		setParent(parent);
		this.statMods.add(new StatsModifier(Stat.WIS, 1));
	}
}
