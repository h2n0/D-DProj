package uk.fls.h2n0.main.util;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;
import uk.fls.h2n0.main.DnD;

public class Font {

	private SpriteParser sp;
	private String letters;

	/**
	 * Static instance of I don't have to keep creating instances of the class
	 */
	public static Font instace = new Font();

	public Font() {
		//All the magic that allows this to work
		sp = new SpriteParser(FileIO.instance.readInternalFile("/font.art"));
		this.letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ+-()!,’.:/";
	}

	/**
	 * Draws the given text in the given location
	 * @param r
	 * @param msg
	 * @param x
	 * @param y
	 */
	public void draw(Renderer r, String msg, int x, int y) {
		msg = msg.trim();
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int pos = this.letters.indexOf(msg.charAt(i));
			if (pos == -1)
				continue;
			int[] data = sp.getData(pos);
			r.renderSection(data, x + i * 7, y, 8);
		}
	}

	/**
	 * Draws the given text in the given place but applys a color to the text
	 * @param r
	 * @param msg
	 * @param x
	 * @param y
	 * @param c
	 */
	public void draw(Renderer r, String msg, int x, int y, int c) {
		msg = msg.trim();
		msg = msg.toUpperCase();
		for (int i = 0; i < msg.length(); i++) {
			int pos = this.letters.indexOf(msg.charAt(i));
			if (pos == -1)
				continue;
			int[] data = sp.getData(pos);
			for (int j = 0; j < 8 * 8; j++) {
				int cx = j % 8;
				int cy = j / 8;

				if (data[j] == -1)
					continue;
				r.setPixel(x + i * 7 + cx, y + cy, data[j] & c);
			}
		}
	}

	/**
	 * Similar to the draw function but only needs an X coordinate
	 * @param r
	 * @param msg
	 * @param y
	 */
	public void drawCenter(Renderer r, String msg, int y) {
		if(msg == null)return;
		draw(r, msg, (DnD.w - (msg.length() * 7)) / 2, y);
	}

	/**
	 * Similar to the draw function but only needs an X coordinate
	 * @param r
	 * @param msg
	 * @param y
	 * @param c
	 */
	public void drawCenter(Renderer r, String msg, int y, int c) {
		if(msg == null)return;
		draw(r, msg, (DnD.w - (msg.length() * 7)) / 2, y, c);
	}
}
