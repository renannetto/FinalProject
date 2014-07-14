package ro7.engine.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;

import ro7.engine.sprites.BoxSprite;
import ro7.engine.world.components.Transform;
import cs195n.Vec2f;

public class Viewport extends Entity {

	private Vec2f dimensions;
	private Vec2f scale;
	private Vec2f gamePosition;

	public Viewport(GameWorld world, PlayerInput playerInput, Transform transform, Vec2f dimensions, Vec2f scale, Vec2f gamePosition) {
		super(world, playerInput, transform);
		this.dimensions = dimensions;
		this.scale = scale;
		this.gamePosition = gamePosition;
		
		this.sprite = new BoxSprite(this, Color.BLACK, null, dimensions);
	}

	/**
	 * Transform the world to game coordinates
	 * @param g Graphics object used to draw
	 */
	public void doTransform(Graphics2D g) {
		g.translate(transform.position.x, transform.position.y);
		g.scale(scale.x, scale.y);
		g.translate(-gamePosition.x, -gamePosition.y);
	}

	/**
	 * Transform the world to screen coordinates
	 * @param g Graphics object used to draw
	 */
	public void undoTransform(Graphics2D g) {
		g.translate(gamePosition.x, gamePosition.y);
		g.scale(1.0f / scale.x, 1.0f / scale.y);
		g.translate(-transform.position.x, -transform.position.y);
	}

	/**
	 * Calculate the game coordinates of a screen point
	 * @param point screen coordinates point
	 * @return game coordinates of the point
	 */
	public Vec2f screenToGame(Vec2f point) {
		return point.minus(transform.position).pdiv(scale).plus(gamePosition);
	}
	
	/**
	 * Calculate the screen coordinates of a game point
	 * @param point game coordinates point
	 * @return screen coordinates of the point
	 */
	public Vec2f gameToScreen(Vec2f point) {
		return point.minus(gamePosition).pmult(scale).plus(transform.position);
	}

	/**
	 * Increase the scale of the viewport
	 * @param factor quantity to increase the scale
	 */
	public void zoomIn(float factor) {
		Vec2f viewportDimensions = getGameDimensions();

		scale = scale.smult(factor);

		Vec2f translateVector = viewportDimensions.smult(factor - 1).sdiv(2.0f);
		translate(translateVector);
	}

	/**
	 * Decrease the scale of the viewport
	 * @param factor quantity to decrease the scale
	 */
	public void zoomOut(float factor) {
		Vec2f viewportDimensions = getGameDimensions();

		scale = scale.sdiv(factor);

		Vec2f translateVector = viewportDimensions.smult(-(factor - 1)).sdiv(
				2.0f);
		translate(translateVector);
	}
	
	/**
	 * Get viewport dimensions on game coordinates
	 * @return viewport dimensions on game coordinates
	 */
	private Vec2f getGameDimensions() {
		Vec2f min = screenToGame(transform.position);
		Vec2f max = screenToGame(dimensions);
		
		return max.minus(min);
	}

	/**
	 * Move the viewport on the game
	 * @param direction vector to move the viewport
	 */
	public void translate(Vec2f direction) {
		gamePosition = gamePosition.plus(direction);
	}

	/**
	 * Set the clipping area, apply the transformation to game
	 * coordinates, draw the GameSpace content and restore 
	 * the Graphics state to the previous one
	 * @param g Graphics object used to draw
	 */
	public void draw(Graphics2D g) {
		super.draw(g);

		Shape clip = g.getClip();
		g.setClip(((BoxSprite)sprite).getShape());
		doTransform(g);

		world.draw(g);
		undoTransform(g);
		g.setClip(clip);
	}

	public Vec2f getGamePosition() {
		return gamePosition;
	}

	public Vec2f getScale() {
		return scale;
	}

	public void move(Vec2f newPosition) {
		this.gamePosition = newPosition;
	}

	public Vec2f getDimensions() {
		return dimensions;
	}

}
