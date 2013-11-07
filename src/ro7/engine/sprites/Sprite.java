package ro7.engine.sprites;

import java.awt.Graphics2D;

import cs195n.Vec2f;

public abstract class Sprite {
	
	protected Vec2f position;
	
	protected Sprite(Vec2f position) {
		this.position = position;
	}
	
	/**
	 * Draw sprite using Graphics object
	 * @param g Graphics object used to draw
	 */
	public abstract void draw(Graphics2D g);
	
	public void update(long nanoseconds) {
		
	}
	
	public void move(Vec2f translation) {
		position = position.plus(translation);
	}
	
	public void moveTo(Vec2f position) {
		this.position = position;
	}

}
