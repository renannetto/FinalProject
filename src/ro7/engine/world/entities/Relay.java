package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Output;

public class Relay extends Entity {
	
	private boolean enable;

	protected Relay(GameWorld world, CollidingShape shape, String name, Map<String, String> properties) {
		super(world, shape, name);
		enable = false;
		outputs.put("onSignal", new Output());
	}
	
	public void enable() {
		enable = true;
	}
	
	public void disable() {
		enable = false;
	}
	
	/**
	 * Call its ouptut only when enabled.
	 */
	public void runOutput() {
		if (enable) {
			outputs.get("onSignal").run();
		}
	}

	@Override
	public void update(long nanoseconds) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(Graphics2D g) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public void add() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void remove() {
		// TODO Auto-generated method stub
		
	}

}
