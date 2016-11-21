package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Font;

public class FirstScreen extends Screen {

	
	private Renderer r;
	private int timer;
	
	public void postInit(){
		r = new Renderer(this.game.getImage());
		this.timer = 60 * 5;
	}
	@Override
	public void update() {
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
		this.r.fill(0);
		String[] lines = new String[]{"","Imporant note", "", "","","", "This software is designed", "for users to create their","own DnD character", "","There is no capability for","keeping track of your","inventory","", "for that I suggest you","use a pencil and some paper"};
		
		for(int i = 0; i < lines.length; i++){
			String l = lines[i];
			int ll = l.trim().length() * 7;
			Font.instace.draw(this.r, l, (DnD.w - ll) / 2, 10 * i);
		}
		
		if(this.timer == 0){
			String end = "press space to continue";
			int xo = end.trim().length() * 7;
			Font.instace.draw(r, end, (DnD.w - xo) / 2, 240);
		}
	}

}
