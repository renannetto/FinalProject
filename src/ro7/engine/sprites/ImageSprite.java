package ro7.engine.sprites;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;

/**
 * @author ro7
 * Sprite that represents an Image
 */
public class ImageSprite extends Sprite {
	
	protected SpriteSheet sheet;
	protected Vec2i sheetPosition; 
	protected Vec2f dimensions;

	public ImageSprite(Vec2f position, SpriteSheet sheet, Vec2i sheetPosition, Vec2f dimensions) {
		super(position);
		this.sheet = sheet;
		this.sheetPosition = sheetPosition;
		this.dimensions = dimensions;
	}

	@Override
	public void draw(Graphics2D g) {
		sheet.draw(g, sheetPosition, position, dimensions);
	}
	
	public void draw(Graphics2D g, Vec2f dimensions) {
		sheet.draw(g, sheetPosition, position, dimensions);
	}
	
	public Vec2f getDimensions() {
		return dimensions;
	}

}
