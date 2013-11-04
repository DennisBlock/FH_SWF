package de.fhswf.cluster;

public class ClusterDistanceFactory
{
    enum ClusterDistanceType
    {
	Minimum, Maximum
    }

    public interface ClusterDistance
    {
	double calculate(Cluster c1, Cluster c2);
	double calculateClusterToPoint(Cluster c, Point p);
    }

    private ClusterDistanceFactory()
    {
    }

    public static ClusterDistance create(ClusterDistanceType type)
    {
	switch (type)
	{
	    case Minimum:
		return new ClusterDistanceMin();
	    case Maximum:
		return new ClusterDistanceMax();
	    default:
		throw new IllegalArgumentException("illegal argument: " + type);
	}
    }

    private static class ClusterDistanceMin implements ClusterDistance
    {
	@Override
	public double calculate(Cluster c1, Cluster c2)
	{
	    double min = Double.MAX_VALUE;

	    for (int i = 0; i < c1.size(); i++)
	    {
		for (int j = 0; j < c2.size(); j++)
		{
		    Point p1 = c1.getPointAt(i);
		    Point p2 = c2.getPointAt(j);

		    double dist = distance(p1, p2);
		    if (min > dist)
		    {
			min = dist;
		    }
		}
	    }

	    return min;
	}

	@Override
	public double calculateClusterToPoint(Cluster c, Point p)
	{
	    
	    return distance(c.getCenter(), p);
	}

    }

    private static class ClusterDistanceMax implements ClusterDistance
    {
	@Override
	public double calculate(Cluster c1, Cluster c2)
	{
	    double max = 0;

	    for (int i = 0; i < c1.size(); i++)
	    {
		for (int j = 0; j < c2.size(); j++)
		{
		    Point p1 = c1.getPointAt(i);
		    Point p2 = c2.getPointAt(j);

		    double dist = distance(p1, p2);
		    if (max < dist)
		    {
			max = dist;
		    }
		}
	    }

	    return max;
	}

	@Override
	public double calculateClusterToPoint(Cluster c, Point p)
	{
	    return Double.MAX_VALUE;
	}

    }

    private static double distance(Point p1, Point p2)
    {
	return Math.sqrt((p2.getX() - p1.getX()) * (p2.getX() - p1.getX())
		+ (p2.getY() - p1.getY()) * (p2.getY() - p1.getY()));
    }
}
