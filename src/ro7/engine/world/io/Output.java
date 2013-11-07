package ro7.engine.world.io;

import java.util.HashSet;
import java.util.Set;

public class Output {
	
	private Set<Connection> connections;
	
	public Output() {
		connections = new HashSet<Connection>();
	}
	
	public void connect(Connection connection) {
		connections.add(connection);
	}
	
	public void run() {
		for (Connection connection : connections) {
			connection.run();
		}
	}

}
