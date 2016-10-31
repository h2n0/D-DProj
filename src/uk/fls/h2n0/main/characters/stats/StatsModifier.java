package uk.fls.h2n0.main.characters.stats;

public class StatsModifier {
	
	/**
	 * Enum for stats, to be used throughout the program
	 * @author h2n0
	 *
	 */
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
	
	/**
	 * Constructor for a StatsModifier
	 * @param Stat stat
	 * @param Int amt
	 */
	public StatsModifier(Stat stat, int amt){
		this.stat = stat;
		this.num = amt;
	}
	
	/**
	 * Returns the Stat that this modifier is going to affect
	 * @return Stat
	 */
	public Stat getStat(){
		return this.stat;
	}
	
	/**
	 * Returns the magnitude of this stat modifier
	 * @return
	 */
	public int getAmt(){
		return this.num;
	}
}
