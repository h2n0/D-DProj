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
	
	
	/**
	 * Basic constructor for race class
	 * @param name of race
	 */
	public Race(String name){
		this.name = name;
		this.statMods = new ArrayList<StatsModifier>();
	}
	
	/**
	 * Returns the name of the current race
	 * @return String
	 */
	public String getName(){
		return this.name;
	}
	
	/**
	 * Returns an array of subraces for the current race
	 * @return Race[]
	 */
	public Race[] getSubraces(){
		return this.subraces;
	}
	
	/**
	 * Returns an array of subrace names for the current race
	 * @return String[]
	 */
	public String[] getSubracesNames(){
		String[] res = new String[this.subraces.length];
		for(int i = 0; i < res.length; i++){
			res[i] = this.subraces[i].name;
		}
		return res;
	}
	
	/**
	 * Returns a list of stat modifiers to be applied at certain times
	 * @return List<StatsModifier>
	 */
	public List<StatsModifier> getMods(){
		return this.statMods;
	}
	
	/**
	 * Set the parent of this race
	 * @param Race parent
	 */
	protected void setParent(Race p){
		this.parent = p;
		this.statMods.addAll(p.getMods());
	}
}
