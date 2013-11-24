package ro7.game.screens;

import java.awt.Color;

import ro7.engine.Application;
import ro7.engine.Screen;
import ro7.engine.screens.TextCutsceneScreen;
import ro7.engine.sprites.ImageSprite;
import ro7.engine.sprites.SpriteSheet;
import ro7.engine.sprites.shapes.AAB;
import ro7.engine.sprites.shapes.CollidingShape;
import ro7.engine.sprites.shapes.CollidingSprite;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class FinalTextCutscene extends TextCutsceneScreen {

	private final SpriteSheet TEXT_BOX_SHEET = new SpriteSheet(
			"resources/sprites/ui/text_box.png", new Vec2i(640, 100),
			new Vec2i(0, 0));

	public FinalTextCutscene(Application app, Screen previousScreen,
			String textFilename) {
		super(app, previousScreen, textFilename);
	}
	
	@Override
	protected CollidingShape createTextBox(Vec2f position, Vec2f dimensions) {
		ImageSprite boxSprite = new ImageSprite(position, TEXT_BOX_SHEET, new Vec2i(0, 0));
		CollidingShape boxShape = new AAB(position, Color.BLACK, Color.BLACK, dimensions);
		return new CollidingSprite(boxSprite, boxShape);
	}

}
