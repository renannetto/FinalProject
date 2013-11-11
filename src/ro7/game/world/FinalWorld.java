package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.ui.EntityPicture;
import ro7.engine.ui.Lifebar;
import ro7.engine.ui.ScreenPosition;
import ro7.engine.world.GameWorld;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private Player player;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);

		player = new Player(this, new AAB(dimensions.sdiv(2.0f), Color.BLACK,
				Color.BLACK, new Vec2f(50.0f, 50.0f)), "player",
				new HashMap<String, String>());
		entities.put("player", player);

		Lifebar lifebar = new Lifebar(new ImageSprite(
				new Vec2f(0.0f, 0.0f), spriteSheets.get("heart"), new Vec2i(0,
						0)), 3);
		hud.addHudElement(ScreenPosition.TOP_LEFT, lifebar);
		
		EntityPicture life = new EntityPicture(new ImageSprite(
				new Vec2f(0.0f, 0.0f), spriteSheets.get("heart"), new Vec2i(0,
						0)));
		hud.addHudElement(ScreenPosition.BOTTOM_RIGHT, life);		
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
