package uk.fls.h2n0.main.characters.stats;

public class StatsModifier {
	
	public enum Stat{
		STR,
		DEX,
		WIS,
		CON,
		INT,
		CHA
	}
	
	
	private Stat stat;
	private int num;
	
	public StatsModifier(Stat stat, int amt){
		this.stat = stat;
		this.num = amt;
	}
	
	public Stat getStat(){
		return this.stat;
	}
	
	public int getAmt(){
		return this.num;
	}
}
