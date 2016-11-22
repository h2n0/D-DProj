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
	
	/**
	 * Abstract class for all UI elements so that they all have the same functions for easy
	 * @param id
	 */
	public UIComponent(String id){
		this.id = id;
	}
	
	/**
	 * Sets the UI manager so that it can get the renderer and other attributes
	 * @param ui
	 */
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
