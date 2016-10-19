package uk.fls.h2n0.main.characters;

import uk.fls.h2n0.main.characters.stats.StatsModifier;

public class Stats {
	
	private int xp;
	private int level;
	private int proficiencyBonus;
	private int hp;
	private int maxHp;
	
	private int strength;
	private int dexterity;
	private int wisdom;
	private int charisma;
	private int intelligence;
	private int consitution;
	
	private int[] xpAmts;
	
	private boolean dead;
	private boolean unconcious;
	private int successfulSaves;
	private int failedSaves;
	
	public Stats(){
		this.xp = 0;
		this.level = 1;
		this.proficiencyBonus = 2;
		
		//This is the xp lookup table, used for level progresion
		this.xpAmts = new int[]{0,300,900,2700,6500,14000,23000,34000,48000,64000,85000,100000,120000,140000,165000,195000,225000,265000,305000,350000};
	}
	
	public void wipe(){
		this.strength = this.dexterity = this.wisdom = this.charisma = this.intelligence = this.consitution = 0;
	}
	
	/**
	 * As described in the D&D basic rule book on page 7.
	 * bonus = (value - 10) / 2 (rounded down)
	 * @param value
	 * @return bonus
	 */
	public int getBonus(int value){
		return (value - 10) / 2;
	}
	
	public void addModifier(StatsModifier sm){
		switch(sm.getStat()){
		case STR:
			this.strength += sm.getAmt();
			break;
		case DEX:
			this.dexterity += sm.getAmt();
			break;
		case WIS:
			this.wisdom += sm.getAmt();
			break;
		case CHA:
			this.charisma += sm.getAmt();
			break;
		case INT:
			this.intelligence += sm.getAmt();
			break;
		case CON:
			this.consitution += sm.getAmt();
			break;
		}
	}
	
	public int getStrength(){
		return this.strength;
	}
	
	public int getDexterity(){
		return this.dexterity;
	}
	
	public int getWisdom(){
		return this.wisdom;
	}
	
	public int getCharisma(){
		return this.charisma;
	}
	
	public int getIntelligence(){
		return this.intelligence;
	}
	
	public int getConstitution(){
		return this.consitution;
	}
	
	public int getLevel(){
		return this.level;
	}
	
	public void print(){
		System.out.println("STR: " + getStrength() + " (" + getBonus(getStrength()) + ")");
		System.out.println("DEX: " + getDexterity() + " (" + getBonus(getDexterity()) + ")");
		System.out.println("WIS: " + getWisdom() + " (" + getBonus(getWisdom()) + ")");
		System.out.println("CHA: " + getCharisma() + " (" + getBonus(getCharisma()) + ")");
		System.out.println("INT: " + getIntelligence() + " (" + getBonus(getIntelligence()) + ")");
		System.out.println("CON: " + getConstitution() + " (" + getBonus(getConstitution()) + ")");
	}
	
	public boolean addXP(int amt){
		this.xp += amt;
		if(this.xp >= this.xpAmts[this.level]){
			this.xp -= this.xpAmts[this.level];
			this.level++;
			return true;
		}
		return false;
	}
	
	public void setMaxHp(String dice){
		String num = dice.substring(dice.indexOf("d")+1);
		int hp = Integer.parseInt(num) + this.getConstitution();
		this.maxHp = hp;
	}
	
	public void setMaxHp(int newMax){
		this.maxHp = newMax;
	}
	
	public int getMaxHp(){
		return this.maxHp;
	}
	
	public void dmg(int amt){
		if(this.hp - amt < 0){
			this.unconcious = true;
			if(this.hp - amt <= this.maxHp){
				this.dead = true;
			}
			this.hp = 0;
		}
	}
	
	public void heal(int amt){
		this.hp += amt;
		if(this.hp > this.maxHp)this.hp = this.maxHp;
	}
	
	public void successfulSave(){
		if(++this.successfulSaves == 3){
			this.successfulSaves = 0;
			this.failedSaves = 0;
		}
	}
	
	public void failSave(){
		if(++this.failedSaves == 3){
			this.successfulSaves = 0;
			this.failedSaves = 0;
		}
	}
}
