package ro7.game.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.audio.AudioManager;
import ro7.engine.screens.TextCutsceneScreen;
import ro7.engine.world.Viewport;
import ro7.game.world.FinalWorld;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class GameScreen extends Screen {

	private Viewport viewport;
	private FinalWorld world;

	private Set<Integer> pressedKeys;

	private static String cutscene;

	public GameScreen(Application app) {
		super(app);
		pressedKeys = new HashSet<Integer>();
		cutscene = "";
		AudioManager.getInstance().playMusic("resources/musics/background.ogg");
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		try {
			if (!cutscene.equals("")) {
				app.pushScreen(new TextCutsceneScreen(app, this, cutscene));
				cutscene = "";
			} else {
				world.update(nanosSincePreviousTick);
				if (world.lost()) {
					app.popScreen();
				} else if (world.won()) {
					app.popScreen();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void playCutscene(String cutscene) {
		GameScreen.cutscene = cutscene;
	}

	@Override
	public void onDraw(Graphics2D g) {
		viewport.draw(g);
	}

	@Override
	public void onKeyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onKeyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_W:
			if (!pressedKeys.contains(keyCode)) {
				world.stopPlayer(new Vec2f(0.0f, 1.0f));
				world.movePlayer(new Vec2f(0.0f, -1.0f));
			}
			break;
		case KeyEvent.VK_A:
			if (!pressedKeys.contains(keyCode)) {
				world.stopPlayer(new Vec2f(1.0f, 0.0f));
				world.movePlayer(new Vec2f(-1.0f, 0.0f));
			}
			break;
		case KeyEvent.VK_S:
			if (!pressedKeys.contains(keyCode)) {
				world.stopPlayer(new Vec2f(0.0f, -1.0f));
				world.movePlayer(new Vec2f(0.0f, 1.0f));
			}
			break;
		case KeyEvent.VK_D:
			if (!pressedKeys.contains(keyCode)) {
				world.stopPlayer(new Vec2f(-1.0f, 0.0f));
				world.movePlayer(new Vec2f(1.0f, 0.0f));
			}
			break;
		case KeyEvent.VK_SPACE:
			world.attack();
			break;
		case KeyEvent.VK_ESCAPE:
			app.popScreen();
			break;
		}
		pressedKeys.add(keyCode);
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_A:
			world.stopPlayer(new Vec2f(-1.0f, 0.0f));
			break;
		case KeyEvent.VK_D:
			world.stopPlayer(new Vec2f(1.0f, 0.0f));
			break;
		case KeyEvent.VK_S:
			world.stopPlayer(new Vec2f(0.0f, 1.0f));
			break;
		case KeyEvent.VK_W:
			world.stopPlayer(new Vec2f(0.0f, -1.0f));
			break;
		}
		pressedKeys.remove(keyCode);
	}

	@Override
	public void onMouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onMousePressed(MouseEvent e) {

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

	}

	@Override
	public void onResize(Vec2i newSize) {
		Vec2i oldSize = windowSize;
		super.onResize(newSize);

		try {
			if (world == null) {
				world = new FinalWorld(new Vec2f(windowSize.x, windowSize.y));
			}

			if (viewport != null) {
				Vec2f gamePosition = viewport.getGamePosition();
				Vec2f proportion = new Vec2f((float) newSize.x / oldSize.x,
						(float) newSize.y / oldSize.y);
				Vec2f scale = viewport.getScale().pmult(proportion);
				viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
						newSize.x, newSize.y), world, scale, gamePosition);
			} else {
				viewport = new Viewport(new Vec2f(0.0f, 0.0f), new Vec2f(
						windowSize.x, windowSize.y), world, new Vec2f(1.0f,
						1.0f), new Vec2f(0.0f, 0.0f));
			}
		} catch (NullPointerException e) {
			System.out.println("No window size defined");
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
