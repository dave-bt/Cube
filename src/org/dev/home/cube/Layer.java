package org.dev.home.cube;

import java.util.HashMap;

public class Layer
{
	private final Cube owner_cube;
	private final Plane plane;
	private HashMap<Coords2D, Piece> pieces; 
	
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
			//rotate pieces and add to temp copy of pieces map
			HashMap<Coords2D, Piece> temp = new HashMap<Coords2D, Piece>(9);
			for (Piece piece : pieces.values())
			{
				piece.rotate(axis, angle);
				add(temp, piece); //add back at new location in temp layer
			}
			//replace old layer with new one
			pieces = temp;
			
			//add piece to layers that we removed a piece from at the beginning
			for (Piece piece : pieces.values())
			{			
				owner_cube.addToLayers(piece, axis);
			}
		}
	}
	
	public void add(Piece piece)
	{
		add(pieces, piece);
	}

	private void add(HashMap<Coords2D, Piece> map, Piece piece)
	{
		Coords2D location2D = piece.getLocation().getCoords2D(plane);
		synchronized (map) {
			Piece replaced = map.put(location2D, piece);
			if (replaced!=null)
			{
				System.out.println("ERROR replaced piece!!");
			}
		}				
				
	}
	
	
	public void remove(Piece piece)
	{
		remove(pieces, piece);
	}
	
	private void remove(HashMap<Coords2D, Piece> map, Piece piece)
	{
		Coords2D location2D = piece.getLocation().getCoords2D(plane);
		synchronized (map) {
			Piece removed = map.remove(location2D);
			if (removed==null) {
				System.out.println("ERROR remove() Piece not found");
			}
			
		}				
	}
	
	public void print()
	{
		System.out.println(this);
		for (Piece piece : pieces.values())
		{
			System.out.println(piece);
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
