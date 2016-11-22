package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Font;

public class FirstScreen extends Screen {

	
	private Renderer r;
	private int timer;
	private int maxTimer;

	
	//House keeping points
	public void postInit(){
		r = new Renderer(this.game.getImage());
		this.maxTimer = 60 * 3;
		this.timer = maxTimer;
	}
	
	@Override
	public void update() {
		
		//Allows the user to move on after they have waited enough time
		if(this.timer > 0){
			this.timer--;
		}else{
			if(this.input.isKeyPressed(this.input.space)){
				setScreen(new TitleScreen());
			}
		}

	}

	@Override
	public void render(Graphics g) {
		this.r.fill(0);// Paints the screen black
		
		//Lines of text to be drawn to the screen
		String[] lines = new String[]{"","!! Imporant note !!", "","","", "","", "This software is designed", "for users to create their","own DnD character", "","There is no capability for","keeping track of your","inventory","", "for that I suggest you","use a pencil and some paper"};
		
		int numLines = lines.length;
		
		for(int i = 0; i < lines.length; i++){
			String l = lines[i];
			int ll = l.trim().length() * 7;
			float fadeMax = 255 - ((float)(this.timer + (i/numLines) * 10f) / (float)(maxTimer)) * 255f;
			Font.instace.draw(this.r, l, (DnD.w - ll) / 2, 10 * i, getCol((int)fadeMax));
		}
		
		if(this.timer <= 0){
			String end = "press space to continue";
			int xo = end.trim().length() * 7;
			Font.instace.draw(r, end, (DnD.w - xo) / 2, 240);
		}
	}
	
	//Used to create the fade in color using a 'luminance value'
	private int getCol(int l){
		return r.makeRGB(l, l, l);
	}

}
