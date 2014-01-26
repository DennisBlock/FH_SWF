/**
 * @author Natalie Block
 * 
 */
public class Initialisierung {

	static int N = 4;
	static int AnzahlEltern = 100;
	static int AnzahlKinder = 200;
	static int AnzahlElite = 20;
	static int TurnierPara = 10;  // turniergröße/Selektionsdruck
	static int Maxiter = 50;
	static int MutationsPara = 30;
	/**
	 * @param args 
	 */
	public static void main(String[] args) {

		Evolutionsalgorithmus evolutionsalgorithmus = new Evolutionsalgorithmus(
				N, AnzahlEltern, AnzahlKinder, AnzahlElite, TurnierPara,
				Maxiter, MutationsPara);
		evolutionsalgorithmus.start();
	}
}
