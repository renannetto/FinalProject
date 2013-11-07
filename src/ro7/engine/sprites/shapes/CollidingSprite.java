package ro7.engine.sprites.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.List;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.world.entities.Ray;
import cs195n.Vec2f;

/**
 * @author ro7
 * A CollidingShape which contains a sprite and a boundary shape.
 * The sprite is used on draw methods and the boundary shape is
 * used for collisions.
 */
public class CollidingSprite extends CollidingShape {
	
	private ImageSprite sprite;
	private CollidingShape shape;

	public CollidingSprite(ImageSprite sprite, CollidingShape shape) {
		super(shape.getPosition());
		this.sprite = sprite;
		this.shape = shape;
		
		this.shape.changeFillColor(null);
	}

	@Override
	public Vec2f collides(CollidingShape shape) {
		return this.shape.collides(shape);
	}

	@Override
	public Vec2f collidesCircle(Circle circle) {
		return this.shape.collidesCircle(circle);
	}

	@Override
	public Vec2f collidesAAB(AAB aab) {
		return this.shape.collidesAAB(aab);
	}

	@Override
	public Vec2f collidesPolygon(Polygon polygon) {
		return this.shape.collidesPolygon(polygon);
	}

	@Override
	public Vec2f collidesCompoundShape(CompoundShape compound) {
		return this.shape.collidesCompoundShape(compound);
	}

	@Override
	public Vec2f collidesRay(Ray ray) {
		return this.shape.collidesRay(ray);
	}

	@Override
	public void changeBorderColor(Color color) {

	}

	@Override
	public void changeFillColor(Color color) {

	}

	@Override
	public Vec2f center() {
		return this.shape.center();
	}

	@Override
	public List<Vec2f> getPoints() {
		return this.shape.getPoints();
	}
	
	/* (non-Javadoc)
	 * @see ro7.engine.sprites.shapes.CollidingShape#move(cs195n.Vec2f)
	 * Move both the sprite and the bounding shape
	 */
	@Override
	public void move(Vec2f translation) {
		super.move(translation);
		this.shape.move(translation);
		this.sprite.move(translation);
	}

	@Override
	public void updatePoints(Vec2f translation) {
		
	}

	/* (non-Javadoc)
	 * @see ro7.engine.sprites.Sprite#draw(java.awt.Graphics2D)
	 * Draw only the sprite, not its bounding shape.
	 */
	@Override
	public void draw(Graphics2D g) {
		this.sprite.draw(g, shape.getDimensions());
	}
	
	@Override
	public void update(long nanoseconds) {
		super.update(nanoseconds);
		this.sprite.update(nanoseconds);
	}

	public void updateSprite(ImageSprite sprite) {
		this.sprite = sprite;
		this.sprite.moveTo(shape.getPosition());
	}

	@Override
	public Vec2f getDimensions() {
		return shape.getDimensions();
	}

}
