package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.StaticEntity;
import ro7.game.world.FinalEntity;
import ro7.game.world.FinalWorld;

public class GameItem extends StaticEntity implements FinalEntity {

	private Item item;
	private boolean visible;

	public GameItem(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		String itemName = properties.get("itemName");
		item = new Item(world, shape, itemName);

		if (((FinalWorld) world).playerHas(item)) {
			visible = false;
		} else {
			visible = true;
		}
	}

	@Override
	public void draw(Graphics2D g) {
		if (visible) {
			super.draw(g);
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
		FinalEntity other = (FinalEntity) collision.other;
		other.getItem(item);
		world.removeEntity(name);
	}

	@Override
	public void getItem(Item item) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(long nanoseconds) {
		if (((FinalWorld) world).playerHas(item)) {
			visible = false;
		} else {
			visible = true;
		}
	}

}
