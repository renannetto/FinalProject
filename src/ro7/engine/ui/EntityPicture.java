package ro7.engine.ui;

import java.awt.Graphics2D;

import ro7.engine.sprites.ImageSprite;
import cs195n.Vec2f;

public class EntityPicture extends HudElement {
	
	private ImageSprite sprite;

	public EntityPicture(ImageSprite sprite) {
		super();
		this.sprite = sprite;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		sprite.draw(g);
	}

	@Override
	public Vec2f getDimensions() {
		return sprite.getDimensions();
	}
	
	@Override
	public void moveTo(Vec2f position) {
		super.moveTo(position);
		this.sprite.moveTo(position);
	}

}
