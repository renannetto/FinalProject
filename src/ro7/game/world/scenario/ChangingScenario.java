package ro7.game.world.scenario;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Input;

public class ChangingScenario extends Scenario {

	private boolean visible;

	public ChangingScenario(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		this.visible = true;

		inputs.put("doInvisible", new Input() {

			@Override
			public void run(Map<String, String> args) {
				visible = false;
			}
		});

		inputs.put("doVisible", new Input() {

			@Override
			public void run(Map<String, String> args) {
				visible = true;
			}
		});
	}

	@Override
	public void draw(Graphics2D g) {
		if (visible) {
			super.draw(g);
		}
	}

}
