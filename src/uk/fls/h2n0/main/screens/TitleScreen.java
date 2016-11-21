package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Dice;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;
import uk.fls.h2n0.main.util.gui.Popup;

public class TitleScreen extends Screen {

	private Renderer r;
	private int bgColor;
	private Point mouse;
	private UI ui;
	private boolean canResume;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.bgColor = r.makeRGB(123, 40, 20);
		this.mouse = Point.zero;
		this.ui = new UI(this.r);
		
		
		int yo = 8 * 16;
		this.ui.add(new Button("NC", "New Character", 47, yo));
		
		String[] files = FileIO.instance.listFiles(FileIO.path+"/data/chars");
		boolean load = false;
		if(files != null){
			this.ui.add(new Button("LC", "Load Character", 43, yo + 24));
			load = true;
		}
		
		String[] temps = FileIO.instance.listFiles(FileIO.instance.path+"/data/temp");
		if(temps != null){
			this.ui.add(new Button("RS", "Resume recent", 47, yo + (load?48:24)));
			this.canResume = true;
		}
	}
	
	@Override
	public void update() {

		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		if(this.ui.getCompByID("P") != null){
			Popup p = (Popup)this.ui.getCompByID("P");
			if(p.accept()){
				FileIO.instance.deleteDir(FileIO.instance.path+"/data/temp/");
				setScreen(new CreationScreen());
			}else if(p.deny()){
				this.ui.remove(p);
			}
		}else{
			if(((Button)this.ui.getCompByID("NC")).clicked){
				if(this.canResume){
					this.ui.add(new Popup("P"));
				}else{
					setScreen(new CreationScreen());
				}
			}
			
			if(this.ui.getCompByID("LC") != null){
				if(((Button)this.ui.getCompByID("LC")).clicked){
				//	setScreen(new LoadScreen());
				}
			}
			
			if(this.ui.getCompByID("RS") != null){
				if(((Button)this.ui.getCompByID("RS")).clicked){
					DataFile df = new DataFile("/temp/temp1");
					setScreen(new CreationScreen(df, 5));//Step 5 because step 4 is a saving step
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		r.fill(this.bgColor);
		

		
		String title = "DnD Companion";
		Font.instace.draw(r, title, (DnD.w-(title.length() * 7))/2, 80);
		
		
		String footer = "Elliot Lee Cerrino 10748430";
		Font.instace.draw(r, footer, (DnD.w-(footer.length() * 7))/2, DnD.h-11);
		
		this.ui.render();
	}

}
