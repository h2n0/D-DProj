package uk.fls.h2n0.main.characters.race;

import uk.fls.h2n0.main.characters.race.half.LightFootHalfling;
import uk.fls.h2n0.main.characters.race.half.StoutHalfling;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class HaflingRace extends Race {

	public HaflingRace(){
		super("Halfling");
		this.minAge = 20;
		this.maxAge = 250;
		this.minSize = 3f;
		this.maxSize = 4f;
		this.speed = 25;
		this.statMods.add(new StatsModifier(Stat.DEX, 2));
		this.subraces = new Race[2];
		this.subraces[0] = new LightFootHalfling(this);
		this.subraces[1] = new StoutHalfling(this);
	}
}
