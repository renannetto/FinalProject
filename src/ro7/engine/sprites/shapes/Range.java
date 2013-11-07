package ro7.engine.sprites.shapes;


public class Range {

	private float min;
	private float max;

	public Range(float min, float max) {
		this.min = min;
		this.max = max;
	}

	public boolean overlaps(Range range2) {
		return this.min <= range2.max && range2.min <= this.max;
	}
	
	public Float mtv(Range range2) {
		float left = range2.max - this.min;
		float right = this.max - range2.min;
		
		if (left < 0 || right < 0) {
			return null;
		}
		if (left < right) {
			return -left;
		}
		return right;
	}

}
