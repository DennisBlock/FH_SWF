package aufgabe1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import aufgabe1.DistanceCalculaterFactory.DistanceCalculater;
import aufgabe1.DistanceCalculaterFactory.DistanceType;
import aufgabe1.util.Pair;
import de.erichseifert.gral.data.DataTable;

public class BMI
{

    private static final boolean HEIGHT_IN_M = true;
    private static final boolean HEIGHT_IN_CM = false;
    
    private static final boolean GENERATE_STANDARD = false;
    private static final boolean GENERATE_WITH_RANDOM = true;

    private ArrayList<Model> list;

    private double averageWeight = 0.0;
    private double averageHeight = 0.0;
    private double standartDeviationWeight = 1.0;
    private double standartDeviationHeight = 1.0;

    public static void main(String[] args)
    {
	BMI bmi = new BMI();
	bmi.generate(HEIGHT_IN_CM, GENERATE_STANDARD);

	DataTable data0 = bmi.toDataTable(0, 100);
	DataTable data1 = bmi.toDataTable(101, 199);

	LinePlotTest frame = new LinePlotTest(data0, data1);
	// frame.setVisible(true);

	// Blatt 2 - Aufgabe 1
	Model paul = new Model(175, 89, -1);
	Model neigbor = bmi
		.calcNeighbor(paul, 1,
			DistanceCalculaterFactory.create(DistanceType.D2))
		.get(0).getRight();

	@SuppressWarnings("unchecked")
	DataTable data3 = new DataTable(Double.class, Double.class);
	data3.add(neigbor.getGroesse(), neigbor.getGewicht());
	frame.displayData(data3);

	// Blatt 2 - Aufgabe 2
	System.out.println("Aufgabe 2");
	System.out.println("============");
	bmi.print();

	// Blatt 2 - Aufgabe 3
	System.out.println("Aufgabe 3");
	System.out.println("============");
	bmi.reset();
	bmi.generate(HEIGHT_IN_M, GENERATE_STANDARD);
	bmi.print();
	System.out.println();

	// Blatt 2 - Aufgabe 4
	System.out.println("Aufgabe 4");
	System.out.println("============");
	bmi.normalize();
	bmi.print();
	bmi.writeToFile();

	// Blatt 2 - Aufgabe 5
	System.out.println("Aufgabe 5");
	System.out.println("============");

	bmi.reset();
	bmi.generate(HEIGHT_IN_M, GENERATE_STANDARD);
	
	double avg[] = bmi.calcAverage(0);
	System.out.println("Mittelwert Klasse 0");
	System.out.println("Hoehe: " + avg[0]);
	System.out.println("Groesse: " + avg[1]);
	System.out.println();
	
	avg = bmi.calcAverage(1);
	System.out.println("Mittelwert Klasse 1");
	System.out.println("Hoehe: " + avg[0]);
	System.out.println("Groesse: " + avg[1]);
	bmi.print5();
	
	// Blatt 2 - Aufgabe 6
	System.out.println("Aufgabe 6");
	System.out.println("============");
	bmi.reset();
	bmi.generate(HEIGHT_IN_M, GENERATE_WITH_RANDOM);
	bmi.print6();
    }

    private double[] calcAverage(int c)
    {
	double average[] = new double[2];
	double averageHeight = 0.0;
	double averageWeight = 0.0;
	int count = 0;
	
	switch (c)
	{
	    case 0:
		for(int i = 0; i < 100; i++)
		{
		    Model m = list.get(i);
		    if(m.getKlassifikation() == 0)
		    {
			averageHeight += m.getGroesse();
			averageWeight += m.getGewicht();
			count++;
		    }
		}
		break;
	    case 1:
		for(int i = 0; i < 100; i++)
		{
		    Model m = list.get(i);
		    if(m.getKlassifikation() == 1)
		    {
			averageHeight += m.getGroesse();
			averageWeight += m.getGewicht();
			count++;
		    }
		}
		break;
	    default:
		throw new RuntimeException("invalid parameter: " + c);
	}
	
	average[0] = averageHeight / count;
	average[1] = averageWeight / count;
	
	return average;
    }

    public void normalize()
    {
	averageHeight = 0;
	averageWeight = 0;

	for (int i = 0; i < 100; i++)
	{
	    averageHeight += list.get(i).getGroesse();
	    averageWeight += list.get(i).getGewicht();
	}

	averageHeight /= 100;
	averageWeight /= 100;

	for (int i = 0; i < 200; i++)
	{
	    Model m = list.get(i);
	    m.setGewicht(m.getGewicht() - averageWeight);
	    m.setGroesse(m.getGroesse() - averageHeight);
	}

	double varianzHeight = 0.0;
	double varianzWeight = 0.0;

	for (int i = 0; i < 100; i++)
	{
	    varianzHeight += (list.get(i).getGroesse())
		    * (list.get(i).getGroesse());
	    varianzWeight += (list.get(i).getGewicht())
		    * (list.get(i).getGewicht());
	}

	varianzHeight /= 100;
	varianzWeight /= 100;

	standartDeviationHeight = Math.sqrt(varianzHeight);
	standartDeviationWeight = Math.sqrt(varianzWeight);

	for (int i = 0; i < 200; i++)
	{
	    Model m = list.get(i);
	    m.setGewicht(m.getGewicht() / standartDeviationWeight);
	    m.setGroesse(m.getGroesse() / standartDeviationHeight);
	}
    }

