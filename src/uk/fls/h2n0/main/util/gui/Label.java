package uk.fls.h2n0.main.util.gui;

import fls.engine.main.util.Point;
import uk.fls.h2n0.main.util.Font;

public class Label extends UIComponent{

	private String text;
	
	public Label(String id, String label, int x, int y) {
		super(id);
		this.text = label;
		this.pos = new Point(x, y);
	}



	@Override
	public void update(int mx, int my, boolean click) {
		
	}

	@Override
	public void render() {
		Font.instace.draw(this.rend, this.text, this.pos.getIX(), this.pos.getIY());
	}
}
