package de.fhswf.staticclassification;

import de.fhswf.staticclassification.DistanceCalculaterFactory.DistanceCalculater;
import de.fhswf.staticclassification.DistanceCalculaterFactory.DistanceType;
import de.fhswf.staticclassification.RepFactory.PersonKlasifikator;
import de.fhswf.staticclassification.RepFactory.Rep;
import de.fhswf.staticclassification.RepFactory.RepType;

/**
 * @author Natalie Block
 * 
 */
public class MaximumCorrelationClassifier
{

    private DatabaseArrayList database;

    public MaximumCorrelationClassifier()
    {
	database = new DatabaseArrayList();
    }

    public double calculateErrorRate(RepType repType, DistanceType type)
    {
	DistanceCalculater distanceCalc = DistanceCalculaterFactory
		.create(type);
	Rep rep = RepFactory.createRep(repType);
	Person normal = rep.findRep(PersonKlasifikator.Normal, database);
	Person overweight = rep
		.findRep(PersonKlasifikator.Overweight, database);
//	System.out.print("normal: " + normal);
//	System.out.println("\toverweight: " + overweight);

	int errors = 0;
	int successes = 0;
	for (int i = 100; i < database.size(); i++)
	{
	    Person m = database.get(i);
	    double distanceNormal = distanceCalc.calculate(normal, m);
	    double distanceOverweight = distanceCalc.calculate(overweight, m);
	    if (distanceNormal < distanceOverweight)
	    {
		if (m.getKlassifikation() == normal.getKlassifikation())
		    successes++;
		else
		    errors++;
	    }
	    else
	    {
		if (m.getKlassifikation() == overweight.getKlassifikation())
		    successes++;
		else
		    errors++;
	    }

	}
	return 100.0 / (successes + errors) * errors;
    }

    public void print(DistanceType type)
    {

	StringBuffer buffer = new StringBuffer();
	buffer.append(calculateErrorRate(RepType.Avarage, type) + "\t");
	buffer.append(calculateErrorRate(RepType.Median, type) + "\t");
	buffer.append(calculateErrorRate(RepType.MC, type) + "\t");
	buffer.append(calculateErrorRate(RepType.MD, type));
	System.out.println(buffer);
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
	MaximumCorrelationClassifier classifier = new MaximumCorrelationClassifier();
	System.out.println("\t\ta)\tb)\tc)\td)");

	System.out.print("l = 1\t\t");
	classifier.print(DistanceType.D1);
	System.out.print("l = 2\t\t");
	classifier.print(DistanceType.D2);
	System.out.print("l = Inf\t\t");
	classifier.print(DistanceType.DInfinit);
    }

}