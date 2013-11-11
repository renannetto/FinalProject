package ro7.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.ui.Lifebar;
import ro7.engine.world.GameWorld;
import ro7.engine.world.Viewport;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private Player player;
	
	private Lifebar lifebar;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);

		player = new Player(this, new AAB(dimensions.sdiv(2.0f), Color.BLACK,
				Color.BLACK, new Vec2f(50.0f, 50.0f)), "player",
				new HashMap<String, String>());
		entities.put("player", player);

		lifebar = new Lifebar(new Vec2f(50.0f, 0.0f), new ImageSprite(
				new Vec2f(0.0f, 0.0f), spriteSheets.get("heart"), new Vec2i(0,
						0)), 3);
		
	}
	
	@Override
	public void draw(Graphics2D g, Viewport viewport) {
		// TODO Auto-generated method stub
		super.draw(g, viewport);
		
		lifebar.draw(g, viewport);
	}

	@Override
	public void setGameClasses() {
		// TODO Auto-generated method stub

	}

	@Override
	public void loadSpriteSheets() {
		spriteSheets.put("heart", new SpriteSheet(
				"resources/sprites/heart.png", new Vec2i(32, 28), new Vec2i(0,
						0)));
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void stopPlayer() {
		player.stop();
	}

}
