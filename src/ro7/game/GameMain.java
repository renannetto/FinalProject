package ro7.game;

import ro7.engine.Application;

public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Tou", false);
		//app.pushScreen(new TitleScreen(app));
		app.startup();
	}

}
