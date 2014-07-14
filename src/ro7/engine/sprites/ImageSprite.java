package ro7.engine.sprites;

import java.awt.Graphics2D;

import ro7.engine.world.Entity;
import cs195n.Vec2i;

/**
 * @author ro7
 * Sprite that represents an Image
 */
public class ImageSprite extends Sprite {
	
	protected SpriteSheet sheet;
	protected Vec2i sheetPosition; 

	public ImageSprite(Entity entity, SpriteSheet sheet, Vec2i sheetPosition) {
		super(entity);
		this.sheet = sheet;
		this.sheetPosition = sheetPosition;
	}

	@Override
	public void drawSprite(Graphics2D g) {
		sheet.draw(g, sheetPosition);
	}

	public void changePosition(Vec2i sheetPosition) {
		this.sheetPosition = sheetPosition;
	}

}
