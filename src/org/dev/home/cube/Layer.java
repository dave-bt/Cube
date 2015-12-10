package org.dev.home.cube;

import java.util.Collection;
import java.util.HashMap;

public class Layer
{
	private final Cube owner_cube;
	private final Plane plane;
	private final HashMap<Coords2D, Piece> pieces; 
	
	public Layer(Plane _plane, Cube cube)
	{
		owner_cube = cube;
		plane = _plane;
		pieces = new HashMap<Coords2D, Piece>(9);
	}
	
	public void rotate(Angle angle)
	{
		synchronized (pieces)
		{
			Axis axis = plane.getAxis();
			//remove pieces from layers that need to be re-evaluated after rotation
			for (Piece piece : pieces.values())
			{ 
				owner_cube.removeFromLayers(piece, axis);				
			}
			for (Piece piece : pieces.values())
			{
				//rotate
				piece.rotate(axis, angle);
			}
			for (Piece piece : pieces.values())
			{
				//add to new layers
				owner_cube.addToLayers(piece, axis);
			}
		}
				
	}

	public void add(Piece piece)
	{
		Coords2D location2D = piece.getLocation().getCoords2D(plane);
		synchronized (pieces) {
			pieces.put(location2D, piece);	
		}				
				
	}
	
	public void remove(Piece piece)
	{
		Coords2D location2D = piece.getLocation().getCoords2D(plane);
		synchronized (pieces) {
			pieces.remove(location2D);	
		}				
	}
	
	@Override
	public String toString() {		
		synchronized (pieces) {
			return "Layer [" + plane + ", " + pieces.size() + " pieces]";	
		}
		 
	}

	public void draw()
	{
		// TODO Auto-generated method stub
		
	}	
}
