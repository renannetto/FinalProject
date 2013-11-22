package ro7.game.world;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.ui.ContinuousBar;
import ro7.engine.ui.DiscreteBar;
import ro7.engine.ui.ScreenPosition;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.entities.Entity;
import ro7.engine.world.entities.Ray;
import ro7.game.screens.GameScreen;
import ro7.game.world.enemies.Enemy;
import ro7.game.world.enemies.PrisonArcher;
import ro7.game.world.enemies.PrisonGuard;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import ro7.game.world.map.MapParser;
import ro7.game.world.player.Attack;
import ro7.game.world.player.Player;
import ro7.game.world.scenario.Door;
import ro7.game.world.scenario.Scenario;
import ro7.game.world.scenario.Wall;
import cs195n.CS195NLevelReader;
import cs195n.CS195NLevelReader.InvalidLevelException;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private final String PLAYER_ATTACK = "playerAttack";

	private Player player;
	private DiscreteBar lifebar;
	private FinalMap map;
	private boolean lost;
	private boolean won;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);
		
		try {
			initLevel(CS195NLevelReader.readLevel(new File("resources/levels/level1.nlf")));
		} catch (FileNotFoundException e) {
			System.out.println("Level file not found");
		} catch (InvalidLevelException e) {
			System.out.println("Invalid level file");
		}

		lifebar = new DiscreteBar(new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("heart"), new Vec2i(0, 0)), 3);
		hud.addHudElement(ScreenPosition.TOP_LEFT, lifebar);

		ImageSprite barSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("empty_energy_bar"), new Vec2i(0, 0));
		ImageSprite fillSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("energy_fill"), new Vec2i(0, 0));
		ContinuousBar energybar = new ContinuousBar(barSprite, fillSprite);
		hud.addHudElement(ScreenPosition.TOP_RIGHT, energybar);
		
		player = (Player)entities.get("player");

		map = MapParser.parseMap("resources/maps/map1.txt");

		lost = false;
		won = false;

		GameScreen.playCutscene("resources/cutscenes/cutscene1.txt");
	}

	@Override
	public void setGameClasses() {
		classes.put("Scenario", Scenario.class);
		classes.put("Wall", Wall.class);
		classes.put("Player", Player.class);
		classes.put("PrisonGuard", PrisonGuard.class);
		classes.put("PrisonArcher", PrisonArcher.class);
		classes.put("Door", Door.class);
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
		spriteSheets.put("hero_walk_sheet", new SpriteSheet(
				"resources/sprites/Char/hero_walk_sheet.png",
				new Vec2i(96, 96), new Vec2i(0, 0)));
		spriteSheets.put("hero_attack_sheet", new SpriteSheet(
				"resources/sprites/Char/hero_attack_sheet.png", new Vec2i(96,
						96), new Vec2i(0, 0)));
		spriteSheets.put("room_001", new SpriteSheet(
				"resources/sprites/Prison/room_001.jpg", new Vec2i(640, 480),
				new Vec2i(0, 0)));
		spriteSheets.put("room_001_top", new SpriteSheet(
				"resources/sprites/Prison/room_001_top.png",
				new Vec2i(640, 480), new Vec2i(0, 0)));
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
		Attack attack = player.attack(PLAYER_ATTACK);
		entities.put(attack.getName(), attack);
	}

	public List<FinalNode> pathToPlayer(Vec2f position) {
		FinalNode startNode = map.getNode(position);
		FinalNode endNode = map.getNode(player.getPosition());

		return map.shortestPath(startNode, endNode);
	}

	public List<FinalNode> shortestPath(Vec2f start, Vec2f end) {
		FinalNode startNode = map.getNode(start);
		FinalNode endNode = map.getNode(end);

		return map.shortestPath(startNode, endNode);
	}

	public void lose() {
		lost = true;
	}
	
	public void win() {
		won = true;
	}

	public boolean lost() {
		return lost;
	}
	
	public boolean won() {
		return won;
	}

	public void decreaseLife() {
		lifebar.decreaseLife(1);
	}

	public Vec2f getPlayerPosition() {
		return player.getPosition();
	}

	public boolean collidesPlayer(Ray ray) {
		RayCollision closest = getCollided(ray);
		return closest.other.equals(player);
	}

	public boolean noEnemies() {
		for (Entity entity : entities.values()) {
			if (entity instanceof Enemy) {
				return false;
			}
		}
		return true;
	}

}
