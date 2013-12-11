package ro7.game.world.enemies;

import java.util.HashMap;
import java.util.Map;

import ro7.engine.ai.BTNode;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Selector;
import ro7.engine.ai.Sequence;
import ro7.engine.ai.Status;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.game.world.FinalWorld;
import ro7.game.world.items.Item;

public class Sorcerer extends PrisonArcher {

	public Sorcerer(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		
		dying = walking;
	}
	
	@Override
	protected void buildBehaviorTree() {
		root = new Selector();
		Composite defense = new Sequence();
		Composite attack = new Sequence();

		BTNode playerClose = new PlayerClose();
		BTNode moveAway = new MoveAway();

		BTNode playerInRange = new PlayerInRange();
		BTNode shoot = new Shoot();

		BTNode walk = new Walk();

		defense.addChild(playerClose);
		defense.addChild(moveAway);

		attack.addChild(playerInRange);
		attack.addChild(shoot);

		root.addChild(defense);
		root.addChild(attack);
		root.addChild(walk);
	}
	
	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub
		super.update(nanoseconds);
	}
	
	@Override
	public void receiveDamage(int damage) {
		// TODO Auto-generated method stub
		super.receiveDamage(damage);
		if (lives <= 0) {
			((FinalWorld)world).getItem(new Item(world, null, "bossKey", new HashMap<String, String>()));
		}
	}
	
	private class Shoot extends PrisonArcher.Shoot {
		
		public Shoot() {
			shootDelay = 3.0f;
		}
		
		@Override
		public Status act(float nanoseconds) {
			if (elapsedTime < shootDelay) {
				elapsedTime += nanoseconds / 1000000000.0f;
				return Status.FAILURE;
			}
			return shoot(nanoseconds);
		}
		
	}

}
