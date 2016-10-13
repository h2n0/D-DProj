package uk.fls.h2n0.main.screens;

import java.awt.Graphics;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;
import uk.fls.h2n0.main.characters.Character;
import uk.fls.h2n0.main.characters.DwarfRace;
import uk.fls.h2n0.main.characters.ElfRace;
import uk.fls.h2n0.main.characters.HumanRace;

public class CreationScreen extends Screen {

	
	private Renderer r;
	private UI ui;
	private int step;
	
	//Used to hold random vars
	private int other;
	private Point mouse;
	private Character chac;
	private int inputdelay;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.ui = new UI(this.r);
		this.step = 0;
		this.other = 0;
		this.mouse = Point.zero;
		this.chac = new Character();
		this.inputdelay = 30;
		setupStep();
	}
	
	@Override
	public void update() {
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		if(this.input.isKeyPressed(this.input.a)){
			prev(1);
		}else if(this.input.isKeyPressed(this.input.d)){
			next(1);
		}
		
		
		
		
		if(inputdelay > 0){
			this.inputdelay--;
			return;
		}
		
		if(this.step == 0){//Race selection
			if(((Button)this.ui.getCompByID("DW")).clicked){//Dwarf
				this.chac.setRace(new DwarfRace());
				next(1);
			}else if(((Button)this.ui.getCompByID("EL")).clicked){//Elf
				this.chac.setRace(new ElfRace());
				next(1);
			}else if(((Button)this.ui.getCompByID("HU")).clicked){//Human
				this.chac.setRace(new HumanRace());
				next(2);
				// 2 steps because no sub race
			}else if(((Button)this.ui.getCompByID("HA")).clicked){//Halfling
				this.chac.setRace(new HaflingRace());
				next(1);
			}
		}else if(this.step == 1){// Subrace selection
			
		}
	}

	@Override
	public void render(Graphics g) {
		this.r.fill(r.makeRGB(123, 123, 123));
		this.ui.render();
		String header = "Chacracter creation";
		Font.instace.draw(r, header, (DnD.w-header.length()*7)/2, 24);
		Font.instace.draw(r, ""+step, (DnD.w-(""+step).length()*7)/2, 32, 255 << 16);
		
		if(this.step == 0){//Select race
			String words = "pick a race";
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, 56);
		}else if(this.step == 1){//Seleceting subrace
			String words = "pick a subrace of " + this.chac.getRace().getName();
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, 56);
		}
	}
	
	public void next(int val){
		this.step += val;
		setupStep();
	}
	
	public void prev(int val){
		this.step -= val;
		if(this.chac.getRace() instanceof HumanRace && this.step == 1){
			this.step--;
		}
		
		if(this.step < 0)this.step = 0;
		setupStep();
	}
	
	public void setupStep(){
		this.ui.clear();
		if(this.step == 0){//Chose a race
			int step = 24;
			int yo = 80;
			
			String[] races = new String[]{"Dwarf","Elf","Human","Halfling"};
			for(int i = 0; i < races.length; i++){
				String name = races[i].toUpperCase();
				int xo = (DnD.w - (name.length()*8))/2;
				System.out.println(name + ":" + name.substring(0,2));
				this.ui.add(new Button(name.substring(0,2),name, xo, yo + step * i));
			}
		}else if(this.step == 1){//Chose a subrace
			int step = 24;
			int yo = 80;

			String[] races = this.chac.getRace().getSubracesNames();
			for(int i = 0; i < races.length; i++){
				String name = races[i].toUpperCase();
				int xo = (DnD.w - (name.length()*8))/2;
				this.ui.add(new Button("RC"+i,name, xo, yo + step * i));
			}
		}
		this.inputdelay = 30;
	}

}
