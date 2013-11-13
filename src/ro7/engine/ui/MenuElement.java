package ro7.engine.ui;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;
import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.Sprite;
import ro7.engine.sprites.SpriteSheet;

public class MenuElement extends AnimatedSprite{
	
	public boolean isSelected;
	private Vec2f originalDimensions;
	private Vec2f originalPosition;
	public float elapsedTime;
	
	public MenuElement(SpriteSheet sheet, Vec2i sheetPosition,
			Vec2f dimensions, int frames, int timeToMove) {
		super(new Vec2f(0.0f, 0.0f), sheet, sheetPosition, dimensions, frames, timeToMove);
		// TODO Auto-generated constructor stub
		originalDimensions = dimensions;
		originalPosition = position;
		elapsedTime = 0.0f;
	}
	
	@Override
	public void update(long nanoseconds) {
		
		elapsedTime += nanoseconds / 10000000.0f;
		if(isSelected)
		{
			double x = 5 + Math.abs(Math.sin(Math.PI*(elapsedTime/100f)));
			double y = 5 + Math.abs(Math.sin(Math.PI*(elapsedTime/100f)));
			Vec2f transformations = new Vec2f((float)x/5, (float)y/5);
			dimensions = originalDimensions.pmult(transformations);
		}
		else
			dimensions = originalDimensions;
		elapsed += nanoseconds / 1000000;
		if (elapsed > TIME_TO_MOVE) {
			currentFrame = (currentFrame + 1) % frames;
			sheetPosition = initPosition.plus(currentFrame, 0);
			elapsed = 0;
		}
	}

}
