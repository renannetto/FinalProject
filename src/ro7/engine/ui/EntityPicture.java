package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class EntityPicture extends HudElement {
	
	private ImageSprite sprite;

	public EntityPicture(ImageSprite sprite) {
		super();
		this.sprite = sprite;
		this.sprite.moveTo(position);
	}

	@Override
	public void drawSprite(Graphics2D g) {
		sprite.draw(g);
	}

	@Override
	public Vec2f getDimensions() {
		return sprite.getDimensions();
	}

}
