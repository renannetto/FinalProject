package ro7.engine.sprites;

import java.awt.AlphaComposite;
import java.awt.Composite;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import cs195n.Vec2f;
import cs195n.Vec2i;

public class Slide {

	private final int DURATION;

	private Vec2f position;
	private Vec2i dimensions;
	private String filename;
	private BufferedImage image;
	private float alpha;
	private float elapsedTime;

	public Slide(Vec2f position, Vec2i dimensions, String filename, int duration) {
		this.position = position;
		this.dimensions = dimensions;
		this.filename = filename;
		this.image = null;
		this.DURATION = duration;
		this.alpha = 0.5f;
	}

	public void update(long nanoseconds) {
		elapsedTime += nanoseconds / 1000000000.0f;
		if (elapsedTime < 1.0f) {
			alpha = 0.5f + (elapsedTime / 2.0f);
		} else {
			float remaining = (DURATION - elapsedTime);
			if (remaining < 1.0f) {
				alpha = 0.5f + (remaining / 2.0f);
			}
		}
	}

	public void draw(Graphics2D g) {
		if (image == null) {
			try {
				this.image = ImageIO.read(new File(filename));
			} catch (IOException e) {
				System.out.println("Could not open image file");
			}
		} else {
			Composite oldComposite = g.getComposite();
			
			g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER,
					alpha));
			g.drawImage(image, (int) position.x, (int) position.y,
					dimensions.x, dimensions.y, null);
			
			g.setComposite(oldComposite);
		}
	}

	public boolean finished() {
		return elapsedTime > DURATION;
	}

	public void resize(Vec2i newSize) {
		dimensions = newSize;
	}

}
