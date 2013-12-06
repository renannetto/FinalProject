package ro7.game.world.player;

import java.awt.Color;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import ro7.engine.sprites.AnimatedSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import ro7.engine.world.Collision;
import ro7.engine.world.GameWorld;
import ro7.engine.world.io.Connection;
import ro7.engine.world.io.Input;
import ro7.game.world.Character;
import ro7.game.world.FinalWorld;
import ro7.game.world.items.Item;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class Player extends Character {

	private String attackCategory;
	private String attackCollision;
	private Attack currentAttack;

	private String actionCategory;
	private String actionCollision;

	private Map<Vec2f, AnimatedSprite> attacking;

	private Set<Item> inventory;
	private String carrying;

	public Player(GameWorld world, CollidingShape shape, String name,
			Map<String, String> properties) {
		super(world, shape, name, properties);

		if (properties.containsKey("attackCategory")) {
			attackCategory = properties.get("attackCategory");
		} else {
			attackCategory = "-1";
		}
		if (properties.containsKey("attackCollision")) {
			attackCollision = properties.get("attackCollision");
		} else {
			attackCollision = "-1";
		}

		inputs.put("doStopAttack", new Input() {

			@Override
			public void run(Map<String, String> args) {
				currentAttack = null;
			}
		});

		inputs.put("doGetItem", new Input() {

			@Override
			public void run(Map<String, String> args) {
				String itemName = args.get("itemName");
				Item item = new Item(Player.this.world, null, itemName);
				getItem(item);
			}
		});

		inputs.put("doWin", new Input() {

			@Override
			public void run(Map<String, String> args) {
				((FinalWorld) Player.this.world).win();
			}
		});

		if (properties.containsKey("actionCategory")) {
			actionCategory = properties.get("actionCategory");
		} else {
			actionCategory = "-1";
		}
		if (properties.containsKey("actionCollision")) {
			actionCollision = properties.get("actionCollision");
		} else {
			actionCollision = "-1";
		}

		Vec2i posDown = new Vec2i(Integer.parseInt(properties.get("posDownX")),
				Integer.parseInt(properties.get("posDownY")));
		Vec2i posUp = new Vec2i(Integer.parseInt(properties.get("posUpX")),
				Integer.parseInt(properties.get("posUpY")));
		Vec2i posRight = new Vec2i(
				Integer.parseInt(properties.get("posRightX")),
				Integer.parseInt(properties.get("posRightY")));
		Vec2i posLeft = new Vec2i(Integer.parseInt(properties.get("posLeftX")),
				Integer.parseInt(properties.get("posLeftY")));

		SpriteSheet attackingSheet = world.getSpriteSheet(properties
				.get("attackingSheet"));
		int framesAttacking = Integer.parseInt(properties
				.get("framesAttacking"));
		float timeToMoveAttacking = Float.parseFloat(properties
				.get("timeToMoveAttacking"));
		attacking = new HashMap<Vec2f, AnimatedSprite>();
		attacking.put(new Vec2f(0.0f, 1.0f),
				new AnimatedSprite(shape.getPosition(), attackingSheet,
						posDown, framesAttacking, timeToMoveAttacking));
		attacking.put(new Vec2f(0.0f, -1.0f),
				new AnimatedSprite(shape.getPosition(), attackingSheet, posUp,
						framesAttacking, timeToMoveAttacking));
		attacking.put(new Vec2f(1.0f, 0.0f),
				new AnimatedSprite(shape.getPosition(), attackingSheet,
						posRight, framesAttacking, timeToMoveAttacking));
		attacking.put(new Vec2f(-1.0f, 0.0f),
				new AnimatedSprite(shape.getPosition(), attackingSheet,
						posLeft, framesAttacking, timeToMoveAttacking));

		this.inventory = new HashSet<Item>();
		this.carrying = "";

		inputs.put("doCarryItem", new Input() {

			@Override
			public void run(Map<String, String> args) {
				carrying = args.get("gameItemName");
			}
		});
	}

	@Override
	public void update(long nanoseconds) {
		if (currentAttack == null) {
			super.update(nanoseconds);
		} else {
			updateSprite(nanoseconds);
		}
	}

	@Override
	protected void updateSprite(long nanoseconds) {
		if (currentAttack == null) {
			super.updateSprite(nanoseconds);
		} else {
			if (Math.abs(direction.y) >= Math.abs(direction.x)) {
				if (direction.y > 0) {
					((CollidingSprite) shape).updateSprite(attacking
							.get(new Vec2f(0.0f, 1.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(attacking
							.get(new Vec2f(0.0f, -1.0f)));
				}
			} else {
				if (direction.x > 0) {
					((CollidingSprite) shape).updateSprite(attacking
							.get(new Vec2f(1.0f, 0.0f)));
				} else {
					((CollidingSprite) shape).updateSprite(attacking
							.get(new Vec2f(-1.0f, 0.0f)));
				}
			}

			this.shape.update(nanoseconds);
		}
	}

	@Override
	public void move(Vec2f direction) {
		if (currentAttack == null && damageTime >= DAMAGE_DELAY) {
			super.move(direction);
		}
	}

	public Attack attack() {
		if (!carrying.equals("") || damageTime < DAMAGE_DELAY) {
			currentAttack = null;
			return currentAttack;
		}
		if (currentAttack != null) {
			return currentAttack;
		}

		Map<String, String> attackProperties = new HashMap<String, String>();
		attackProperties.put("categoryMask", attackCategory);
		attackProperties.put("collisionMask", attackCollision);
		attackProperties.put("damage", "1");

		Vec2f attackDirection = getAttackDirection();
		Vec2f attackPosition = shape.getPosition().plus(
				shape.getDimensions().sdiv(2.0f).pmult(attackDirection));
		Vec2f attackDimensions = shape.getDimensions().pdiv(
				Math.abs(attackDirection.x) + 1,
				Math.abs(attackDirection.y) + 1);
		CollidingShape attackShape = new AAB(attackPosition, Color.BLUE,
				Color.BLUE, attackDimensions);

		currentAttack = new Attack(world, attackShape, name + "Attack",
				attackProperties);

		Connection connection = new Connection(inputs.get("doStopAttack"),
				new HashMap<String, String>());
		currentAttack.connect("onFinish", connection);

		return currentAttack;
	}

	public Action action() {
		if (damageTime < DAMAGE_DELAY) {
			return null;
		}

		Vec2f actionDirection = getAttackDirection();
		Vec2f actionPosition = shape.getPosition().plus(
				shape.getDimensions().sdiv(2.0f).pmult(actionDirection));
		Vec2f actionDimensions = shape.getDimensions().pdiv(
				Math.abs(actionDirection.x) + 1,
				Math.abs(actionDirection.y) + 1);
		CollidingShape actionShape = new AAB(actionPosition, Color.BLUE,
				Color.BLUE, actionDimensions);
		String actionName = name + "Action";

		if (!carrying.equals("")) {
			Map<String, String> dropActionProperties = new HashMap<String, String>();
			dropActionProperties.put("categoryMask", "0");
			dropActionProperties.put("collisionMask", "0");

			DropAction action = new DropAction(world, actionShape, actionName,
					dropActionProperties, this, inventory, carrying);
			carrying = "";
			return action;
		} else {
			Map<String, String> actionProperties = new HashMap<String, String>();
			actionProperties.put("categoryMask", actionCategory);
			actionProperties.put("collisionMask", actionCollision);

			return new Action(world, actionShape, actionName, actionProperties,
					this, inventory);
		}
	}

	private Vec2f getAttackDirection() {
		Vec2f attackDirection = direction;
		if (Math.abs(attackDirection.y) >= Math.abs(attackDirection.x)) {
			attackDirection = new Vec2f(0.0f, attackDirection.y).normalized();
		} else {
			attackDirection = new Vec2f(attackDirection.x, 0.0f).normalized();
		}
		return attackDirection;
	}

	public Vec2f getPosition() {
		return shape.getPosition();
	}

	@Override
	public void receiveDamage(int damage) {
		super.receiveDamage(damage);
		((FinalWorld) world).decreaseLife();
		if (lives <= 0) {
			((FinalWorld) world).lose();
		}
	}

	@Override
	public void touchEnemy(Collision collision) {
		receiveDamage(1);
		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}
		push(mtv);
	}

	@Override
	public void getItem(Item item) {
		inventory.add(item);
	}

	public boolean hasItem(Item item) {
		return inventory.contains(item);
	}

	@Override
	public void fall(Collision collision) {
		// receiveDamage(1);
		stop(direction);

		Vec2f mtv = collision.mtv;
		Vec2f centerDistance = collision.thisShape.center().minus(
				collision.otherShape.center());
		if (mtv.dot(centerDistance) < 0) {
			mtv = mtv.smult(-1.0f);
		}

		shape.moveTo(shape.getPosition().plus(
				mtv.normalized().pmult(shape.getDimensions().sdiv(1.5f))));
	}

	@Override
	public String toString() {
		String playerString = lives + "\n";
		playerString += inventory.size() + "\n";
		for (Item item : inventory) {
			playerString += item.toString() + "\n";
		}
		return playerString;
	}

	public void setLives(int lives) {
		int oldLives = this.lives;
		this.lives = lives;
		for (int i = 0; i < oldLives - lives; i++) {
			((FinalWorld) world).decreaseLife();
		}
	}

	public void copy(Player oldPlayer) {
		this.lives = oldPlayer.lives;
		this.inventory = oldPlayer.inventory;
		this.direction = oldPlayer.direction;
		this.velocity = oldPlayer.velocity;
	}

}
