package ro7.game.world.enemies;

import java.util.Map;

import ro7.engine.ai.BTNode;
import ro7.engine.ai.Composite;
import ro7.engine.ai.Selector;
import ro7.engine.ai.Sequence;
import ro7.engine.ai.Status;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;

public class Sorcerer extends PrisonArcher {

	public Sorcerer(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
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
	
	private class Shoot extends PrisonArcher.Shoot {
		
		public Shoot() {
			shootDelay = 1.5f;
		}
		
		@Override
		public Status act(float nanoseconds) {
			return shoot(nanoseconds);
		}
		
	}

}
