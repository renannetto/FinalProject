package ro7.game.world.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import ro7.game.exceptions.ExpectedTokenException;
import ro7.game.exceptions.InvalidTokenException;
import cs195n.Vec2f;
import cs195n.Vec2i;

public class MapParser {

	public static FinalMap parseMap(String mapFilename) {
		FinalMap map = new FinalMap();
		
		File mapFile = new File(mapFilename);	
		try {
			Map<Vec2i, FinalNode> nodes = parseFile(mapFile);
			
			for (Map.Entry<Vec2i, FinalNode> nodesEntry : nodes.entrySet()) {
				Vec2i gridPosition = nodesEntry.getKey();
				FinalNode node = nodesEntry.getValue();
				
				connectNeighbor(nodes, node, gridPosition.plus(1, 0));
				connectNeighbor(nodes, node, gridPosition.plus(-1, 0));
				connectNeighbor(nodes, node, gridPosition.plus(0, 1));
				connectNeighbor(nodes, node, gridPosition.plus(0, -1));
				
				map.addNode(node);
			}
			
		} catch (FileNotFoundException e) {
			System.out.println(mapFilename + " not found");
			return null;
		} catch (IOException e) {
			System.out.println("Could not read " + mapFilename);
			return null;
		} catch (ExpectedTokenException e) {
			System.out.println(e.getMessage());
			return null;
		} catch (InvalidTokenException e) {
			System.out.println(e.getMessage());
		}

		return map;
	}

	private static void connectNeighbor(Map<Vec2i, FinalNode> nodes, FinalNode node, Vec2i gridPosition) {
		if (nodes.containsKey(gridPosition)) {
			node.connect(nodes.get(gridPosition), 1);
		}
	}

	private static Map<Vec2i, FinalNode> parseFile(File mapFile) throws FileNotFoundException,
			IOException, ExpectedTokenException, InvalidTokenException {
		Map<Vec2i, FinalNode> nodes = new HashMap<Vec2i, FinalNode>();
		
		BufferedReader reader;
		Scanner scanner;
		
		reader = new BufferedReader(new FileReader(mapFile));
		String line = reader.readLine();
		scanner = new Scanner(line);

		if (!scanner.hasNextInt()) {
			reader.close();
			scanner.close();
			throw new ExpectedTokenException("<width>", line);
		}
		int gridX = scanner.nextInt();

		if (!scanner.hasNextInt()) {
			reader.close();
			scanner.close();
			throw new ExpectedTokenException("<height>", line);
		}
		int gridY = scanner.nextInt();

		scanner.close();

		line = reader.readLine();
		scanner = new Scanner(line);

		if (!scanner.hasNextInt()) {
			reader.close();
			scanner.close();
			throw new ExpectedTokenException("<width>", line);
		}
		int mapWidth = scanner.nextInt();

		if (!scanner.hasNextInt()) {
			reader.close();
			scanner.close();
			throw new ExpectedTokenException("<height>", line);
		}
		int mapHeight = scanner.nextInt();

		scanner.close();

		for (int i = 0; i < mapWidth; i++) {
			line = reader.readLine();
			scanner = new Scanner(line);
			for (int j = 0; j < mapHeight; j++) {
				if (!scanner.hasNext("X|-")) {
					reader.close();
					scanner.close();
					throw new InvalidTokenException(
							"Invalid token on line " + line);
				}
				String tile = scanner.next("X|-");
				if (tile.equals("-")) {
					Vec2i gridPosition = new Vec2i(i, j);
					Vec2f position = new Vec2f(i*gridX, j*gridY);
					nodes.put(gridPosition, new FinalNode(position));
				}
			}
			scanner.close();
		}

		reader.close();
		scanner.close();
		
		return nodes;
	}

}
