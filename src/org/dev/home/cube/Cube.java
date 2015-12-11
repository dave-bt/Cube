package org.dev.home.cube;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

public class Cube extends JFrame implements GLEventListener
{
	private static final long serialVersionUID = -5890105166564384763L;
	private final LinkedList<Piece> pieces; 
	private final HashMap<Plane, Layer> layers; //same pieces, just organised in layers
	
	public Cube()
	{
		super("Cube");
		initialiseOpenGL();
		
		layers = new HashMap<Plane, Layer>(9);
		pieces = new LinkedList<>();			
		
		//centres
		addPiece(new Piece(
				new Coords3D(-1, 0, 0), 
				new Colours3D(Colour.Green, null, null)));
		
		addPiece(new Piece(
				new Coords3D(1, 0, 0), 
				new Colours3D(Colour.Blue, null, null)));
		
		addPiece(new Piece(
				new Coords3D(0, 1, 0), 
				new Colours3D(null, Colour.White, null)));
		
		addPiece(new Piece(
				new Coords3D(0, -1, 0), 
				new Colours3D(null, Colour.Yellow, null)));
		
		addPiece(new Piece(
				new Coords3D(0, 0, 1), 
				new Colours3D(null, null, Colour.Red)));
		
		addPiece(new Piece(
				new Coords3D(0, 0, -1), 
				new Colours3D(null, null, Colour.Orange)));
		
		//bottom layer
		//edges
		addPiece(new Piece(
				new Coords3D(0, -1, 1), 
				new Colours3D(null, Colour.Yellow, Colour.Red)));
		addPiece(new Piece(
				new Coords3D(0, -1, -1), 
				new Colours3D(null, Colour.Yellow, Colour.Orange)));		
		addPiece(new Piece(
				new Coords3D(1, -1, 0), 
				new Colours3D(Colour.Blue, Colour.Yellow, null)));
		addPiece(new Piece(
				new Coords3D(-1, -1, 0), 
				new Colours3D(Colour.Green, Colour.Yellow, null)));
		
		//corners
		addPiece(new Piece(
				new Coords3D(1, -1, 1), 
				new Colours3D(Colour.Blue, Colour.Yellow, Colour.Red)));
		addPiece(new Piece(
				new Coords3D(-1, -1, 1), 
				new Colours3D(Colour.Green, Colour.Yellow, Colour.Red)));				
		addPiece(new Piece(
				new Coords3D(1, -1, -1), 
				new Colours3D(Colour.Blue, Colour.Yellow, Colour.Orange)));
		addPiece(new Piece(
				new Coords3D(-1, -1, -1), 
				new Colours3D(Colour.Green, Colour.Yellow, Colour.Orange)));
		
		//middle layer
		//edges
		addPiece(new Piece(
				new Coords3D(1, 0, 1), 
				new Colours3D(Colour.Blue, null, Colour.Red)));
		addPiece(new Piece(
				new Coords3D(-1, 0, 1), 
				new Colours3D(Colour.Green, null, Colour.Red)));				
		addPiece(new Piece(
				new Coords3D(1, 0, -1), 
				new Colours3D(Colour.Blue, null, Colour.Orange)));
		addPiece(new Piece(
				new Coords3D(-1, 0, -1), 
				new Colours3D(Colour.Green, null, Colour.Orange)));

		//top layer
		//edges
		addPiece(new Piece(
				new Coords3D(0, 1, 1), 
				new Colours3D(null, Colour.White, Colour.Red)));
		addPiece(new Piece(
				new Coords3D(0, 1, -1), 
				new Colours3D(null, Colour.White, Colour.Orange)));		
		addPiece(new Piece(
				new Coords3D(1, 1, 0), 
				new Colours3D(Colour.Blue, Colour.White, null)));
		addPiece(new Piece(
				new Coords3D(-1, 1, 0), 
				new Colours3D(Colour.Green, Colour.White, null)));

		//corners
		addPiece(new Piece(
				new Coords3D(1, 1, 1), 
				new Colours3D(Colour.Blue, Colour.White, Colour.Red)));
		addPiece(new Piece(
				new Coords3D(-1, 1, 1), 
				new Colours3D(Colour.Green, Colour.White, Colour.Red)));				
		addPiece(new Piece(
				new Coords3D(1, 1, -1), 
				new Colours3D(Colour.Blue, Colour.White, Colour.Orange)));
		addPiece(new Piece(
				new Coords3D(-1, 1, -1), 
				new Colours3D(Colour.Green, Colour.White, Colour.Orange)));
		
		for (Piece piece : pieces)
		{
			piece.setHomeAsCurrentLocation();
		}
		
		shuffle();
		
		//for testing
		/*rotate(Axis.Z, 1, Angle.Ninety);
		rotate(Axis.X, 0, Angle.Ninety);
		rotate(Axis.Z, 1, Angle.Ninety);*/
		
		
	}
	