    public void print()
    {
	int[] k = { 1, 3, 5, 9, 15, 31 };

	for (int i = 0; i < k.length; i++)
	{
	    System.out.println("K = " + k[i]);
	    double d = kNN(k[i], DistanceType.D1);
	    System.out.println("d1: " + d);
	    d = kNN(k[i], DistanceType.D2);
	    System.out.println("d2: " + d);
	    d = kNN(k[i], DistanceType.DInfinit);
	    System.out.println("dinf: " + d);
	    System.out.println();
	}
    }
    
    public void print5()
    {
	int[] k = { 1, 3, 5, 9, 15, 31 };
	
	for (int i = 0; i < k.length; i++)
	{
	    System.out.println("K = " + k[i]);
	    double d = kNN(k[i], DistanceType.D1_A5);
	    System.out.println("d1: " + d);
	    d = kNN(k[i], DistanceType.D2_A5);
	    System.out.println("d2: " + d);
	    d = kNN(k[i], DistanceType.DInfinit_A5);
	    System.out.println("dinf: " + d);
	    System.out.println();
	}
    }
    
    public void print6()
    {
	int[] k = { 1, 3, 5, 9, 15, 31 };
	
	for (int i = 0; i < k.length; i++)
	{
	    System.out.println("K = " + k[i]);
	    double d = kNN(k[i], DistanceType.D1_A6);
	    System.out.println("d1: " + d);
	    d = kNN(k[i], DistanceType.D2_A6);
	    System.out.println("d2: " + d);
	    d = kNN(k[i], DistanceType.DInfinit_A6);
	    System.out.println("dinf: " + d);
	    System.out.println();
	}
    }

    public double kNN(int k, DistanceType d)
    {
	int errors = 0;
	int successes = 0;
	List<Pair> pairs = null;
	DistanceCalculater calc = DistanceCalculaterFactory.create(d);
	for (int i = 100; i < list.size(); i++)
	{
	    Model m = list.get(i);
	    pairs = calcNeighbor(m, k, calc);
	    int class0 = 0;
	    int class1 = 0;
	    for (Pair p : pairs)
	    {
		if (p.getRight().getKlassifikation() == 0)
		    class0++;
		else
		    class1++;
	    }
	    if (class0 > class1)
	    {
		if (m.getKlassifikation() == 0)
		    successes++;
		else
		    errors++;
	    }
	    else
	    {
		if (m.getKlassifikation() == 1)
		    successes++;
		else
		    errors++;
	    }
	}
	return 100 / (successes + errors) * errors;
    }

    public List<Pair> calcNeighbor(Model data, int k,
	    DistanceCalculater distance)
    {
	List<Pair> m = new ArrayList<Pair>();
	for (int i = 0; i < 100; i++)
	{
	    Model temp = list.get(i);
	    double d2 = distance.calculate(temp, data);
	    m.add(new Pair(d2, temp));
	}

	Collections.sort(m);

	return m.subList(0, k);
    }

    public void generate(boolean inMeter, boolean random)
    {
	list = new ArrayList<Model>();

	for (int i = 0; i < 200; i++)
	{
	    double groesse = 162 + (7 * i % 19) + (5 * i % 7) * (i % 3);
	    double gewicht = (groesse - 100 + 2 * (i % 2 - 0.5)
		    * (3 * i % 11 + 5 * i % 13 + 1));
	    int klassifikation = i % 2;
	    if (i % 19 == 0 && i != 0)
	    {
		klassifikation = 1 - klassifikation;
	    }

	    if (inMeter)
		groesse /= 100;
	    
	    Model model = null;
	    
	    if(random)
	    {
		Random rand = new Random();
		int r = rand.nextInt() % 10000;
		model = new Model(groesse, gewicht, klassifikation, r);
	    }
	    else
		model = new Model(groesse, gewicht, klassifikation);
	    
	    list.add(model);
	}

	writeToFile();
    }

    @SuppressWarnings("unchecked")
    public DataTable toDataTable(int start, int end)
    {
	DataTable data = new DataTable(Double.class, Double.class);
	for (int i = start; i <= end; i++)
	{
	    Model m = list.get(i);
	    data.add(m.getGroesse(), m.getGewicht());
	}
	return data;
    }

    public void reset()
    {
	list.clear();
    }

    public void writeToFile()
    {
	FileWriter fileWriter = null;

	try
	{
	    fileWriter = new FileWriter("Bla");
	}
	catch (IOException e1)
	{
	    e1.printStackTrace();
	}
	try
	{
	    for (int i = 0; i < 200; i++)
		fileWriter.write(i + " " + list.get(i).toString() + "\n");
	}
	catch (IOException e)
	{
	    e.printStackTrace();
	}
	finally
	{
	    try
	    {
		fileWriter.close();
	    }
	    catch (IOException e)
	    {
		e.printStackTrace();
	    }
	}
    }

}
