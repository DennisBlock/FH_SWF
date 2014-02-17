package application;

import java.util.Random;

public class DiamondSquare {

	public static void main(String[] args) {
//		print();
		diamond2();
	}

	public static void print() {
		// print out the data
		for (float[] row : diamondSquare(2, 1, 500)) {
			for (float d : row) {
				System.out.printf("%8.3f ", d);
			}
			System.out.println();
		}
	}
	
	public static double[][] diamond2() {
		//size of grid to generate, note this must be a
		//value 2^n+1
		final int DATA_SIZE = 1025;
		//an initial seed value for the corners of the data
		final double SEED = 1000.0;
		double[][] data = new double[DATA_SIZE][DATA_SIZE];

		double h = 1.0;//the range (-h -> +h) for the average offset
		Random r = new Random();//for the new value in range of h
		//side length is distance of a single square side
		//or distance of diagonal in diamond
		//seed the data
		data[0][0] = data[0][DATA_SIZE-1] = data[DATA_SIZE-1][0] = 
				data[DATA_SIZE-1][DATA_SIZE-1] = r.nextDouble();
		for(int sideLength = DATA_SIZE-1;
		    //side length must be >= 2 so we always have
		    //a new value (if its 1 we overwrite existing values
		    //on the last iteration)
		    sideLength >= 2;
		    //each iteration we are looking at smaller squares
		    //diamonds, and we decrease the variation of the offset
		    sideLength /=2, h/= 2.0){
		  //half the length of the side of a square
		  //or distance from diamond center to one corner
		  //(just to make calcs below a little clearer)
		  int halfSide = sideLength/2;

		  //generate the new square values
		  for(int x=0;x<DATA_SIZE-1;x+=sideLength){
		    for(int y=0;y<DATA_SIZE-1;y+=sideLength){
		      //x, y is upper left corner of square
		      //calculate average of existing corners
		      double avg = data[x][y] + //top left
		      data[x+sideLength][y] +//top right
		      data[x][y+sideLength] + //lower left
		      data[x+sideLength][y+sideLength];//lower right
		      avg /= 4.0;

		      double temp = (avg + (r.nextDouble()*2*h) - h);
		      //center is average plus random offset
		      data[x+halfSide][y+halfSide] = 
		    //We calculate random value in range of 2h
		    //and then subtract h so the end value is
		    //in the range (-h, +h)
		    temp > 1 ? 1 : temp;
		    }
		  }

		  //generate the diamond values
		  //since the diamonds are staggered we only move x
		  //by half side
		  //NOTE: if the data shouldn't wrap then x < DATA_SIZE
		  //to generate the far edge values
		  for(int x=0;x<DATA_SIZE-1;x+=halfSide){
		    //and y is x offset by half a side, but moved by
		    //the full side length
		    //NOTE: if the data shouldn't wrap then y < DATA_SIZE
		    //to generate the far edge values
		    for(int y=(x+halfSide)%sideLength;y<DATA_SIZE-1;y+=sideLength){
		      //x, y is center of diamond
		      //note we must use mod  and add DATA_SIZE for subtraction 
		      //so that we can wrap around the array to find the corners
		      double avg = 
		        data[(x-halfSide+DATA_SIZE)%DATA_SIZE][y] + //left of center
		        data[(x+halfSide)%DATA_SIZE][y] + //right of center
		        data[x][(y+halfSide)%DATA_SIZE] + //below center
		        data[x][(y-halfSide+DATA_SIZE)%DATA_SIZE]; //above center
		      avg /= 4.0;

		      //new value = average plus random offset
		      //We calculate random value in range of 2h
		      //and then subtract h so the end value is
		      //in the range (-h, +h)
		      avg = avg + (r.nextDouble()*2*h) - h;
		      //update value for center of diamond
		      data[x][y] = avg;

		      //wrap values on the edges, remove
		      //this and adjust loop condition above
		      //for non-wrapping values.
		      if(x == 0)  data[DATA_SIZE-1][y] = avg;
		      if(y == 0)  data[x][DATA_SIZE-1] = avg;
		    }
		  }
		}

//		//print out the data
//		for(double[] row : data){
//		  for(double d : row){
//		    System.out.printf("%8.3f ", d);
//		  }
//		  System.out.println();
//		}
		
		return data;
	}
	public static double[][] normalize(double min, double max, double[][] map)
    {
//        double den = max - min;
		
		NormUtil norm = new NormUtil(1, -1.0, 1.0, 0.0);
		for(int i=0;i<map.length;i++)
		{
			for(int j=0;j<map.length;j++)
			{
				map[i][j] = norm.normalize(map[i][j]);
			}
		}
		
        return map;
    }
	public static float[][] diamondSquare(int size, float rough, int seed) {

		Random random = new Random();

		int depth = size - 1;

		float[][] dSMap = new float[(int) Math.round(Math.pow(2, size) + 1)]
				[(int) Math.round(Math.pow(2, size) + 1)];

		dSMap[0][0] = rough * (2 * random.nextFloat() - 1);
		dSMap[0][size - 1] = rough * (2 * random.nextFloat() - 1);
		dSMap[size - 1][0] = rough * (2 * random.nextFloat() - 1);
		dSMap[size - 1][size - 1] = rough * (2 * random.nextFloat() - 1);

		int iteration;

		boolean putX, putZ;

		while (depth < -1) {

			iteration = (int) Math.round(Math.pow(2, depth));
			putX = false;
			for (int i = 0; i < dSMap.length; i += iteration) {
				putZ = false;
				for (int j = 0; j < dSMap[0].length; j += iteration) {
					if (putX == true && putZ == true) {
						// put diamond
						dSMap[i][j] = (dSMap[i - (iteration)][j - (iteration)]
								+ dSMap[i + (iteration)][j - (iteration)]
								+ dSMap[i - (iteration)][j + (iteration)]
								+ dSMap[i + (iteration)][j + (iteration)])
								/ 4 + rough * (2f * random.nextFloat() - 1);
					}
					if (putX != putZ) {
						// put squares
						if (putX == true) {
							dSMap[i][j] = (dSMap[i - (iteration)][j]
									+ dSMap[i + (iteration)][j])
									/ 2 + rough * (2f * random.nextFloat() - 1);
						} else {
							dSMap[i][j] = (dSMap[i][j - (iteration)] + 
									dSMap[i][j+ (iteration)])
									/ 2 + rough * (2f * random.nextFloat() - 1); 
						}
					}
					putZ = !putZ;
				}
				putX = !putX;
			}
			rough /= 2;
			depth--;
		}
		for (int i = 0; i < dSMap.length; i++) {
			for (int j = 0; j < dSMap.length; j++) {
				dSMap[i][j] = (float) (dSMap[i][j] <= 0 ? -Math.sqrt(Math
						.abs(dSMap[i][j])) : dSMap[i][j]);
			}
		}
		return dSMap;
	}
}