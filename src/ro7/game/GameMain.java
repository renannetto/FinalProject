package ro7.game;

import ro7.engine.Application;
import ro7.engine.screens.TextCutsceneScreen;
import ro7.game.screens.GameScreen;


public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false);
		app.pushScreen(new TextCutsceneScreen(app, "resources/text_cutscenes/cutscene1"));
		app.startup();
	}

}
