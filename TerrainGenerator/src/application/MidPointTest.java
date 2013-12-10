package application;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class MidPointTest extends Application {
	private static final int REPEAT = 4;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Midpoint Displacement in One Dimension");
		Group root = new Group();
		Canvas canvas = new Canvas(1250, 400);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawShapes(gc);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	private void drawShapes(GraphicsContext gc) {
		Line line = new Line();
		line.addSegment(new LineSegment(25, 200, 1225, 200));

		Pool<LineSegment> pool = new Pool<LineSegment>(
				new PoolObjectFactory<LineSegment>() {

					@Override
					public LineSegment create() {
						return new LineSegment();
					}
				}, 1024);

		Random rand = new Random();

		for (int i = 0; i < REPEAT; i++) {
			long startRand = 100 ;
			Line temp = new Line();
			for (int j = 0; j < line.size(); j++) {
				LineSegment seg = line.get(j);
				Point2D mid = seg.midPoint();
				double randY = (rand.nextInt((int) (startRand))+ 200) ;
				
				LineSegment prev = pool.get();
				prev.set(new Point2D(seg.x1, seg.y1), new Point2D(mid.getX(),
						randY));
				LineSegment after = pool.get();
				after.set(new Point2D(mid.getX(), randY), new Point2D(seg.x2,
						seg.y2));

				temp.addSegment(prev);
				temp.addSegment(after);
			}
			startRand /= 2;
			line = new Line(temp);
		}
		line.draw(gc);
	}
}
