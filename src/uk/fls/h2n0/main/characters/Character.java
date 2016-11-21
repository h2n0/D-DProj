package uk.fls.h2n0.main.characters;

import uk.fls.h2n0.main.characters.classes.Role;
import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.stats.SkillManager;
import uk.fls.h2n0.main.characters.stats.StatsModifier;
import uk.fls.h2n0.main.characters.stats.StatsModifier.Stat;
import uk.fls.h2n0.main.util.Dice;

public class Character {

	private String name;
	private Race r;
	private Stats stats;
	private SkillManager sk;
	private Role playerClass;
	
	public Character(){
		this.name = "Null Nullson";
		this.stats = new Stats();
		this.sk = new SkillManager();
	}
	
	public void setRace(Race r){
		this.r = r;
		this.stats.wipe();
		if(r.getMods().isEmpty())return;
		for(StatsModifier sm : r.getMods()){
			this.stats.addModifier(sm);
		}
	}
	
	public void setRole(Role role){
		this.playerClass = role;
		this.stats.setMaxHp(this.playerClass.getHitDie());
	}
	
	public void addXP(int amt){
		if(this.stats.addXP(amt)){ // If leveled up
			this.playerClass.levelUp(this.stats.getLevel());
			int additional = Dice.rollDice(this.playerClass.getHitDie())[0];
			additional += this.stats.getBonus(this.stats.getConstitution());
			this.stats.setMaxHp(this.stats.getMaxHp() + additional);
		}
	}
	
	public Role getPlayerClass(){
		return null;
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
	
	public SkillManager getSkillManager(){
		return this.sk;
	}
	
	public String getHitDiceString(){
		return this.stats.getLevel() + "" + this.playerClass.getHitDie();
	}
	
	public void dmg(int amt){
		this.stats.dmg(amt);
	}
	
	public void heal(int amt){
		this.stats.heal(amt);
	}
	
	public void applyStats(int[] stats){
		for(int i = 0; i < stats.length; i++){
			this.stats.addModifier(new StatsModifier(Stat.values()[i], stats[i]));
		}
	}
	
	
}
