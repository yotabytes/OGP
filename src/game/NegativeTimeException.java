package game;

public class NegativeTimeException extends RuntimeException {
	NegativeTimeException(double value) {
		this.value = value;
	}
	
	@be.kuleuven.cs.som.annotate.Basic @be.kuleuven.cs.som.annotate.Immutable
	public double getValue() {
		return this.value;
	}
	
	private final double value;
}
