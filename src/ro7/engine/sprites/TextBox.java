package ro7.engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;

import ro7.engine.sprites.shapes.AAB;
import cs195n.Vec2f;

public class TextBox extends Sprite {
	
	private Message text;
	private AAB box;

	public TextBox(Vec2f position, Vec2f dimensions, Color boxColor, String text, int fontSize, Color fontColor) {
		super(position);
		this.box = new AAB(position, boxColor, boxColor, dimensions);
		this.text = new Message(text, fontSize, fontColor, position);
	}

	@Override
	public void draw(Graphics2D g) {
		box.draw(g);
		text.draw(g);
	}

}
