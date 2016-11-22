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
		
		String[] files = FileIO.instance.listFiles(FileIO.path+"/data/chac");
		boolean load = false;
		if(files != null && files.length > 0){
			this.ui.add(new Button("LC", "Load Character", 43, yo + 24));
			load = true;
		}
		
		String[] temps = FileIO.instance.listFiles(FileIO.instance.path+"/data/temp");
		if(temps != null && temps.length > 0){
			this.ui.add(new Button("RS", "Resume recent", 47, yo + (load?48:24)));
			this.canResume = true;
		}
	}
	
	@Override
	public void update() {

		//Setting the mouse objects X and Y value
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		
		//Passing the mouse's x & y values along with the mouse clicked variable
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		//Checks if a popup is currently visible
		//If so dosen't render don't update anything else
		if(this.ui.getCompByID("P") != null){
			Popup p = (Popup)this.ui.getCompByID("P");
			
			//Accept the popups demands
			if(p.accept()){
				//Deletes the temp dir and moves no
				FileIO.instance.deleteDir(FileIO.instance.path+"/data/temp/");
				setScreen(new CreationScreen());
			}else if(p.deny()){//Ignore them
				this.ui.remove(p);
			}
		}else{
			//Check to see if the new character button is pressed
			if(((Button)this.ui.getCompByID("NC")).clicked){
				if(this.canResume){
					this.ui.add(new Popup("P", true, "!! WARNING !!", "", "", "", "You will lose your", "previously saved","character if you", "continue"));
				}else{
					setScreen(new CreationScreen());
				}
			}
			
			//Checks to see if the load character button is pressed
			if(this.ui.getCompByID("LC") != null){
				if(((Button)this.ui.getCompByID("LC")).clicked){
					setScreen(new LoadScreen());
				}
			}
			
			//Checks to see if the resume button is pressed
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
		

		
		//Just draw some text
		String title = "DnD Companion";
		Font.instace.draw(r, title, (DnD.w-(title.length() * 7))/2, 80);
		
		
		//This means that it's mine
		String footer = "Elliot Lee Cerrino 10748430";
		Font.instace.draw(r, footer, (DnD.w-(footer.length() * 7))/2, DnD.h-11);
		
		this.ui.render();
	}

}
