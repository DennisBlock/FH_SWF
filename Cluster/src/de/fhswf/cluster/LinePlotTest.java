package de.fhswf.cluster;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataSeries;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.BarPlot;
import de.erichseifert.gral.plots.PlotArea;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.axes.AxisRenderer;
import de.erichseifert.gral.plots.points.DefaultPointRenderer2D;
import de.erichseifert.gral.plots.points.PointRenderer;
import de.erichseifert.gral.ui.InteractivePanel;
import de.erichseifert.gral.util.Insets2D;

public class LinePlotTest extends JFrame
{
    private static final long serialVersionUID = 1L;
    XYPlot plot;
    public LinePlotTest(DataTable data)
    {
	plot = stylePlot(data);

	// Display on screen
	getContentPane().add(new InteractivePanel(plot), BorderLayout.CENTER);
	setDefaultCloseOperation(EXIT_ON_CLOSE);
	setMinimumSize(getContentPane().getMinimumSize());
	setSize(504, 327);
    }

    private XYPlot stylePlot(DataTable data0)
    {

	// Create series
	DataSeries series1 = new DataSeries("Series 1", data0, 0, 1);
	XYPlot plot = new XYPlot(series1);
	// Style the plot
	double insetsTop = 20.0, insetsLeft = 60.0, insetsBottom = 60.0, insetsRight = 40.0;
	plot.setInsets(new Insets2D.Double(insetsTop, insetsLeft, insetsBottom,
		insetsRight));
	plot.setSetting(BarPlot.TITLE, "Aufgabe 2");

	// Style the plot area
	plot.getPlotArea().setSetting(PlotArea.COLOR,
		new Color(0.0f, 0.3f, 1.0f));
	plot.getPlotArea().setSetting(PlotArea.BORDER, new BasicStroke(2f));

	// Style data series
	PointRenderer points1 = new DefaultPointRenderer2D();
	points1.setSetting(PointRenderer.SHAPE, new Ellipse2D.Double(-3.0,
		-3.0, 6.0, 6.0));
	points1.setSetting(PointRenderer.COLOR, new Color(0f, 1.0f, 0f, 1.0f));
	plot.setPointRenderer(series1, points1);

	// Style axes
	plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(AxisRenderer.LABEL,
		"X");
	plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(AxisRenderer.LABEL,
		"Y");
	plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(
		AxisRenderer.TICKS_SPACING, 1.0);
	plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(
		AxisRenderer.TICKS_SPACING, 2.0);
	plot.getAxisRenderer(XYPlot.AXIS_X).setSetting(
		AxisRenderer.INTERSECTION, -Double.MAX_VALUE);
	plot.getAxisRenderer(XYPlot.AXIS_Y).setSetting(
		AxisRenderer.INTERSECTION, -Double.MAX_VALUE);
	return plot;
    }
    
    public void displayData(DataTable data)
    {

	DataSeries series = new DataSeries("Nachbar", data, 0, 1);
	plot.add(series);
	
	PointRenderer points2 = new DefaultPointRenderer2D();
	points2.setSetting(PointRenderer.SHAPE, new Rectangle2D.Double(-2.5,
		-2.5, 5, 5));
	points2.setSetting(PointRenderer.COLOR, Color.BLUE);
	plot.setPointRenderer(series, points2);
	
	invalidate();
    }
}
