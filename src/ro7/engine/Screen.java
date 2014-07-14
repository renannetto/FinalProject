package ro7.engine;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import ro7.engine.world.PlayerInput;
import cs195n.Vec2i;

public abstract class Screen {
	
	protected Application app;
	protected Vec2i windowSize;
	protected PlayerInput playerInput;
	
	protected Screen(Application app) {
		this.app = app;
		this.playerInput = new PlayerInput();
	}
	
	public abstract void onTick(long nanosSincePreviousTick);

	
	public abstract void onDraw(Graphics2D g);

	
	public void onKeyTyped(KeyEvent e) {
		
	}

	
	public void onKeyPressed(KeyEvent e) {
		playerInput.press(e.getKeyCode(), e);
	}

	
	public void onKeyReleased(KeyEvent e) {
		playerInput.release(e.getKeyCode());
	}

	
	public void onMouseClicked(MouseEvent e) {
		
	}

	
	public void onMousePressed(MouseEvent e) {
		playerInput.press(e.getButton(), e);
	}

	
	public void onMouseReleased(MouseEvent e) {
		playerInput.release(e.getButton());
	}

	
	public abstract void onMouseDragged(MouseEvent e);

	
	public abstract void onMouseMoved(MouseEvent e);

	
	public abstract void onMouseWheelMoved(MouseWheelEvent e);

	
	/**
	 * Updates the window size of the screen
	 * @param newSize new size to be updated
	 */
	public void onResize(Vec2i newSize) {
		windowSize = newSize;
	}

}
