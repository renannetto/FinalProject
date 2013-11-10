package ro7.game;

import ro7.engine.Application;
import ro7.engine.screens.TextCutsceneScreen;
import ro7.engine.screens.VideoScreen;
import ro7.game.screens.GameScreen;


public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false);
		app.pushScreen(new TextCutsceneScreen(app, "resources/cutscenes/cutscene1"));
		//app.pushScreen(new VideoScreen(app, "resources/videos/wildlife.wmv"));
		app.startup();
	}

}
