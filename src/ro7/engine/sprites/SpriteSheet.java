package ro7.engine.sprites;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class SpriteSheet {
	
	private BufferedImage sheet;
	private Vec2i frameDimensions;
	private Vec2i padding;
	
	public SpriteSheet(String sheetFile, Vec2i frameDimensions, Vec2i padding) {
		try {
			sheet = ImageIO.read(new File(sheetFile));
			this.frameDimensions = frameDimensions;
			this.padding = padding;
		} catch (IOException e) {
			System.out.println("Invalid sprite sheet file");
		}
	}
	
	/**
	 * Draw sprite correspondent to the given position on the sprite 
	 * sheet
	 * @param g Graphics object used to draw
	 * @param sheetPosition position of the sprite on the sprite sheet
	 * @param position position on the screen to draw
	 */
	public void draw(Graphics2D g, Vec2i sheetPosition, Vec2f position) {
		int x = (sheetPosition.x*frameDimensions.x) + ((sheetPosition.x+1)*padding.x);
		int y = (sheetPosition.y*frameDimensions.y) + ((sheetPosition.y+1)*padding.y);
		BufferedImage subImage = sheet.getSubimage(x, y, frameDimensions.x, frameDimensions.y);
		Vec2i topLeft = new Vec2i((int)(position.x-frameDimensions.x/2.0f), (int)(position.y-frameDimensions.y/2.0f));
		g.drawImage(subImage, topLeft.x, topLeft.y, null);
	}
	
	public void draw(Graphics2D g, Vec2i sheetPosition, Vec2f position, Vec2f dimensions) {
		int x = (sheetPosition.x*frameDimensions.x) + ((sheetPosition.x+1)*padding.x);
		int y = (sheetPosition.y*frameDimensions.y) + ((sheetPosition.y+1)*padding.y);
		BufferedImage subImage = sheet.getSubimage(x, y, frameDimensions.x, frameDimensions.y);
		Vec2i topLeft = new Vec2i((int)(position.x-dimensions.x/2.0f), (int)(position.y-dimensions.y/2.0f));
		g.drawImage(subImage, topLeft.x, topLeft.y, (int)dimensions.x, (int)dimensions.y, null);
	}

	public Vec2f getFrameDimensions() {
		return new Vec2f(frameDimensions.x, frameDimensions.y);
	}

}
