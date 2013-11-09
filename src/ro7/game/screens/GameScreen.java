package ro7.game.screens;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.HashSet;
import java.util.Set;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.world.Viewport;
import ro7.game.world.FinalWorld;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class GameScreen extends Screen {
	
	private Viewport viewport;
	private FinalWorld world;

	private Set<Integer> pressedKeys;

	public GameScreen(Application app) {
		super(app);
		pressedKeys = new HashSet<Integer>();
	}

	@Override
	public void onTick(long nanosSincePreviousTick) {
		
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
		
		}
		pressedKeys.add(keyCode);
	}

	@Override
	public void onKeyReleased(KeyEvent e) {
		pressedKeys.remove(e.getKeyCode());
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
				Vec2f proportion = new Vec2f((float)newSize.x/oldSize.x, (float)newSize.y/oldSize.y);
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
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
