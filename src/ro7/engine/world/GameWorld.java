package ro7.engine.world;

import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.Map;

import ro7.engine.world.components.Collider;

public class GameWorld {
	
	protected Map<String, Entity> entities;
	protected PhysicsEngine physEngine;
	
	public GameWorld() {
		entities = new HashMap<String, Entity>();
		physEngine = new PhysicsEngine(100.0f);
	}
	
	public void update(long nanoseconds) {
		for (Entity entity : entities.values()) {
			entity.update(nanoseconds);
		}
		
		physEngine.update(nanoseconds);
		physEngine.applyGravity(entities);
	}
	
	public void draw(Graphics2D g) {
		for (Entity entity : entities.values()) {
			entity.draw(g);
		} 
	}

	public void addCollider(Collider collider) {
		physEngine.addCollider(collider);
	}

}
