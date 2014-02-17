package application;

import java.util.Random;

import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
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
		Canvas canvas = new Canvas(1025, 1025);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		drawHeightMap(gc);
		// drawShapes(gc);
		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	private void drawHeightMap(GraphicsContext gc) {
		double[][] map = DiamondSquare.diamond2();
		map = DiamondSquare.normalize(0.0, 1.0, map);
		WritableImage image = new WritableImage(1025, 1025);
		PixelWriter writer = image.getPixelWriter();

		for (int x = 0; x < map.length; ++x) {
			for (int y = 0; y < map.length; ++y) {
				double temp = map[x][y];
				if(temp > 1) temp = 1;
				if(temp < 0) temp = 0;
				writer.setColor(x, y,Color.gray(temp));
			}

		}
		
		gc.drawImage(image, 0, 0);
	}

}
