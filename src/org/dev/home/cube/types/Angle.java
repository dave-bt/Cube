package org.dev.home.cube.types;

public enum Angle
{
	Ninety(90), OneEighty(180), MinusNinety(270);
	public final int degrees;
	private Angle(int _degrees)
	{
		degrees = _degrees;
	}	
}
