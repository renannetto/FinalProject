package ro7.engine.world.entities;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public abstract class GroupEntity extends Entity {
	
	protected int categoryMask;
	protected int collisionMask;

	protected GroupEntity(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name);
		this.categoryMask = Integer.parseInt(properties.get("categoryMask"));
		this.collisionMask = Integer.parseInt(properties.get("collisionMask"));
	}
	
	protected GroupEntity(GameWorld world, int categoryMask, int collisionMask) {
		super(world, null, null);
		this.name = toString();
		this.categoryMask = categoryMask;
		this.collisionMask = collisionMask;
	}
	
	public boolean collidable(GroupEntity other) {
		return (this.categoryMask & other.collisionMask) != 0;
	}

}
