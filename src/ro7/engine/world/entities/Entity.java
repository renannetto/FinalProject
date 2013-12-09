package ro7.engine.world.entities;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Connection;
import ro7.engine.world.io.Input;
import ro7.engine.world.io.Output;

public abstract class Entity {

	protected GameWorld world;
	protected CollidingShape shape;
	protected String name;
	protected Map<String, Output> outputs;
	public Map<String, Input> inputs;
	
	protected Entity(GameWorld world, CollidingShape shape, String name) {
		this.world = world;
		this.shape = shape;
		this.name = name;
		this.outputs = new HashMap<String, Output>();
		this.inputs = new HashMap<String, Input>();
		
		inputs.put("doRemove", new Input() {

			@Override
			public void run(Map<String, String> args) {
				Entity.this.world
						.removeEntity(Entity.this.name);
			}
		});

		inputs.put("doAdd", new Input() {

			@Override
			public void run(Map<String, String> args) {
				Entity.this.world.addEntity(Entity.this);
			}
		});
	}
	
	public abstract void update(long nanoseconds);
	
	public abstract void draw(Graphics2D g);
	
	public void connect(String output, Connection connection) {
		outputs.get(output).connect(connection);
	}
	
	public void disconnect(String output) {
		outputs.remove(output);
	}
	
	public abstract void add();
	
	public abstract void remove();
	
	public String getName() {
		return name;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		Entity other = (Entity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return name;
	}

}
