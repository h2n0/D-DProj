package uk.fls.h2n0.main.util;

public class Dice {
	
	/**
	 * Takes a given dice string and returns the correct number of dice
	 * @param dice
	 * @return Int[]
	 */
	public static int[] rollDice(String dice){
		dice = dice.toLowerCase();
		
		int indexOfD = dice.indexOf("d");
		if(indexOfD == -1){//Checks to see if this dice string given is even valid
			System.err.println("Invalid dice: " + dice);
			return null;
		}
		
		if(dice.substring(0,indexOfD).isEmpty() || Integer.parseInt(dice.substring(0, indexOfD)) == 1){//Single dice
			String sides = dice.substring(indexOfD+1);
			System.out.println("Rolling dice: D"+sides);// Console output to help debug
			int res = genNumber(Integer.parseInt(sides));
			System.out.println("Dice value: " + res);
			System.out.println(" ");
			return new int[]{res};
		}else{//Many dice
			String numberOfDice = dice.substring(0,indexOfD);
			String sides = dice.substring(indexOfD+1);
			int[] res = new int[Integer.parseInt(numberOfDice)];
			for(int i = 0; i < res.length; i++){ //Generate enough dice as requested
				res[i] = genNumber(Integer.parseInt(sides));
			}

			System.out.println("Rolling dice: "+ numberOfDice +"D"+sides); // More output for easy debugging
			String diceValues = "";
			for(int i = 0; i < res.length; i++){
				diceValues += res[i] + ",";
			}
			
			diceValues = diceValues.trim().substring(0, diceValues.length()-1);
			System.out.println("Dice values: " + diceValues);
			System.out.println(" ");
			
			return res;// Final output
		}
	}
	
	/**
	 * Generates a number similar to dice rolls in the real world
	 * @param sides
	 * @return Int
	 */
	private static int genNumber(int n){
		return 1 + (int)(Math.random() * n);
	}
}
