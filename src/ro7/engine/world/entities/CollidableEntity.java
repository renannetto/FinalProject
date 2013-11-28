package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public abstract class CollidableEntity extends Entity {

	protected int categoryMask;
	protected int collisionMask;

	protected CollidableEntity(GameWorld world, CollidingShape shape,
			String name, Map<String, String> properties) {
		super(world, shape, name);
		if (properties.containsKey("categoryMask")) {
			this.categoryMask = Integer
					.parseInt(properties.get("categoryMask"));
		} else {
			this.categoryMask = -1;
		}
		if (properties.containsKey("collisionMask")) {
			this.collisionMask = Integer.parseInt(properties
					.get("collisionMask"));
		} else {
			this.collisionMask = -1;
		}
		world.addCollidableEntity(this);
	}

	protected CollidableEntity(GameWorld world, int categoryMask,
			int collisionMask) {
		super(world, null, null);
		this.name = toString();
		this.categoryMask = categoryMask;
		this.collisionMask = collisionMask;
		world.addCollidableEntity(this);
	}

	public boolean collidable(CollidableEntity other) {
		return (this.categoryMask & other.collisionMask) != 0;
	}

	/**
	 * @param other
	 *            Return a Collision object representing the collision with
	 *            other. If there is no collision, the mtv is null and the
	 *            Collision is not valid
	 * @return a Collision object
	 */
	public Collision collides(CollidableEntity other) {
		Vec2f mtv = this.shape.collides(other.shape);
		return new Collision(other, mtv, this.shape, other.shape);
	}

	/**
	 * @param other
	 *            Return a Collision object representing the collision with
	 *            other. If there is no collision, the mtv is null and the
	 *            Collision is not valid
	 * @return a Collision object
	 */
	public RayCollision collidesRay(Ray ray) {
		Vec2f point = this.shape.collidesRay(ray);
		return new RayCollision(this, point, this.shape);
	}

	/**
	 * @param collision
	 *            Do the necessary operations when a collision happens
	 */
	public abstract void onCollision(Collision collision);

	public abstract void onCollisionDynamic(Collision collision);

	public abstract void onCollisionStatic(Collision collision);

	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ro7.engine.world.entities.Entity#remove() Remove this entity from
	 * the world. This method is called automatically in GameWorld.
	 */
	@Override
	public void remove() {
		world.removeCollidableEntity(this);
	}
	
	public void moveTo(Vec2f position) {
		shape.moveTo(position);
	}

}
