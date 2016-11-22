package uk.fls.h2n0.main.util.gui;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Font;

public class TextBox extends UIComponent{
	
	private final String nullValue;
	private String currentValue;
	private int w,h;
	private boolean selected;
	
	private int timer;
	private boolean blink;
	
	public TextBox(String id, String nullValue, int x, int y, int w){
		super(id);
		this.nullValue = nullValue;
		this.pos = new Point(x,y);
		this.w = w;
		this.h = 16;
		this.currentValue = "";
		this.blink = false;
		this.timer = 0;
	}
	
	@Override
	public void update(int mx, int my, boolean click) {
		boolean xvalid = mx > this.pos.getIX() && mx < this.pos.getIX() + this.w;
		boolean yvalid = my > this.pos.getIY() && my < this.pos.getIY() + this.h;
		
		if(click){
			if(xvalid && yvalid){
				this.selected = true;
				this.blink = true;
			}else{
				this.selected = false;
				this.timer = 0;
			}
		}
		
		if(this.blink){
			this.timer = (this.timer+1) % 60;
		}
	}
	@Override
	public void render() {
		drawBackground(w, h, 0);
		String v = trimVisible(this.currentValue.isEmpty()?this.nullValue:this.currentValue);
		Font.instace.draw(this.rend, v, this.pos.getIX() + 1, this.pos.getIY() + (this.h/4), this.currentValue.isEmpty()?rend.makeRGB(128, 128, 128):rend.makeRGB(255, 255, 255));
		
		if(this.selected){
			if(this.timer/20 % 2 == 0){
				for(int y = 0; y < 8; y++){
					String cc = this.currentValue.isEmpty()?"":trimVisible(currentValue);
					this.rend.setPixel(this.pos.getIX() + 1 + (cc.length()) * 7 + 2, this.pos.getIY() + (this.h/4) + y, rend.makeRGB(255, 255, 255));
				}
			}
		}
	}
	
	/**
	 * Trim the given value so that it looks like the text is hidden behind the text box
	 * @param v
	 * @return A shortened version of currentValue
	 */
	public String trimVisible(String v){
		int size = 7;
		int cw = v.length() * size;
		int d = this.w - cw;
		if(d > 0)return v;// No need to trim or anything
		else{
			d = -d;
			d /= size;
			return v.substring(d + 1);
		}
	}
	
	/**
	 * Appends the current value with the given string
	 * @param c
	 */
	public void addChar(String c){
		this.currentValue += c;
	}
	
	/**
	 * Appends the current value with a space
	 */
	public void addSpace(){
		this.currentValue = this.currentValue + " ";
	}
	
	/**
	 * Simulate a backspace operation bu triming the current value by its length - 1
	 */
	public void backSpace(){
		if(this.currentValue.isEmpty())return;
		this.currentValue = this.currentValue.substring(0, this.currentValue.length()-1);
	}
	
	/**
	 * Used by the UI class to see if key values should be passed to the object
	 * @return
	 */
	public boolean isSelected(){
		return this.selected;
	}
	
	/**
	 * Returns the value of currentValue
	 * @return
	 */
	public String getValue(){
		return this.currentValue;
	}
	
	/**
	 * Same code as in the Button class, just draw a gray backround with some fake highlights
	 * @param w
	 * @param h
	 * @param c
	 */
	private void drawBackground(int w, int h, int c){
		//Setting the fake highlights
		int high = 0xFFFFFF;
		int low = 0x444444;	
		
		for(int xx = 0; xx < w; xx++){
			for(int yy = 0; yy < h; yy++){
				int dx = this.pos.getIX() + xx;
				int dy = this.pos.getIY() + yy;
				

				if((yy < h-1 && xx == 0) || (yy == 0 && xx < w -1)){
					rend.setPixel(dx, dy, high);
				}else if((yy >= h-1 && xx > 0) || (xx == w - 1 && yy > 0)){
					rend.setPixel(dx, dy, low);
				}else{
					//Skip over two corners that shouldn't be there
					if(xx == 0 && yy == h-1)continue;
					if(yy == 0 && xx == w-1)continue;
					
					rend.setPixel(dx, dy, c);
				}
			}
		}
	}
}
