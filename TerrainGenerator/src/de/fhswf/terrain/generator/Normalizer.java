package de.fhswf.terrain.generator;
/**
 * Normalize the values from (-1 , 1) to (0 , 1)
 */
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
		double temp = ((x - lowerBound) / (upperBound - lowerBound))
				* (normalizedUpperBound - normalizedLowerBound)
				+ normalizedLowerBound;
		
		if (temp > 1) {
			temp = 1;
		}
		if (temp < 0) {
			temp = 0;
		}
		return temp;
	}

	public double denormalize(double x) {
		return ((lowerBound - upperBound) * x - normalizedUpperBound
				* lowerBound + upperBound * normalizedLowerBound)
				/ (normalizedLowerBound - normalizedUpperBound);
	}
}