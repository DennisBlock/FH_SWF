package application;

import java.util.ArrayList;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Line {
	private ArrayList<LineSegment> segments;
	private int size;

	private double x1;
	private double x2;
	private double y1;
	private double y2;

	public Line() {
		segments = new ArrayList<LineSegment>();
	}

	public Line(Line temp) {
		segments = new ArrayList<LineSegment>(temp.segments);
		this.size = temp.size;
		this.x1 = temp.x1;
		this.x2 = temp.x2;
		this.y1 = temp.y1;
		this.y2 = temp.y2;
	}

	public void addSegment(LineSegment segment) {
		addSegment(size, segment);
	}

	public void addSegment(int index, LineSegment segment) {
		if (index < 0)
			throw new IndexOutOfBoundsException("Index < 0");
		if (index > size)
			throw new IndexOutOfBoundsException("Index: " + index + " Size: "
					+ size);
		if (index == 0) {
			if (size > index + 1) {
				LineSegment after = segments.get(1);
				if (segment.x2 != after.x2 || segment.y2 != after.y2) {
					throw new IllegalArgumentException("Line is not closed");
				}
			}

			x1 = segment.x1;
			y1 = segment.y1;
		} else if (index == size || index == size - 1) {
			LineSegment prev = segments.get(index - 1);
			if (segment.x1 != prev.x2 || segment.y1 != prev.y2) {
				throw new IllegalArgumentException("Line is not closed: "
						+ prev + " and " + segment);
			}

			x2 = segment.x2;
			y2 = segment.y2;
		}

		size++;
		segments.add(index, segment);
	}

	// public boolean removeSegment(LineSegment segment) {
	// if(segment == null) {
	// throw new NullPointerException("argument is null");
	// }
	//
	// int index = segments.indexOf(segment);
	// LineSegment prev = null;
	// if(index == 0)
	// prev = segments.get(0);
	// else
	// prev = segments.get(index - 1);
	//
	// LineSegment after = null;
	// if(index == size - 1)
	// after = segments.get(size - 1);
	// else
	// after = segments.get(index + 1);
	//
	//
	//
	// x2 = prev.x2;
	// y2 = prev.y2;
	//
	// size--;
	//
	// return segments.remove(segment);
	// }

	public LineSegment removeSegment(int index) {
		return segments.remove(index);
	}

	public LineSegment get(int index) {
		return segments.get(index);
	}

	public int size() {
		return size;
	}

	public void clear() {
		segments.clear();
	}

	public void draw(GraphicsContext gc) {
		gc.setLineWidth(2.0);
		gc.setStroke(Color.BLUE);
		for (LineSegment ls : segments) {
			gc.strokeLine(ls.x1, ls.y1, ls.x2, ls.y2);
		}
	}
}
