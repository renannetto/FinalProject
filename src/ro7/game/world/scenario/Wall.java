package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;
import ro7.game.world.FinalEntity;

public class Wall extends StaticEntity implements FinalEntity {

	public Wall(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}
	
	@Override
	public void onCollision(Collision collision) {
		super.onCollision(collision);
	}

	@Override
	public void receiveDamage(int damage) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void touchEnemy(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveAttack(Collision collision) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void receiveAction() {
		// TODO Auto-generated method stub
		
	}

}
