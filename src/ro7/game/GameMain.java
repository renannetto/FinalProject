package ro7.game;

import ro7.engine.Application;
import ro7.game.screens.PhysicsSimulationScreen;
import cs195n.Vec2i;


public class GameMain {
	
	public static void main(String[] args) {
		Application app = new Application("Melda", false, new Vec2i(640, 480));
		app.pushScreen(new PhysicsSimulationScreen(app));
		app.startup();
	}

}
