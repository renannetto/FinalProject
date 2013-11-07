package ro7.engine.world.io;

import java.util.Map;

public class Connection {
	
	private Input target;
	private Map<String, String> args;
	
	public Connection(Input target, Map<String, String> args) {
		super();
		this.target = target;
		this.args = args;
	}
	
	public void run() {
		target.run(args);
	}

}
