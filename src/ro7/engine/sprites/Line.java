package ro7.engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import ro7.engine.world.Entity;
import cs195n.Vec2f;

public class Line extends Sprite {
	
	private Vec2f end;
	private Color color;

	public Line(Entity entity, Vec2f end, Color color) {
		super(entity);
		this.end = end;
		this.color = color;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		Line2D line = new Line2D.Float(0, 0, end.x, end.y);
		g.setColor(color);
		g.draw(line);
	}

}
