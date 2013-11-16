package ro7.game.world.map;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Pattern;

import ro7.game.exceptions.ExpectedTokenException;
import ro7.game.exceptions.InvalidTokenException;
import cs195n.Vec2i;

public class MapParser {
	
	private static FinalMap map;

	public static FinalMap parseMap(String mapFilename) {
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

	private static void connectNeighbor(Map<Vec2i, FinalNode> nodes,
			FinalNode node, Vec2i gridPosition) {
		if (nodes.containsKey(gridPosition)) {
			node.connect(nodes.get(gridPosition), 1);
		}
	}

	private static Map<Vec2i, FinalNode> parseFile(File mapFile)
			throws FileNotFoundException, IOException, ExpectedTokenException,
			InvalidTokenException {
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
		int tileX = scanner.nextInt();

		if (!scanner.hasNextInt()) {
			reader.close();
			scanner.close();
			throw new ExpectedTokenException("<height>", line);
		}
		int tileY = scanner.nextInt();
		Vec2i tileDimensions = new Vec2i(tileX, tileY);
		map = new FinalMap(tileDimensions);

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

		for (int j = 0; j < mapHeight; j++) {
			line = reader.readLine();
			if (line == null) {
				reader.close();
				throw new ExpectedTokenException("X or -", line);
			}
			scanner = new Scanner(line);
			for (int i = 0; i < mapWidth; i++) {
				String tile = scanner.findInLine(Pattern.compile("X|-"));
				if (tile == null) {
					reader.close();
					scanner.close();
					throw new InvalidTokenException("Invalid token on line "
							+ line);
				}
				if (tile.equals("-")) {
					Vec2i gridPosition = new Vec2i(i, j);
					nodes.put(gridPosition, new FinalNode(gridPosition));
				}
			}
			scanner.close();
		}

		reader.close();

		return nodes;
	}

}
