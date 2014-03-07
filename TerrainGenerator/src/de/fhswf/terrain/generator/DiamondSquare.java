package de.fhswf.terrain.generator;

import java.util.Random;

/**
 * This class implements the diamond-square algorithm. The diamond-square
 * algorithm is a method for generating heightmaps for computer graphics.
 */
public class DiamondSquare {
	private double[][] map; // Heightmap
	private int size; // Lenght of the heightmap
	private double h; // The range (-h -> +h) for the average offset
	private Random random; // Pseudorandom generator
	private Normalizer normalizer; // Normalize values from (-1 , 1) to (0 , 1)

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
		// Should be square, and the dimension should be a power of two, plus
		// one (e.g. 33x33, 65x65, 129x129, etc.).
		if (!isPowerOfTwo(size - 1))
			throw new IllegalArgumentException("Die Groesse muss 2^n+1 sein.");

		map = new double[size][size];
		this.size = size;
		this.h = h;

		// initialize the pseudorandom number generator with the seed. The same
		// input will always produce the same output.
		random = new Random(seed);
		// Normalize the values from (-1 , 1) to (0 , 1)
		normalizer = new Normalizer(1.0, -1.0, 1.0, 0.0);
	}

	/**
	 * A heightmap contains one channel interpreted as a distance of height from
	 * the floor.
	 */
	public void generateHeightmap() {
		double randomValue = random.nextDouble();

		// Assign a height value to each corner of the rectangle
		map[0][0] = randomValue;
		map[0][size - 1] = randomValue;
		map[size - 1][0] = randomValue;
		map[size - 1][size - 1] = randomValue;

		for (int sideLength = size - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
			// Each iteration we are looking at smaller squares diamonds
			squareStep(sideLength);
			diamondStep(sideLength);
		}

		// Ozean to uneven lowland mode 
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = (float) (map[i][j] <= 0 ? Math.sqrt(Math
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
	 *            Distance of a single square.
	 * @param halfSide
	 */
	private void squareStep(int sideLength) {
		// generate the new square values

		int halfSide = sideLength / 2;
		for (int x = 0; x < size - 1; x += sideLength) {
			for (int y = 0; y < size - 1; y += sideLength) {

				double topLeft = map[x][y];
				double topRight = map[x + sideLength][y];
				double lowerLeft = map[x][y + sideLength];
				double lowerRight = map[x + sideLength][y + sideLength];

				double average = average(topLeft, topRight, lowerLeft,
						lowerRight);
				double temp = (average + (random.nextDouble() * 2 * h) - h);

				map[x + halfSide][y + halfSide] = temp > 1 ? 1 : temp;
			}
		}
	}
	private double average(double topLeft, double topRight, double lowerLeft,
			double lowerRight) {
		return ((topLeft + topRight + lowerLeft + lowerRight) / 4.0);
	}

	/**
	 * The diamond step: Taking a square of four points, generate a random value
	 * at the square midpoint, where the two diagonals meet. The midpoint value
	 * is calculated by averaging the four corner values, plus a random amount.
	 * This gives you diamonds when you have multiple squares arranged in a
	 * grid.
	 * 
	 * @param sideLength
	 *            Distance of diagonal in diamond.
	 * @param halfSide
	 */
	private void diamondStep(int sideLength) {
		int halfSide = sideLength / 2;
		// generate the diamond values
		for (int x = 0; x < size - 1; x += halfSide) {
			for (int y = (x + halfSide) % sideLength; y < size - 1; y += sideLength) {

				double leftOfCenter = map[(x - halfSide + size - 1)
						% (size - 1)][y];
				double rightOfCenter = map[(x + halfSide) % (size - 1)][y];
				double belowOfCenter = map[x][(y + halfSide) % (size - 1)];
				double aboveOfCenter = map[x][(y - halfSide + size - 1)
						% (size - 1)];

				// calculate the avarege
				double average = average(leftOfCenter, rightOfCenter,
						belowOfCenter, aboveOfCenter);
				// new value is in the range (-h, +h)
				average = average + (random.nextDouble() * 2 * h) - h;

				// Set the center value of the diamond (x, y is the center of
				// the diamond)
				map[x][y] = average;

				// Calculate a value on the edge and store it on the opposite
				// side of the array. These points need to be exactly the same
				// in order for seamless wrapping to occur.
				if (x == 0)
					map[size - 1][y] = average;
				if (y == 0)
					map[x][size - 1] = average;
			}
		}
	}
	/**
	 * Normalize the map.
	 */
	public void normalize() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				map[x][y] = normalizer.normalize(map[x][y]);;
			}
		}
	}

	/**
	 * Should be square
	 * 
	 * @param n
	 *            Length of the map minus one.
	 * @return True if power of two.
	 */
	private boolean isPowerOfTwo(int n) {
		return ((n != 0) && ((n & (n - 1)) == 0));
	}

	/**
	 * Print out the data
	 */
	public void debugOutput() {
		for (double[] row : map) {
			for (double d : row) {
				System.out.printf("%8.3f ", d);
			}
			System.out.println();
		}
	}
	/**
	 * Getter for the map size.
	 * 
	 * @return size Length of the map.
	 */
	public int size() {
		return size;
	}

	/**
	 * Setter for the map size.
	 * 
	 * @param size
	 *            Length of the map.
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * Getter for the h value.
	 * 
	 * @return h The range (-h -> +h) for the average offset.
	 */
	public double getH() {
		return h;
	}

	/**
	 * Setter for the h value.
	 * 
	 * @param h
	 *            The range (-h -> +h) for the average offset.
	 */
	public void setH(double h) {
		this.h = h;
	}

	/**
	 * Getter for the map.
	 * 
	 * @param x
	 *            X value of the map.
	 * @param y
	 *            Y vakue of the map.
	 * @return Value at x,y position.
	 */
	public double get(int x, int y) {
		return map[x][y];
	}

}