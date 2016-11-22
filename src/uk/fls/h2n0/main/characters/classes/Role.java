package uk.fls.h2n0.main.characters.classes;

import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;

/**
 * @author h2n0
 *
 */
public class Role {
	
	protected Stat primaryStat;
	protected Stat[] savingStat;
	protected String hitDice;
	private int proficencyBonus;
	
	protected boolean castsSpells;
	protected int knownCantribs;
	protected String name;
	
	/**
	 * Basic constructor for creating Roles
	 */
	public Role(){
		this.proficencyBonus = 2;
		this.castsSpells = false;
		this.knownCantribs = 3;
	}
	
	/**
	 * Manages leveling bonuses when it's called from the Stat class
	 * @param int newLevel
	 */
	public void levelUp(int newLevel){
		if(newLevel == 5 || newLevel == 9 || newLevel == 13 || newLevel == 17){
			this.proficencyBonus ++;
		}
		
		if(this.castsSpells){
			if(newLevel == 4 || newLevel == 10){
				this.knownCantribs ++;
			}
		}
	}
	
	/**
	 * Returns this classes profeciency bonus
	 * @return Int
	 */
	public int getProficencyBonus(){
		return this.proficencyBonus;
	}
	
	/**
	 * Returns this classes primary stat
	 * @return Stat
	 */
	public Stat getPrimaryStat(){
		return this.primaryStat;
	}
	
	/**
	 * Returns a string to be used with the Dice utility class
	 * @return String
	 */
	public String getHitDie(){
		return this.hitDice;
	}
	
	public String getName(){
		return this.name;
	}
}
