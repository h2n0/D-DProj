package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Dice;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;

public class TitleScreen extends Screen {

	private Renderer r;
	private int bgColor;
	private Point mouse;
	private UI ui;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.bgColor = r.makeRGB(123, 40, 20);
		this.mouse = Point.zero;
		this.ui = new UI(this.r);
		
		
		int yo = 8 * 16;
		this.ui.add(new Button("NC", "New Character", 47, yo));
		
		String[] files = FileIO.instance.listFiles(FileIO.path+"/chars");
		if(files != null){
			this.ui.add(new Button("LC", "Load Character", 43, yo + 24));
		}
	}
	
	@Override
	public void update() {

		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		if(((Button)this.ui.getCompByID("NC")).clicked){
			Dice.rollDice("3d4");
			setScreen(new CreationScreen());
		}
		
		if(this.ui.getCompByID("LC") != null){
			if(((Button)this.ui.getCompByID("LC")).clicked){
			//	setScreen(new LoadScreen());
			}
		}
	}

	@Override
	public void render(Graphics g) {
		r.fill(this.bgColor);
		
		
		this.ui.render();
		
		String title = "DnD Companion";
		Font.instace.draw(r, title, (DnD.w-(title.length() * 7))/2, 80);
		
		
		String footer = "Elliot Lee Cerrino 10748430";
		Font.instace.draw(r, footer, (DnD.w-(footer.length() * 7))/2, DnD.h-11);
	}

}
