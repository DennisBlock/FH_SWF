package de.fhswf.terrain.generator;

import java.util.Random;

/**
 * This class implements the diamond-square algorithm. The diamond-square
 * algorithm is a method for generating heightmaps for computer graphics.
 * 
 */
public class DiamondSquare {
	private double[][] map;
	private int size;
	private double h; // the range (-h -> +h) for the average offset

	private Random random;

	private Normalizer normalizer;

	/**
	 * Constructor
	 * 
	 * @param size
	 *            Size of the map.
	 * @param h
	 *            The range (-h -> +h) for the average offset.
	 * @param seed
	 *            Initial seed value for the random generator.
	 */
	public DiamondSquare(int size, double h, long seed) {
		// it should be square, and the dimension should be a power of two, plus
		// one (e.g. 33x33, 65x65, 129x129, etc.).
		if (!isPowerOfTwo(size - 1))
			throw new IllegalArgumentException("Die Groesse muss 2^n+1 sein.");

		map = new double[size][size];
		this.size = size;
		this.h = h;

		random = new Random(seed);
		normalizer = new Normalizer(1.0, -1.0, 1.0, 0.0);
	}

	public void generateHeightmap() {

		// map[0][0] = random.nextDouble();
		// map[0][size - 1] = random.nextDouble();
		// map[size - 1][0] = random.nextDouble();
		// map[size - 1][size - 1] = random.nextDouble();

		// Assign a height value to each corner of the rectangle
		map[0][0] = map[0][size - 1] = map[size - 1][0] = map[size - 1][size - 1] = random
				.nextDouble();

		// sideLength is distance of a single square side or distance of
		// diagonal in diamond
		for (int sideLength = size - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
			int halfSide = sideLength / 2;

			squareStep(sideLength, halfSide);
			diamondStep(sideLength, halfSide);

		}

		// Uneven lowland sollte es eigentlich sein xD
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = (float) (map[i][j] <= 0 ? -Math.sqrt(Math
						.abs(map[i][j])) : map[i][j]);
			}
		}
	}

	/**
	 * The square step: Taking each diamond of four points, generate a random
	 * value at the center of the diamond. Calculate the midpoint value by
	 * averaging the corner values, plus a random amount generated in the same
	 * range as used for the diamond step. This gives you squares again.
	 * 
	 * @param sideLength
	 * @param halfSide
	 */
	private void squareStep(int sideLength, int halfSide) {
		// generate the new square values
		for (int x = 0; x < size - 1; x += sideLength) {
			for (int y = 0; y < size - 1; y += sideLength) {
				double average = map[x][y] + // top left
						map[x + sideLength][y] + // top right
						map[x][y + sideLength] + // lower left
						map[x + sideLength][y + sideLength];// lower right
				average /= 4.0;

				double temp = (average + (random.nextDouble() * 2 * h) - h);

				// set negative values to 1
				map[x + halfSide][y + halfSide] = temp > 1 ? 1 : temp;
			}
		}
	}

	/**
	 * The diamond step: Taking a square of four points, generate a random value
	 * at the square midpoint, where the two diagonals meet. The midpoint value
	 * is calculated by averaging the four corner values, plus a random amount.
	 * This gives you diamonds when you have multiple squares arranged in a
	 * grid.
	 * 
	 * @param sideLength
	 * @param halfSide
	 */
	private void diamondStep(int sideLength, int halfSide) {
		// generate the diamond values
		for (int x = 0; x < size - 1; x += halfSide) {
			for (int y = (x + halfSide) % sideLength; y < size - 1; y += sideLength) {
				// double average = map[(x - halfSide + size) % size][y] + //
				// left
				// map[(x + halfSide) % size][y] + // right of center
				// map[x][(y + halfSide) % size] + // below center
				// map[x][(y - halfSide + size) % size]; // above
				double average = map[(x - halfSide + size - 1) % (size - 1)][y]
						+ // left
							// of
							// center
						map[(x + halfSide) % (size - 1)][y] + // right of center
						map[x][(y + halfSide) % (size - 1)] + // below center
						map[x][(y - halfSide + size - 1) % (size - 1)]; // above
																		// center
				average /= 4.0;

				average = average + (random.nextDouble() * 2 * h) - h;
				// update value for center of diamond
				map[x][y] = average;

				if (x == 0)
					map[size - 1][y] = average;
				if (y == 0)
					map[x][size - 1] = average;
			}
		}
	}

	public void normalize() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				double temp = normalizer.normalize(map[x][y]);
				if (temp > 1) {
					temp = 1;
				}
				if (temp < 0) {
					temp = 0;
				}
				map[x][y] = temp;
			}
		}
	}

	public void denormalize() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				map[x][y] = normalizer.denormalize(map[x][y]);
			}
		}
	}

	/**
	 * Should be square
	 * @param n length of the map minus one
	 * @return true if power of two
	 */
	private boolean isPowerOfTwo(int n) {
		return ((n != 0) && ((n & (n - 1)) == 0));
	}

	public void debugOutput() {
		// print out the data
		for (double[] row : map) {
			for (double d : row) {
				System.out.printf("%8.3f ", d);
			}
			System.out.println();
		}
	}
	public int size() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getH() {
		return h;
	}

	public void setH(double h) {
		this.h = h;
	}

	public double get(int x, int y) {
		return map[x][y];
	}

}