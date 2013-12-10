/**
 * 
 */
package de.fhswf.staticclassification;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

/**
 * @author Natalie Block
 * 
 */
public final class RepFactory
{

    public enum PersonKlasifikator
    {
	Normal(0), Overweight(1);

	private int klassifikation;

	PersonKlasifikator(int klassifikation)
	{
	    this.klassifikation = klassifikation;
	}

	public int getKlassifikation()
	{
	    return klassifikation;
	}
    }

    public enum RepType
    {
	Avarage, Median, MC, MD
    }

    private RepFactory()
    {
    }

    public static Rep createRep(RepType repType)
    {
	switch (repType)
	{
	    case Avarage:
		return new AvarageRep();
	    case Median:
		return new MedianRep();
	    case MC:
		return new MCRep();
	    case MD:
		return new MDRep();
	    default:
		throw new IllegalArgumentException("Falscher Rep Typ");

	}
    }

    public interface Rep
    {
	public Person findRep(PersonKlasifikator personKlasifikator,
		DatabaseArrayList database);
    }

    public static class MedianRep implements Rep
    {

	@Override
	public Person findRep(PersonKlasifikator personKlasifikator,
		DatabaseArrayList database)
	{

	    ArrayList<Person> selectedPerson = database
		    .selectPersonOf(personKlasifikator);

	    // Sortieren

	    Comparator<Person> cmp = new Comparator<Person>()
	    {
		@Override
		public int compare(Person o1, Person o2)
		{
		    return o1.getGroesse().compareTo(o2.getGroesse());
		}
	    };

	    Collections.sort(selectedPerson, cmp);

	    if ((selectedPerson.size() % 2) == 0)
	    {
		return selectedPerson.get(selectedPerson.size() / 2);
	    }
	    else
	    {
		return selectedPerson.get(selectedPerson.size() / 2 + 1);
	    }
	}

    }

    public static class AvarageRep implements Rep
    {

	@Override
	public Person findRep(PersonKlasifikator personKlasifikator,
		DatabaseArrayList database)
	{

	    double averageGewicht = 0;
	    double averageGroesse = 0;

	    ArrayList<Person> selectedPerson = database
		    .selectPersonOf(personKlasifikator);

	    // arithmetische Mittel
	    for (int i = 0; i < selectedPerson.size(); i++)
	    {
		averageGewicht += selectedPerson.get(i).getGewicht();
		averageGroesse += selectedPerson.get(i).getGroesse();
	    }

	    averageGewicht /= selectedPerson.size();
	    averageGroesse /= selectedPerson.size();

	    NumberFormat nf = NumberFormat.getInstance(Locale.US);
	    DecimalFormat format = (DecimalFormat) nf;
	    format.applyPattern("###.##");

	    averageGroesse = Double.valueOf(format.format(averageGroesse));
	    averageGewicht = Double.valueOf(format.format(averageGewicht));

	    return new Person(averageGroesse, averageGewicht,
		    personKlasifikator);
	}

    }

    private static class MCRep implements Rep
    {

	@Override
	public Person findRep(PersonKlasifikator personKlasifikator,
		DatabaseArrayList database)
	{
	    switch (personKlasifikator)
	    {
		case Normal:
		    return new Person(176, 72,
			    personKlasifikator.getKlassifikation());
		case Overweight:
		    return new Person(172, 76,
			    personKlasifikator.getKlassifikation());
		default:
		    throw new IllegalArgumentException("illegal argument: "
			    + personKlasifikator);
	    }
	}

    }

    private static class MDRep implements Rep
    {

	@Override
	public Person findRep(PersonKlasifikator personKlasifikator,
		DatabaseArrayList database)
	{
	    switch (personKlasifikator)
	    {
		case Normal:
		    return new Person(174, 70,
			    personKlasifikator.getKlassifikation());
		case Overweight:
		    return new Person(174, 78,
			    personKlasifikator.getKlassifikation());
		default:
		    throw new IllegalArgumentException("illegal argument: "
			    + personKlasifikator);
	    }
	}

    }

}