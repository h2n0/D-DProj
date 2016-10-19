package uk.fls.h2n0.main.characters.race.elf;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class WoodElfRace extends Race {

	public WoodElfRace(Race parent) {
		super("Wood elf");
		setParent(parent);
		this.statMods.add(new StatsModifier(Stat.WIS, 1));
	}

}
