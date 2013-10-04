package aufgabe1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import aufgabe1.DistanceCalculaterFactory.DistanceCalculater;
import aufgabe1.DistanceCalculaterFactory.DistanceType;
import aufgabe1.util.Pair;
import de.erichseifert.gral.data.DataTable;

public class BMI
{

    private ArrayList<Model> list;

    private Model model;
    private double groesse;
    private double gewicht;
    private int klassifikation;

    public static void main(String[] args)
    {
	BMI bmi = new BMI();
	bmi.generate();

	DataTable data0 = bmi.toDataTable(0, 100);
	DataTable data1 = bmi.toDataTable(101, 199);

	LinePlotTest frame = new LinePlotTest(data0, data1);
	frame.setVisible(true);

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
	double d = bmi.kNN(1, DistanceType.D2);
	System.out.println(d);
	d = bmi.kNN(31, DistanceType.DInfinit);
	System.out.println(d);

    }

    public double kNN(int k, DistanceType d)
    {
	int errors = 0;
	int successes = 0;
	List<Pair> pairs = null;
	DistanceCalculater calc = DistanceCalculaterFactory.create(d);
	for (int i = 101; i < list.size(); i++)
	{
	    Model m = list.get(i);
	    pairs = calcNeighbor(m, k, calc);
	    int index = k % 2 == 0 ? k / 2 : k / 2 + 1;
	    if (m.getKlassifikation() == pairs.get(index).getRight()
		    .getKlassifikation())
		successes++;
	    else
		errors++;
	}

	return (successes+errors)/100.0*errors;
    }

    public List<Pair> calcNeighbor(Model data, int k,
	    DistanceCalculater distance)
    {
	List<Pair> m = new ArrayList<Pair>();
	for (int i = 0; i < 101; i++)
	{
	    Model temp = list.get(i);
	    double d2 = distance.calculate(temp, data);

	    m.add(new Pair(d2, temp));
	}

	Collections.sort(m);

	return m.subList(0, k + 1);
    }

    public void generate()
    {
	list = new ArrayList<Model>();

	for (int i = 0; i < 200; i++)
	{
	    groesse = 162 + (7 * i % 19) + (5 * i % 7) * (i % 3);
	    gewicht = (groesse - 100 + 2 * (i % 2 - 0.5)
		    * (3 * i % 11 + 5 * i % 13 + 1));
	    klassifikation = i % 2;
	    if (i % 19 == 0 && i != 0)
	    {
		klassifikation = 1 - klassifikation;
	    }

	    model = new Model(groesse, gewicht, klassifikation);
	    list.add(model);
	}

	write(list);
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

    private static void write(ArrayList<Model> list)
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
