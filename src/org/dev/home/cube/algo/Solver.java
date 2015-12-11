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

		//try and get one piece in position to start with. TODO might turn 3 times for nothing here!!
		int rotations=0;
		while (countHomes(cross_pieces) == 0 && rotations<3)
		{
			cube.rotate(new Plane(Axis.Y, 1), Angle.Ninety);
			rotations++;
		}	
		
		//iterate through the four pieces choosing appropriate algo based on which layer it is in 
		for (Piece piece : cross_pieces)
		{
			Coords3D location_current = piece.getCurrentPosition().getLocation();
			Coords3D location_home = piece.getHomePosition().getLocation();
			Colours3D colours = piece.getCurrentPosition().getColours();
			if (!piece.isHome())
			{
				switch (location_current.y)
				{
				case 1:					
					//turn piece to middle layer
					if (location_current.x!=0)
					{
						cube.rotate(new Plane(Axis.X, location_current.x), Angle.Ninety);
					}
					else
					{
						cube.rotate(new Plane(Axis.Z, location_current.z), Angle.Ninety);
					}
					solveTopLayerCross_MiddleLayerPiece(piece);
					break;					
				case 0:
					solveTopLayerCross_MiddleLayerPiece(piece);
					break;
				case -1:
					//rotate until lined up directly underneath home position
					while (location_current.x!=location_home.x || location_current.z!=location_home.z)
					{
						cube.rotate(new Plane(Axis.Y, -1), Angle.Ninety);
					}
					if (colours.getY()==Colour.White)
					{
						//turn piece to top layer
						if (location_current.x!=0)
						{
							cube.rotate(new Plane(Axis.X, location_current.x), Angle.OneEighty);
						}
						else
						{
							cube.rotate(new Plane(Axis.Z, location_current.z), Angle.OneEighty);
						}
					}
					else
					{
						//rotate to middle layer 
						if (location_current.x!=0)
						{
							cube.rotate(new Plane(Axis.X, location_current.x), Angle.MinusNinety);
						}
						else
						{
							cube.rotate(new Plane(Axis.Z, location_current.z), Angle.MinusNinety);
						}
						solveTopLayerCross_MiddleLayerPiece(piece);
					}
					break;
				}		
			}
		}
	}
	
	private void solveTopLayerCross_MiddleLayerPiece(Piece piece)
	{
		Coords3D location_current = piece.getCurrentPosition().getLocation();
		Coords3D location_home = piece.getHomePosition().getLocation();
		Colours3D colours_current = piece.getCurrentPosition().getColours();
		
		if(location_home.x!=0)
		{
			//rotate piece until it is in the correct z plane and has white on
			//it's z face
			while (location_current.x!=location_home.x || colours_current.getZ()!=Colour.White)
			{
				cube.rotate(new Plane(Axis.Y, 0), Angle.Ninety);
			}
			//turn up to top layer
			if (location_current.z==1)
			{
				cube.rotate(new Plane(Axis.X, location_current.x), Angle.Ninety);
			}
			else
			{
				cube.rotate(new Plane(Axis.X, location_current.x), Angle.MinusNinety);
			}
		}
		else
		{
			//rotate piece until it is in the correct x plane and has white on
			//it's x face
			while (location_current.z!=location_home.z || colours_current.getX()!=Colour.White)
			{
				cube.rotate(new Plane(Axis.Y, 0), Angle.Ninety);
			}
			//turn up to top layer
			if (location_current.x==1)
			{
				cube.rotate(new Plane(Axis.Z, location_current.z), Angle.MinusNinety);
			}
			else
			{
				cube.rotate(new Plane(Axis.Z, location_current.z), Angle.Ninety);
			}
		}
 	}
	
	/* gets the 
	private Plane getNonZeroOrthogonalPlane(Piece piece, Plane plane)
	{
		if (location_current.x!=0)
		{
			new Plane(Axis.X, location_current.x);
		}
		else
		{
			cube.rotate(new Plane(Axis.Y, location_current.y), Angle.Ninety);
		}
	}*/
	
	
	
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
