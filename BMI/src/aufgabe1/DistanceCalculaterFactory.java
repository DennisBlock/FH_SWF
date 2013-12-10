package aufgabe1;

public class DistanceCalculaterFactory
{
    public enum DistanceType
    {
	D1, D2, DInfinit, D1_A5, D2_A5, DInfinit_A5, D1_A6, D2_A6, DInfinit_A6
    }

    private DistanceCalculaterFactory()
    {
    }

    public static DistanceCalculater create(DistanceType d)
    {
	switch (d)
	{
	    case D1:
		return new D1DistanceCalculater();
	    case D2:
		return new D2DistanceCalculater();
	    case DInfinit:
		return new DInfDistanceCalculater();
	    case D1_A5:
		return new D1A5DistanceCalculater();
	    case D2_A5:
		return new D2A5DistanceCalculater();
	    case DInfinit_A5:
		return new DInfA5DistanceCalculater();
	    case D1_A6:
		return new D1A6DistanceCalculater();
	    case D2_A6:
		return new D2A6DistanceCalculater();
	    case DInfinit_A6:
		return new DInfA6DistanceCalculater();
	    default:
		throw new RuntimeException("illegal argument: " + d);
	}
    }

    public static interface DistanceCalculater
    {
	public double calculate(Person m1, Person m2);
    }

    public static class D1DistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math.abs(m2.getGroesse() - m1.getGroesse())
		    + Math.abs(m2.getGewicht() - m1.getGewicht());
	}
    }

    public static class D2DistanceCalculater implements DistanceCalculater
    {

	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math
		    .sqrt(((m2.getGewicht() - m1.getGewicht()) * (m2
			    .getGewicht() - m1.getGewicht()))
			    + ((m2.getGroesse() - m1.getGroesse()) * (m2
				    .getGroesse() - m1.getGroesse())));
	}
    }

    public static class DInfDistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    double temp1 = Math.abs(m2.getGroesse() - m1.getGroesse());
	    double temp2 = Math.abs(m2.getGewicht() - m1.getGewicht());
	    return Math.max(temp1, temp2);
	}
    }

    public static class D1A5DistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math.abs(m2.getGewicht() - m1.getGewicht());
	}
    }

    public static class D2A5DistanceCalculater implements DistanceCalculater
    {

	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math.sqrt(((m2.getGewicht() - m1.getGewicht()) * (m2
		    .getGewicht() - m1.getGewicht())));
	}
    }

    public static class DInfA5DistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    double temp2 = Math.abs(m2.getGewicht() - m1.getGewicht());
	    return temp2;
	}
    }

    public static class D1A6DistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math.abs(m2.getGroesse() - m1.getGroesse())
		    + Math.abs(m2.getGewicht() - m1.getGewicht())
		    + Math.abs(m2.getRandom() - m1.getRandom());
	}
    }

    public static class D2A6DistanceCalculater implements DistanceCalculater
    {

	@Override
	public double calculate(Person m1, Person m2)
	{
	    return Math
		    .sqrt(((m2.getGewicht() - m1.getGewicht()) * (m2
			    .getGewicht() - m1.getGewicht()))
			    + ((m2.getGroesse() - m1.getGroesse()) * (m2
				    .getGroesse() - m1.getGroesse()))
			    + ((m2.getRandom() - m1.getRandom()) * (m2
				    .getRandom() - m1.getRandom())));
	}
    }

    public static class DInfA6DistanceCalculater implements DistanceCalculater
    {
	@Override
	public double calculate(Person m1, Person m2)
	{
	    double temp1 = Math.abs(m2.getGroesse() - m1.getGroesse());
	    double temp2 = Math.abs(m2.getGewicht() - m1.getGewicht());
	    double temp3 = Math.abs(m2.getRandom() - m1.getRandom());
	    return Math.max(temp1, Math.max(temp2, temp3));
	}
    }
}
