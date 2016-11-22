package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;
import uk.fls.h2n0.main.characters.Character;

public class LoadScreen extends Screen {

	
	public Renderer r;
	public UI ui;
	public int bgColor;
	public Point mouse;
	public DataFile[] dfs;
	
	public LoadScreen(){
		
	}
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.ui = new UI(this.r);
		this.bgColor = this.r.makeRGB(128, 128, 128);
		this.mouse = Point.zero;
		
		lookForFiles();
		addButtonsForFiles();
	}
	
	@Override
	public void update() {
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY() / DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		for(int i = 0; i < this.dfs.length; i++){
			String id = "CH"+i;
			Button btn = (Button)this.ui.getCompByID(id);
			if(btn == null)continue;
			else{
				if(btn.clicked){
					Character c = new Character();
					c.loadFromFile(this.dfs[i]);
					setScreen(new CharacterScreen(c));
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		this.r.fill(this.bgColor);
		Font.instace.drawCenter(r, "Select a character to load", 8);
		this.ui.render();
	}
	
	//Adds buttons for to the ui manager so that the user can load the character
	private void addButtonsForFiles(){
		for(int i = 0; i < this.dfs.length; i++){
			DataFile df = this.dfs[i];
			String name = df.getData("Name").getString();
			System.out.println(name);
			int xo = (DnD.w-name.length()*7) / 2;
			this.ui.add(new Button("CH"+i, name, xo, 40 + 24 * i));
		}
	}
	
	//Looks for character files and then fills an array with acceptable ones
	private void lookForFiles(){
		String[] possible = FileIO.instance.listFiles(FileIO.path + "/data/chac/");
		int amt = 0;
		for(int i = 0; i < possible.length; i++){
			String f = possible[i];
			if(f.indexOf(".") != -1){
				String ending = f.substring(f.indexOf(".")+1);
				if(ending.equalsIgnoreCase("dat")){
					amt++;
				}
			}else continue;
		}
		
		if(amt == 0)return;
		DataFile[] df = new DataFile[amt];
		amt = 0;
		for(int i = 0; i < possible.length; i++){
			String f = possible[i];
			if(f.indexOf(".") != -1){
				String ending = f.substring(f.indexOf(".")+1);
				if(ending.equalsIgnoreCase("dat")){
					String newPath = "/chac/" + f.substring(0, f.indexOf("."));
					df[amt++] = new DataFile(newPath, true);
				}
			}else continue;
		}
		
		this.dfs = df;
	}

}
