package uk.fls.h2n0.main.characters.race.half;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class LightFootHalfling extends Race {

	public LightFootHalfling(Race par) {
		super("Lightfoot halfling");
		setParent(par);
		this.statMods.add(new StatsModifier(Stat.CHA, 1));
	}

}
