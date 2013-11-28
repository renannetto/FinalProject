package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.CollidableEntity;
import ro7.game.world.FinalEntity;
import ro7.game.world.FinalWorld;
import ro7.game.world.player.Item;
import cs195n.Vec2f;

public class Door extends CollidableEntity implements FinalEntity {
	
	private String nextRoom;
	private Vec2f nextPosition;

	public Door(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		nextRoom = properties.get("nextRoom");
		
		if (properties.containsKey("nextPosX") && properties.containsKey("nextPosY")) {
			nextPosition = new Vec2f(Float.parseFloat(properties.get("nextPosX")), Float.parseFloat(properties.get("nextPosY")));
		} else {
			nextPosition = null;
		}
	}
	
	@Override
	public void onCollision(Collision collision) {
		enter();
	}

	@Override
	public void onCollisionDynamic(Collision collision) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onCollisionStatic(Collision collision) {
		// TODO Auto-generated method stub

	}

	public void enter() {
		world.loadLevel(nextRoom);
		if (nextPosition!=null) {
			((FinalWorld)world).setInitialPosition(nextPosition);
		}
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
	public void receiveAction(Collision collision, Set<Item> inventory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics2D g) {
		
	}

}
