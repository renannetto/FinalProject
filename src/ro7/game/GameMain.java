package ro7.game;

import ro7.engine.Application;
import ro7.engine.screens.VideoScreen;

public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false);
		app.pushScreen(new VideoScreen(app, "resources/videos/birds.mkv"));
		app.startup();
	}

}
