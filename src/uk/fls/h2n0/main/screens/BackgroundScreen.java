package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.characters.Character;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;

public class BackgroundScreen extends Screen{

	private UI ui;
	private Renderer r;
	private Point mouse;
	private Character c;
	private int step;
	private int other;
	private DataFile[] dfs;
	private DataFile selectedBG;
	private String[] selectedParts;
	private int bgColor;
	
	public BackgroundScreen(Character c) {
		this.c = c;
		this.step = 0;
		this.other = 0;
		this.mouse = Point.zero;
		this.selectedParts = new String[4];
	}
	
	@Override
	public void postInit() {
		this.r = new Renderer(this.game.getImage());
		this.ui = new UI(r);
		this.bgColor = r.makeRGB(123, 123, 123);
		

		fillDFs();
		setupStep();
	}
	
	
	@Override
	public void update() {
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		if(this.step == 0){// Selecting the BG
			
			for(int i = 0; i < dfs.length; i++){
				Button btn = (Button)this.ui.getCompByID("BG"+(i + 1));
				if(btn == null)continue;
				if(btn.clicked){
					selectBG(i);
					next();
				}
			}
		}else if(this.step == 1){// Selecting trait
			if(this.input.isKeyPressed(this.input.a)){
				other = (other - 1) % 8;
			}else if(this.input.isKeyPressed(this.input.d)){
				other = (other + 1) % 8;
			}
		}
		
		if(this.input.isKeyPressed(this.input.space)){
			setupStep();
		}
	}
	
	@Override
	public void render(Graphics g) {
		this.r.fill(this.bgColor);
		this.ui.render();
		
		if(this.step == 0){
			String phrase = "Select a background";
			Font.instace.draw(r, phrase, (DnD.w-phrase.length()*7)/2, 24);
		}else if(this.step == 1){
			String name = this.selectedBG.getData("Title").getString();
			Font.instace.draw(r, name, (DnD.w-name.length()*7)/2, 24);

			String phrase = "Select a trait";
			Font.instace.draw(r, phrase, (DnD.w-phrase.length()*7)/2, 32);
			
			
			String words = this.selectedParts[this.other];
			String[] parts = words.split(" ");
			
			String[] out = getRenderWorthy(parts, 100);
			for(int i = 0; i < out.length; i++){
				Font.instace.drawCenter(r, out[i], 40 + i * 16);
			}
		}
	}
	
	private void fillDFs(){
		String[] files = FileIO.instance.listFiles(FileIO.path + "/res/bgs/");
		
		int good = 0;
		for(String s : files){
			String ending = s.substring(s.indexOf(".")+1).trim();
			if(ending.equals("dat")){
				good++;
			}
		}
		
		
		this.dfs = new DataFile[good];
		good = 0;
		for(String s : files){
			String ending = s.substring(s.indexOf(".")+1).trim();
			if(ending.equals("dat")){
				String name = s.substring(0, s.indexOf(".")).trim();
				String loc = "res/bgs/" + name;
				DataFile df = new DataFile(loc, false);
				//System.out.println(df.getData("Title").getString());
				this.dfs[good] = df;
				good ++;
			}
		}
	}
	
	private void setupStep(){
		this.ui.clear();
		
		if(this.step == 0){// Selecting BG
			int yo = 48;
			for(int i = 0; i < this.dfs.length; i++){
				String text = this.dfs[i].getData("Title").getString();
				this.ui.add(new Button("BG"+(i+1), text, (DnD.w-(text.length())*8)/2, yo + i * 24));
			}
		}else if(this.step == 1){
			String[] ts = splitupString(this.selectedBG.getData("T").getString());
			this.selectedParts = ts;
		}
	}
	
	private void selectBG(int i){
		this.selectedBG = this.dfs[i];
	}
	
	private String[] splitupString(String line){
		String[] res = new String[0];
		boolean inQuote = false;
		
		int amt = 0;
		
		for(int i = 0; i < line.length(); i++){
			String c = line.substring(i, i+1);
			if(c.equals("\"")){
				boolean p = inQuote;
				inQuote = !inQuote; // Toggles state
				if(p && !inQuote){// Just left
					amt++;
				}
			}
		}
		
		if(inQuote){
			System.err.println("Incorrect formatting!!!");
			return null;
		}
		
		inQuote = false;
		res = new String[amt];
		int current = 0;
		String curr = "";
		
		for(int i = 0; i < line.length(); i++){
			String c = line.substring(i, i+1);
			
			boolean p = inQuote;
			if(c.equals("\"")){
				inQuote = !inQuote;
			}
			
			if(inQuote){
				curr += c;
			}else if(p && !inQuote){
				res[current++] = curr.substring(1);
				curr = "";
			}
		}
		return res;
	}
	
	private String[] getRenderWorthy(String[] split, int max){
		int w = 0;
		int size = 0;
		for(int i = 0; i < split.length; i++){
			if(w + split[i].length() * 7 > max){
				size ++;
				w = 0;
			}
		}
		
		w = 0;
		int c = 0;
		String[] res = new String[size];
		for(int i = 0; i < split.length; i++){
			if(w + split[i].length() * 7 <= max){
				res[c] += split[i] + " ";
			}else{
				res[c] = res[c].substring(0, res[c].length());
				c++;
				w = 0;
			}
		}
		
		return res;
	}
	
	private void next(){
		this.step ++;
		setupStep();
	}
}
