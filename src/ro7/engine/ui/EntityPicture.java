package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.FloatingSprite;
import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class EntityPicture extends FloatingSprite {
	
	private ImageSprite sprite;

	public EntityPicture(Vec2f position, ImageSprite sprite) {
		super(position);
		this.sprite = sprite;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		sprite.draw(g);
	}

}
