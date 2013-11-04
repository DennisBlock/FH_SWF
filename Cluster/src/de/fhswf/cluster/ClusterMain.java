package de.fhswf.cluster;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import de.erichseifert.gral.data.DataTable;
import de.fhswf.cluster.ClusterDistanceFactory.ClusterDistance;
import de.fhswf.cluster.ClusterDistanceFactory.ClusterDistanceType;

public class ClusterMain
{

    enum DataSet
    {
	Set1, Set2
    }

    List<Point> points = new ArrayList<Point>();
    List<Cluster> list = new ArrayList<Cluster>();
    private boolean isMerged = false;

    public static void main(String[] args)
    {
	ClusterMain cluster = new ClusterMain();
	cluster.generate(DataSet.Set1);
	cluster.treeMerge(ClusterDistanceType.Maximum);
	cluster.reset();
	cluster.generate(DataSet.Set1);
	cluster.treeMerge(ClusterDistanceType.Minimum);

	cluster.reset();
	cluster.generate(DataSet.Set2);
	cluster.display();
	cluster.treeMerge(ClusterDistanceType.Maximum);
	cluster.reset();
	cluster.generate(DataSet.Set2);
	cluster.treeMerge(ClusterDistanceType.Minimum);
	
	System.out.println("kMean");
	System.out.println("==============");
	cluster.reset();
	cluster.generate(DataSet.Set1);
	System.out.println("Startwert: Zeile 1 und 2");
	int[] indizes = {1,2};
	cluster.kMean(indizes);
	System.out.println("Startwert: Zeile 1 und 3");
	int[] indizes2 = {1,3};
	cluster.kMean(indizes2);
    }

    public void treeMerge(ClusterDistanceType distanceType)
    {
	if (isMerged)
	    throw new IllegalStateException("Cluster already merged.");

	// For all points, create new cluster
	for (Point p : points)
	{
	    list.add(new Cluster(p));
	}

	ClusterDistance clusterDistance = ClusterDistanceFactory
		.create(distanceType);

	int c = 20;
	do
	{
	    double dMin = Double.MAX_VALUE;
	    Cluster min1 = null;
	    Cluster min2 = null;
	    for (int i = 0; i < list.size(); i++)
	    {
		Cluster c1 = list.get(i);
		for (int j = i + 1; j < list.size(); j++)
		{
		    Cluster c2 = list.get(j);

		    double distance = clusterDistance.calculate(c1, c2);

		    if (dMin > distance)
		    {
			dMin = distance;
			min1 = c1;
			min2 = c2;
		    }
		}
	    }
	    min1.merge(min2);
	    list.remove(min2);

	    print();
	}
	while (list.size() > 1);

	isMerged = true;
    }

    public void kMean(int[] center)
    {
	Cluster[] cluster = new Cluster[center.length];

	for (int i = 0; i < center.length; i++)
	{
	    Cluster c = new Cluster(points.get(center[i]));
	    points.remove(center[i]);
	    c.center();
	    cluster[i] = c;
	}

	ClusterDistance clusterDist = ClusterDistanceFactory
		.create(ClusterDistanceType.Minimum);

	int maxiter = points.size();
	double f = 0.0;
	double e = 0.0;
	double eAlt = e;
	double delta = 1.0;
	int pointIndex = 0;
	do
	{
	    Cluster clusterMin = null;
	    double distance = Double.MAX_VALUE;
	    
	    for(Cluster c : cluster)
	    {
		double d = clusterDist.calculateClusterToPoint(c, points.get(pointIndex));
		if(distance > d)
		{
		    distance = d;
		    clusterMin = c;
		}
	    }
	    
	    clusterMin.add(points.get(pointIndex));
//	    points.remove(pointIndex);
	    
	    for(Cluster c : cluster)
	    {
		for(int i = 0; i < c.size(); i++)
		{
		    double dist = clusterDist.calculateClusterToPoint(c, c.getPointAt(i));
		    e += dist * dist;
		}
	    }

	    f = Math.abs(e - eAlt);
	    e = eAlt;
	    maxiter--;
	    
	    pointIndex++;
	    
	    print(cluster[0], cluster[1]);
	}
	while (f > delta && maxiter > 0);
    }

    public void generate(DataSet set)
    {
	if (points.size() != 0)
	    throw new IllegalStateException(
		    "Already genereated points. (Reset before generating)");

	switch (set)
	{
	    case Set1:
		for (int i = 1; i <= 20; i++)
		{
		    double x = 1 + 5 * (i % 2) + ((i - 1) / 4);
		    double y = 1 + 5 * (i % 2) + (i % 5);

		    Point point = new Point(x, y);
		    points.add(point);
		}
		break;
	    case Set2:
		for (int i = 1; i <= 20; i++)
		{
		    double x = 5 + (i % 2) * ((i / 2) - 4) * (0.3 + 1 / i);
		    double y = 5 + (1 - (i % 2)) * ((i / 2) - 4)
			    * (0.3 + 1.0 / (i + 1));

		    Point point = new Point(x, y);
		    points.add(point);
		}
		break;
	    default:
		throw new RuntimeException("invalid argument: " + set);
	}
    }

    private void print(Cluster... cluster)
    {
	System.out.println();
	for (Cluster c : cluster)
	{
	    System.out.println(c);
	}
	System.out.println();
    }
    
    private void print()
    {
	System.out.println("Anzahl Cluster: " + list.size());
	System.out.println();
	for (Cluster c : list)
	{
	    System.out.println(c);
	}
	System.out.println();
    }

    public void display()
    {
	@SuppressWarnings("unchecked")
	DataTable data = new DataTable(Double.class, Double.class);
	for (Point p : points)
	{
	    data.add(p.getX(), p.getY());
	}

	LinePlotTest frame = new LinePlotTest(data);
	frame.setVisible(true);
    }

    public void reset()
    {
	points.clear();
	list.clear();
	isMerged = false;
	Cluster.id = 0;
    }
}
