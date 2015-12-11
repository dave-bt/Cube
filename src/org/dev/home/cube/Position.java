package org.dev.home.cube;

import java.util.LinkedList;

import org.dev.home.cube.types.Axis;
import org.dev.home.cube.types.Colours3D;
import org.dev.home.cube.types.Coords3D;

/* combines location and colours orientation of a piece */
public class Position {

	private Coords3D location;
	private Colours3D colours;
	
	public Position(Coords3D _location, Colours3D _colours)
	{
		location =_location;
		colours = _colours;
	}	
	
	public Position(Position position)
	{
		location = new Coords3D(position.location);
		colours = new Colours3D(position.colours);
	}
	
	public void rotateNinetyDegrees(Axis axis)
	{
		colours.rotateNinetyDegrees(axis);
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
	

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((colours == null) ? 0 : colours.hashCode());
		result = prime * result
				+ ((location == null) ? 0 : location.hashCode());
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
		Position other = (Position) obj;
		if (colours == null) {
			if (other.colours != null)
				return false;
		} else if (!colours.equals(other.colours))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		return true;
	}

	public Coords3D getLocation() {
		return location;
	}

	public Colours3D getColours() {
		return colours;
	}	

	public LinkedList<Plane> getCommonPlanes(Position arg1)
	{		
		return location.getCommonPlanes(arg1.location);
	}

	@Override
	public String toString() {		
		return "Position [X=" + location.x + ", Y=" + location.y + ", Z=" + location.z + "]";
	}
}
