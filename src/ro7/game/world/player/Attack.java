package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;
import ro7.game.world.entities.FinalCollidableEntity;
import ro7.game.world.entities.FinalEntity;
import cs195n.Vec2f;

public class Attack extends FinalCollidableEntity implements FinalEntity {

	private final float TIME_LIMIT = 0.35f;

	private int damage;
	private float elapsedTime;

	public Attack(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		if (properties.containsKey("damage")) {
			this.damage = Integer.parseInt(properties.get("damage"));
		} else {
			this.damage = 1;
		}

		outputs.put("onFinish", new Output());

		this.elapsedTime = 0.0f;
	}

	@Override
	public void draw(Graphics2D g) {
		
	}

	@Override
	public void onCollision(Collision collision) {
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.receiveDamage(damage);
		otherEntity.receiveAttack(new Collision(this, collision.mtv, collision.otherShape, collision.thisShape));
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		
	}

	@Override
	public void onCollisionStatic(Collision collision) {
		
	}

	@Override
	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime > TIME_LIMIT) {
			world.removeEntity(name);
			outputs.get("onFinish").run();
		}
	}

	public void moveTo(Vec2f position) {
		shape.moveTo(position);
	}
	
}
