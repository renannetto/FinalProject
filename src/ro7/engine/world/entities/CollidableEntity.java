package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import cs195n.Vec2f;

public abstract class CollidableEntity extends GroupEntity {
	
	protected CollidableEntity(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name, properties);
		world.addCollidableEntity(this);
	}
	
	public Collision collides(CollidableEntity other) {
		Vec2f mtv = this.shape.collides(other.shape);
		return new Collision(other, mtv, this.shape, other.shape);
	}
	
	public RayCollision collidesRay(Ray ray) {
		Vec2f point = this.shape.collidesRay(ray);
		return new RayCollision(this, point, this.shape);
	}
	
	public abstract void onCollision(Collision collision);
	
	@Override
	public void draw(Graphics2D g) {
		shape.draw(g);
	}
	
	@Override
	public void remove() {
		world.removeCollidableEntity(this);
	}

}
