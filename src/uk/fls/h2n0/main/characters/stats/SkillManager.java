package uk.fls.h2n0.main.characters.stats;

import uk.fls.h2n0.main.characters.Stats;

public class SkillManager {

	private boolean[] prof;
	private int[] skills;
	
	public SkillManager(){
		this.prof = new boolean[18];
		this.skills = new int[18];
	}
	
	public int getValue(int i){
		return this.skills[i];
	}
	
	public boolean isProf(int i){
		return this.prof[i];
	}
	
	public int[] getSkills(){
		return this.skills;
	}
	
	public boolean[] getProfs(){
		return this.prof;
	}
	
	public void setStrengthStats(int val){
		this.skills[3] = val;
	}
	
	public void setDexteritySkills(int val){
		this.skills[0] = this.skills[15] = this.skills[16] = val;
	}
	
	public void setWisdomSkills(int val){
		this.skills[1] = this.skills[6] = this.skills[9] = this.skills[11] = this.skills[17] = val;
	}
	
	public void setCharismaSkills(int val){
		this.skills[4] = this.skills[7] = this.skills[12] = this.skills[13] = val;
	}
	
	public void setIntelligenceSkills(int val){
		this.skills[2] = this.skills[5] = this.skills[8] = this.skills[10] = this.skills[14] = val;
	}
	
	public void makeProf(int i){
		this.prof[i] = true;
	}
	
	public void fillWithStats(Stats s){
		int strB = s.getBonus(s.getStrength());
		int dexB = s.getBonus(s.getDexterity());
		int wisB = s.getBonus(s.getWisdom());
		int contB = s.getBonus(s.getConstitution());
		int intB = s.getBonus(s.getIntelligence());
		int chrB = s.getBonus(s.getCharisma());
		
		//Applying skills where needed
		setStrengthStats(strB);
		setDexteritySkills(dexB);
		setWisdomSkills(wisB);
		setIntelligenceSkills(intB);
		setCharismaSkills(chrB);
	}
	
	public void setFromString(String data){
		String[] parts = data.trim().split(",");
		for(String s : parts){
			
		}
	}
	
	public void print(){
		String[] skills = new String[]{"Acrobatics","Animal handling","Arcana","Athletics","Description","History","Insight","Intimidation","Investigation","Medicine","Nature","Perception","Performace","Persuasion","Religion","Sleight of hand","Stealth","Survival"};
		boolean[] prof = getProfs();
		
		String res = "";
		for(int i = 0; i < skills.length; i++){
			String p = prof[i]?"(*) ":"( ) ";
			String name = skills[i] + " ";
			res += p + name + getValue(i) + "\n";
		}
		
		System.out.println(res);
	}
}
