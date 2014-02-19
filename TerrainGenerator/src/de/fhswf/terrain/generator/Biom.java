package de.fhswf.terrain.generator;

import javafx.scene.paint.Color;

public class Biom {
	private enum BiomType {
		DEEP_WATER(0.2f, Color.DARKBLUE),
		SHALLOW_WATER(0.5f, Color.BLUE),
		SAND(0.52f, Color.SANDYBROWN),
		GRASS(0.76f, Color.GREEN),
		HILLS(0.85f, Color.DARKGRAY),
		MOUNTAIN(0.95f, Color.LIGHTGRAY),
		EVEREST(1.0f, Color.WHITE);
		
		private float threshold;
		private Color color;
		
		BiomType(float threshold,Color color){
			this.threshold = threshold;
			this.color = color;
		}
	}
	
	public static Color heightToBiom(double height) {
		if( height < BiomType.DEEP_WATER.threshold) {
			return BiomType.DEEP_WATER.color;
		}
		else if(height < BiomType.SHALLOW_WATER.threshold) {
			return BiomType.SHALLOW_WATER.color;
		}
		else if(height < BiomType.SAND.threshold) {
			return BiomType.SAND.color;
		}
		else if(height < BiomType.GRASS.threshold) {
			return BiomType.GRASS.color;
		}
		else if(height < BiomType.HILLS.threshold) {
			return BiomType.HILLS.color;
		}
		else if(height < BiomType.MOUNTAIN.threshold) {
			return BiomType.MOUNTAIN.color;
		}
		
		return BiomType.EVEREST.color;
	}
}
