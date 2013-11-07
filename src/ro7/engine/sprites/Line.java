package ro7.engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;

import cs195n.Vec2f;

public class Line extends Sprite {
	
	private Vec2f end;
	private Color color;

	public Line(Vec2f start, Vec2f end, Color color) {
		super(start);
		this.end = end;
		this.color = color;
	}

	@Override
	public void draw(Graphics2D g) {
		Line2D line = new Line2D.Float(position.x, position.y, end.x, end.y);
		g.setColor(color);
		g.draw(line);
	}

}
