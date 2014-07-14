package ro7.engine.sprites;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import ro7.engine.world.Entity;
import cs195n.Vec2f;

/**
 * @author ro7
 * Sprite that represents a text menu
 */
public class Menu extends Sprite {

	private Vec2f space;
	List<Message> options;

	public Menu(Entity entity, Vec2f space) {
		super(entity);
		this.space = space;
		this.options = new ArrayList<Message>();
	}

	public void addOption(Message message) {
		options.add(message);
	}

	/* (non-Javadoc)
	 * @see ro7.engine.sprites.Sprite#draw(java.awt.Graphics2D)
	 * Draw a list of messages on the screen with their
	 * respective number.
	 */
	@Override
	public void drawSprite(Graphics2D g) {
		for (Message message : options) {
			Message number = new Message(options.indexOf(message) + 1 + "", message);
			number.draw(g);
			g.translate(space.x, 0.0f);
			message.draw(g);
			g.translate(0.0f, space.y);
			g.translate(-space.x, 0.0f);
		}
		g.translate(0, -((options.size() - 1)
				* space.y));
	}

	public String getOption(int index) {
		return options.get(index).getText();
	}

}
