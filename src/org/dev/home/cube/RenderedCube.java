package org.dev.home.cube;

import java.util.LinkedList;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;

public class RenderedCube extends Cube  implements GLEventListener
{
	private GLCanvas glcanvas;
	private JFrame jframe;
	private GLU glu = new GLU();
	private int canvas_width = 600;
	private int canvas_height= 600;
	private static final float cube_size = 0.25f;
	
	public RenderedCube() {
		super();
		jframe = new JFrame("Cube");
		GLProfile profile = GLProfile.get(GLProfile.GL2);
		GLCapabilities capabilities = new GLCapabilities(profile);
		glcanvas = new GLCanvas(capabilities);
		glcanvas.addGLEventListener(this);
			
		jframe.getContentPane().add(glcanvas);
		
		jframe.setSize(canvas_width, canvas_height);
		jframe.setLocationRelativeTo(null);
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setVisible(true);
		jframe.setResizable(false);
		
		glcanvas.requestFocusInWindow();
	}
	
	@Override
	public void redraw()
	{
		if (glcanvas!=null)
		{
			glcanvas.display();
		}
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
		
		LinkedList<Piece> pieces = getPieces();
		synchronized (pieces) {
			for (Piece piece : pieces)
			{			
				displayPiece(piece, gl);
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

	public void displayPiece(Piece piece, GL2 gl)
	{
		Coords3D location = piece.getLocation();
		Colours3D colours = piece.getColours();
		
		gl.glPushMatrix();//save current translations, rotations etc (to a stack)		
		gl.glTranslatef(location.x * (cube_size*2.1f), location.y * (cube_size*2.1f), location.z * (cube_size*2.1f));
		gl.glBegin( GL2.GL_QUADS ); // Start Drawing The Cubes
		gl.glColor3fv(colours.getY().getRGB(), 0 );
		gl.glVertex3f( cube_size, cube_size, (cube_size * -1) ); // Top Right Of The Quad (Top)
		gl.glVertex3f( (cube_size * -1), cube_size, (cube_size * -1)); // Top Left Of The Quad (Top)
		gl.glVertex3f( (cube_size * -1), cube_size, cube_size ); // Bottom Left Of The Quad (Top)
		gl.glVertex3f( cube_size, cube_size, cube_size ); // Bottom Right Of The Quad (Top)
		gl.glColor3fv(colours.getY().getRGB(), 0 );
		gl.glVertex3f( cube_size, (cube_size * -1), cube_size ); // Top Right Of The Quad (Bottom?) 
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), cube_size ); // Top Left Of The Quad 
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), (cube_size * -1) ); // Bottom Left Of The Quad 
		gl.glVertex3f( cube_size, (cube_size * -1), (cube_size * -1) ); // Bottom Right Of The Quad 
		gl.glColor3fv(colours.getZ().getRGB(), 0 );
		gl.glVertex3f( cube_size, cube_size, cube_size ); // Top Right Of The Quad (Front)
		gl.glVertex3f( (cube_size * -1), cube_size, cube_size ); // Top Left Of The Quad (Front)
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), cube_size ); // Bottom Left Of The Quad 
		gl.glVertex3f( cube_size, (cube_size * -1), cube_size ); // Bottom Right Of The Quad 
		gl.glColor3fv(colours.getZ().getRGB(), 0 );
		gl.glVertex3f( cube_size, (cube_size * -1), (cube_size * -1) ); // Bottom Left Of The Quad 
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), (cube_size * -1) ); // Bottom Right Of The Quad
		gl.glVertex3f( (cube_size * -1), cube_size, (cube_size * -1) ); // Top Right Of The Quad (Back)
		gl.glVertex3f( cube_size, cube_size, (cube_size * -1) ); // Top Left Of The Quad (Back)
		gl.glColor3fv(colours.getX().getRGB(), 0 );
		gl.glVertex3f( (cube_size * -1), cube_size, cube_size ); // Top Right Of The Quad (Left)
		gl.glVertex3f( (cube_size * -1), cube_size, (cube_size * -1) ); // Top Left Of The Quad (Left)
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), (cube_size * -1) ); // Bottom Left Of The Quad 
		gl.glVertex3f( (cube_size * -1), (cube_size * -1), cube_size ); // Bottom Right Of The Quad 
		gl.glColor3fv(colours.getX().getRGB(), 0 );
		gl.glVertex3f( cube_size, cube_size, (cube_size * -1) ); // Top Right Of The Quad (Right)
		gl.glVertex3f( cube_size, cube_size, cube_size ); // Top Left Of The Quad 
		gl.glVertex3f( cube_size, (cube_size * -1), cube_size ); // Bottom Left Of The Quad 
		gl.glVertex3f( cube_size, (cube_size * -1), (cube_size * -1) ); // Bottom Right Of The Quad 				
		gl.glEnd(); // Done Drawing The Quads
		
		gl.glPopMatrix();//retrieve saved translations, rotations etc (from stack)
	}

}
