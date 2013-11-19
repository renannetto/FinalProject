package ro7.game.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.ui.ContinuousBar;
import ro7.engine.ui.DiscreteBar;
import ro7.engine.ui.ScreenPosition;
import ro7.engine.world.GameWorld;
import ro7.engine.world.RayCollision;
import ro7.engine.world.Viewport;
import ro7.engine.world.entities.Ray;
import ro7.game.world.map.FinalMap;
import ro7.game.world.map.FinalNode;
import ro7.game.world.map.MapParser;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private final String PLAYER_ATTACK = "playerAttack";

	private ImageSprite background;
	private Player player;
	private DiscreteBar lifebar;
	private FinalMap map;
	private boolean lost;

	public FinalWorld(Vec2f dimensions) {
		super(dimensions);

		background = new ImageSprite(dimensions.sdiv(2.0f),
				spriteSheets.get("prison_room001"), new Vec2i(0, 0));

		Map<String, String> wallProperties = new HashMap<String, String>();
		wallProperties.put("categoryMask", "-1");
		wallProperties.put("collisionMask", "-1");
		entities.put("wall1", new Wall(this, new AAB(new Vec2f(160.0f, 16.0f),
				Color.BLACK, Color.BLACK, new Vec2f(320.0f, 32.0f)), "wall1", wallProperties));
		entities.put("wall2", new Wall(this, new AAB(new Vec2f(544.0f, 16.0f),
				Color.BLACK, Color.BLACK, new Vec2f(320.0f, 32.0f)), "wall2", wallProperties));
		entities.put("wall3", new Wall(this, new AAB(new Vec2f(16.0f, 240.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 480.0f)), "wall3", wallProperties));
		entities.put("wall4", new Wall(this, new AAB(new Vec2f(624.0f, 240.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 480.0f)), "wall4", wallProperties));
		entities.put("wall5", new Wall(this, new AAB(new Vec2f(320.0f, 464.0f),
				Color.BLACK, Color.BLACK, new Vec2f(640.0f, 32.0f)), "wall5", wallProperties));
		entities.put("wall6", new Wall(this, new AAB(new Vec2f(192.0f, 270.0f),
				Color.BLACK, Color.BLACK, new Vec2f(384.0f, 32.0f)), "wall6", wallProperties));
		entities.put("wall7", new Wall(this, new AAB(new Vec2f(192.0f, 302.0f),
				Color.BLACK, Color.BLACK, new Vec2f(384.0f, 32.0f)), "wall7", wallProperties));
		entities.put("wall8", new Wall(this, new AAB(new Vec2f(544.0f, 270.0f),
				Color.BLACK, Color.BLACK, new Vec2f(192.0f, 32.0f)), "wall8", wallProperties));
		entities.put("wall9", new Wall(this, new AAB(new Vec2f(544.0f, 302.0f),
				Color.BLACK, Color.BLACK, new Vec2f(192.0f, 32.0f)), "wall9", wallProperties));
		entities.put("wall10", new Wall(this, new AAB(new Vec2f(240.0f, 48.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 96.0f)), "wall10", wallProperties));
		entities.put("wall11", new Wall(this, new AAB(new Vec2f(272.0f, 48.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 96.0f)), "wall11", wallProperties));
		entities.put("wall12", new Wall(this, new AAB(new Vec2f(240.0f, 208.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 96.0f)), "wall12", wallProperties));
		entities.put("wall13", new Wall(this, new AAB(new Vec2f(272.0f, 208.0f),
				Color.BLACK, Color.BLACK, new Vec2f(32.0f, 96.0f)), "wall13", wallProperties));

		Map<String, String> playerProperties = new HashMap<String, String>();
		playerProperties.put("targetVelocity", "100");
		playerProperties.put("lives", "3");
		playerProperties.put("standingSheet", "hero_walk_sheet");
		playerProperties.put("posStandingDownX", "0");
		playerProperties.put("posStandingDownY", "0");
		playerProperties.put("posStandingUpX", "0");
		playerProperties.put("posStandingUpY", "1");
		playerProperties.put("posStandingLeftX", "0");
		playerProperties.put("posStandingLeftY", "0");
		playerProperties.put("posStandingRightX", "0");
		playerProperties.put("posStandingRightY", "0");
		playerProperties.put("walkingSheet", "hero_walk_sheet");
		playerProperties.put("framesWalking", "4");
		playerProperties.put("timeToMoveWalking", "0.2");
		playerProperties.put("posWalkingDownX", "0");
		playerProperties.put("posWalkingDownY", "0");
		playerProperties.put("posWalkingUpX", "0");
		playerProperties.put("posWalkingUpY", "1");
		playerProperties.put("categoryMask", "1");
		playerProperties.put("collisionMask", "26");
		playerProperties.put("attackCategory", "4");
		playerProperties.put("attackCollision", "2");
		ImageSprite playerSprite = new ImageSprite(dimensions.sdiv(2.0f),
				spriteSheets.get("hero_walk_sheet"), new Vec2i(0, 0));
		CollidingSprite playerShape = new CollidingSprite(playerSprite,
				new AAB(new Vec2f(80.0f, 368.0f), Color.BLACK, Color.BLACK,
						new Vec2f(32.0f, 32.0f)));
		player = new Player(this, playerShape, "player", playerProperties);
		entities.put("player", player);

		// Map<String, String> enemyProperties = new HashMap<String, String>();
		// enemyProperties.put("actionRadius", "100");
		// enemyProperties.put("targetVelocity", "50");
		// enemyProperties.put("lives", "2");
		// enemyProperties.put("categoryMask", "2");
		// enemyProperties.put("collisionMask", "23");
		// PrisonGuard enemy = new PrisonGuard(this, new AAB(new
		// Vec2f(dimensions.x / 2.0f,
		// dimensions.y / 4.0f), Color.RED, Color.RED, new Vec2f(36.0f,
		// 36.0f)), "enemy1", enemyProperties);
		// entities.put("enemy1", enemy);

		// Map<String, String> archerProperties = new HashMap<String, String>();
		// archerProperties.put("actionRadius", "100");
		// archerProperties.put("detectionRadius", "200");
		// archerProperties.put("targetVelocity", "50");
		// archerProperties.put("lives", "2");
		// archerProperties.put("categoryMask", "2");
		// archerProperties.put("collisionMask", "23");
		// PrisonArcher archer = new PrisonArcher(this, new AAB(new
		// Vec2f(dimensions.x / 2.0f,
		// dimensions.y / 4.0f), Color.RED, Color.RED, new Vec2f(36.0f,
		// 36.0f)), "enemy2", archerProperties);
		// entities.put("enemy2", archer);

		ImageSprite topPrisonSprite = new ImageSprite(dimensions.sdiv(2.0f),
				spriteSheets.get("prison_room001_top"), new Vec2i(0, 0));
		CollidingSprite topPrisonShape = new CollidingSprite(topPrisonSprite,
				new AAB(dimensions.sdiv(2.0f), Color.BLACK, Color.BLACK,
						dimensions));
		TopScenario topPrison = new TopScenario(this, topPrisonShape,
				"topPrison");
		entities.put("topPrison", topPrison);

		lifebar = new DiscreteBar(new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("heart"), new Vec2i(0, 0)), 3);
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
		spriteSheets.put("hero_walk_sheet", new SpriteSheet(
				"resources/sprites/Char/hero_walk_sheet.png",
				new Vec2i(96, 96), new Vec2i(0, 0)));
		spriteSheets.put("prison_room001", new SpriteSheet(
				"resources/sprites/Prison/room_001.jpg", new Vec2i(640, 480),
				new Vec2i(0, 0)));
		spriteSheets.put("prison_room001_top", new SpriteSheet(
				"resources/sprites/Prison/room_001_top.png",
				new Vec2i(640, 480), new Vec2i(0, 0)));
	}

	@Override
	public void draw(Graphics2D g, Viewport viewport) {
		background.draw(g);
		super.draw(g, viewport);
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

	public boolean lost() {
		return lost;
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

}
