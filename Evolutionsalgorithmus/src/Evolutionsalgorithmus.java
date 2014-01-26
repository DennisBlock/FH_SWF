import java.util.Collections;
import java.util.Comparator;
import java.util.Random;
import java.util.Vector;
/**
 * @author Natalie Block
 * 
 */
public class Evolutionsalgorithmus {
	private int n;
	private int anzahlEltern;
	private int anzahlKinder;
	private int anzahlElite;
	private int turnierPara;
	private int maxiter;
	private int mutationsPara;

	private Vector<Individuum> elternPopulation;
	private Vector<Individuum> kinderPopulation;

	public Evolutionsalgorithmus(int n, int anzahlEltern, int anzahlKinder,
			int anzahlElite, int turnierPara, int maxiter, int mutationsPara) {
		this.n = n;
		this.anzahlEltern = anzahlEltern;
		this.anzahlKinder = anzahlKinder;
		this.anzahlElite = anzahlElite;
		this.turnierPara = turnierPara;
		this.maxiter = maxiter;
		this.mutationsPara = mutationsPara;

		elternPopulation = new Vector<Individuum>();
		kinderPopulation = new Vector<Individuum>();
	}

	public void start() {

		for (int i = 0; i < anzahlEltern; i++) {
			elternPopulation.add(new Individuum(n));
		}

		for (int i = 0; i < maxiter; i++) {
			kinderPopulation.clear();
			for (int x = 0; x < anzahlKinder; x++) {
				
				Individuum vater = turnier(elternPopulation);
				Individuum mutter = turnier(elternPopulation);
				Individuum kind = rekombination(vater, mutter);
				
				// jeder 30te ist ein xman
				if ((x % mutationsPara) == 0) {
					mutation(kind);
				}
				kinderPopulation.add(kind);
			}
			selektion();
		}
		System.out.println("Bestes Indivuduum:\n"
				+ bestIndividuum());
		
//		System.out.println("Anz="+elternPopulation.size() +"\n "+elternPopulation.toString());
//		System.out.println("Anz="+kinderPopulation.size() +" "+kinderPopulation.toString());
	}

	private String bestIndividuum() {
		Individuum invictus = elternPopulation.get(getRandom(0, anzahlEltern));
		
		for(Individuum i : elternPopulation)
		{
			if(i.getFitWert() < invictus.getFitWert())
			{
				invictus = i;
			}
		}
		
		return invictus.toString();
	}

	private void mutation(Individuum kind) {
		for (int i = 0; i < n; i++) {
			kind.getX().set(i, (Math.random() * 2) - 1);
		}
		kind.calcFitWert();
	}

	private void selektion() {
		Vector<Individuum> eliteEltern = getElite(elternPopulation, anzahlElite);
		Vector<Individuum> eliteKinder = getElite(kinderPopulation, anzahlEltern - anzahlElite);
		
		elternPopulation.clear();
		elternPopulation.addAll(eliteEltern);
		elternPopulation.addAll(eliteKinder);
	}

	private Vector<Individuum> getElite(Vector<Individuum> population, int anz) {
		Comparator<Individuum> comp = new Comparator<Individuum>() {
			public int compare(Individuum o1, Individuum o2) {
				return Double.compare(o1.getFitWert(), o2.getFitWert());
			}
		};

		Collections.sort(population, comp);

		Vector<Individuum> elitaerePopulation = new Vector<Individuum>();
		for (int i = 0; i < anz; i++) {
			elitaerePopulation.add(population.get(i));
		}
		return elitaerePopulation;
	}

	private Individuum rekombination(Individuum vater, Individuum mutter) {
		Individuum kind = new Individuum();
		Vector<Double> rekombination = new Vector<Double>();
		for (int i = 0; i < n; i++) {
			rekombination.add(i,
					(vater.getX().get(i) + mutter.getX().get(i) / 2));
		}

		kind.setX(rekombination);
		kind.calcFitWert();
		return kind;
	}

	public Individuum turnier(Vector<Individuum> population) {

		Individuum champion = population.get(getRandom(0, population.size()));

		for (int i = 0; i < turnierPara; i++) {
			Individuum konkurrent;
			do {
				konkurrent = population.get(getRandom(0, population.size()));
			} while (konkurrent != champion);
			
			if (konkurrent.getFitWert() < champion.getFitWert()) {
				champion = konkurrent;
			}
		}
		return champion;
	}

	public static int getRandom(int low, int high) {
		return (int) (Math.random() * (high - low) + low);
	}

}
