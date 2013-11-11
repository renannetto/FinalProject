package ro7.engine.screens;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cs195n.Vec2f;
import cs195n.Vec2i;
import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.sprites.TextBox;

public class TextCutsceneScreen extends Screen {

	private final float TEXT_BOX_HEIGHT = 100.0f;
	private final Color TEXT_BOX_COLOR = Color.BLUE;
	private final Color FONT_COLOR = Color.WHITE;

	private Screen previousScreen;
	private List<String> texts;
	private List<TextBox> textBoxes;
	private int currentBox;

	public TextCutsceneScreen(Application app, Screen previousScreen, String textFilename) {
		super(app);
		this.previousScreen = previousScreen;
		texts = new ArrayList<String>();
		textBoxes = new ArrayList<TextBox>();
		currentBox = 0;
		try {
			BufferedReader reader = new BufferedReader(new FileReader(new File(
					textFilename)));
			String line = reader.readLine();
			while (line != null) {
				texts.add(line);
				line = reader.readLine();
			}
			reader.close();
		} catch (FileNotFoundException e) {
			System.out.println("Invalid text file!");
		} catch (IOException e) {
			System.out.println("Failure to read text file!");
		}
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		if (currentBox >= textBoxes.size()) {
			app.popScreen();
		}
	}

	@Override
	public void onDraw(Graphics2D g) {
		previousScreen.onDraw(g);
		if (currentBox < textBoxes.size()) {
			textBoxes.get(currentBox).draw(g);
		}
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		if (keyCode == 10) {
			currentBox++;
		}
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
		try {
			Vec2f boxPosition = new Vec2f(((float) newSize.x) / 2.0f, newSize.y
					- (TEXT_BOX_HEIGHT / 2.0f));
			Vec2f boxDimensions = new Vec2f(newSize.x, TEXT_BOX_HEIGHT);

			textBoxes.clear();
			for (String text : texts) {
				textBoxes.add(new TextBox(boxPosition, boxDimensions,
						TEXT_BOX_COLOR, text, FONT_COLOR));
			}
		} catch (NullPointerException e) {
			System.out.println("No window size defined");
		}
	}

}
