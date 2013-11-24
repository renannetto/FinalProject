package ro7.game.world.player;

import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;
import ro7.game.world.FinalEntity;

public class GameItem extends StaticEntity implements FinalEntity {
	
	private Item item;

	public GameItem(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		String itemName = properties.get("itemName");
		item = new Item(world, shape, itemName);
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
		FinalEntity other = (FinalEntity)collision.other;
		other.getItem(item);
		world.removeEntity(name);
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		
	}

}
