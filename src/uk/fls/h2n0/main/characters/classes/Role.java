/**
 * 
 */
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
	
	public Role(){
		this.proficencyBonus = 2;
		this.castsSpells = false;
		this.knownCantribs = 3;
	}
	
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
	
	public int getProficencyBonus(){
		return this.proficencyBonus;
	}
	
	public Stat getPrimaryStat(){
		return this.primaryStat;
	}
	
	public String getHitDie(){
		return this.hitDice;
	}
}
