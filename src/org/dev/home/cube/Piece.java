package org.dev.home.cube;

import java.util.LinkedList;

import org.dev.home.cube.types.Angle;
import org.dev.home.cube.types.Axis;
import org.dev.home.cube.types.Colours3D;
import org.dev.home.cube.types.Coords3D;

public class Piece {
	
	private final Position position_home;
	private final Position position_current;
	
	/* create Piece with the specified properties and set these as home properties. i.e
	 * this is where piece needs to return to to be solved
	 */
	public Piece(Coords3D _location, Colours3D _colours)
	{
		position_current = new Position(_location, _colours);
		position_home = new Position(position_current);
	}
	
	/* returns those planes common to both home anc current positions */
	public LinkedList<Plane> getCommonPlanes()
	{
		return position_home.getCommonPlanes(position_current);
	}


	
	public boolean isHome()
	{
		return position_current.equals(position_home);
	}
	
	public void rotate(Axis axis, Angle angle)
	{
		int turns = angle.degrees / 90;
		for (int i=0; i<turns; i++)
		{
			position_current.rotateNinetyDegrees(axis);			
		}
	}		
	
	/* checks if the piece is aligned below/above/to the side of its home location 
	* (below/above/to the side being specified by the axis provided) */
	public boolean isBestAligned(Axis axis)
	{
		Coords3D location_home = this.position_home.getLocation();		
		Coords3D location_current = this.position_current.getLocation();
		//return if location aligned
		switch(axis)
		{
		case X:
			if (location_current.z==location_home.z && location_current.y==location_home.y) 
			{
				return true;
			}
			break;
		case Y:
			if (location_current.x==location_home.x && location_current.z==location_home.z) 
			{
				return true;
			}
			break;			
		case Z:
			if (location_current.x==location_home.x && location_current.y==location_home.y) 
			{
				return true;
			}
			break;
		}			
		return false;
	}

	public Position getCurrentPosition() {
		return position_current;		
	}
	

	public Position getHomePosition() {
		return position_home;
	}
	
	@Override
	public String toString() {		
		return "Piece [Home " + position_home + " : Current " + position_current + "]";
	}


	

	
	
}
