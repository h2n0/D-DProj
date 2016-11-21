package uk.fls.h2n0.main.util.gui;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Font;

public class Button extends UIComponent{
	
	
	private String label;
	private boolean hover;
	public boolean clicked;
	private int w, h;
	
	public Button(String id, String label, int x, int y){
		super(id);
		this.label = label;
		this.pos = new Point(x, y);
		this.w = this.h = 0;
	}
	
	public void update(int mx, int my, boolean click) {
		boolean xvalid = mx > this.pos.getIX() && mx < this.pos.getIX() + this.w;
		boolean yvalid = my > this.pos.getIY() && my < this.pos.getIY() + this.h;
		
		this.hover = false;
		this.clicked = false;
		
		if(xvalid && yvalid){
				this.hover = true;
				if(click){
					this.clicked = true;
				}
		}
	}
	
	public void render() {
		label = label.trim();
		this.w = label.length();
		this.h = 2 * 8;
		drawBackground(w * 8, this.h, rend.makeRGB(123, 123, this.hover?153:123));
		int xo = this.pos.getIX() + (w * 8) / 2;
		Font.instace.draw(rend, label, xo - (w * 7)/2, this.pos.getIY() + 3, this.hover?this.rend.makeRGB(255, 255, 63):this.rend.makeRGB(255, 255, 255));
		this.w *= 8;
	}
	
	private void drawBackground(int w, int h, int c){
		int high = c << 4;
		int low = c & 0xCECECE;	
		for(int xx = 0; xx < w; xx++){
			for(int yy = 0; yy < h; yy++){
				int dx = this.pos.getIX() + xx;
				int dy = this.pos.getIY() + yy;
				
				rend.setPixel(dx, dy, c);
				

				if((yy < h-1 && xx == 0) || (yy == 0 && xx < w -1)){
					rend.setPixel(dx, dy, high);
				}else if((yy >= h-1 && xx > 0) || (xx == w - 1 && yy > 0)){
					rend.setPixel(dx, dy, low);
				}
			}
		}
	}
}
