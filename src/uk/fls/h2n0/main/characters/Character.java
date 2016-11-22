package uk.fls.h2n0.main.characters;

import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import uk.fls.h2n0.main.characters.classes.ClericRole;
import uk.fls.h2n0.main.characters.classes.FighterRole;
import uk.fls.h2n0.main.characters.classes.Role;
import uk.fls.h2n0.main.characters.classes.RougeRole;
import uk.fls.h2n0.main.characters.classes.WizardRole;
import uk.fls.h2n0.main.characters.race.DwarfRace;
import uk.fls.h2n0.main.characters.race.ElfRace;
import uk.fls.h2n0.main.characters.race.HumanRace;
import uk.fls.h2n0.main.characters.race.Race;
import uk.fls.h2n0.main.characters.race.dwarf.HillDwarfRace;
import uk.fls.h2n0.main.characters.race.dwarf.MountainDwarfRace;
import uk.fls.h2n0.main.characters.race.elf.HighElfRace;
import uk.fls.h2n0.main.characters.race.elf.WoodElfRace;
import uk.fls.h2n0.main.characters.race.half.LightFootHalfling;
import uk.fls.h2n0.main.characters.race.half.StoutHalfling;
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
	private String role;
	private DataFile saveFile;
	
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
		this.role = this.playerClass.getName();
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
	
	public void loadFromFile(DataFile df){
		
		String race = df.getData("Race").getString();
		String[] parts = race.split(" ");
		
		if(parts.length == 1){// Human
			setRace(new HumanRace());
		}else{// Non human
			String rac = parts[1];
			if(rac.equalsIgnoreCase("Dwarf")){
				Race p = new DwarfRace();
				if(parts[0].equalsIgnoreCase("Mountain")){
					setRace(new MountainDwarfRace(p));
				}else{
					setRace(new HillDwarfRace(p));
				}
			}else if(rac.equalsIgnoreCase("Elf")){
				Race p = new ElfRace();
				if(parts[0].trim().equalsIgnoreCase("Wood")){
					setRace(new WoodElfRace(p));
				}else{
					setRace(new HighElfRace(p));
				}
			}else if(rac.equalsIgnoreCase("Halfling")){
				Race p = new DwarfRace();
				if(parts[0].equalsIgnoreCase("Stout")){
					setRace(new StoutHalfling(p));
				}else{
					setRace(new LightFootHalfling(p));
				}
			}
		}
		
		String cs = df.getData("CLS").getString();
		cs = cs.trim();
		
		if(cs.equalsIgnoreCase("Wizard")){
			this.playerClass = new WizardRole();
		}else if(cs.equalsIgnoreCase("Cleric")){
			this.playerClass = new ClericRole();
		}else if(cs.equalsIgnoreCase("Rouge")){
			this.playerClass = new RougeRole();
		}else if(cs.equalsIgnoreCase("Fighter")){
			this.playerClass = new FighterRole();
		}

		this.stats.setMaxHp(Integer.parseInt(df.getData("MHP").getString()));
		this.stats.setCurrentHp(Integer.parseInt(df.getData("CHP").getString()));
		
		int ss = df.getData("SS").getInt();
		for(int i = 0 ; i < ss; i++){
			this.stats.successfulSave();
		}
		
		int us = df.getData("FS").getInt();
		for(int i = 0; i < us; i++){
			this.stats.failSave();
		}
		
		setName(df.getData("Name").getString());
		this.stats.loadFromFile(df);
		
		this.saveFile = df;
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
	
	public void setName(String name){
		this.name = name;
	}
	
	public Role getRole(){
		return this.playerClass;
	}
	
	public void save(){
		if(this.saveFile == null){
			int v = 0;
			if(FileIO.instance.doesFileExist(FileIO.path + "/data/chac/")){
				String[] oldChars = FileIO.instance.listFiles(FileIO.path + "/data/chac/");
				v = oldChars.length;
			}
			String fileName = "/chac/char" + v;
			Stats stats = this.getStats();
			DataFile df = new DataFile(fileName);
			save(df);
			this.saveFile = df;
		}else{
			save(this.saveFile);
		}
	}
	
	public void save(DataFile df){
		df.setValue("State", "C");
		df.setValue("Name", "" + this.name);
		df.setValue("STR", "" + stats.getStrength());
		df.setValue("DEX", "" + stats.getDexterity());
		df.setValue("WIS", "" + stats.getWisdom());
		df.setValue("CON", "" + stats.getConstitution());
		df.setValue("INT", "" + stats.getIntelligence());
		df.setValue("CHR", "" + stats.getCharisma());
		df.setValue("Race", "" + getRace().getName());
		df.setValue("MHP", "" + stats.getMaxHp());
		df.setValue("CHP", "" + stats.getHp());
		df.setValue("HD", "" + getHitDiceString());
		df.setValue("CLS", "" + this.playerClass.getName());
		
		String skills = "";
		SkillManager sk = getSkillManager();
		int[] vs = sk.getSkills();
		boolean[] ps = sk.getProfs();

		for (int i = 0; i < vs.length; i++) {
			int val = vs[i];
			boolean prof = ps[i];
			skills += "" + val + "/" + (prof ? "t" : "f") + ",";
		}
		skills = skills.trim().substring(0, skills.length() - 1);
		df.setValue("SKL", skills);
		
		df.setValue("SS", "" + stats.getSuccessSaves());
		df.setValue("FS", "" + stats.getFailSaves());
	}
	
	
}
