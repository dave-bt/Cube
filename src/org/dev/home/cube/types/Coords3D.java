package org.dev.home.cube.types;

import java.util.LinkedList;

import org.dev.home.cube.Plane;

public class Coords3D
{	
	public int x;
	public int y;
	public int z; 
		
	public Coords3D(int _x, int _y, int _z)
	{		
		x=_x;
		y=_y;
		z=_z;		
	}	
	
	public Coords3D(Coords3D location)
	{
		x = location.x;
		y = location.y;
		z = location.z;
	}

	//gets the 2D coords of this piece in the given plane
	public Coords2D getCoords2D(Plane plane)	
	{
		Coords2D coords2d = null;
		Axis axis = plane.getAxis();
		switch (axis)
		{
		case X:
			coords2d = new Coords2D(y, z);
			break;
		case Y:
			coords2d = new Coords2D(x, z);
			break;
		case Z:
			coords2d = new Coords2D(x, y);
			break;
		}
		return coords2d;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
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
		Coords3D other = (Coords3D) obj;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}

	public LinkedList<Plane> getCommonPlanes(Coords3D location)
	{
		LinkedList<Plane> planes = new LinkedList<Plane>();
		if (x == location.x)
		{
			planes.add(new Plane(Axis.X, x));
		}
		if (y == location.y)
		{
			planes.add(new Plane(Axis.Y, y));
		}
		if (z == location.z)
		{
			planes.add(new Plane(Axis.Z, z));
		}		
		return planes;
	}
	
}
