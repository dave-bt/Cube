package org.dev.home.cube;

public class Piece {
	
	private Coords3D home; 
	private Coords3D location;
	private Colours3D colours;
	
	public Piece(Coords3D _location, Colours3D _orientation)
	{
		location = _location;
		colours = _orientation;
	}
	
	public void setHomeAsCurrentLocation()
	{
		home = location;
	}
	
	public boolean isHome()
	{
		return location.equals(home);
	}
	

	public void rotate(Axis axis, Angle angle)
	{
		int turns = angle.degrees / 90;
		for (int i=0; i<turns; i++)
		{
			locationRotateNinetyDegrees(axis);
			colours.rotateNinetyDegrees(axis);
		}
	}

	private void locationRotateNinetyDegrees(Axis axis)
	{
		//System.out.print("Moving " + this);
		switch(axis)
		{
		case X:
			switch(location.y)
			{
			case 1:
				switch(location.z)
				{
				case 1:
					location.z = -1;
					break;
				case 0:
					location.y=0;
					location.z=-1;
					break;
				case -1:
					location.y=-1;
					break;
				}
				break;			
			case 0:
				switch(location.z)
				{
				case 1:
					location.y=1;
					location.z=0;
					break;
				case 0:
					break;
				case -1:
					location.y=-1;
					location.z=0;
					break;
				}
				break;
			case -1:
				switch(location.z)
				{
				case 1:
					location.y=1;
					break;
				case 0:
					location.y=0;
					location.z=1;
					break;
				case -1:
					location.z=1;
					break;
				}
				break;
			}
			break;
		case Y:
			switch(location.x)
			{
			case 1:
				switch(location.z)
				{
				case 1:
					location.x = -1;
					break;
				case 0:
					location.x=0;
					location.z=1;
					break;
				case -1:
					location.z=1;
					break;
				}
				break;			
			case 0:
				switch(location.z)
				{
				case 1:
					location.x=-1;
					location.z=0;
					break;
				case 0:
					break;
				case -1:
					location.x=1;
					location.z=0;
					break;
				}
				break;
			case -1:
				switch(location.z)
				{
				case 1:
					location.z=-1;
					break;
				case 0:
					location.x=0;
					location.z=-1;
					break;
				case -1:
					location.x=1;
					break;
				}
				break;
			}
			break;
		case Z:
			switch(location.x)
			{
			case 1:
				switch(location.y)
				{
				case 1:
					location.y = -1;
					break;
				case 0:
					location.x=0;
					location.y=-1;
					break;
				case -1:
					location.x=-1;					
					break;
				}
				break;			
			case 0:
				switch(location.y)
				{
				case 1:
					location.x=1;
					location.y=0;
					break;
				case 0:
					break;
				case -1:
					location.x=-1;
					location.y=0;
					break;
				}
				break;
			case -1:
				switch(location.y)
				{
				case 1:
					location.x=1;
					break;
				case 0:
					location.x=0;
					location.y=1;
					break;
				case -1:
					location.y=1;
					break;
				}
				break;
			}
			break;
		}		
		
		//System.out.println(" to " + this);
	}

	public Coords3D getLocation() {
		return location;		
	}
	
	@Override
	public String toString() {		
		return "Piece [X=" + location.x + ", Y=" + location.y + ", Z=" + location.z + "]";
	}

	public Colours3D getColours() {
		return colours;
	}	
	
	
}
