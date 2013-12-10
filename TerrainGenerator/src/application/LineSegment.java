package application;

import javafx.geometry.Point2D;

public class LineSegment {
	double x1;
	double y1;
	double x2;
	double y2;
	
	public LineSegment() {
	}
	
	public LineSegment(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public LineSegment(Point2D start, Point2D end) {
		this(start.getX(), start.getY(), end.getX(), end.getY());
	}
	
	public Point2D midPoint()
	{
		return new Point2D((x1 + x2) / 2, (y1 + y2) / 2); 
	}
	
	public void set(double x1, double y1, double x2, double y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void set(Point2D start, Point2D end) {
		set(start.getX(), start.getY(), end.getX(), end.getY());
	}

	@Override
	public String toString() {
		return "LineSegment [x1=" + x1 + ", y1=" + y1 + ", x2=" + x2 + ", y2="
				+ y2 + "]";
	}
	
}
