package org.dev.home.cube;

public class Plane
{
	private Axis axis;
	private int value;
	
	public Plane(Axis _axis, int _value)
	{
		axis = _axis;
		value = _value;			
	}

	public Axis getAxis()
	{
		return axis;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((axis == null) ? 0 : axis.hashCode());
		result = prime * result + value;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Plane other = (Plane) obj;
		if (axis != other.axis)
			return false;
		if (value != other.value)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return axis + "=" + value;
				
	}
	
	
}
