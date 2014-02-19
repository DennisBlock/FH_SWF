package de.fhswf.terrain.generator;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

public class TerrainGenerator extends Application {

	public static final int MAP_SIZE = 513;

	private DiamondSquare diamond;

	private Image image;

	public TerrainGenerator() {
		diamond = new DiamondSquare(MAP_SIZE, 3.0);

		createHeightmapImage();
		try {
			image = createTerrain();
			image = blur(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Image blur(Image src) {
        PixelReader reader = src.getPixelReader();
		WritableImage dest = new WritableImage(MAP_SIZE, MAP_SIZE);
        PixelWriter writer = dest.getPixelWriter();

        for (int x = 0; x < src.getWidth(); x++) {
            for (int y = 0; y < src.getHeight(); y++) {
                double red = 0;
                double green = 0;
                double blue = 0;
                double alpha = 0;
                int count = 0;

                int kernelSize = 1;
				// reading neighbor pixels
                for (int i = -kernelSize ; i <= kernelSize; i++) {
                    for (int j = -kernelSize; j <= kernelSize; j++) {
                        if (x + i < 0 || x + i >= src.getWidth() 
                           || y + j < 0 || y + j >= src.getHeight()) {
                            continue;
                        }
                        Color color = reader.getColor(x + i, y + j);
                        red += color.getRed();
                        green += color.getGreen();
                        blue += color.getBlue();
                        alpha += color.getOpacity();
                        count++;
                    }
                }
                Color blurColor = Color.color(red / count, 
                                              green / count, 
                                              blue / count, 
                                              alpha / count);
                writer.setColor(x, y, blurColor);
            }
        }
        return dest;
    }

	private void createHeightmapImage() {
		diamond.generateHeightmap();
		diamond.normalize();

		WritableImage image = new WritableImage(MAP_SIZE, MAP_SIZE);
		PixelWriter writer = image.getPixelWriter();

		for (int x = 0; x < diamond.size(); ++x) {
			for (int y = 0; y < diamond.size(); ++y) {
				double temp = diamond.get(x, y);
				 if (temp > 1)
				 temp = 1;
				 if (temp < 0)
				 temp = 0;
				writer.setColor(x, y, Color.gray(temp));
			}

		}

		File file = new File("heightmap.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private Image createTerrain() throws IOException {
		Image image = SwingFXUtils.toFXImage(
				ImageIO.read(new File("heightmap.png")), null);
		PixelReader reader = image.getPixelReader();
		WritableImage wImage = new WritableImage(MAP_SIZE, MAP_SIZE);
		PixelWriter writer = wImage.getPixelWriter();

		for (int x = 0; x < wImage.getWidth(); x++) {
			for (int y = 0; y < wImage.getHeight(); y++) {
				double height = reader.getColor(x, y).getRed();
				Color c = Biom.heightToBiom(height);
				writer.setColor(x, y, c);
			}
		}
		
		return wImage;
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("Terrain Generator");
		Group root = new Group();

		Canvas canvas = new Canvas(MAP_SIZE, MAP_SIZE);
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(image, 0, 0);

		root.getChildren().add(canvas);
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}

	public static void main(String[] args) {
		// launch ruft automatisch die umgebene Klasse auf, inkl. Konstruktor,
		// die von Application erbt
		launch(args);
	}

}
