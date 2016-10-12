package uk.fls.h2n0.main.util;

import fls.engine.main.io.FileIO;
import fls.engine.main.util.Renderer;
import fls.engine.main.util.rendertools.SpriteParser;

public class Font {

	private SpriteParser sp;
	private String letters;
	
	public static Font instace = new Font();
	
	public Font(){
		sp = new SpriteParser(FileIO.instance.readInternalFile("/font.art"));
		this.letters = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	}
	
	public void draw(Renderer r, String msg, int x, int y){
		draw(r,msg,x,y,r.makeRGB(255, 255, 255));
	}
	
	public void draw(Renderer r, String msg, int x, int y, int c){
		msg = msg.trim();
		msg = msg.toUpperCase();
		for(int i = 0; i < msg.length(); i++){
			int pos = this.letters.indexOf(msg.charAt(i));
			if(pos == -1)continue;
			int[] data = sp.getData(pos);
			for(int j = 0; j < 8 * 8; j++){
				int cx = j % 8;
				int cy = j / 8;
				
				if(data[j] == -1)continue;
				r.setPixel(x + i * 7 + cx, y + cy, data[j] & c);
			}
		}
	}
}
