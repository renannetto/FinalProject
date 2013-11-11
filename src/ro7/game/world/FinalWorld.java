package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;

import ro7.engine.sprites.shapes.AAB;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;

public class FinalWorld extends GameWorld {
	
	private Player player;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);

		player = new Player(this, new AAB(dimensions.sdiv(2.0f),
				Color.BLACK, Color.BLACK, new Vec2f(50.0f, 50.0f)), "player", new HashMap<String, String>());
		entities.put("player", player);
	}

	@Override
	public void setGameClasses() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadSpriteSheets() {
		// TODO Auto-generated method stub

	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void stopPlayer() {
		player.stop();
	}

}
