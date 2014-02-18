package de.fhswf.terrain.generator;

import java.util.Random;

public class DiamondSquare {
	private double[][] map;
	private int size;
	private double h; // the range (-h -> +h) for the average offset

	private Random random;

	private Normalizer normalizer;

	public DiamondSquare(int size, double h) {
		if (!isPowerOfTwo(size - 1))
			throw new IllegalArgumentException("Die Groesse muss 2^n+1 sein.");
		// Check für zu hohe groesse -> zu wenig memory(?)
		// if()

		map = new double[size][size];
		this.size = size;
		this.h = h;

		random = new Random();
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

		// side length is distance of a single square side
		// or distance of diagonal in diamond
		// seed the data
		map[0][0] = random.nextDouble();
		map[0][size - 1] = random.nextDouble();
		map[size - 1][0] = random.nextDouble();
		map[size - 1][size - 1] = random.nextDouble();
		
		for (int sideLength = size - 1;
		// side length must be >= 2 so we always have
		// a new value (if its 1 we overwrite existing values
		// on the last iteration)
		sideLength >= 2;
		// each iteration we are looking at smaller squares
		// diamonds, and we decrease the variation of the offset
		sideLength /= 2, h /= 2.0) {
			// half the length of the side of a square
			// or distance from diamond center to one corner
			// (just to make calcs below a little clearer)
			int halfSide = sideLength / 2;

			// generate the new square values
			for (int x = 0; x < size - 1; x += sideLength) {
				for (int y = 0; y < size - 1; y += sideLength) {
					// x, y is upper left corner of square
					// calculate average of existing corners
					double avg = map[x][y] + // top left
							map[x + sideLength][y] + // top right
							map[x][y + sideLength] + // lower left
							map[x + sideLength][y + sideLength];// lower right
					avg /= 4.0;

					double temp = (avg + (random.nextDouble() * 2 * h) - h);
					// center is average plus random offset
					map[x + halfSide][y + halfSide] =
					// We calculate random value in range of 2h
					// and then subtract h so the end value is
					// in the range (-h, +h)
					temp > 1 ? 1 : temp;
				}
			}

			// generate the diamond values
			// since the diamonds are staggered we only move x
			// by half side
			// NOTE: if the data shouldn't wrap then x < size
			// to generate the far edge values
			for (int x = 0; x < size - 1; x += halfSide) {
				// and y is x offset by half a side, but moved by
				// the full side length
				// NOTE: if the data shouldn't wrap then y < DATA_SIZE
				// to generate the far edge values
				for (int y = (x + halfSide) % sideLength; y < size - 1; y += sideLength) {
					// x, y is center of diamond
					// note we must use mod and add DATA_SIZE for subtraction
					// so that we can wrap around the array to find the corners
					double avg = map[(x - halfSide + size) % size][y] + // left
																		// of
																		// center
							map[(x + halfSide) % size][y] + // right of center
							map[x][(y + halfSide) % size] + // below center
							map[x][(y - halfSide + size) % size]; // above
																	// center
					avg /= 4.0;

					// new value = average plus random offset
					// We calculate random value in range of 2h
					// and then subtract h so the end value is
					// in the range (-h, +h)
					avg = avg + (random.nextDouble() * 2 * h) - h;
					// update value for center of diamond
					map[x][y] = avg;

					// wrap values on the edges, remove
					// this and adjust loop condition above
					// for non-wrapping values.
					if (x == 0)
						map[size - 1][y] = avg;
					if (y == 0)
						map[x][size - 1] = avg;
				}
			}
		}
	}

	public int getSize() {
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

	public double[][] getHeightmap() {
		return map;
	}
}