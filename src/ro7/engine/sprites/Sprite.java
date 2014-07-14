package ro7.engine.sprites;

import java.awt.Graphics2D;

import ro7.engine.world.Entity;
import cs195n.Vec2f;

public abstract class Sprite {
	
	protected Entity entity;
	
	protected Sprite(Entity entity) {
		this.entity = entity;
	}
	
	/**
	 * Draw sprite using Graphics object
	 * @param g Graphics object used to draw
	 */
	public void draw(Graphics2D g) {
		Vec2f position = entity.transform.position;
		Vec2f scale = entity.transform.scale;
		float rotation = entity.transform.rotation;
		
		g.rotate(rotation);
		g.translate(position.x, position.y);
		g.scale(scale.x, scale.y);
		
		drawSprite(g);
		
		g.scale(1/scale.x, 1/scale.y);
		g.translate(-position.x, -position.y);
		g.rotate(-rotation);
	}
	
	public abstract void drawSprite(Graphics2D g);

}
