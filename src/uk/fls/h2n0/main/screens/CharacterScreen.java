package uk.fls.h2n0.main.screens;

import java.awt.Graphics;

import fls.engine.main.screen.Screen;
import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.characters.Character;
import uk.fls.h2n0.main.characters.Stats;
import uk.fls.h2n0.main.util.Dice;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;
import uk.fls.h2n0.main.util.gui.Button;

public class CharacterScreen extends Screen {

	public Renderer r;
	public UI ui;
	private Character chac;
	private int bgColor;
	private Point mouse;
	private int inputDelay;
	private int[] diceRes;
	
	public CharacterScreen(Character c) {
		this.chac = c;
		this.mouse = Point.zero;
		this.inputDelay = 0;
	}
	
	public void postInit(){
		this.r = new Renderer(this.game.getImage());
		this.ui = new UI(this.r);
		this.bgColor = this.r.makeRGB(128, 128, 128);
		
		
		String dice = "Roll Dice";
		int xo = 16;
		this.ui.add(new Button("D20","D20", xo, 148));
		this.ui.add(new Button("DHT","Hit", xo + 40, 148));
		
		int co = DnD.w/2 - 15;
		this.ui.add(new Button("EXT", "Exit", co - 40, DnD.h - 40));
		this.ui.add(new Button("SCC", "Save state", co, DnD.h - 40));
		
		this.ui.add(new Button("FAIL","FAIL", 116, 100));
		this.ui.add(new Button("SAVE","SAVE", 116 + 32, 100));
	}
	
	@Override
	public void update() {
		if(this.inputDelay > 0)this.inputDelay--;
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY() / DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());
		
		//Check to see if the user wants to leave the current screen
		if(exitClicked()){
			setScreen(new TitleScreen());
		}
		
		//Check to see if the user wants to roll the dice
		if(buttonClicked("DHT")){
			this.diceRes = Dice.rollDice(this.chac.getHitDiceString());
			this.inputDelay = 10;
		}
		
		if(buttonClicked("D20")){
			this.diceRes = Dice.rollDice("d20");
			this.inputDelay = 10;
		}
		
		//Check to see if the user made an unsuccessful save roll
		if(buttonClicked("FAIL")){
			this.chac.getStats().failSave();
			this.inputDelay = 10;
		}
		
		//Check to see if the user made a successful save roll
		if(buttonClicked("SAVE")){
			this.chac.getStats().successfulSave();
			this.inputDelay = 10;
		}
		
		//Check to see if the user wants to save the current character
		if(buttonClicked("SCC")){
			this.chac.save();
		}
	}

	@Override
	public void render(Graphics g) {
		this.r.fill(this.bgColor);
		this.ui.render();

		Stats stat = this.chac.getStats();
		//General things
		Font.instace.drawCenter(r, "Name: " + this.chac.getName(), 8);
		Font.instace.drawCenter(r, "Race: " + this.chac.getRace().getName(), 16);
		Font.instace.drawCenter(r, "Class: " + this.chac.getRole().getName() + " / LVL "+stat.getLevel(), 24);
		
		//Show the stats about the current character
		int yo = 16;
		int ys = 48;
		Font.instace.draw(r, "STR: " + stat.getStrength() + " ("+stat.getBonusString(stat.getStrength())+")", 8, ys);
		Font.instace.draw(r, "DEX: " + stat.getDexterity() + " ("+stat.getBonusString(stat.getDexterity())+")", 8, ys + yo);
		Font.instace.draw(r, "INT: " + stat.getIntelligence() + " ("+stat.getBonusString(stat.getIntelligence())+")", 8, ys + yo * 2);
		Font.instace.draw(r, "WIS: " + stat.getWisdom() + " ("+stat.getBonusString(stat.getWisdom())+")", 8, ys + yo * 3);
		Font.instace.draw(r, "CON: " + stat.getConstitution() + " ("+stat.getBonusString(stat.getConstitution())+")", 8, ys + yo * 4);
		Font.instace.draw(r, "CHA: " + stat.getCharisma() + " ("+stat.getBonusString(stat.getCharisma())+")", 8, ys + yo * 5);

		if(diceRes != null && diceRes.length > 0){
			Font.instace.draw(r, "Dice: ", 8, ys + yo * 8);
			Font.instace.draw(r, ""+readableDice(diceRes), 8, ys + yo * 9);
		}
		
		//Health and things
		Font.instace.draw(r, "HP: " + stat.getHp() + "/" + stat.getMaxHp(), 116, ys);
		
		
		//Render the saving throws
		//Similar technique to the "fake" hilighing is used for borders
		int l = 200;
		int base = r.makeRGB(l, l, l);
		int max = base & 0xFEFEFE;
		int min = base << 4;
		int space = 16;
		int suc = r.makeRGB(0, 220, 100);
		int fai = r.makeRGB(220, 0, 100);
		for(int i = 0; i < 3; i++){
			int c = i < stat.getFailSaves()?fai: base >> 1;
			r.drawCircle(132 + i * space - 1, ys + yo * 2 + 4 - 1, 6, max);
			r.drawCircle(132 + i * space + 1, ys + yo * 2 + 4 + 1, 6, min);
			r.drawCircle(132 + i * space, ys + yo * 2 + 4, 5, c);
		}
		
		for(int i = 0; i < 3; i++){
			int c = i < stat.getSuccessSaves()?suc: base >> 1;
			r.drawCircle(132 + i * space - 1, ys + yo + 4 - 1, 6, max);
			r.drawCircle(132 + i * space + 1, ys + yo + 4 + 1, 6, min);
			r.drawCircle(132 + i * space, ys + yo + 4, 5, c);
		}
	}
	
	//Not too sue why I need this function anymore, oh well
	private boolean exitClicked(){
		return buttonClicked("EXT");
	}
	
	//Utility function to check buttons easily
	private boolean buttonClicked(String id){
		if(this.inputDelay > 0)return false;
		Button btn = (Button)this.ui.getCompByID(id);
		if(btn == null)return false;
		else return btn.clicked;
	}
	
	//Conver the dice int[] to a readable string
	private String readableDice(int[] d){
		String res = "";
		for(int i : d){
			res += i + ",";
		}
		return res.substring(0, res.length()-1);
	}

}
