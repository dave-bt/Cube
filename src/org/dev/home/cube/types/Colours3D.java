package org.dev.home.cube.types;

import java.util.HashMap;

public class Colours3D
{
	private Colour colour_x;
	private Colour colour_y;
	private Colour colour_z;
	private static final Colour DEFAULT_COLOUR = Colour.Black;
	private final HashMap<Colour, Axis> colourmap = new HashMap<Colour, Axis>(3); 
	
	public Colours3D(Colour _plane_x, Colour _plane_y, Colour _plane_z)
	{
		colour_x = _plane_x;
		colour_y = _plane_y;
		colour_z = _plane_z;
		colourmap.put(colour_x, Axis.X);
		colourmap.put(colour_y, Axis.Y);
		colourmap.put(colour_z, Axis.Z);
	}
	
	public Colours3D(Colours3D colours)
	{
		this(colours.colour_x, colours.colour_y, colours.colour_z);
	}

	public Colour getX()
	{
		return colour_x==null? DEFAULT_COLOUR : colour_x;
	}
	
	public Colour getY()
	{
		return colour_y==null? DEFAULT_COLOUR : colour_y;
	}
	
	public Colour getZ()
	{
		return colour_z==null? DEFAULT_COLOUR : colour_z;
	}	
	
	//returns the colour on a given axis
	public Axis find(Colour colour)
	{
		return this.colourmap.get(colour);
	}
	
	public void rotateNinetyDegrees(Axis axis)
	{
		switch(axis)
		{
		case X:
			Colour z = colour_z;
			colour_z = colour_y;
			colour_y = z;
			colourmap.put(colour_y, Axis.Y);
			colourmap.put(colour_z, Axis.Z);
			break;
		case Y:
			Colour x = colour_x;
			colour_x = colour_z;
			colour_z = x;
			colourmap.put(colour_x, Axis.X);
			colourmap.put(colour_z, Axis.Z);
			break;
		case Z:
			Colour y = colour_y;
			colour_y = colour_x;
			colour_x = y;			
			colourmap.put(colour_y, Axis.Y);
			colourmap.put(colour_x, Axis.X);
			break;
		}
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colour_x == null) ? 0 : colour_x.hashCode());
		result = prime * result + ((colour_y == null) ? 0 : colour_y.hashCode());
		result = prime * result + ((colour_z == null) ? 0 : colour_z.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Colours3D other = (Colours3D) obj;
		if (colour_x != other.colour_x)
			return false;
		if (colour_y != other.colour_y)
			return false;
		if (colour_z != other.colour_z)
			return false;
		return true;
	}

	public boolean containsAll(Colours3D colours_current)
	{
		for (Colour color : colours_current.colourmap.keySet())
		{
			if (!this.colourmap.containsKey(color))
			{
				return false;
			}
		}
		return true;
	}	
	
}
