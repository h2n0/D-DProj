package uk.fls.h2n0.main.characters.race;

import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class HumanRace extends Race{

	public HumanRace(){
		super("Human");
		this.minAge = 18;
		this.maxAge = 95;
		this.minSize = 5f;
		this.maxSize = 6f;
		this.speed = 30;
		this.statMods.add(new StatsModifier(Stat.STR, 1));
		this.statMods.add(new StatsModifier(Stat.DEX, 1));
		this.statMods.add(new StatsModifier(Stat.CON, 1));
		this.statMods.add(new StatsModifier(Stat.INT, 1));
		this.statMods.add(new StatsModifier(Stat.WIS, 1));
		this.statMods.add(new StatsModifier(Stat.CHA, 1));
	}
}
