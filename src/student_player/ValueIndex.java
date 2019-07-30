package student_player;

public class ValueIndex {
	private double value;
	private int index;
	public double getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public ValueIndex(double value, int index) {
		super();
		this.value = value;
		this.index = index;
	}
}
