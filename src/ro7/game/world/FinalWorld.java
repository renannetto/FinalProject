package ro7.game.world;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.ui.ContinuousBar;
import ro7.engine.ui.DiscreteBar;
import ro7.engine.ui.ScreenPosition;
import ro7.engine.world.GameWorld;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import ro7.game.world.map.MapParser;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private Player player;
	private DiscreteBar lifebar;
	private FinalMap map;
	private boolean lost;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);

		Map<String, String> playerProperties = new HashMap<String, String>();
		playerProperties.put("targetVelocity", "100");
		playerProperties.put("lives", "3");
		playerProperties.put("categoryMask", "1");
		playerProperties.put("collisionMask", "2");
		player = new Player(this, new AAB(dimensions.sdiv(2.0f), Color.BLACK,
				Color.BLACK, new Vec2f(36.0f, 36.0f)), "player",
				playerProperties);
		entities.put("player", player);

		Map<String, String> enemyProperties = new HashMap<String, String>();
		enemyProperties.put("targetVelocity", "100");
		enemyProperties.put("lives", "2");
		playerProperties.put("categoryMask", "2");
		playerProperties.put("collisionMask", "3");
		Enemy enemy = new Enemy(this, new AAB(new Vec2f(dimensions.x / 2.0f,
				dimensions.y / 4.0f), Color.RED, Color.RED, new Vec2f(36.0f,
				36.0f)), "enemy1", enemyProperties);
		entities.put("enemy1", enemy);

		lifebar = new DiscreteBar(new ImageSprite(new Vec2f(0.0f,
				0.0f), spriteSheets.get("heart"), new Vec2i(0, 0)), 3);
		hud.addHudElement(ScreenPosition.TOP_LEFT, lifebar);

		ImageSprite barSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("empty_energy_bar"), new Vec2i(0, 0));
		ImageSprite fillSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("energy_fill"), new Vec2i(0, 0));
		ContinuousBar energybar = new ContinuousBar(barSprite, fillSprite);
		hud.addHudElement(ScreenPosition.TOP_RIGHT, energybar);
		
		map = MapParser.parseMap("resources/maps/map1.txt");
		
		lost = false;
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
		spriteSheets.put("empty_energy_bar", new SpriteSheet(
				"resources/sprites/empty_energy_bar.png", new Vec2i(200, 16),
				new Vec2i(0, 0)));
		spriteSheets.put("energy_fill", new SpriteSheet(
				"resources/sprites/energy_fill.png", new Vec2i(16, 16),
				new Vec2i(0, 0)));
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void stopPlayer() {
		player.stop(new Vec2f(-1.0f, 0.0f));
		player.stop(new Vec2f(1.0f, 0.0f));
		player.stop(new Vec2f(0.0f, -1.0f));
		player.stop(new Vec2f(0.0f, 1.0f));
	}

	public void stopPlayer(Vec2f direction) {
		player.stop(direction);
	}

	public void attack() {
		Attack attack = player.attack();
		entities.put(attack.getName(), attack);
	}

	public List<FinalNode> pathToPlayer(Vec2f position) {
		FinalNode startNode = map.getNode(position);
		FinalNode endNode = map.getNode(player.getPosition());
		return map.shortestPath(startNode, endNode);
	}

	public void lose() {
		lost = true;
	}
	
	public boolean lost() {
		return lost;
	}

	public void decreaseLife() {
		lifebar.decreaseLife(1);
	}

}
