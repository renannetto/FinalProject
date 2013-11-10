package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.FloatingSprite;
import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class Lifebar extends FloatingSprite {
	
	private ImageSprite lifeSprite;
	private int lives;

	public Lifebar(Vec2f position, ImageSprite lifeSprite, int lives) {
		super(position);
		this.lifeSprite = lifeSprite;
		this.lives = lives;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		Vec2f lifeDimensions = lifeSprite.getDimensions();
		this.lifeSprite.moveTo(position);
		Vec2f translationVector = new Vec2f(lifeDimensions.x, 0.0f);
		for (int i=0; i<lives; i++) {
			lifeSprite.move(translationVector);
			lifeSprite.draw(g, lifeDimensions);
		}
	}

}
