package de.fhswf.cluster;
import java.util.ArrayList;
import java.util.List;

import de.erichseifert.gral.data.DataTable;


public class Cluster
{

    enum DataSet
    {
	Number1, Number2
    }

    List<Point> list = new ArrayList<Point>();

    public static void main(String[] args)
    {
	Cluster cluster = new Cluster();
	cluster.generate(DataSet.Number1);
	cluster.display();
	cluster.reset();
	cluster.generate(DataSet.Number2);
	cluster.display();
    }
    
    public void generate(DataSet set)
    {
	switch (set)
	{
	    case Number1:
		for (int i = 1; i <= 20; i++)
		{
		    double x = 1 + 5 * (i % 2) + ((i - 1) / 4);
		    double y = 1 + 5 * (i % 2) + (i % 5);

		    Point point = new Point(x, y);
		    list.add(point);
		    System.out.println(point);
		}
		break;
	    case Number2:
		for (int i = 1; i <= 20; i++)
		{
		    double x = 5 + (i % 2) * ((i / 2) - 4) * (0.3 + 1 / i);
		    double y = 5 + (1 - (i % 2)) * ((i / 2) - 4)
			    * (0.3 + 1.0 / (i + 1));

		    Point point = new Point(x, y);
		    list.add(point);
		    System.out.println(point);
		}
		break;
	    default:
		throw new RuntimeException("invalid argument: " + set);
	}
    }
    
    public void display()
    {
	@SuppressWarnings("unchecked")
	DataTable data = new DataTable(Double.class, Double.class);
	for(Point p : list)
	{
	    data.add(p.getX(), p.getY());
	}
	
	LinePlotTest frame = new LinePlotTest(data);
	 frame.setVisible(true);
    }

    public void reset()
    {
        list.clear();
    }
}
