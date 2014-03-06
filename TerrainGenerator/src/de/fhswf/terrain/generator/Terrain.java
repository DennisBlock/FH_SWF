package de.fhswf.terrain.generator;

import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.image.WritablePixelFormat;
import javafx.scene.paint.Color;

import javax.imageio.ImageIO;
/**
 * The Terrain class creates a terrain with biomes from a blurred heightmap
 * image. The heightmap are generated with the diamond-square algorithm.
 */
public class Terrain {

	private int size;	// Size of the map
	private DiamondSquare diamond;  // Diamond-Square algorithm

	/**
	 * Constructor.
	 * @param mapSize Size of the map.
	 * @param h	The range (-h -> +h) for the average offset.
	 * @param seed Initial seed value for the random generator.
	 */
	public Terrain(int mapSize, double h, long seed) {
		size = mapSize;
		diamond = new DiamondSquare(mapSize, h, seed);
	}

	/**
	 * This method generates a terrain image. 
	 * @param deepWater Height of deep wather.
	 * @param water Height of wather.
	 * @param sand Height of sand.
	 * @param grass Height of grass.
	 * @param hills Height of hills.
	 * @param mountain Height of mountain.
	 * @param everest Height of everest (mountain whith snow).
	 * @return image Blured Terrain Image.
	}
	 */
	public Image createTerrain(double deepWater, double water, double sand,
			double grass, double hills, double mountain, double everest) {

		// Set the biom values
		Biom biom = new Biom(deepWater, water, sand, grass, hills, mountain,
				everest);
		
		// Generate a normalized heightmap
		Image image = createHeightmapImage();
		
		PixelReader reader = image.getPixelReader();
		WritableImage wImage = new WritableImage(size, size);
		PixelWriter writer = wImage.getPixelWriter();

		// Transforms the height to biom. Set the Color to the given height.
		for (int x = 0; x < wImage.getWidth(); x++) {
			for (int y = 0; y < wImage.getHeight(); y++) {
				double height = reader.getColor(x, y).getRed();
				Color c = biom.heightToBiom(height);
				writer.setColor(x, y, c);
			}
		}

		return blur2(wImage);
	}
	
	/**
	 * This method generates a normalized heightmap and write an png image.
	 */
	private Image createHeightmapImage() {
		diamond.generateHeightmap();
		diamond.normalize();

		WritableImage image = new WritableImage(size, size);
		PixelWriter writer = image.getPixelWriter();

		for (int x = 0; x < diamond.size(); ++x) {
			for (int y = 0; y < diamond.size(); ++y) {
				writer.setColor(x, y, Color.gray(diamond.get(x, y)));
			}

		}

		File file = new File("heightmap.png");
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return image;
	}
	/**
	 * Blur filter to reduce image noise and reduce detail. Blur is reading both
	 * neighbor pixels around target pixel, averaging these pixels color, and
	 * then write the target pixel with the average color. BAD parformance!
	 * 
	 * @param src
	 *            Terrain Image.
	 * @return dest Blured Terrain Image.
	 */
	private Image blur(Image src) {
		WritableImage dest = new WritableImage(size, size);

		PixelReader reader = src.getPixelReader();
		PixelWriter writer = dest.getPixelWriter();

		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {

				double red = 0;
				double green = 0;
				double blue = 0;
				double alpha = 0;
				int count = 0;

				// Set the quantity of blur here
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
				// Average color
				Color blurColor = Color.color(red / count, green / count, blue
						/ count, alpha / count);
				writer.setColor(x, y, blurColor);
			}
		}
		return dest; // Blured Terrain Image
	}
	/**
	 * Decreasing the number of reading pixels
	 * http://skrb.hatenablog.com/entry/2013/01/07/171901
	 * 
	 * @param src
	 * @return
	 */
	private Image blur2(Image src) {

		WritableImage dest = new WritableImage(size, size);
		PixelReader reader = src.getPixelReader();
		PixelWriter writer = dest.getPixelWriter();
		WritablePixelFormat<IntBuffer> format = WritablePixelFormat
				.getIntArgbInstance();

		// Set the quantity of blur here
		int kernelSize = 3;

		for (int x = 0; x < src.getWidth(); x++) {
			for (int y = 0; y < src.getHeight(); y++) {
				int centerX = x - kernelSize;
				int centerY = y - kernelSize;
				int kernelWidth = kernelSize * 2 + 1;
				int kernelHeight = kernelSize * 2 + 1;

				if (centerX < 0) {
					centerX = 0;
					kernelWidth = x + kernelSize;
				} else if (x + kernelSize >= src.getWidth()) {
					kernelWidth = (int) (src.getWidth() - centerX);
				}

				if (centerY < 0) {
					centerY = 0;
					kernelHeight = y + kernelSize;
				} else if (y + kernelSize >= src.getHeight()) {
					kernelHeight = (int) (src.getHeight() - centerY);
				}

				int[] buffer = new int[kernelWidth * kernelHeight];
				reader.getPixels(centerX, centerY, kernelWidth, kernelHeight,
						format, buffer, 0, kernelWidth);

				int alpha = 0;
				int red = 0;
				int green = 0;
				int blue = 0;

				for (int color : buffer) {
					alpha += (color >>> 24);
					red += (color >>> 16 & 0xFF);
					green += (color >>> 8 & 0xFF);
					blue += (color & 0xFF);
				}

				alpha = alpha / kernelWidth / kernelHeight;
				red = red / kernelWidth / kernelHeight;
				green = green / kernelWidth / kernelHeight;
				blue = blue / kernelWidth / kernelHeight;

				int blurColor = (alpha << 24) + (red << 16) + (green << 8)
						+ blue;
				writer.setArgb(x, y, blurColor);
			}
		}
		return dest; // Blured Terrain Image
	}
}
