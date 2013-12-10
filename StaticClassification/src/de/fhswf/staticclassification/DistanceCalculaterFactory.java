package de.fhswf.staticclassification;

public final class DistanceCalculaterFactory
{
    public enum DistanceType
    {
	D1, D2, DInfinit
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

}
