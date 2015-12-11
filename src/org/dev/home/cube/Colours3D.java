package org.dev.home.cube;

public class Colours3D
{
	private Colour plane_x;
	private Colour plane_y;
	private Colour plane_z;
	private static final Colour DEFAULT_COLOUR = Colour.Black;
	
	public Colours3D(Colour _plane_x, Colour _plane_y, Colour _plane_z)
	{
		plane_x = _plane_x;
		plane_y = _plane_y;
		plane_z = _plane_z;
	}
	
	public Colour getX()
	{
		return plane_x==null? DEFAULT_COLOUR : plane_x;
	}
	
	public Colour getY()
	{
		return plane_y==null? DEFAULT_COLOUR : plane_y;
	}
	
	public Colour getZ()
	{
		return plane_z==null? DEFAULT_COLOUR : plane_z;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((plane_x == null) ? 0 : plane_x.hashCode());
		result = prime * result + ((plane_y == null) ? 0 : plane_y.hashCode());
		result = prime * result + ((plane_z == null) ? 0 : plane_z.hashCode());
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
		if (plane_x != other.plane_x)
			return false;
		if (plane_y != other.plane_y)
			return false;
		if (plane_z != other.plane_z)
			return false;
		return true;
	}	
	
}
