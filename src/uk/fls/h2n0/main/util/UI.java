package uk.fls.h2n0.main.util;

import java.util.List;
import java.util.ArrayList;

import fls.engine.main.util.Renderer;
import uk.fls.h2n0.main.util.gui.TextBox;
import uk.fls.h2n0.main.util.gui.UIComponent;

public class UI {

	private Renderer r;

	private List<UIComponent> comps;

	/**
	 * UI manager class
	 * @param Renderer r
	 */
	public UI(Renderer r) {
		this.r = r;
		this.comps = new ArrayList<UIComponent>();
		clear();
	}

	public Renderer getRenderer() {
		return this.r;
	}

	/**
	 * Passes the arguments to the components so they can
	 * decide if they have been activated at all
	 * @param int mx
	 * @param int my
	 * @param boolean click
	 */
	public void update(int mx, int my, boolean click) {
		for (UIComponent p : this.comps) {
			if (p.isHidden())// If the current component is hidden, don't update it
				continue;
			p.update(mx, my, click);
		}
	}

	public void render() {
		for (UIComponent p : this.comps) {
			if (p.isHidden())// If the current component is hidden, don't render it
				continue;
			p.render();
		}
	}

	/**
	 * Adds a component to the list so they can be rendered and updated
	 * @param comp
	 * @return success
	 */
	public boolean add(UIComponent comp) {
		if (getCompByID(comp.id) == null) {
			this.comps.add(comp);
			comp.setManager(this);
			return true;
		}
		return false;
	}

	public boolean remove(UIComponent comp) {
		if (getCompByID(comp.id) != null) {
			this.comps.remove(comp);
			return true;
		}
		return false;
	}

	/**
	 * Clears the current list of UI objects
	 */
	public void clear() {
		this.comps.clear();
	}

	/**
	 * Trys the return the requested component
	 * @param id
	 * @return Either the element with the id given or null
	 */
	public UIComponent getCompByID(String id) {
		for (UIComponent part : this.comps) {
			if (part.id.equals(id)) {
				return part;
			}
		}
		return null;
	}

	/**
	 * Special function used for passing key values to TextBoxs
	 * @param e
	 */
	public void keyTyped(char e) {
		for (int i = 0; i < this.comps.size(); i++) {
			if (this.comps.get(i) instanceof TextBox){
				TextBox tb = (TextBox)this.comps.get(i);
				if (tb.isSelected()) {
					if((int)e == 8){
						tb.backSpace();
					}else{
						if((""+e).equals(" ")){
							tb.addSpace();
						}else{
							tb.addChar(""+e);
						}
					}
				}
			}
		}
	}
}
