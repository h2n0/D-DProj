package uk.fls.h2n0.main;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import uk.fls.h2n0.main.screens.TitleScreen;

@SuppressWarnings("serial")
public class DnD extends Init{

	public static int w = 200;
	public static int h = 300;
	public static int s = 2;
	
	public DnD(){
		super("D&D Compainion", w * s, h * s);
		useCustomBufferedImage(w, h, false);
		setInput(new Input(this, Input.MOUSE, Input.KEYS));
		setScreen(new TitleScreen());
		skipInit();
	}
	
	
	public static void main(String[] args){
		new DnD().start();
		
		//TODO
		//2. Flag stats as proficient
		//3. Roll ability scores and get player to add them where they want (Drag and drop GUI?)
		//4. Add success and failur rolls when the player's hp drops to 0
		//5. Add outright death
	}
}
