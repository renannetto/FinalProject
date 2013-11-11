package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class Lifebar extends HudElement {
	
	private ImageSprite lifeSprite;
	private int lives;

	public Lifebar(ImageSprite lifeSprite, int lives) {
		super();
		this.lifeSprite = lifeSprite;
		this.lives = lives;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		Vec2f lifeDimensions = lifeSprite.getDimensions();
		Vec2f translationVector = new Vec2f(lifeDimensions.x, 0.0f);
		
		Vec2f barDimensions = this.getDimensions();
		g.translate(-barDimensions.x/2.0f+lifeDimensions.x/2.0f, 0.0f);
		
		for (int i=0; i<lives; i++) {
			lifeSprite.draw(g, lifeDimensions);
			g.translate(translationVector.x, 0.0f);
		}
		g.translate(-translationVector.x*lives, 0.0f);
		
		g.translate(barDimensions.x/2.0f-lifeDimensions.x/2.0f, 0.0f);
	}

	@Override
	public Vec2f getDimensions() {
		Vec2f lifeDimensions = lifeSprite.getDimensions();
		return new Vec2f(lifeDimensions.x*lives, lifeDimensions.y);
	}
	
	@Override
	public void moveTo(Vec2f position) {
		super.moveTo(position);
		this.lifeSprite.moveTo(position);
	}

}
