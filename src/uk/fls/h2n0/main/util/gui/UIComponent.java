package uk.fls.h2n0.main.util.gui;

import fls.engine.main.util.Point;
import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.util.UI;

public abstract class UIComponent {

	
	protected Point pos;
	public String id;
	protected UI manager;
	protected Renderer rend;
	protected boolean hiden;
	
	public UIComponent(String id){
		this.id = id;
	}
	
	public void setManager(UI ui){
		this.manager = ui;
		this.rend = ui.getRenderer();
	}
	
	public void setPos(int x, int y){
		this.pos.setPos(x, y);
	}

	public abstract void update(int mx, int my, boolean click);
	
	public abstract void render();
	
	public boolean isHidden(){
		return this.hiden;
	}
	
	public void hide(){
		this.hiden = true;
	}
	
	public void unhide(){
		this.hiden = false;
	}
	
}
