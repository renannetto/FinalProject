package ro7.engine.ui;

import java.awt.Graphics2D;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.world.Viewport;
import cs195n.Vec2f;

public class Hud {
	
	private Vec2f dimensions;
	private Set<HudElement> elements;
	
	public Hud(Vec2f dimensions) {
		this.dimensions = dimensions;
		this.elements = new HashSet<HudElement>();
	}
	
	public void addHudElement(ScreenPosition screenPosition, HudElement element) {
		Vec2f position = null;
		Vec2f elementDimensions = element.getDimensions();
		switch (screenPosition) {
		case TOP_LEFT:
			position = elementDimensions.sdiv(2.0f);
			break;
		case TOP_CENTER:
			position = new Vec2f(dimensions.x/2.0f, elementDimensions.y/2.0f);
			break;
		case TOP_RIGHT:
			position = new Vec2f(dimensions.x-(elementDimensions.x/2.0f), elementDimensions.y/2.0f);
			break;
		case CENTER_LEFT:
			position = new Vec2f(elementDimensions.x/2.0f, dimensions.y/2.0f);
			break;
		case CENTER:
			position = dimensions.sdiv(2.0f);
			break;
		case CENTER_RIGHT:
			position = new Vec2f(dimensions.x-(elementDimensions.x/2.0f), dimensions.y/2.0f);
			break;
		case BOTTOM_LEFT:
			position = new Vec2f(elementDimensions.x/2.0f, dimensions.y-(elementDimensions.y/2.0f));
			break;
		case BOTTOM_CENTER:
			position = new Vec2f(dimensions.x/2.0f, dimensions.y-(elementDimensions.y/2.0f));
			break;
		case BOTTOM_RIGHT:
			position = new Vec2f(dimensions.x-(elementDimensions.x/2.0f), dimensions.y-(elementDimensions.y/2.0f));
			break;
		}
		element.moveTo(position);
		elements.add(element);
	}
	
	public void draw(Graphics2D g, Viewport viewport) {
		for (HudElement element : elements) {
			element.draw(g, viewport);
		}
	}

}
