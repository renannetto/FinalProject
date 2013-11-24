package ro7.game.world;

import java.util.Set;

import ro7.engine.world.Collision;
import ro7.game.world.player.Item;

public interface FinalEntity {
	
	public void receiveDamage(int damage);
	
	public void touchEnemy(Collision collision);
	
	public void receiveAttack(Collision collision);

	public void receiveAction(Collision collision, Set<Item> inventory);
	
	public void getItem(Item item);

}
