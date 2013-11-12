package ro7.engine.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.sprites.Slide;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class SlideShowScreen extends Screen {

	private List<Slide> slides;
	private Slide currentSlide;

	public SlideShowScreen(Application app, String slideShowFilename) {
		super(app);

		slides = new ArrayList<Slide>();
		Vec2f position = new Vec2f(0.0f, 0.0f);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(
					slideShowFilename));

			String imagePath = reader.readLine();
			while (imagePath != null) {
				int duration = Integer.parseInt(reader.readLine());
				slides.add(new Slide(position, windowSize, imagePath, duration));
				imagePath = reader.readLine();
			}

			reader.close();

			currentSlide = slides.remove(0);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		} catch (IOException e) {
			System.out.println("Failed to read slide show file");
		}
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		currentSlide.update(nanosSincePreviousTick);
		if (currentSlide.finished()) {
			if (slides.isEmpty()) {
				app.popScreen();
			} else {
				currentSlide = slides.remove(0);
			}
		}
	}

	@Override
	public void onDraw(Graphics2D g) {
		currentSlide.draw(g);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMouseWheelMoved(MouseWheelEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onResize(Vec2i newSize) {
		super.onResize(newSize);
		currentSlide.resize(newSize);
		for (Slide slide : slides) {
			slide.resize(newSize);
		}
	}

}
