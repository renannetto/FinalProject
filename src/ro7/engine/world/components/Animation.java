package ro7.engine.world.components;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.world.Entity;
import cs195n.Vec2i;

public class Animation extends Component {

	protected final float TIME_TO_MOVE;

	protected ImageSprite sprite;
	protected Vec2i initPosition;
	protected int currentFrame;
	protected int frames;

	protected float elapsed;

	public Animation(Entity entity, Vec2i initPosition, SpriteSheet sheet,
			int frames, float timeToMove) {
		super(entity);
		this.initPosition = initPosition;
		this.sprite = new ImageSprite(entity, sheet, initPosition);
		this.currentFrame = 0;
		this.frames = frames;
		this.elapsed = 0;
		this.TIME_TO_MOVE = timeToMove;

		entity.addSprite(sprite);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro7.engine.sprites.Sprite#update(long) If the elapsed time is bigger
	 * than TIME_TO_MOVE, update the frame of the animation. The next frame has
	 * to be on the right in the sprite sheet.
	 */
	@Override
	public void update(long nanoseconds) {
		elapsed += nanoseconds / 1000000000.0f;
		if (elapsed > TIME_TO_MOVE) {
			currentFrame = (currentFrame + 1) % frames;
			sprite.changePosition(initPosition.plus(currentFrame, 0));
			entity.addSprite(sprite);
			elapsed = 0;
		}
	}

	public void reset() {
		currentFrame = 0;
	}

}
