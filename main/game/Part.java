package main.game;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Quaternion;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Part extends Geometry
{
	private boolean mirror, isSelected;
	private int xOffset, yOffset;

	public Part()
	{
		super();
	}

	public Part(String name, Mesh mesh)
	{
		super(name, mesh);
	}

	public Part(String name)
	{
		super(name);
	}

	@Override
	public void setLocalRotation(Quaternion q)
	{
		float x = (float) (Options.inDegrees ? Math.toRadians(q.getX()) : q.getX());
		float y = (float) (Options.inDegrees ? Math.toRadians(q.getY()) : q.getY());
		float z = (float) (Options.inDegrees ? Math.toRadians(q.getZ()) : q.getZ());
		q = new Quaternion(x, y, z, q.getW());
		super.setLocalRotation(q);
	}

	public boolean isMirrored()
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

	public boolean isSelected()
	{
		return isSelected;
	}

	public void setSelected(boolean isSelected)
	{
		this.isSelected = isSelected;
	}

	public Part select(AssetManager assetManager)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = assetManager.loadTexture("/images/frame_selected.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		setQueueBucket(Bucket.Transparent);
		setMaterial(mat);
		return this;
	}

	public Part unSelect(AssetManager assetManager)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = assetManager.loadTexture("/images/frame.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		setQueueBucket(Bucket.Transparent);
		setMaterial(mat);
		return null;
	}

	@Override
	public Box getMesh()
	{
		return mesh instanceof Box ? (Box) mesh : null;
	}

	@Override
	public Part deepClone()
	{
		Part p = (Part) super.deepClone();
		p.setMirror(isMirrored());
		p.setXOffset(getXOffset());
		p.setYOffset(getYOffset());
		return p;
	}
}
