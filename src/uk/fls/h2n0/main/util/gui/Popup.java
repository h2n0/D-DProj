package uk.fls.h2n0.main.util.gui;

import uk.fls.h2n0.main.DnD;
import uk.fls.h2n0.main.util.Font;
import uk.fls.h2n0.main.util.UI;

public class Popup extends UIComponent {

	
	private boolean acpt, deny;
	private Button[] btns;
	private int w, h, x, y;
	private int t;
	
	public Popup(String id) {
		super(id);
		this.acpt = false;
		this.deny = false;
		this.w = 160;
		this.h = 100;
		this.x = (DnD.w-this.w)/2 - 1;
		this.y = (DnD.h-this.h)/2;
		int xo = this.w/5;
		this.btns = new Button[]{new Button("ACP","Okay", this.x + xo, this.y + this.h - 17), new Button("DNY","Nope", this.x + xo * 3, this.y + this.h - 17)};
	}

	@Override
	public void update(int mx, int my, boolean click) {
		for(Button b : this.btns){
			b.update(mx, my, click);
		}
		
		if(this.btns[0].clicked){
			this.acpt = true;
		}else if(this.btns[1].clicked){
			this.deny = true;
		}
		
		this.t = (this.t + 1) % 1000;
	}

	@Override
	public void render() {
		int c = this.rend.makeRGB(123,123,123);
		int high = c << 4;
		int low = c & 0xCECECE;	
		
		
		for(int xx = 0; xx < this.w; xx++){
			for(int yy = 0; yy < this.h; yy++){

				int dx = this.x + xx;
				int dy = this.y + yy;
				

				if((yy < h-1 && xx == 0) || (yy == 0 && xx < w -1)){
					rend.setPixel(dx, dy, high);
				}else if((yy >= h-1 && xx > 0) || (xx == w - 1 && yy > 0)){
					rend.setPixel(dx, dy, low);
				}else{
					rend.setPixel(dx, dy, c);
				}
			}
		}
		for(Button b : this.btns){
			b.render();
		}
		
		String[] lines = new String[]{"!! WARNING !!", "", "", "", "You will lose your", "previously saved","character if you", "continue"};
		int w = this.rend.makeRGB(255, 255, 255);
		for(int i = 0; i < lines.length; i++){
			String l = lines[i];
			int xo = l.trim().length() * 7;
			
			int color = i == 0?((this.t/30 % 2) == 0?rend.makeRGB(255, 150, 0):rend.makeRGB(255, 0, 0)):w;
			Font.instace.draw(rend, l, this.x + (this.w-xo)/2, this.y + i * 8, color);
		}
	}
	
	public boolean accept(){
		return this.acpt;
	}
	
	public boolean deny(){
		return this.deny;
	}
	
	public void setManager(UI ui){
		super.setManager(ui);
		for(Button b : this.btns){
			b.setManager(ui);
		}
	}

}
