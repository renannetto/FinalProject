package ro7.engine.sprites.shapes;

import java.awt.Color;

import cs195n.Vec2f;

public abstract class SingleShape extends CollidingShape {

	protected Color borderColor;
	protected Color fillColor;
	
	protected SingleShape(Vec2f position, Color borderColor, Color fillColor) {
		super(position);
		this.borderColor = borderColor;
		this.fillColor = fillColor;
	}
	
	public void changeBorderColor(Color color) {
		this.borderColor = color;
	}
	
	public void changeFillColor(Color color) {
		this.fillColor = color;
	}

}
