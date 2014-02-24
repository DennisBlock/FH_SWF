package de.fhswf.terrain.generator;

import java.io.File;
import java.io.IOException;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;

public class Terrain {

	private int size;

	private DiamondSquare diamond;

	public Terrain(int mapSize, double h, long seed) {
		size = mapSize;
		diamond = new DiamondSquare(mapSize, h, seed);
	}
	
	private void createHeightmapImage() {
		diamond.generateHeightmap();
		diamond.normalize();

		WritableImage image = new WritableImage(size, size);
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

	public Image createTerrain(double deepWater, double water, double sand, double grass, double hills, double mountain, double everest){

		Biom biom = new Biom(deepWater, water, sand, grass, hills, mountain, everest);
		
		createHeightmapImage();
		Image image = null;
		try {
			image = SwingFXUtils.toFXImage(
					ImageIO.read(new File("heightmap.png")), null);
		} catch (IOException e) {
			e.printStackTrace();
		}
		PixelReader reader = image.getPixelReader();
		WritableImage wImage = new WritableImage(size, size);
		PixelWriter writer = wImage.getPixelWriter();

		for (int x = 0; x < wImage.getWidth(); x++) {
			for (int y = 0; y < wImage.getHeight(); y++) {
				double height = reader.getColor(x, y).getRed();
				Color c = biom.heightToBiom(height);
				writer.setColor(x, y, c);
			}
		}
		
		return blur(wImage);
	}
	
	private Image blur(Image src) {
		PixelReader reader = src.getPixelReader();
		WritableImage dest = new WritableImage(size, size);
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
				for (int i = -kernelSize; i <= kernelSize; i++) {
					for (int j = -kernelSize; j <= kernelSize; j++) {
						if (x + i < 0 || x + i >= src.getWidth() || y + j < 0
								|| y + j >= src.getHeight()) {
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
				Color blurColor = Color.color(red / count, green / count, blue
						/ count, alpha / count);
				writer.setColor(x, y, blurColor);
			}
		}
		return dest;
	}
}
