package de.fhswf.terrain.generator;

import java.util.Random;

public class DiamondSquare {
	private double[][] map;
	private int size;
	private double h; // the range (-h -> +h) for the average offset

	private Random random;

	private Normalizer normalizer;

	public DiamondSquare(int size, double h, long seed) {
		if (!isPowerOfTwo(size - 1))
			throw new IllegalArgumentException("Die Groesse muss 2^n+1 sein.");

		map = new double[size][size];
		this.size = size;
		this.h = h;

		random = new Random(seed);
		normalizer = new Normalizer(1.0, -1.0, 1.0, 0.0);
	}

	public void normalize() {
		for (int x = 0; x < map.length; x++) {
			for (int y = 0; y < map.length; y++) {
				map[x][y] = normalizer.normalize(map[x][y]);
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

	public void generateHeightmap() {

		map[0][0] = random.nextDouble();
		map[0][size - 1] = random.nextDouble();
		map[size - 1][0] = random.nextDouble();
		map[size - 1][size - 1] = random.nextDouble();

		for (int sideLength = size - 1; sideLength >= 2; sideLength /= 2, h /= 2.0) {
			int halfSide = sideLength / 2;

			// generate the new square values
			for (int x = 0; x < size - 1; x += sideLength) {
				for (int y = 0; y < size - 1; y += sideLength) {
					double avg = map[x][y] + // top left
							map[x + sideLength][y] + // top right
							map[x][y + sideLength] + // lower left
							map[x + sideLength][y + sideLength];// lower right
					avg /= 4.0;

					double temp = (avg + (random.nextDouble() * 2 * h) - h);
					map[x + halfSide][y + halfSide] = temp > 1 ? 1 : temp;
				}
			}

			// generate the diamond values
			for (int x = 0; x < size - 1; x += halfSide) {
				for (int y = (x + halfSide) % sideLength; y < size - 1; y += sideLength) {
					double avg = map[(x - halfSide + size) % size][y] + // left
							map[(x + halfSide) % size][y] + // right of center
							map[x][(y + halfSide) % size] + // below center
							map[x][(y - halfSide + size) % size]; // above
					avg /= 4.0;

					avg = avg + (random.nextDouble() * 2 * h) - h;
					// update value for center of diamond
					map[x][y] = avg;

					if (x == 0)
						map[size - 1][y] = avg;
					if (y == 0)
						map[x][size - 1] = avg;
				}
			}
		}
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map.length; j++) {
				map[i][j] = (float) (map[i][j] <= 0 ? -Math.sqrt(Math
						.abs(map[i][j])) : map[i][j]);
			}
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