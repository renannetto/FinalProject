package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalStaticEntity;

public class FallingArea extends FinalStaticEntity implements FinalEntity {

	public FallingArea(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		if (properties.containsKey("remove")) {
			boolean remove = Boolean.parseBoolean(properties.get("remove"));
			if (remove) {
				world.removeEntity(name);
			}
		}
	}

	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.fall(new Collision(this, collision.mtv,
				collision.otherShape, collision.thisShape));
	}
	
	@Override
	public void onCollisionDynamic(Collision collision) {
		super.onCollisionDynamic(collision);
		FinalEntity otherEntity = (FinalEntity) collision.other;
		otherEntity.fall(new Collision(this, collision.mtv,
				collision.otherShape, collision.thisShape));
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
