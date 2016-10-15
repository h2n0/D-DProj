package uk.fls.h2n0.main.characters;

import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.StatsModifier;

public class Character {

	private String name;
	private Race r;
	private Stats stats;
	
	public Character(){
		this.name = "Null Nullson";
		this.stats = new Stats();
	}
	
	public void setRace(Race r){
		System.out.println(r.getName());
		this.r = r;
		this.stats.wipe();
		if(r.getMods().isEmpty())return;
		System.out.println(r.getMods().size());
		for(StatsModifier sm : r.getMods()){
			this.stats.addModifier(sm);
		}
	}
	
	public Race getRace(){
		return this.r;
	}
	
	public String getName(){
		return this.name;
	}
	
	public Stats getStats(){
		return this.stats;
	}
}
