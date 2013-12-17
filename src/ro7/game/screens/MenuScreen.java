package ro7.game.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.io.File;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.world.Viewport;
import ro7.game.world.FinalSaveFile;
import ro7.game.world.FinalWorld;
import ro7.game.world.MenuWorld;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class MenuScreen extends Screen {

	private final String DEFAULT_SAVE_FILE = "resources/saves/save1";
	private final String FIRST_LEVEL = "level1.nlf";

	private Viewport viewport;
	private MenuWorld world;

	public MenuScreen(Application app) {
		super(app);
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		// TODO Auto-generated method stub
		world.update(nanosSincePreviousTick);
	}

	@Override
	public void onDraw(Graphics2D g) {
		// TODO Auto-generated method stub
		viewport.draw(g);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		switch (e.getKeyCode()) {
		case KeyEvent.VK_W:
			world.menu.moveIndex(-1);
			break;
		case KeyEvent.VK_S:
			world.menu.moveIndex(1);
			break;
		case KeyEvent.VK_ENTER: {
			switch (world.menu.getSelectedIndex()) {
			case 0: {
				Vec2f worldDimensions = new Vec2f(windowSize.x, windowSize.y);
				File file = new File(DEFAULT_SAVE_FILE);
				file.delete();
				FinalSaveFile saveFile = new FinalSaveFile(DEFAULT_SAVE_FILE);
				FinalWorld gameWorld = new FinalWorld(worldDimensions, saveFile);
				gameWorld.initLevel(FIRST_LEVEL);
				app.pushScreen(new GameScreen(app, gameWorld));
			}
				break;
			case 2: {
				// FinalSaveFile saveFile = new
				// FinalSaveFile(DEFAULT_SAVE_FILE);
				// Vec2f worldDimensions = new Vec2f(windowSize.x,
				// windowSize.y);
				// FinalWorld gameWorld = (FinalWorld) saveFile
				// .load(worldDimensions);
				// if (gameWorld != null) {
				// app.pushScreen(new GameScreen(app, gameWorld));
				// }
				break;
			}
			case 1: {
				System.exit(0);
			}
				break;
			}
		}
			break;
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
		Vec2i oldSize = windowSize;
		super.onResize(newSize);

		try {
			if (world == null) {
				world = new MenuWorld(new Vec2f(windowSize.x, windowSize.y));
			}

			if (viewport != null) {
				Vec2f gamePosition = viewport.getGamePosition();
				Vec2f proportion = new Vec2f((float) newSize.x / oldSize.x,
						(float) newSize.y / oldSize.y);
				Vec2f scale = viewport.getScale().pmult(proportion);
				viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
						newSize.x, newSize.y), world, scale, gamePosition);
			} else {
				Vec2f initialDimensions = new Vec2f(640.0f, 480.0f);
				Vec2f viewportDimensions = new Vec2f(windowSize.x, windowSize.y);
				Vec2f scale = viewportDimensions.pdiv(initialDimensions);
				viewport = new Viewport(new Vec2f(0.0f, 0.0f),
						viewportDimensions, world, scale, new Vec2f(0.0f, 0.0f));
			}
		} catch (NullPointerException e) {
			System.out.println("No window size defined");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
