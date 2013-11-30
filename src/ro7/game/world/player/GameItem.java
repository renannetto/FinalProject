package ro7.game.world.player;

import java.awt.Graphics2D;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;
import ro7.game.world.FinalWorld;
import ro7.game.world.entities.FinalEntity;
import ro7.game.world.entities.FinalStaticEntity;
import cs195n.Vec2f;

public class GameItem extends FinalStaticEntity implements FinalEntity {

	private Item item;
	private boolean visible;

	public GameItem(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		String itemName = properties.get("itemName");
		item = new Item(world, shape, itemName);
		
		if (properties.containsKey("visible")) {
			visible = Boolean.parseBoolean(properties.get("visible"));
		} else {
			visible = true;
		}
		
		outputs.put("receiveAction", new Output());
	}

	@Override
	public void draw(Graphics2D g) {
		if (visible) {
			super.draw(g);
		}
	}

	@Override
	public void receiveAction(Collision collision, Set<Item> inventory) {
		FinalEntity other = (FinalEntity) collision.other;
		other.getItem(item);
		outputs.get("receiveAction").run();
	}

	@Override
	public void update(long nanoseconds) {
		if (((FinalWorld) world).playerHas(item)) {
			visible = false;
			world.removeCollidableEntity(this);
			world.removePhysicalEntity(this);
		} else {
			visible = true;
			world.addCollidableEntity(this);
			world.addCollidableEntity(this);
		}
	}
	
	public Item getItem() {
		return this.item;
	}
	
	@Override
	public void onCollisionStatic(Collision collision) {
		Vec2f mtv = collision.mtv;
		if (mtv.mag2() == 0) {
			return;
		}

		shape.move(mtv);

	}

}
