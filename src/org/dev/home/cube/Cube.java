package org.dev.home.cube;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.dev.home.cube.types.Angle;
import org.dev.home.cube.types.Axis;
import org.dev.home.cube.types.Colour;
import org.dev.home.cube.types.Colours3D;
import org.dev.home.cube.types.Coords3D;

public class Cube
{
	private final HashMap<Position, Piece> pieces;//stored by home position 
	private final HashMap<Plane, Layer> layers; //same pieces, just organised in layers
	private static final Random random = new Random();
	
	public Cube()
	{		
		layers = new HashMap<Plane, Layer>(9);
		pieces = new HashMap<Position, Piece>(27);
	}
	
	public void initialise()
	{
		//centres
		addPiece(new Piece(new Coords3D(-1, 0, 0), new Colours3D(Colour.Green, null, null)));	
		addPiece(new Piece(new Coords3D(1, 0, 0), new Colours3D(Colour.Blue, null, null)));
		addPiece(new Piece(new Coords3D(0, 1, 0), new Colours3D(null, Colour.White, null)));
		addPiece(new Piece(new Coords3D(0, -1, 0), new Colours3D(null, Colour.Yellow, null)));
		addPiece(new Piece(new Coords3D(0, 0, 1), new Colours3D(null, null, Colour.Red)));
		addPiece(new Piece(new Coords3D(0, 0, -1), new Colours3D(null, null, Colour.Orange)));
		
		//bottom layer*********
		//edges
		addPiece(new Piece(new Coords3D(0, -1, 1), new Colours3D(null, Colour.Yellow, Colour.Red)));
		addPiece(new Piece(new Coords3D(0, -1, -1), new Colours3D(null, Colour.Yellow, Colour.Orange)));		
		addPiece(new Piece(new Coords3D(1, -1, 0), new Colours3D(Colour.Blue, Colour.Yellow, null)));
		addPiece(new Piece(new Coords3D(-1, -1, 0), new Colours3D(Colour.Green, Colour.Yellow, null)));
		
		//corners
		addPiece(new Piece(new Coords3D(1, -1, 1), new Colours3D(Colour.Blue, Colour.Yellow, Colour.Red)));
		addPiece(new Piece(new Coords3D(-1, -1, 1), new Colours3D(Colour.Green, Colour.Yellow, Colour.Red)));				
		addPiece(new Piece(new Coords3D(1, -1, -1),	new Colours3D(Colour.Blue, Colour.Yellow, Colour.Orange)));
		addPiece(new Piece(new Coords3D(-1, -1, -1), new Colours3D(Colour.Green, Colour.Yellow, Colour.Orange)));
		
		//middle layer************
		//edges
		addPiece(new Piece(new Coords3D(1, 0, 1), new Colours3D(Colour.Blue, null, Colour.Red)));
		addPiece(new Piece(new Coords3D(-1, 0, 1), new Colours3D(Colour.Green, null, Colour.Red)));				
		addPiece(new Piece(new Coords3D(1, 0, -1), new Colours3D(Colour.Blue, null, Colour.Orange)));
		addPiece(new Piece(new Coords3D(-1, 0, -1), new Colours3D(Colour.Green, null, Colour.Orange)));

		//top layer**************
		//edges
		addPiece(new Piece(new Coords3D(0, 1, 1), new Colours3D(null, Colour.White, Colour.Red)));
		addPiece(new Piece(new Coords3D(0, 1, -1), new Colours3D(null, Colour.White, Colour.Orange)));		
		addPiece(new Piece(new Coords3D(1, 1, 0), new Colours3D(Colour.Blue, Colour.White, null)));
		addPiece(new Piece(new Coords3D(-1, 1, 0), new Colours3D(Colour.Green, Colour.White, null)));

		//corners
		addPiece(new Piece(new Coords3D(1, 1, 1), new Colours3D(Colour.Blue, Colour.White, Colour.Red)));
		addPiece(new Piece(new Coords3D(-1, 1, 1), new Colours3D(Colour.Green, Colour.White, Colour.Red)));				
		addPiece(new Piece(new Coords3D(1, 1, -1), new Colours3D(Colour.Blue, Colour.White, Colour.Orange)));
		addPiece(new Piece(new Coords3D(-1, 1, -1),	new Colours3D(Colour.Green, Colour.White, Colour.Orange)));		
		
		draw();
	}
	
