package uk.fls.h2n0.main.characters.race.half;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class StoutHalfling extends Race {

	public StoutHalfling(Race p) {
		super("Stout halfling");
		setParent(p);
		this.statMods.add(new StatsModifier(Stat.CON, 1));
	}

}
