package de.fhswf.terrain.generator;

import javafx.scene.paint.Color;

public class Biom {
	private double deepWater;
	private double water;
	private double sand;
	private double grass;
	private double hills;
	private double mountain;
	private double everest;
	
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

	private enum BiomType {
		DEEP_WATER(Color.DARKBLUE),
		SHALLOW_WATER(Color.BLUE),
		SAND(Color.SANDYBROWN),
		GRASS(Color.GREEN),
		HILLS(Color.DARKGRAY),
		MOUNTAIN(Color.LIGHTGRAY),
		EVEREST(Color.WHITE);
		
		private Color color;
		
		BiomType(Color color){
			this.color = color;
		}
	}
	
	public Color heightToBiom(double height) {
		if( height < deepWater) {
			return BiomType.DEEP_WATER.color;
		}
		else if(height < water) {
			return BiomType.SHALLOW_WATER.color;
		}
		else if(height < sand) {
			return BiomType.SAND.color;
		}
		else if(height < grass) {
			return BiomType.GRASS.color;
		}
		else if(height < hills) {
			return BiomType.HILLS.color;
		}
		else if(height < mountain) {
			return BiomType.MOUNTAIN.color;
		}
		else if(height < everest) {
			return BiomType.EVEREST.color;
		}
		return BiomType.EVEREST.color;
	}
}
