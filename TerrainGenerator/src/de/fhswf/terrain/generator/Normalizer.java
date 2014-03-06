package de.fhswf.terrain.generator;

public class Normalizer {
	private double upperBound;
	private double lowerBound;
	private double normalizedUpperBound;
	private double normalizedLowerBound;

	public Normalizer(double dataHigh, double dataLow) {
		this(dataHigh, dataLow, 1, -1);
	}

	public Normalizer(double high, double low, double normalizedHigh,
			double normalizedLow) {
		upperBound = high;
		lowerBound = low;
		normalizedUpperBound = normalizedHigh;
		normalizedLowerBound = normalizedLow;
	}

	public double normalize(double x) {
		return ((x - lowerBound) / (upperBound - lowerBound))
				* (normalizedUpperBound - normalizedLowerBound)
				+ normalizedLowerBound;
	}

	public double denormalize(double x) {
		return ((lowerBound - upperBound) * x - normalizedUpperBound * lowerBound + upperBound
				* normalizedLowerBound)
				/ (normalizedLowerBound - normalizedUpperBound);
	}
}