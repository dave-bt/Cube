package org.dev.home.cube.algo;

import java.util.LinkedList;

import org.dev.home.cube.Cube;
import org.dev.home.cube.Piece;
import org.dev.home.cube.Plane;
import org.dev.home.cube.Position;
import org.dev.home.cube.types.Angle;
import org.dev.home.cube.types.Axis;
import org.dev.home.cube.types.Colour;
import org.dev.home.cube.types.Colours3D;
import org.dev.home.cube.types.Coords3D;

public class Solver {

	private final Cube cube;
	
	public Solver(Cube _cube)
	{
		cube = _cube;
	}
	
	public void start()
	{
		//position white centre *****
		Piece p1 = cube.findPiece(new Position(new Coords3D(0, 1, 0), new Colours3D(null, Colour.White, null)));
		//find common planes
		LinkedList<Plane> planes = p1.getCommonPlanes();
		//rotate common plane till home (note doesn't mater which one we use here
		while (!p1.isHome())
		{			
			cube.rotate(planes.getFirst(), Angle.Ninety);
		}
		
		solveTopLayerCross();
		
	}
	
	
	
	
	private void solveTopLayerCross()
	{
		//y=0 plane cross****
		LinkedList<Piece> cross_pieces = new LinkedList<Piece>();
		cross_pieces.add(cube.findPiece(new Position(new Coords3D(0, 1, 1), new Colours3D(null, Colour.White, Colour.Red))));
		cross_pieces.add(cube.findPiece(new Position(new Coords3D(0, 1, -1), new Colours3D(null, Colour.White, Colour.Orange))));
		cross_pieces.add(cube.findPiece(new Position(new Coords3D(1, 1, 0), new Colours3D(Colour.Blue, Colour.White, null))));
		cross_pieces.add(cube.findPiece(new Position(new Coords3D(-1, 1, 0), new Colours3D(Colour.Green, Colour.White, null))));

		//try and get one piece in position to start with
		int rotations=0;
		while (countHomes(cross_pieces) == 0 && rotations<3)
		{
			cube.rotate(new Plane(Axis.Y, 1), Angle.Ninety);
			rotations++;
		}	
		
		//iterate through the four pieces choosing appropriate algo based on where it is 
		for (Piece piece : cross_pieces)
		{
			Coords3D location_current = piece.getCurrentPosition().getLocation();
			Coords3D location_home = piece.getHomePosition().getLocation();
			Colours3D colours = piece.getCurrentPosition().getColours();
			switch (location_current.y)
			{
			case 1:
				switch (colours.getX())
				{
				case White :
					if (location_current.equals(location_home))
					{
						break;
					}
					else
					{
						//piece is oriented correctly but not in right place on top layer
						
					}
				default:
					break;					
				}
				break;
			}
		}
		
	}
	
	
	
	
	private int countHomes(LinkedList<Piece> pieces)
	{
		int i=0;
		for(Piece piece : pieces)
		{
			if (piece.isHome())
			{
				i++;
			}
		}
		return i;
	}
	
}
