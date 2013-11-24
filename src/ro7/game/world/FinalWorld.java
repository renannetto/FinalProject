package ro7.game.world;

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
import ro7.game.world.npcs.NPC;
import ro7.game.world.npcs.SaveNPC;
import ro7.game.world.player.Action;
import ro7.game.world.player.Attack;
import ro7.game.world.player.GameItem;
import ro7.game.world.player.Player;
import ro7.game.world.scenario.Door;
import ro7.game.world.scenario.LockedDoor;
import ro7.game.world.scenario.Scenario;
import ro7.game.world.scenario.Wall;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalWorld extends GameWorld {

	private FinalSaveFile currentSave;

	private String currentLevel;
	private String currentMap;

	private Vec2f playerInitPosition;

	private Player player;
	private DiscreteBar lifebar;
	private FinalMap map;
	private boolean lost;
	private boolean won;

	public FinalWorld(Vec2f dimensions, FinalSaveFile currentSave) {
		super(dimensions);

		this.currentSave = currentSave;
		this.playerInitPosition = null;

		initUI();

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
		classes.put("LockedDoor", LockedDoor.class);
		classes.put("NPC", NPC.class);
		classes.put("SaveNPC", SaveNPC.class);
		classes.put("GameItem", GameItem.class);
	}

	@Override
	public void loadSpriteSheets() {
		spriteSheets.put("heart", new SpriteSheet(
				"resources/sprites/ui/heart.png", new Vec2i(32, 28), new Vec2i(
						0, 0)));
		spriteSheets.put("empty_energy_bar", new SpriteSheet(
				"resources/sprites/ui/empty_energy_bar.png",
				new Vec2i(200, 16), new Vec2i(0, 0)));
		spriteSheets.put("energy_fill", new SpriteSheet(
				"resources/sprites/ui/energy_fill.png", new Vec2i(16, 16),
				new Vec2i(0, 0)));
		spriteSheets.put("statue_key", new SpriteSheet(
				"resources/sprites/items/statue_key.png", new Vec2i(24, 24),
				new Vec2i(0, 0)));
		spriteSheets.put("save", new SpriteSheet(
				"resources/sprites/npcs/save.png", new Vec2i(36, 36),
				new Vec2i(0, 0)));

		spriteSheets.put("hero_walk_sheet", new SpriteSheet(
				"resources/sprites/char/hero_walk_sheet.png",
				new Vec2i(96, 96), new Vec2i(0, 0)));
		spriteSheets.put("hero_attack_sheet", new SpriteSheet(
				"resources/sprites/char/hero_attack_sheet.png", new Vec2i(96,
						96), new Vec2i(0, 0)));
		spriteSheets.put("room_001", new SpriteSheet(
				"resources/sprites/prison/room_001.jpg", new Vec2i(640, 480),
				new Vec2i(0, 0)));
		spriteSheets.put("room_001_top", new SpriteSheet(
				"resources/sprites/prison/room_001_top.png",
				new Vec2i(640, 480), new Vec2i(0, 0)));
		spriteSheets.put("room_002", new SpriteSheet(
				"resources/sprites/prison/room_002.jpg", new Vec2i(640, 480),
				new Vec2i(0, 0)));
		spriteSheets.put("testZombie_001", new SpriteSheet(
				"resources/sprites/enemies/testZombie_001.png", new Vec2i(96,
						96), new Vec2i(0, 0)));
	}

	@Override
	public void initLevel(String levelName) {
		super.initLevel(levelName);
		currentLevel = levelName;
		Player newPlayer = (Player) entities.get("player");
		if (player == null) {
			player = newPlayer;
		}
		
		entities.put(player.getName(), player);
		collidables.remove(newPlayer);
		collidables.add(player);
		physEntities.remove(newPlayer);
		physEntities.add(player);
		
		if (playerInitPosition != null) {
			player.moveTo(playerInitPosition);
		} else {
			player.moveTo(newPlayer.getPosition());
		}
		playerInitPosition = null;
	}

	public void loadMap(String mapFile) {
		map = MapParser.parseMap("resources/maps/" + mapFile);
		currentMap = mapFile;
	}

	private void initUI() {
		lifebar = new DiscreteBar(new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("heart"), new Vec2i(0, 0)), 3);
		hud.addHudElement(ScreenPosition.TOP_LEFT, lifebar);

		ImageSprite barSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("empty_energy_bar"), new Vec2i(0, 0));
		ImageSprite fillSprite = new ImageSprite(new Vec2f(0.0f, 0.0f),
				spriteSheets.get("energy_fill"), new Vec2i(0, 0));
		ContinuousBar energybar = new ContinuousBar(barSprite, fillSprite);
		hud.addHudElement(ScreenPosition.TOP_RIGHT, energybar);
	}

	public void save() {
		currentSave.save(this);
	}

	public void movePlayer(Vec2f direction) {
		player.move(direction);
	}

	public void setInitialPosition(Vec2f position) {
		this.playerInitPosition = position;
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

	public void action() {
		Action action = player.action();
		entities.put(action.getName(), action);
	}

	@Override
	public String toString() {
		String worldString = currentLevel;
		worldString += "\n" + currentMap;
		return worldString;
	}

}
