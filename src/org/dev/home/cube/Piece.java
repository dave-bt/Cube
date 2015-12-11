package org.dev.home.cube;

import com.jogamp.opengl.GL2;

public class Piece {
	
	private Coords3D home; 
	private Coords3D location;
	private Colours3D colours;
	private final float cube_size = 0.25f;
	
	public Piece(Coords3D _location, Colours3D _orientation)
	{
		location = _location;
		colours = _orientation;
	}
	
	public void setHomeAsCurrentLocation()
	{
		home = location;
	}
	
	public boolean isHome()
	{
		return location.equals(home);
	}
	

	public void rotate(Axis axis, Angle angle)
	{
		int turns = angle.degrees / 90;
		for (int i=0; i<turns; i++)
		{
			locationRotateNinetyDegrees(axis);
			colours.rotateNinetyDegrees(axis);
		}
	}

	private void locationRotateNinetyDegrees(Axis axis)
	{
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

	public Coords3D getLocation() {
		return location;		
	}
	
	@Override
	public String toString() {		
		return "Piece [X=" + location.x + ", Y=" + location.y + ", Z=" + location.z + "]";
	}

	public void display(GL2 gl)
	{
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
