package ro7.engine.sprites;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;

import cs195n.Vec2f;

/**
 * @author ro7
 * Sprite that represents a text message
 */
public class Message extends Sprite {
	
	private final int OFFSET = 5;
	
	private String text;
	private float fontSize;
	private Color fontColor;
	
	public Message(String text, float fontSize, Color fontColor, Vec2f position) {
		super(position);
		this.text = text;
		this.fontSize = fontSize;
		this.fontColor = fontColor;
	}

	public Message(String text, Message message) {
		super(message.position);
		this.text = text;
		this.fontSize = message.fontSize;
		this.fontColor = message.fontColor;
	}

	/* (non-Javadoc)
	 * @see ro7.engine.sprites.Sprite#draw(java.awt.Graphics2D)
	 * Draw a text message on the screen.
	 */
	@Override
	public void draw(Graphics2D g) {
		g.setFont(new Font("Arial", Font.PLAIN, (int)fontSize));
		g.setColor(fontColor);
		g.drawString(text, position.x, position.y+g.getFontMetrics().getHeight());
	}
	
	public void draw(Graphics2D g, Vec2f dimensions) {
		String[] texts = text.split("\n");
		for (int i=0; i<texts.length; i++) {
			String t = texts[i];
			
			g.setFont(new Font("Arial", Font.PLAIN, (int)fontSize));
			g.setColor(fontColor);
			
			FontMetrics metrics = g.getFontMetrics();
			int height = metrics.getHeight();
			int width = metrics.stringWidth(t);
			
			while (height > (dimensions.y + OFFSET)) {
				fontSize = fontSize/2.0f;
				g.setFont(g.getFont().deriveFont(fontSize));
				metrics = g.getFontMetrics();
				height = metrics.getHeight();
				width = metrics.stringWidth(t);
			}
			while(width > (dimensions.x + OFFSET)) {
				fontSize = fontSize/2.0f;
				g.setFont(g.getFont().deriveFont(fontSize));
				metrics = g.getFontMetrics();
				height = metrics.getHeight();
				width = metrics.stringWidth(t);
			}
			
			g.drawString(t, position.x, position.y+(height*(i+1)));
		}
	}

	public String getText() {
		return text;
	}

}
