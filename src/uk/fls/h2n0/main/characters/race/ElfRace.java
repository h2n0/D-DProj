package uk.fls.h2n0.main.characters.race;

import uk.fls.h2n0.main.characters.race.elf.HighElfRace;
import uk.fls.h2n0.main.characters.race.elf.WoodElfRace;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

public class ElfRace extends Race {
	
	public ElfRace() {
		super("Elf");
		this.minAge = 100;
		this.maxAge = 750;
		this.minSize = 5f;
		this.maxSize = 6f;
		this.speed = 30;
		this.statMods.add(new StatsModifier(Stat.DEX, 2));
		this.subraces = new Race[2];
		this.subraces[0] = new HighElfRace(this);
		this.subraces[1] = new WoodElfRace(this);
	}

}
