package uk.fls.h2n0.main.characters;

public class Race {
	
	protected String name;
	protected Race[] subraces;
	protected Race parent;
	
	protected int minAge;
	protected int maxAge;

	protected float minSize;
	protected float maxSize;
	
	protected int speed;
	
	public Race(String name){
		this.name = name;
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
}
