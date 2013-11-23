package ro7.game.world.scenario;

import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.entities.Sensor;
import ro7.engine.world.io.Input;
import ro7.game.world.FinalEntity;
import ro7.game.world.FinalWorld;
import cs195n.Vec2f;

public class Door extends Sensor implements FinalEntity {
	
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
		
		inputs.put("doEnter", new Input() {

			@Override
			public void run(Map<String, String> args) {
				enter();
			}
		});
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
	public void receiveAction() {
		// TODO Auto-generated method stub
		
	}

}
