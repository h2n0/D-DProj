package uk.fls.h2n0.main;

import fls.engine.main.Init;
import fls.engine.main.input.Input;
import fls.engine.main.io.DataFile;
import uk.fls.h2n0.main.screens.FirstScreen;
import uk.fls.h2n0.main.screens.TitleScreen;

@SuppressWarnings("serial")
public class DnD extends Init{

	//Global decleration of screen size vairables
	public static int w = 200;
	public static int h = 300;
	public static int s = 2;
	
	/**
	 * DND Companion application
	 */
	public DnD(){
		super("D&D Companion", w * s, h * s);
		useCustomBufferedImage(w, h, false);
		setInput(new Input(this, Input.MOUSE, Input.KEYS));
		
		DataFile df = new DataFile("options");
		
		//Checks to see if the user has used the application at all
		//If they are new show the warning else move to the title screen.
		if(df.getData("NOTE") == null || !df.getData("NOTE").getBool()){
			df.setValue("NOTE", "true");
			setScreen(new FirstScreen());
		}else{
			setScreen(new TitleScreen());
		}
		
		//House keeping code
		skipInit();
	}
	
	
	public static void main(String[] args){
		new DnD().start();
	}
}
