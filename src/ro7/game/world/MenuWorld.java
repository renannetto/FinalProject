package ro7.game.world;

import java.awt.Graphics2D;

import cs195n.Vec2f;
import cs195n.Vec2i;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.ui.Menu;
import ro7.engine.ui.MenuElement;
import ro7.engine.world.GameWorld;
import ro7.engine.world.Viewport;

public class MenuWorld extends GameWorld{

	private ImageSprite background;
	public Menu menu;
	
	public MenuWorld(Vec2f dimensions) {
		super(dimensions);
		background = new ImageSprite(new Vec2f(320f, 240f), spriteSheets.get("menuBackground"), new Vec2i(0, 0), new Vec2f(640, 480));
		menu = new Menu(new Vec2f(320, 300f), 50f);
		menu.addElement(new MenuElement(spriteSheets.get("menuOptStart"), new Vec2i(0,0), new Vec2f(240f, 60f), 1, 1));
		menu.addElement(new MenuElement(spriteSheets.get("menuOptOptions"), new Vec2i(0,0), new Vec2f(240f, 60f), 1, 1));
		menu.addElement(new MenuElement(spriteSheets.get("menuOptExit"), new Vec2i(0,0), new Vec2f(240f, 60f), 1, 1));
	}

	@Override
	public void setGameClasses() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void update(long nanoseconds) {
		
		menu.update(nanoseconds);
	}
	
	@Override
	public void draw(Graphics2D g, Viewport viewport)
	{
		background.draw(g);
		menu.draw(g);
	}

	@Override
	public void loadSpriteSheets() {
		// TODO Auto-generated method stub
		spriteSheets.put("menuBackground", new SpriteSheet(
				"resources/sprites/ui/Menu.jpg", new Vec2i(640, 480), new Vec2i(0, 0)));
		spriteSheets.put("menuOptStart", new SpriteSheet(
				"resources/sprites/ui/Start.png", new Vec2i(240, 60), new Vec2i(0, 0)));
		spriteSheets.put("menuOptOptions", new SpriteSheet(
				"resources/sprites/ui/Options.png", new Vec2i(240, 60), new Vec2i(0, 0)));
		spriteSheets.put("menuOptExit", new SpriteSheet(
				"resources/sprites/ui/Exit.png", new Vec2i(240, 60), new Vec2i(0, 0)));
	}
	
	

}
