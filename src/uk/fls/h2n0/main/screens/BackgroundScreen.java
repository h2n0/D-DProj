package uk.fls.h2n0.main.screens;

import java.awt.Graphics;
import java.io.File;
import java.net.URL;
import java.util.Arrays;

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
import uk.fls.h2n0.main.util.gui.TextBox;

public class BackgroundScreen extends Screen {

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

	private String[] finalParts;

	private int inputDelay;

	public BackgroundScreen(Character c) {
		this.c = c;
		this.step = 0;
		this.other = 0;
		this.mouse = Point.zero;
		this.selectedParts = new String[4];
		this.finalParts = new String[4];
		this.inputDelay = 0;
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
		if (this.inputDelay > 0) {
			this.inputDelay--;
			return;
		}
		this.mouse.setPos(this.input.mouse.getX() / DnD.s, this.input.mouse.getY() / DnD.s);
		this.ui.update(this.mouse.getIX(), this.mouse.getIY(), this.input.leftMouseButton.justClicked());

		if (this.step == 0) {// Selecting the BG

			for (int i = 0; i < dfs.length; i++) {
				Button btn = (Button) this.ui.getCompByID("BG" + (i + 1));
				if (btn == null)
					continue;
				if (btn.clicked) {
					selectBG(i);
					next();
				}
			}
		} else if (this.step == 1) {// Selecting trait
			if (checkCONF()) {
				this.finalParts[0] = this.selectedParts[this.other];
				this.other = 0;
				next();
			}
		} else if (this.step == 2) { // Selecting bond
			if (checkCONF()) {
				this.finalParts[1] = this.selectedParts[this.other];
				this.other = 0;
				next();
			}
		} else if (this.step == 3) {
			if (checkCONF()) {
				this.finalParts[2] = this.selectedParts[this.other];
				this.other = 0;
				next();
			}
		} else if (this.step == 4) {
			if (checkCONF()) {
				this.finalParts[3] = this.selectedParts[this.other];
				this.other = 0;
				next();
			}
		} else if (this.step == 5) {
			if (checkCONF()) {
				this.c.setName(((TextBox) this.ui.getCompByID("TB")).getValue());
				
				
				String[] p = this.selectedBG.getData("SP").getString().split(",");
				for(String pa : p){
					int id = Integer.parseInt(pa.trim());
					this.c.getSkillManager().makeProf(id);
				}
				
				this.c.getSkillManager().print();
				this.c.getStats().setCurrentHp(this.c.getStats().getMaxHp());
				FileIO.instance.deleteDir(FileIO.instance.path+"/data/temp");
				this.c.save();
				setScreen(new CharacterScreen(this.c));
			}
		}

		if (this.step >= 1 && this.step <= 4) {
			int amt = this.step == 1 ? 8 : 6;
			if (this.input.isKeyPressed(this.input.a)) {
				other = other - 1;
				if (other < 0)
					other = amt - 1;
			} else if (this.input.isKeyPressed(this.input.d)) {
				other = (other + 1) % amt;
			}
		}
	}

	@Override
	public void render(Graphics g) {
		this.r.fill(this.bgColor);
		this.ui.render();

		if (this.step == 0) {
			String phrase = "Select a background";
			Font.instace.draw(r, phrase, (DnD.w - phrase.length() * 7) / 2, 24);
		} else if (this.step >= 1 && this.step <= 4) {
			String name = this.selectedBG.getData("Title").getString();
			Font.instace.draw(r, name, (DnD.w - name.length() * 7) / 2, 24);

			String[] part = new String[] { "Trait", "Ideal", "Bonus", "Flaw" };
			String phrase = "Select a " + part[this.step - 1];
			Font.instace.draw(r, phrase, (DnD.w - phrase.length() * 7) / 2, 32);

			String words = this.selectedParts[this.other];
			String[] parts = words.split(" ");

			String[] out = getRenderWorthy(parts, DnD.w / 8);
			for (int i = 0; i < out.length; i++) {
				Font.instace.draw(r, out[i], 4, 80 + i * 8);
				// Font.instace.drawCenter(r, out[i], 80 + (i * 8));
			}
		} else if (this.step == 5) {
			Font.instace.drawCenter(this.r, "Finally", 8);
			Font.instace.drawCenter(this.r, "Name your character", 24);
		}
	}

