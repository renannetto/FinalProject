package ro7.engine.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.Circle;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.sprites.shapes.CompoundShape;
import ro7.engine.sprites.shapes.Polygon;
import ro7.engine.ui.Hud;
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.entities.Entity;
import ro7.engine.world.entities.PhysicalEntity;
import ro7.engine.world.entities.Ray;
import ro7.engine.world.io.Connection;
import ro7.engine.world.io.Input;
import cs195n.CS195NLevelReader;
import cs195n.CS195NLevelReader.InvalidLevelException;
import cs195n.LevelData;
import cs195n.LevelData.ConnectionData;
import cs195n.LevelData.EntityData;
import cs195n.LevelData.ShapeData;
import cs195n.LevelData.ShapeData.Type;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class GameWorld {

	/*
	 * Map used to associate each Color name with the correct object
	 */
	private final static Map<String, Color> COLORS;
	static {
		COLORS = new HashMap<String, Color>();
		COLORS.put("BLACK", Color.BLACK);
		COLORS.put("BLUE", Color.BLUE);
		COLORS.put("CYAN", Color.CYAN);
		COLORS.put("DARK_GRAY", Color.DARK_GRAY);
		COLORS.put("GRAY", Color.GRAY);
		COLORS.put("GREEN", Color.GREEN);
		COLORS.put("LIGHT_GRAY", Color.LIGHT_GRAY);
		COLORS.put("MAGENTA", Color.MAGENTA);
		COLORS.put("ORANGE", Color.ORANGE);
		COLORS.put("PINK", Color.PINK);
		COLORS.put("RED", Color.RED);
		COLORS.put("WHITE", Color.WHITE);
		COLORS.put("YELLOW", Color.YELLOW);
	}

	protected Map<String, Class<?>> classes;
	protected Map<String, Entity> entities;

	protected Map<String, SpriteSheet> spriteSheets;

	protected Hud hud;

	protected Vec2f dimensions;
	protected Set<CollidableEntity> collidables;
	protected Set<PhysicalEntity> physEntities;
	protected Set<Ray> rays;

	protected Set<Entity> newEntities;
	protected Set<String> removeEntities;
	
	protected String nextLevel;

	/**
	 * @param dimensions
	 *            Initialize the sets of collidable entities, physical entities,
	 *            rays and the map of classes, entities and spriteSheets.
	 */
	protected GameWorld(Vec2f dimensions) {
		this.dimensions = dimensions;
		collidables = new HashSet<CollidableEntity>();
		physEntities = new HashSet<PhysicalEntity>();
		rays = new HashSet<Ray>();

		newEntities = new HashSet<Entity>();
		removeEntities = new HashSet<String>();

		classes = new HashMap<String, Class<?>>();
		entities = new LinkedHashMap<String, Entity>();
		setGameClasses();

		spriteSheets = new HashMap<String, SpriteSheet>();
		loadSpriteSheets();

		hud = new Hud(dimensions);
		
		nextLevel = "";
	}

	/**
	 * Initialize the mapping from String to Class with all the game entities
	 */
	public abstract void setGameClasses();

	/**
	 * Initialize the mapping from String to SpriteSheet with all the game
	 * sprite sheets
	 */
	public abstract void loadSpriteSheets();

	/**
	 * @param level
	 *            Iterate through all entities described on the level,
	 *            initialize them and their connections.
	 */
	public void initLevel(String levelName) {
		entities.clear();
		collidables.clear();
		physEntities.clear();
		rays.clear();
		newEntities.clear();
		removeEntities.clear();
		nextLevel = "";
		
		try {
			LevelData level = CS195NLevelReader.readLevel(new File(
					"resources/levels/" + levelName));

			List<? extends EntityData> entitiesDatas = level.getEntities();
			for (EntityData entityData : entitiesDatas) {
				String entityClassName = entityData.getEntityClass();
				Class<?> entityClass = classes.get(entityClassName);
				String entityName = entityData.getName();

				List<? extends ShapeData> shapeDatas = entityData.getShapes();
				CollidingShape shape = null;
				if (shapeDatas.size() > 1) {
					List<CollidingShape> shapes = new ArrayList<CollidingShape>();
					for (ShapeData shapeData : shapeDatas) {
						CollidingShape partShape = createShape(shapeData);
						shapes.add(partShape);
					}
					shape = new CompoundShape(shapes.get(0).center(), shapes);
				} else if (shapeDatas.size() == 1) {
					shape = createShape(shapeDatas.get(0));
				}
				Map<String, String> properties = entityData.getProperties();

				Constructor<?> constructor;
				constructor = entityClass.getConstructor(GameWorld.class,
						CollidingShape.class, String.class, Map.class);
				Entity entity = (Entity) constructor.newInstance(this, shape,
						entityName, properties);
				entities.put(entityName, entity);
			}

			List<? extends ConnectionData> connectionDatas = level
					.getConnections();
			for (ConnectionData connectionData : connectionDatas) {
				String target = connectionData.getTarget();
				String targetInput = connectionData.getTargetInput();

				Entity targetEntity = entities.get(target);
				Input input = targetEntity.inputs.get(targetInput);

				Map<String, String> connectionProperties = connectionData
						.getProperties();

				Connection connection = new Connection(input,
						connectionProperties);

				String source = connectionData.getSource();
				String sourceOutput = connectionData.getSourceOutput();

				Entity sourceEntity = entities.get(source);
				sourceEntity.connect(sourceOutput, connection);
			}
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid constructor");
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			System.out.println("Constructor can't be accessed");
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Save file not found");
		} catch (InvalidLevelException e) {
			// TODO Auto-generated catch block
			System.out.println("Invalid save file");
		}
	}

	/**
	 * @param shapeData
	 *            Using the shapeData from the level editor create the
	 *            CollidingShape of the Entity. If there are properties
	 *            representing a CollidingSprite, returns the appropriate
	 *            object.
	 * @return a CollidingShape object
	 */
	private CollidingShape createShape(ShapeData shapeData) {
		CollidingShape shape = null;

		Type type = shapeData.getType();
		Vec2f center = shapeData.getCenter();
		Map<String, String> properties = shapeData.getProperties();

		String colorString;
		if (properties.containsKey("color")) {
			colorString = properties.get("color");
		} else {
			colorString = "BLACK";
		}
		Color color;
		if (COLORS.containsKey(colorString)) {
			color = COLORS.get(colorString);
		} else {
			color = Color.decode(colorString);
		}

		switch (type) {
		case CIRCLE:
			float radius = shapeData.getRadius();
			shape = new Circle(center, color, color, radius);
			break;
		case BOX:
			Vec2f dimensions = new Vec2f(shapeData.getWidth(),
					shapeData.getHeight());
			shape = new AAB(center, color, color, dimensions);
			break;
		case POLY:
			List<Vec2f> points = shapeData.getVerts();
			shape = new Polygon(center, color, points);
			break;
		}

		if (!properties.containsKey("sprite")) {
			return shape;
		}

		SpriteSheet sheet = spriteSheets.get(getSpriteSheetName(properties
				.get("sprite")));
		Vec2i sheetPosition = new Vec2i(Integer.parseInt(properties
				.get("spritePosX")), Integer.parseInt(properties
				.get("spritePosY")));
		Vec2f position = shape.getPosition();

		ImageSprite sprite;
		if (properties.containsKey("frames")) {
			int frames = Integer.parseInt(properties.get("frames"));
			int timeToMove = Integer.parseInt(properties.get("timeToMove"));
			sprite = new AnimatedSprite(position, sheet, sheetPosition,
					sheet.getFrameDimensions(), frames, timeToMove);
		} else {
			sprite = new ImageSprite(position, sheet, sheetPosition,
					sheet.getFrameDimensions());
		}
		CollidingShape colShape = shape;
		shape = new CollidingSprite(sprite, colShape);

		return shape;
	}

	private String getSpriteSheetName(String spriteSheet) {
		String[] strings = spriteSheet.split("/|\\.");
		return strings[strings.length - 2];
	}

	/**
	 * Draw the game world inside the viewport
	 * 
	 * @param g
	 *            Graphics object used to draw
	 * @param viewport
	 *            the current viewport
	 */
	public void draw(Graphics2D g, Viewport viewport) {
		for (Entity entity : entities.values()) {
			entity.draw(g);
		}
		for (Ray ray : rays) {
			ray.draw(g);
		}
		hud.draw(g, viewport);
	}

	/**
	 * @param nanoseconds
	 *            Call the update method from each Entity, check for collisions
	 *            and check for ray collisions. At the end remove from the world
	 *            all Entities in removeEntities.
	 */
	public void update(long nanoseconds) {
		for (Entity entity : entities.values()) {
			entity.update(nanoseconds);
		}

		for (CollidableEntity collidableA : collidables) {
			for (CollidableEntity collidableB : collidables) {
				if (!(collidableA.equals(collidableB))
						&& collidableA.collidable(collidableB)) {
					Collision collision = collidableA.collides(collidableB);
					if (collision.validCollision()) {
						collidableA.onCollision(collision);
					}
				}
			}
		}

		for (Ray ray : rays) {
			RayCollision closest = getCollided(ray);
			if (closest != null) {
				ray.onCollision(closest);
				ray.updateShape(closest.point);
			}
		}

		for (Entity entity : newEntities) {
			entities.put(entity.getName(), entity);
		}
		newEntities.clear();

		for (String entity : removeEntities) {
			entities.get(entity).remove();
			entities.remove(entity);
		}
		removeEntities.clear();
		
		if (!nextLevel.equals("")) {
			initLevel(nextLevel);
		}
	}

	/**
	 * @param ray
	 *            Find the closest Entity that collided with the ray and return
	 *            an object which represents this collision
	 * @return a RayCollision object
	 */
	public RayCollision getCollided(Ray ray) {
		RayCollision closest = null;
		float minDistance = Float.MAX_VALUE;
		for (CollidableEntity other : collidables) {
			if (ray.collidable(other)) {
				RayCollision collision = other.collidesRay(ray);
				if (collision.validCollision()) {
					Vec2f point = collision.point;
					float distance = ray.dist2(point);
					if (distance < minDistance) {
						minDistance = distance;
						closest = collision;
					}
				}
			}
		}
		return closest;
	}

	public Vec2f getDimensions() {
		return dimensions;
	}

	/**
	 * @param ray
	 *            Add the ray in a list to be removed at the end of the update
	 *            method
	 */
	public void removeRay(Ray ray) {
		removeEntities.add(ray.toString());
	}

	public void addCollidableEntity(CollidableEntity entity) {
		collidables.add(entity);
	}

	/**
	 * @param entity
	 *            Remove a collidableEntity from the world. This method is
	 *            called in the remove method from collidableEntity and should
	 *            not be called from the update method of any Entity.
	 */
	public void removeCollidableEntity(CollidableEntity entity) {
		collidables.remove(entity);
	}

	public void addPhysicalEntity(PhysicalEntity entity) {
		physEntities.add(entity);
	}

	/**
	 * @param entity
	 *            Remove a physicalEntity from the world. This method is called
	 *            in the remove method from physicalEntity and should not be
	 *            called from the update method of any Entity.
	 */
	public void removePhysicalEntity(PhysicalEntity entity) {
		physEntities.remove(entity);
	}

	public SpriteSheet getSpriteSheet(String sheet) {
		return spriteSheets.get(sheet);
	}

	public void addEntity(Entity entity) {
		newEntities.add(entity);
	}

	/**
	 * @param entityName
	 *            Add the entity in a list to be deleted at the end of the
	 *            update method.
	 */
	public void removeEntity(String entityName) {
		removeEntities.add(entityName);
	}
	
	public void loadLevel(String levelName) {
		nextLevel = levelName;
	}

}
