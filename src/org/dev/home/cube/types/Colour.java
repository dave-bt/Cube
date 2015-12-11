package org.dev.home.cube.types;

public enum Colour {

	Yellow(1f, 1f, 0f),
	Red(1f, 0f, 0f), 
	Blue(0f, 0f, 1f), 
	Green(0f, 1f, 0f), 
	Orange(1f, 0.5f, 0f), 
	White(1f, 1f, 1f), 
	Black(0f, 0f, 0f);
	
	private final float[] rgb;
	
	private Colour(float r, float g, float b)
	{
		rgb = new float[] {r, g, b};
	}
	
	public float[] getRGB()
	{
		return rgb;
	}
}
