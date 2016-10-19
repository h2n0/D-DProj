package uk.fls.h2n0.main.characters.race.elf;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class HighElfRace extends Race {

	public HighElfRace(Race p) {
		super("High Elf");
		setParent(p);
		this.statMods.add(new StatsModifier(Stat.INT, 1));
	}

}
