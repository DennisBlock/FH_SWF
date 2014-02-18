package de.fhswf.terrain.generator;

import javafx.scene.paint.Color;

public class Biom {
	private enum BiomEnum {
		DEEP_WATER(0.3f, Color.DARKBLUE),
		SHALLOW_WATER(0.5f, Color.BLUE),
		GRASS(0.7f, Color.GREEN),
		HILLS(0.85f, Color.LIGHTGRAY),
		MOUNTAIN(0.95f, Color.DARKGRAY);
		
		private float threshold;
		private Color color;
		
		BiomEnum(float threshold,Color color){
			this.threshold = threshold;
			this.color = color;
		}
	}
	
	public static Color heightToBiom(double height) {
		if( height < BiomEnum.DEEP_WATER.threshold) {
			return BiomEnum.DEEP_WATER.color;
		}
		else if(height < BiomEnum.SHALLOW_WATER.threshold) {
			return BiomEnum.SHALLOW_WATER.color;
		}
		else if(height < BiomEnum.GRASS.threshold) {
			return BiomEnum.GRASS.color;
		}
		else if(height < BiomEnum.HILLS.threshold) {
			return BiomEnum.HILLS.color;
		}
		else if(height < BiomEnum.MOUNTAIN.threshold) {
			return BiomEnum.MOUNTAIN.color;
		}
		
		return Color.DARKSLATEGREY;
	}
}
