package de.fhswf.cluster;

import java.util.ArrayList;

public class Cluster
{
    static int id = 0;

    private ArrayList<Point> points;
    private int identifier;
    private Point center;

    /**
     * Cluster hat zu Beginn immer nur einen Point.
     * @param p
     */
    public Cluster(Point p)
    {
	points = new ArrayList<Point>();
	points.add(p);
	
//	center = p;
	
	identifier = id++;
    }
    
    public void add(Point p)
    {
	points.add(p);
	recalculateCenter();
    }
    
    public int size()
    {
	return points.size();
    }
    
    public Point getPointAt(int index)
    {
	return points.get(index);
    }
    
    private void recalculateCenter()
    {
	double x = 0.0;
	double y = 0.0;
	
	for(Point p : points)
	{
	    x += p.getX();
	    y += p.getY();
	}
	
	x = x / points.size();
	y = y / points.size();
	center = new Point(x, y);
    }
    
    public Point getCenter()
    {
	return center;
    }
    
    public void center()
    {
	center = points.get(0);
    }
    
    public void merge(Cluster cluster)
    {
	points.addAll(cluster.points);
	System.out.println("merged cluster " + identifier + " with cluster " + cluster.identifier);
    }

    @Override
    public String toString()
    {
	return "Cluster [center=" + center + ", identifier=" + identifier
		+ ", points=" + points + "]";
    }
    
    
}
