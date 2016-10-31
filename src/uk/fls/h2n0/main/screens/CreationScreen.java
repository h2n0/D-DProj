package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.io.DataFile;
import fls.engine.main.io.FileIO;
import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.characters.Character;
import uk.fls.h2n0.main.characters.Stats;
import uk.fls.h2n0.main.characters.classes.ClericRole;
import uk.fls.h2n0.main.characters.classes.FighterRole;
import uk.fls.h2n0.main.characters.classes.RougeRole;
import uk.fls.h2n0.main.characters.classes.WizardRole;
import uk.fls.h2n0.main.characters.race.DwarfRace;
import uk.fls.h2n0.main.characters.race.ElfRace;
import uk.fls.h2n0.main.characters.race.HaflingRace;
import uk.fls.h2n0.main.characters.race.HumanRace;
import uk.fls.h2n0.main.characters.race.dwarf.MountainDwarfRace;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;
import uk.fls.h2n0.main.util.gui.Label;

public class CreationScreen extends Screen {

	
	private Renderer r;
	private UI ui;
	private int step;
	
	//Used to hold random vars
	private int other;
	private Point mouse;
	private Character chac;
	private int inputdelay;
	
	private int[] modStats;
	private int modPoints;
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.ui = new UI(this.r);
		this.step = 0;
		this.other = 0;
		this.mouse = Point.zero;
		this.chac = new Character();
		this.inputdelay = 30;
		this.modStats = new int[]{8,8,8,8,8,8};
		this.modPoints = 27;
		setupStep();
	}
	
	@Override
	public void update() {
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY()/ DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		if(this.input.isKeyPressed(this.input.a)){
			prev(999);
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
			for(int i = 0; i < 2; i++){
				Button btn = ((Button)this.ui.getCompByID("RC"+i));
				if(btn == null)continue;
				if(btn.clicked){
					this.chac.setRace(this.chac.getRace().getSubraces()[i]);
					next(1);
					break;
				}
			}
		}else if(this.step == 2){// Class selection
			for(int i = 0; i < 4; i++){
				Button btn = ((Button)this.ui.getCompByID("CC"+i));
				if(btn == null)continue;
				if(btn.clicked){
					if(i == 0){//Cleric
						this.chac.setRole(new ClericRole());
						next(1);
					}else if(i == 1){//Fighter
						this.chac.setRole(new FighterRole());
						next(1);
					}else if(i == 2){//Rouge
						this.chac.setRole(new RougeRole());
						next(1);
					}else if(i == 3){// Wizard
						this.chac.setRole(new WizardRole());
						next(1);
					}
				}
			}
		}else if(this.step == 3){// Assigning ability scores
			for(int i = 0; i < 6; i++){
				Button min = (Button)this.ui.getCompByID("BTN"+i+":"+0);
				Button plus = (Button)this.ui.getCompByID("BTN"+i+":"+1);
				if(plus == null || min == null)continue;
				
				if(plus.clicked){
					if(this.modPoints > 0){
						this.modStats[i] ++;
						if(this.modStats[i] > 15){
							this.modStats[i] = 15;
						}
						this.inputdelay = 10;
						calcModPoints(1);
					}
				}else if(min.clicked){
					this.modStats[i] --;
					this.inputdelay = 10;
					if(this.modStats[i] < 8){
						this.modStats[i] = 8;
					}
					calcModPoints(-1);
				}
				
				Button conf = (Button)this.ui.getCompByID("CONF");
				if(conf == null)return;
				if(conf.clicked){
					this.chac.applyStats(modStats);
					this.chac.getStats().print();
					next(1);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		this.r.fill(r.makeRGB(123, 123, 123));
		this.ui.render();
		String header = "Chacracter creation";
		Font.instace.draw(r, header, (DnD.w-header.length()*7)/2, 24);
		Font.instace.draw(r, ""+step, (DnD.w-(""+step).length()*7)/2, 32, 255 << 16);
		
		
		int yoWord = 8 * 5;
		if(this.step == 0){//Select race
			String words = "pick a race";
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, yoWord);
		}else if(this.step == 1){//Seleceting subrace
			String words = "pick a subrace of " + this.chac.getRace().getName();
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, yoWord);
		}else if(this.step == 2){// Class selection
			String words = "pick a class";
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, yoWord);
		}else if(this.step == 3){
			String words = "Apply stats";
			Font.instace.draw(r, words, (DnD.w-(words.length()*7))/2, yoWord);
			
			for(int i = 0; i < 6; i++){
				int xOff = Math.max((""+this.modStats[i]).length()-1, 0);
				Font.instace.drawCenter(r, ""+this.modStats[i], 8 * 9 + (i * 8 * 4));
			}
			
			Font.instace.draw(r, "Remaining points " + this.modPoints, 8, 8);
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
		}else if(this.step == 2){// Chose a class
			this.chac.getStats().print();
			int step = 24;
			int yo = 80;

			String[] races = new String[]{"Cleric","Fighter","Rouge","Wizard"};
			for(int i = 0; i < races.length; i++){
				String name = races[i].toUpperCase();
				int xo = (DnD.w - (name.length()*8))/2;
				this.ui.add(new Button("CC"+i,name, xo, yo + step * i));
			}
		}else if(this.step == 3){// Assign ability scores
			String[] abilitys = new String[]{"STRength","DEXterity","WISdom","CONstitution","INTelligence", "CHRarisma"};

			int xo = 8 * 9;
			int yo = 8 * 8;
			int step = 8 * 4;
			
			for(int i = 0; i < 6; i++){
				int xx = (DnD.w-(abilitys[i].length()*7))/2;
				this.ui.add(new Label("LB"+i, abilitys[i]+":", xx, yo + i * step));
				for(int j = 0; j < 2; j++){
					this.ui.add(new Button("BTN"+i+":"+j, j==1?"+":"-",xo+(j==0?-24:72), yo + step * i));
				}
			}
			
			this.ui.add(new Button("CONF", "Confirm", 8 * 9, 8 * 32));
		}else if(this.step == 4){
			saveTempFile();
		}
		this.inputdelay = 30;
	}
	
	private boolean calcModPoints(int amt){
		int reduc = 0;
		for(int i = 0; i < 6; i++){
			int p = this.modStats[i];
			int o = p - 8;
			if(p == 14){
				o = 7;
			}else if(p == 15){
				o = 9;
			}
			reduc += o;
		}
		this.modPoints = 27 - reduc;
		return true;
	}
	
	/**
	 * Saves a temporary version of the character in it's current state
	 */
	private void saveTempFile(){
		String state = "State: T";
		String name = "Name: ?";
		String fileName = "/chars/temp/temp1";
		Stats stats = this.chac.getStats();
		DataFile df = new DataFile(fileName);
		df.setValue("State", "T");
		df.setValue("Name", "????");
		df.setValue("STR", valueToHex(stats.getStrength()));
		df.setValue("DEX", valueToHex(stats.getDexterity()));
		df.setValue("WIS", valueToHex(stats.getWisdom()));
		df.setValue("CON", valueToHex(stats.getConstitution()));
		df.setValue("INT", valueToHex(stats.getIntelligence()));
		df.setValue("CHR", valueToHex(stats.getCharisma()));
		df.setValue("Race", this.chac.getRace().getName());
	}
	
	private String valueToHex(int v){
		switch(v){
			default:
				return ""+v;
			case 10:
				return "A";
			case 11:
				return "B";
			case 12:
				return "C";
			case 13:
				return "D";
			case 14:
				return "E";
			case 15:
				return "F";
		}			
	}

}
