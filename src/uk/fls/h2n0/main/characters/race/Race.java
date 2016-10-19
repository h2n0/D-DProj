package uk.fls.h2n0.main.characters.race;

import java.util.ArrayList;
import java.util.List;

import uk.fls.h2n0.main.characters.stats.StatsModifier;

public class Race {
	
	protected String name;
	protected Race[] subraces;
	protected Race parent;
	protected int minAge;
	protected int maxAge;

	protected float minSize;
	protected float maxSize;
	
	protected int speed;
	
	protected List<StatsModifier> statMods;
	
	public Race(String name){
		this.name = name;
		this.statMods = new ArrayList<StatsModifier>();
	}
	
	public String getName(){
		return this.name;
	}
	
	public Race[] getSubraces(){
		return this.subraces;
	}
	
	public String[] getSubracesNames(){
		String[] res = new String[this.subraces.length];
		for(int i = 0; i < res.length; i++){
			res[i] = this.subraces[i].name;
		}
		return res;
	}
	
	public List<StatsModifier> getMods(){
		return this.statMods;
	}
	
	protected void setParent(Race p){
		this.parent = p;
		this.statMods.addAll(p.getMods());
	}
}
