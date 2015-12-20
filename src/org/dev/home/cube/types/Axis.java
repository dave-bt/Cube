package org.dev.home.cube.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public enum Axis
{
	X, Y, Z;
	
	private static final List<Axis> values = Collections.unmodifiableList(Arrays.asList(values()));	
	private static final int size = values.size();
	private static final Random random = new Random();

	public static Axis randomAxis()
	{
	    return values.get(random.nextInt(size));
	}
}
