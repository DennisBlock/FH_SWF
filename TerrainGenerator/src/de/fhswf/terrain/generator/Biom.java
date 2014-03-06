package de.fhswf.terrain.generator;

import javafx.scene.paint.Color;
/**
 * Biomes are climatically and geographically defined as contiguous areas with
 * similar climatic conditions on the Earth.
 */
public class Biom {
	// Define the biom here.
	private double deepWater;
	private double water;
	private double sand;
	private double grass;
	private double hills;
	private double mountain;
	private double everest;
	
	private enum BiomType {
		DEEP_WATER(Color.DARKBLUE), SHALLOW_WATER(Color.BLUE), SAND(
				Color.SANDYBROWN), GRASS(Color.GREEN), HILLS(Color.DARKGRAY), MOUNTAIN(
				Color.LIGHTGRAY), EVEREST(Color.WHITE);

		// The Color of the biom
		private Color color;

		/**
		 * Constructor set the color of the biom
		 * @param color
		 */
		BiomType(Color color) {
			this.color = color;
		}
	}

	/**
	 * The constructor initiates the given bioms
	 * 
	 * @param deepWater Height of deep wather.
	 * @param water Height of wather.
	 * @param sand Height of sand.
	 * @param grass Height of grass.
	 * @param hills Height of hills.
	 * @param mountain Height of mountain.
	 * @param everest Height of everest (mountain whith snow).
	 */
	public Biom(double deepWater, double water, double sand, double grass,
			double hills, double mountain, double everest) {
		this.deepWater = deepWater;
		this.water = water;
		this.sand = sand;
		this.grass = grass;
		this.hills = hills;
		this.mountain = mountain;
		this.everest = everest;
	}

	/**
	 * Transforms the height to biom. Set the Color to the given height.
	 * 
	 * @param height
	 *            height to .
	 * @return Color of the biom.
	 */
	public Color heightToBiom(double height) {
		if (height < deepWater) {
			return BiomType.DEEP_WATER.color;
		} else if (height < water) {
			return BiomType.SHALLOW_WATER.color;
		} else if (height < sand) {
			return BiomType.SAND.color;
		} else if (height < grass) {
			return BiomType.GRASS.color;
		} else if (height < hills) {
			return BiomType.HILLS.color;
		} else if (height < mountain) {
			return BiomType.MOUNTAIN.color;
		} else if (height < everest) {
			return BiomType.EVEREST.color;
		}
		return BiomType.EVEREST.color;
	}
}
