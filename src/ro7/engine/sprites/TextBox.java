package ro7.engine.sprites;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import ro7.engine.sprites.shapes.AAB;
import cs195n.Vec2f;

public class TextBox extends Sprite {
	
	private final int INITIAL_FONT_SIZE = 20;
	
	private Message text;
	private Message nextBox;
	private AAB box;

	public TextBox(Vec2f position, Vec2f dimensions, Color boxColor, String text, Color fontColor, int nextBoxKey) {
		super(position);
		this.box = new AAB(position, boxColor, boxColor, dimensions);
		this.text = new Message(text, INITIAL_FONT_SIZE, fontColor, position.minus(dimensions.sdiv(2.0f)));
		String nextBoxText = "Press " + KeyEvent.getKeyText(nextBoxKey) + " to continue";
		this.nextBox = new Message(nextBoxText, INITIAL_FONT_SIZE, fontColor, position.plus(dimensions.pmult(0.25f, 0.25f)));
	}

	@Override
	public void draw(Graphics2D g) {
		box.draw(g);
		text.draw(g, box.getDimensions().pmult(1.0f, 0.75f));
		nextBox.draw(g, box.getDimensions().pmult(0.25f, 0.25f));		
	}

}
