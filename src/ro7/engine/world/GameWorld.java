package ro7.engine.world;

import java.awt.Color;
import java.awt.Graphics2D;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import ro7.engine.world.entities.CollidableEntity;
import ro7.engine.world.entities.Entity;
import ro7.engine.world.entities.PhysicalEntity;
import ro7.engine.world.entities.Ray;
import ro7.engine.world.io.Connection;
import ro7.engine.world.io.Input;
import cs195n.LevelData;
import cs195n.LevelData.ConnectionData;
import cs195n.LevelData.EntityData;
import cs195n.LevelData.ShapeData;
import cs195n.LevelData.ShapeData.Type;
import cs195n.Vec2f;
import cs195n.Vec2i;

public abstract class GameWorld {

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

	protected Vec2f dimensions;
	protected List<CollidableEntity> collidables;
	protected Set<PhysicalEntity> physEntities;
	protected Set<Ray> rays;

	protected Set<String> removeEntities;

	protected GameWorld(Vec2f dimensions) {
		this.dimensions = dimensions;
		collidables = new ArrayList<CollidableEntity>();
		physEntities = new HashSet<PhysicalEntity>();
		rays = new HashSet<Ray>();

		removeEntities = new HashSet<String>();

		classes = new HashMap<String, Class<?>>();
		entities = new HashMap<String, Entity>();
		setGameClasses();

		spriteSheets = new HashMap<String, SpriteSheet>();
		loadSpriteSheets();
	}

	public abstract void setGameClasses();

	public abstract void loadSpriteSheets();

	public void initLevel(LevelData level) {
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
			try {
				constructor = entityClass.getConstructor(GameWorld.class,
						CollidingShape.class, String.class, Map.class);
				Entity entity = (Entity) constructor.newInstance(this, shape, entityName,
						properties);
				entities.put(entityName, entity);
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
			}
		}

		List<? extends ConnectionData> connectionDatas = level.getConnections();
		for (ConnectionData connectionData : connectionDatas) {
			String target = connectionData.getTarget();
			String targetInput = connectionData.getTargetInput();

			Entity targetEntity = entities.get(target);
			Input input = targetEntity.inputs.get(targetInput);

			Map<String, String> properties = connectionData.getProperties();

			Connection connection = new Connection(input, properties);

			String source = connectionData.getSource();
			String sourceOutput = connectionData.getSourceOutput();

			Entity sourceEntity = entities.get(source);
			sourceEntity.connect(sourceOutput, connection);
		}
	}

	private CollidingShape createShape(ShapeData shapeData) {
		CollidingShape shape = null;

		Type type = shapeData.getType();
		Vec2f center = shapeData.getCenter();
		Map<String, String> properties = shapeData.getProperties();

		String colorString = properties.get("color");
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
		
		if (!properties.containsKey("spriteSheet")) {
			return shape;
		}
		
		SpriteSheet sheet = spriteSheets.get(properties
				.get("spriteSheet"));
		Vec2i sheetPosition = new Vec2i(Integer.parseInt(properties
				.get("spritePosX")), Integer.parseInt(properties
				.get("spritePosY")));
		Vec2f position = shape.getPosition();
		
		ImageSprite sprite;
		if (properties.containsKey("frames")) {
			int frames = Integer.parseInt(properties.get("frames"));
			int timeToMove = Integer.parseInt(properties.get("timeToMove"));
			sprite = new AnimatedSprite(position, sheet, sheetPosition, frames, timeToMove);
		} else {
			sprite = new ImageSprite(position, sheet,
					sheetPosition);
		}
		CollidingShape colShape = shape;
		shape = new CollidingSprite(sprite, colShape);
		
		return shape;
	}

	/**
	 * Draw the game world inside the viewport
	 * 
	 * @param g
	 *            Graphics object used to draw
	 * @param min
	 *            minimum game coordinate that will appear on the viewport
	 * @param max
	 *            maximum game coordinate that will appear on the viewport
	 * @param viewport
	 *            the current viewport
	 */
	public void draw(Graphics2D g, Vec2f min, Vec2f max, Viewport viewport) {
		for (Entity entity : entities.values()) {
			entity.draw(g);
		}
		for (Ray ray : rays) {
			ray.draw(g);
		}
	}

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

		for (String entity : removeEntities) {
			entities.get(entity).remove();
			entities.remove(entity);
		}
		removeEntities.clear();
	}

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

	public void removeRay(Ray ray) {
		removeEntities.add(ray.toString());
	}

	public void addCollidableEntity(CollidableEntity entity) {
		collidables.add(entity);
	}
	
	public void removeCollidableEntity(CollidableEntity entity) {
		collidables.remove(entity);
	}

	public void addPhysicalEntity(PhysicalEntity entity) {
		physEntities.add(entity);
	}
	
	public void removePhysicalEntity(PhysicalEntity entity) {
		physEntities.remove(entity);
	}

	public SpriteSheet getSpriteSheet(String sheet) {
		return spriteSheets.get(sheet);
	}
	
	public void removeEntity(String entityName) {
		removeEntities.add(entityName);
	}

}
