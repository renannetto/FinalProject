package ro7.game.world.scenario;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Sensor;
import ro7.engine.world.io.Input;
import ro7.game.screens.GameScreen;
import ro7.game.world.FinalEntity;
import ro7.game.world.FinalWorld;

public class Door extends Sensor implements FinalEntity {

	private boolean tried = false;

	public Door(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);
		inputs.put("doEnter", new Input() {

			@Override
			public void run(Map<String, String> args) {
				enter();
			}
		});
	}

	@Override
	public void onCollision(Collision collision) {
		if (((FinalWorld) world).noEnemies()) {
			super.onCollision(collision);
		} else {
			if (!tried) {
				GameScreen.playCutscene("resources/cutscenes/cutscene2.txt");
				tried = true;
			}
		}
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
		((FinalWorld) world).win();
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