	protected Collection<Piece> getPieces()
	{
		return pieces.values();
	}
	
	/* returns the piece with the requested Home position */
	public Piece findPiece(Position position)
	{
		return pieces.get(position);
	}
	
	private void addPiece(Piece piece)
	{
		//add to linked list
		synchronized (pieces) {
			pieces.put(piece.getHomePosition(), piece);	
		}		
		
		//add to appropriate layers
		addToLayers(piece, null);		
	}
	
	//add piece to the appropriate layers
	public void addToLayers(Piece piece, Axis exclude_axis)
	{		
		Coords3D location = piece.getCurrentPosition().getLocation();
		Plane plane;
		//add to the appropriate x plane layer
		if (exclude_axis!=Axis.X)
		{
			plane = new Plane(Axis.X, location.x);
			addToLayer(plane, piece);
		}
		
		//add to the appropriate y plane layer
		if (exclude_axis!=Axis.Y)
		{
			plane = new Plane(Axis.Y, location.y);
			addToLayer(plane, piece);
		}
		
		//add to the appropriate z plane layer 
		if (exclude_axis!=Axis.Z)
		{
			plane = new Plane(Axis.Z, location.z);
			addToLayer(plane, piece);
		}
	}
	
	private void addToLayer(Plane plane, Piece piece)
	{
		Layer layer = layers.get(plane);
		if (layer==null) {
			layer = new Layer(plane, this);
			layers.put(plane, layer);
		}
		synchronized (layer) {
			layer.add(piece);	
		}		
		//System.out.println("After adding " + piece + "... " + layer);
	}
	
	public void removeFromLayers(Piece piece, Axis exclude_axis)
	{
		Coords3D location = piece.getCurrentPosition().getLocation();
		Plane plane;
		//remove from the appropriate x plane layer
		if (exclude_axis!=Axis.X)
		{
			plane = new Plane(Axis.X, location.x);
			removeFromLayer(plane, piece);
		}
		
		//remove from the appropriate y plane layer
		if (exclude_axis!=Axis.Y)
		{
			plane = new Plane(Axis.Y, location.y);
			removeFromLayer(plane, piece);
		}
		
		//remove from the appropriate z plane layer
		if (exclude_axis!=Axis.Z)
		{
			plane = new Plane(Axis.Z, location.z);
			removeFromLayer(plane, piece);
		}
	}
	
	private void removeFromLayer(Plane plane, Piece piece)
	{
		Layer layer = layers.get(plane);
		if (layer!=null) {
			synchronized (layer) {
				layer.remove(piece);	
			}			
		}
		//System.out.println("After removing " + piece + "... " + layer);
	}
	
	public boolean rotateUntilBestAligned(Piece piece, Axis axis)
	{
		//TODO
	}
	
	public void rotate(Axis axis, int index, Angle angle)
	{
		Plane plane = new Plane(axis, index);
		System.out.println("Rotating " + plane + " by " + angle);
		rotate(plane, angle);
	}	

	public void rotate(Plane plane, Angle angle)
	{
		Layer layer = layers.get(plane);
		if (layer!=null)
		{
			layer.rotate(angle);
			draw();
		}		
	}
	
	public void shuffle()
	{		
		/*ExecutorService exec = Executors.newSingleThreadExecutor();
		
		exec.execute(new Runnable() {
			
			@Override
			public void run()
			{*/
				for (int i=0; i<25; i++)
				{
					int index = random.nextInt(3) - 1;
					int forward = random.nextInt(2);
					rotate(Axis.randomAxis(), index, forward==0 ? Angle.Ninety : Angle.MinusNinety);																				
				}
			/*	
			}
		});		*/		
	}

	public void draw()
	{
		//sub-classes can do more with this method!
	}


	
}
