package org.dev.home.cube;

import java.util.LinkedList;

import org.dev.home.cube.types.Angle;
import org.dev.home.cube.types.Axis;
import org.dev.home.cube.types.Colours3D;
import org.dev.home.cube.types.Coords3D;

public class Piece {
	
	private Position position_home;
	private Position position_current;
	
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
