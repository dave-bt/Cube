package org.dev.home.cube;

public class Colours3D
{
	private Colour plane_x;
	private Colour plane_y;
	private Colour plane_z;
	private Colour default_colour = Colour.Black;
	
	public Colours3D(Colour _plane_x, Colour _plane_y, Colour _plane_z)
	{
		plane_x = _plane_x;
		plane_y = _plane_y;
		plane_z = _plane_z;
	}
	
	public Colour getX()
	{
		return plane_x==null? default_colour : plane_x;
	}
	
	public Colour getY()
	{
		return plane_y==null? default_colour : plane_y;
	}
	
	public Colour getZ()
	{
		return plane_z==null? default_colour : plane_z;
	}
	
	public void rotateNinetyDegrees(Axis axis)
	{
		switch(axis)
		{
		case X:
			Colour z = plane_z;
			plane_z = plane_y;
			plane_y = z;
			break;
		case Y:
			Colour x = plane_x;
			plane_x = plane_z;
			plane_z = x;
			break;
		case Z:
			Colour y = plane_y;
			plane_y = plane_x;
			plane_x = y;			
			break;
		}
	}	
	
}
