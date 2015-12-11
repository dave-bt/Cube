package org.dev.home.cube;

public class Piece {
	
	private Coords3D location_home;
	private Colours3D colours_home;
	private Coords3D location_current;
	private Colours3D colours_current;
	
	public Piece(Coords3D _location, Colours3D _orientation)
	{
		location_current = _location;
		colours_current = _orientation;
	}
	
	public void setHomeAsCurrentLocation()
	{
		location_home = location_current;
	}
	
	public boolean isHome()
	{
		return location_current.equals(location_home) && colours_current.equals(colours_home);
	}
	

	public void rotate(Axis axis, Angle angle)
	{
		int turns = angle.degrees / 90;
		for (int i=0; i<turns; i++)
		{
			locationRotateNinetyDegrees(axis);
			colours_current.rotateNinetyDegrees(axis);
		}
	}

	private void locationRotateNinetyDegrees(Axis axis)
	{
		//System.out.print("Moving " + this);
		switch(axis)
		{
		case X:
			switch(location_current.y)
			{
			case 1:
				switch(location_current.z)
				{
				case 1:
					location_current.z = -1;
					break;
				case 0:
					location_current.y=0;
					location_current.z=-1;
					break;
				case -1:
					location_current.y=-1;
					break;
				}
				break;			
			case 0:
				switch(location_current.z)
				{
				case 1:
					location_current.y=1;
					location_current.z=0;
					break;
				case 0:
					break;
				case -1:
					location_current.y=-1;
					location_current.z=0;
					break;
				}
				break;
			case -1:
				switch(location_current.z)
				{
				case 1:
					location_current.y=1;
					break;
				case 0:
					location_current.y=0;
					location_current.z=1;
					break;
				case -1:
					location_current.z=1;
					break;
				}
				break;
			}
			break;
		case Y:
			switch(location_current.x)
			{
			case 1:
				switch(location_current.z)
				{
				case 1:
					location_current.x = -1;
					break;
				case 0:
					location_current.x=0;
					location_current.z=1;
					break;
				case -1:
					location_current.z=1;
					break;
				}
				break;			
			case 0:
				switch(location_current.z)
				{
				case 1:
					location_current.x=-1;
					location_current.z=0;
					break;
				case 0:
					break;
				case -1:
					location_current.x=1;
					location_current.z=0;
					break;
				}
				break;
			case -1:
				switch(location_current.z)
				{
				case 1:
					location_current.z=-1;
					break;
				case 0:
					location_current.x=0;
					location_current.z=-1;
					break;
				case -1:
					location_current.x=1;
					break;
				}
				break;
			}
			break;
		case Z:
			switch(location_current.x)
			{
			case 1:
				switch(location_current.y)
				{
				case 1:
					location_current.y = -1;
					break;
				case 0:
					location_current.x=0;
					location_current.y=-1;
					break;
				case -1:
					location_current.x=-1;					
					break;
				}
				break;			
			case 0:
				switch(location_current.y)
				{
				case 1:
					location_current.x=1;
					location_current.y=0;
					break;
				case 0:
					break;
				case -1:
					location_current.x=-1;
					location_current.y=0;
					break;
				}
				break;
			case -1:
				switch(location_current.y)
				{
				case 1:
					location_current.x=1;
					break;
				case 0:
					location_current.x=0;
					location_current.y=1;
					break;
				case -1:
					location_current.y=1;
					break;
				}
				break;
			}
			break;
		}		
		
		//System.out.println(" to " + this);
	}

	public Coords3D getLocation() {
		return location_current;		
	}
	
	@Override
	public String toString() {		
		return "Piece [X=" + location_current.x + ", Y=" + location_current.y + ", Z=" + location_current.z + "]";
	}

	public Colours3D getColours() {
		return colours_current;
	}	
	
	
}
