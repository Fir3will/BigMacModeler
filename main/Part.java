package main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import com.jme3.scene.Spatial;

public class Part implements Serializable, Cloneable
{
	private Spatial spatial;
	private boolean mirror;
	private int xOffset, yOffset;

	public Part(Spatial spatial, boolean mirror, int xOffset, int yOffset)
	{
		if (getPartFor(spatial) == null)
		{
			setSpatial(spatial);
			setMirror(mirror);
			setXOffset(xOffset);
			setYOffset(yOffset);

			parts.add(this);
		}
	}

	public boolean isMirror()
	{
		return mirror;
	}

	public void setMirror(boolean mirror)
	{
		this.mirror = mirror;
	}

	public int getXOffset()
	{
		return xOffset;
	}

	public void setXOffset(int xOffset)
	{
		this.xOffset = xOffset;
	}

	public int getYOffset()
	{
		return yOffset;
	}

	public void setYOffset(int yOffset)
	{
		this.yOffset = yOffset;
	}

	public Spatial getSpatial()
	{
		return spatial;
	}

	public void setSpatial(Spatial spatial)
	{
		this.spatial = spatial;
	}

	@Override
	public String toString()
	{
		return getSpatial().getName();
	}

	public static Part getPartFor(Spatial spatial)
	{
		for (int i = 0; i < parts.size(); i++)
		{
			if (parts.get(i).getSpatial() == spatial)
			{
				return parts.get(i);
			}
		}

		return null;
	}

	public static List<Part> getAllParts()
	{
		return Collections.unmodifiableList(parts);
	}

	private static final long serialVersionUID = 1L;
	private static final ArrayList<Part> parts = new ArrayList<Part>();
}
