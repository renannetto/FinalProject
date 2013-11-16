package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class Player extends Character {

	public Player(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}

	public Attack attack() {
		Map<String, String> attackProperties = new HashMap<String, String>();
		attackProperties.put("categoryMask", "1");
		attackProperties.put("collisionMask", "2");
		attackProperties.put("damage", "1");
		
		Vec2f attackPosition = shape.getPosition().plus(shape.getDimensions().pmult(direction));
		CollidingShape attackShape = new AAB(attackPosition, Color.BLUE, Color.BLUE, new Vec2f(36.0f, 36.0f));
		Attack attack = new Attack(world, attackShape, "player_attack", attackProperties);
		return attack;
	}

	public Vec2f getPosition() {
		return shape.getPosition();
	}

}
