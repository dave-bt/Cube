package org.dev.home.cube;

import org.dev.home.cube.algo.Solver;

public class Controller {

	public static void main(String[] args)
	{
		Cube cube = new RenderedCube();
		cube.initialise();
		cube.shuffle();
		
		Solver solver = new Solver(cube);
		solver.start();
	}

}
