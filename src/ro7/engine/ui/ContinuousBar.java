package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class ContinuousBar extends HudElement {

	private ImageSprite bar;
	private ImageSprite fill;
	private Vec2f dimensions;
	private float lifepoints;

	public ContinuousBar(ImageSprite bar, ImageSprite fill) {
		super();
		this.bar = bar;
		this.fill = fill;
		this.dimensions = this.bar.getDimensions();
		this.lifepoints = 100.0f;
	}

	@Override
	public Vec2f getDimensions() {
		return dimensions;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		bar.draw(g);
		fill.draw(g, dimensions.smult(lifepoints / 100.0f));
	}

	@Override
	public void moveTo(Vec2f position) {
		super.moveTo(position);
		bar.moveTo(position);
		float empty = 1-(lifepoints/100.0f);
		fill.moveTo(position.minus(dimensions.x*(empty/2.0f), 0.0f));
	}

}
