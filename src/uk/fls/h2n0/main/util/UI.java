package uk.fls.h2n0.main.util;

import java.util.List;
import java.util.ArrayList;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.util.gui.UIComponent;

public class UI {

	private Renderer r;
	
	private List<UIComponent> comps;
	
	public UI(Renderer r){
		this.r = r;
		this.comps = new ArrayList<UIComponent>();
		clear();
	}
	
	public Renderer getRenderer(){
		return this.r;
	}
	
	public void update(int mx, int my, boolean click){
		for(UIComponent p : this.comps){
			if(p.isHidden())continue;
			p.update(mx, my, click);
		}
	}
	
	public void render(){
		for(UIComponent p : this.comps){
			if(p.isHidden())continue;
			p.render();
		}
	}
	
	public boolean add(UIComponent comp){
		if(getCompByID(comp.id) == null){
			this.comps.add(comp);
			comp.setManager(this);
			return true;
		}
		return false;
	}
	
	public boolean remove(UIComponent comp){
		if(getCompByID(comp.id) != null){
			this.comps.remove(comp);
			return true;
		}
		return false;
	}
	
	public void clear(){
		this.comps.clear();
	}
	
	public UIComponent getCompByID(String id){
		for(UIComponent part : this.comps){
			if(part.id.equals(id)){
				return part;
			}
		}		
		return null;
	}
}
