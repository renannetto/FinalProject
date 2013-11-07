package ro7.engine.sprites;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class AnimatedSprite extends ImageSprite {

	protected final int TIME_TO_MOVE;

	protected Vec2i initPosition;
	protected int currentFrame;
	protected int frames;
	protected int elapsed;

	public AnimatedSprite(Vec2f position, SpriteSheet sheet,
			Vec2i sheetPosition, int frames, int timeToMove) {
		super(position, sheet, sheetPosition);
		this.initPosition = sheetPosition;
		this.currentFrame = 0;
		this.frames = frames;
		this.elapsed = 0;
		this.TIME_TO_MOVE = timeToMove;
	}

	public void update(long nanoseconds) {
		elapsed += nanoseconds / 1000000;
		if (elapsed > TIME_TO_MOVE) {
			currentFrame = (currentFrame + 1) % frames;
			sheetPosition = initPosition.plus(currentFrame, 0);
			elapsed = 0;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		super.draw(g);
	}

}
