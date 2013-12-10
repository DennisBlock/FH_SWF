package de.fhswf.randomwalk;

import java.util.Vector;

public class RandomWalk {
	private static final int MAXITER = 1;

	private double f(Vector<Double> x) {
		int n = x.size();
		double ret = 0.0;
		for(int i = 1; i < n; i++) {
			ret += 100 * Math.sin(300 * x.get(i) - 100) * Math.sin(300 * x.get(i) - 100);
		}
		
		for(int i = 1; i < n; i++){
			ret += (3 * x.get(i) - 1) * (3 * x.get(i) - 1);
		}
		
		return ret;
	}
	

	public static void main(String[] args) {

	}

}
