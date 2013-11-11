package ro7.engine.ui;

import cs195n.Vec2f;
import ro7.engine.sprites.FloatingSprite;

public abstract class HudElement extends FloatingSprite {

	protected HudElement() {
		super(new Vec2f(0.0f, 0.0f));
	}
	
	public abstract Vec2f getDimensions();

}
