package ro7.game.world;

import ro7.engine.world.Collision;

public interface FinalEntity {
	
	public void receiveDamage(int damage);
	
	public void touchEnemy(Collision collision);
	
	public void receiveAttack(Collision collision);

}
