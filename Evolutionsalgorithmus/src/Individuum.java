import java.util.Random;
import java.util.Vector;

/**
 * @author Natalie Block
 * 
 */
public class Individuum {

	private Vector<Double> x;
	private double fitWert;

	public Individuum() {
		x = new Vector<Double>();
	}

	public Individuum(int n) {
		this.x = rndX(n);
		
		for (int j = 0; j < x.size(); j++) {
			 this.fitWert += (100 * Math.sin(300 * x.get(j) - 100) * Math
			 .sin(300 * x.get(j) - 100))
			 + ((3 * x.get(j) - 1) * (3 * x.get(j) - 1));
		}
	}
	public Vector<Double> rndX(int n) {
		x = new Vector<Double>();
		for (int i = 0; i < n; i++) {
			 x.add((Math.random() * 2) - 1);
		}
		return x;
	}
	/**
	 * @return the x
	 */
	public Vector<Double> getX() {
		return x;
	}
	/**
	 * @return the fitWert
	 */
	public Double getFitWert() {
		return fitWert;
	}
	/**
	 * @param fitWert
	 *            the fitWert to set
	 */
	public void calcFitWert() {
		for (int j = 0; j < x.size(); j++) {
			fitWert +=  100
					* (Math.sin(300 * x.get(j) - 100) * Math.sin(300 * x
							.get(j) - 100))
					+ ((3 * x.get(j) - 1) * (3 * x.get(j) - 1));
		}
//		 fitWert *= (-1);
	}

	/**
	 * @param x
	 *            the x to set
	 */
	public void setX(Vector<Double> x) {
		this.x = x;
	}

	@Override
	public String toString() {
		return "x = " + x + " \nfitWert = " + fitWert;
	}
}