	private void fillDFs() {

		String[] bgs = new String[] { "BG1", "BG2", "BG3", "BG4", "BG5", "BG6" };

		this.dfs = new DataFile[6];
		int good = 0;
		for (int i = 0; i < bgs.length; i++) {
			try {
				String p = "/bgs/" + bgs[i] + ".dat";
				String[] lines = FileIO.instance.readInternalFile(p).split("\n");
				DataFile df = new DataFile(lines);
				this.dfs[good] = df;
				good++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void setupStep() {
		this.ui.clear();

		if (this.step == 0) {// Selecting BG
			int yo = 48;
			for (int i = 0; i < this.dfs.length; i++) {
				String text = this.dfs[i].getData("Title").getString();
				this.ui.add(new Button("BG" + (i + 1), text, (DnD.w - (text.length()) * 8) / 2, yo + i * 24));
			}
		} else if (this.step == 1) {// Trait selection
			String[] ts = splitupString(this.selectedBG.getData("T").getString());
			this.selectedParts = ts;

			String conf = "Select";
			this.ui.add(new Button("CONF", conf, (DnD.w - conf.length() * 7) / 2, DnD.h - 40));
		} else if (this.step == 2) {// Bond selection
			String[] ts = splitupString(this.selectedBG.getData("B").getString());
			this.selectedParts = ts;

			String conf = "Select";
			this.ui.add(new Button("CONF", conf, (DnD.w - conf.length() * 7) / 2, DnD.h - 40));
		} else if (this.step == 3) {// Ideal selection
			String[] ts = splitupString(this.selectedBG.getData("I").getString());
			this.selectedParts = ts;

			String conf = "Select";
			this.ui.add(new Button("CONF", conf, (DnD.w - conf.length() * 7) / 2, DnD.h - 40));
		} else if (this.step == 4) {// Flaw selection
			String[] ts = splitupString(this.selectedBG.getData("F").getString());
			this.selectedParts = ts;

			String conf = "Select";
			this.ui.add(new Button("CONF", conf, (DnD.w - conf.length() * 7) / 2, DnD.h - 40));
		} else if (this.step == 5) {// Allow the user to name their character
			int w = 14 * 7;
			this.ui.add(new TextBox("TB", "", (DnD.w - w + 2) / 2, (DnD.h - 18) / 2, w));

			String conf = "Finish";
			this.ui.add(new Button("CONF", conf, (DnD.w - conf.length() * 7) / 2, DnD.h - 40));
		}
	}

	private void selectBG(int i) {
		this.selectedBG = this.dfs[i];
	}

	private String[] splitupString(String line) {
		String[] res = new String[0];
		boolean inQuote = false;

		int amt = 0;

		for (int i = 0; i < line.length(); i++) {
			String c = line.substring(i, i + 1);
			if (c.equals("\"")) {
				boolean p = inQuote;
				inQuote = !inQuote; // Toggles state
				if (p && !inQuote) {// Just left
					amt++;
				}
			}
		}

		if (inQuote) {
			System.err.println("Incorrect formatting!!!");
			return null;
		}

		inQuote = false;
		res = new String[amt];
		int current = 0;
		String curr = "";

		for (int i = 0; i < line.length(); i++) {
			String c = line.substring(i, i + 1);

			boolean p = inQuote;
			if (c.equals("\"")) {
				inQuote = !inQuote;
			}

			if (inQuote) {
				curr += c;
			} else if (p && !inQuote) {
				res[current++] = curr.substring(1);
				curr = "";
			}
		}
		return res;
	}

	private String[] getRenderWorthy(String[] split, int max) {
		max = max * 8;
		int w = 0;
		int size = 0;
		for (int i = 0; i < split.length; i++) {
			int s = (split[i].length() + 1) * 7;
			if (w + s > max) {
				size++;
				w = s;
			} else {
				w += s;
			}
		}

		w = 0;
		int c = 0;
		String[] res = new String[size + 1];
		Arrays.fill(res, "");

		for (int i = 0; i < split.length; i++) {
			int s = (split[i].length() + 1) * 7;
			if (w + s <= max) {
				res[c] += split[i] + " ";
				w += s;
			} else {
				res[c] = res[c].trim();
				c++;
				res[c] = split[i] + " ";
				w = s;
			}
		}

		return res;
	}

	private void next() {
		this.step++;
		setupStep();
	}

	private int randomColor() {
		float rr = (float) Math.random() * 255;
		float gg = (float) Math.random() * 255;
		float bb = (float) Math.random() * 255;
		return this.r.makeRGB((int) rr, (int) gg, (int) bb);
	}

	@Override
	public void keyTyped(char e) {
		this.ui.keyTyped(e);
	}

	private boolean checkCONF() {
		if (this.inputDelay > 0)
			return false;
		Button btn = (Button) this.ui.getCompByID("CONF");
		if (btn == null)
			return false;
		if (btn.clicked) {
			this.inputDelay = 30;
			return true;
		} else
			return false;
	}
}
