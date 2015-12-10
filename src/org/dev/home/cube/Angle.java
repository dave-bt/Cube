package org.dev.home.cube;

public enum Angle
{
	Ninety(90), OneEighty(180), MinusNinety(270);
	final int degrees;
	private Angle(int _degrees)
	{
		degrees = _degrees;
	}	
}
