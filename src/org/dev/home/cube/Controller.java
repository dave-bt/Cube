package org.dev.home.cube;

public class Controller {

	public static void main(String[] args)
	{
		Cube cube = new RenderedCube();
		cube.initialise();
		cube.shuffle();
	}

}