	private void addPiece(Piece piece)
	{
		//add to linked list
		synchronized (pieces) {
			pieces.add(piece);	
		}		
		
		//add to appropriate layers
		addToLayers(piece, null);		
	}
	
	//add piece to the appropriate layers
	public void addToLayers(Piece piece, Axis exclude_axis)
	{		
		Coords3D location = piece.getLocation();
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
		Coords3D location = piece.getLocation();
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
		}		
	}
	
	private static final Random random = new Random();
	public void shuffle()
	{		
		ExecutorService exec = Executors.newSingleThreadExecutor();
		
		exec.execute(new Runnable() {
			
			@Override
			public void run()
			{
				for (int i=0; i<100; i++)
				{
					int index = random.nextInt(3) - 1;
					int forward = random.nextInt(2);
					rotate(Axis.randomAxis(), index, forward==0 ? Angle.Ninety : Angle.MinusNinety);
					
					/*Cube.this.invalidate();
					Cube.this.validate();
					Cube.this.repaint();*/
					
					try {
						//Thread.sleep(1000);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			}
		});				
	}

	//GLEventListener stuff
	private GLU glu = new GLU();
	private int canvas_width = 600;
	private int canvas_height= 600;
	private float rquad=0.0f;
	
	private void initialiseOpenGL()
	{
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		GLCanvas glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
		
		super.setName("Rubik's Cube"); 
		super.getContentPane().add(glcanvas);
		
		super.setSize(canvas_width, canvas_height);
		super.setLocationRelativeTo(null);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		super.setVisible(true);
		super.setResizable(false);
		
		glcanvas.requestFocusInWindow();
		
		//final FPSAnimator animator = new FPSAnimator( glcanvas, 300, true);
	    //animator.start();
	}
	
	@Override
	public void init(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glShadeModel( GL2.GL_SMOOTH );
		gl.glClearColor( 0f, 0f, 0f, 0f );
		gl.glClearDepth( 1.0f );
		gl.glEnable( GL2.GL_DEPTH_TEST );
		gl.glDepthFunc( GL2.GL_LEQUAL );
		gl.glHint( GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST );	
	}

	
	@Override
	public void display(GLAutoDrawable drawable)
	{
		GL2 gl = drawable.getGL().getGL2();
		gl.glClear( GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );     
		gl.glLoadIdentity(); 	
		synchronized (pieces) {
			for (Piece piece : pieces)
			{			
				piece.display(gl);
			}	
		}				
		gl.glFlush();
		
	}
	

	@Override
	public void dispose(GLAutoDrawable drawable)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height)
	{
		GL2 gl = drawable.getGL().getGL2();
		if(height <=0)
		{
	         height =1;
		}
	      final float h = (float) width / (float) height;
	      gl.glViewport(0, 0, width, height);
	      gl.glMatrixMode(GL2.GL_PROJECTION);
	      gl.glLoadIdentity();
	      glu.gluPerspective(45.0f, h, 1, 1000);
	      //from side (positive z)
	      //glu.gluLookAt(0, 0, 5, 0, 0, 0, 0, 1, 0);
	      //from top (positive y)
	      //glu.gluLookAt(0, 5, 0, 0, 0, 0, 1, 0, 0);
	      glu.gluLookAt(2, 3, 5, 0, 0, 0, 0, 1, 0);
	      
	      gl.glMatrixMode(GL2.GL_MODELVIEW);
	      gl.glLoadIdentity();
		
	}
	
	
}
