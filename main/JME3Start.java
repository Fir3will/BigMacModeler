package main;

import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.Quaternion;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.system.AppSettings;
import com.jme3.texture.Texture;

public class JME3Start extends SimpleApplication
{
	public Geometry selectedGeometry;
	public Node shootables;
	public static JME3Start instance;

	public static void init()
	{
		instance = new JME3Start();
		instance.setSettings(new AppSettings(true));
		instance.settings.setResolution(Options.getXResolution(), Options.getYResolution());
		instance.settings.setTitle("Modeler");
		instance.setShowSettings(false);
		instance.setDisplayFps(false);
		instance.setDisplayStatView(false);
	}

	public static void startJME3()
	{
		instance.start();
	}

	@Override
	public void simpleInitApp()
	{
		initCrossHairs();

		shootables = new Node("Shootables");

		Box floor = new Box(50.0F, 0.05F, 50.0F);
		Geometry floorG = new Geometry("Floor", floor);
		setGridTexFor(floorG);
		floorG.setLocalTranslation(0.0F, -4.0F, 0.0F);
		rootNode.attachChild(floorG);
		rootNode.attachChild(shootables);

		inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(al, "Shoot");

		cam.setFrustumFar(270);

		flyCam.setDragToRotate(true);
		flyCam.setMoveSpeed(20.0F);
		inputManager.setCursorVisible(true);
	}

	private final ActionListener al = new ActionListener()
	{
		@Override
		public void onAction(String name, boolean isPressed, float tpf)
		{
			if (name.equals("Shoot") && !isPressed)
			{
				CollisionResults results = new CollisionResults();
				shootables.collideWith(new Ray(cam.getLocation(), cam.getDirection()), results);

				for (int i = 0; i < shootables.getQuantity(); i++)
				{
					unSelect(shootables.getChild(i));
				}
				if (results.getClosestCollision() != null)
				{
					select(results.getClosestCollision().getGeometry());
				}
				if (results.size() < 1)
				{
					JME3Start.this.selectedGeometry = null;
				}
			}
		}
	};

	public void unSelect(Spatial geom)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = this.assetManager.loadTexture("/images/frame.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		geom.setQueueBucket(Bucket.Transparent);
		geom.setMaterial(mat);
	}

	public void select(Geometry geom)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = this.assetManager.loadTexture("/images/frame_selected.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		geom.setQueueBucket(Bucket.Transparent);
		geom.setMaterial(mat);
		this.selectedGeometry = geom;
	}

	public void setGridTexFor(Geometry geom)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = this.assetManager.loadTexture("/images/grid.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		geom.setQueueBucket(Bucket.Transparent);
		geom.setMaterial(mat);
	}

	@Override
	public void simpleUpdate(float tpf)
	{
		Vector3f loc = this.cam.getLocation();

		if (loc.x > 50 || loc.x < -50)
		{
			cam.setLocation(loc.setX(loc.x < -50 ? -50 : 50));
		}

		if (loc.y > 50 || loc.y < -50)
		{
			cam.setLocation(loc.setY(loc.y < -50 ? -50 : 50));
		}

		if (loc.z > 50 || loc.z < -50)
		{
			cam.setLocation(loc.setZ(loc.z < -50 ? -50 : 50));
		}
	}

	public void addBox(boolean mirror, int xOffset, int yOffset)
	{
		Box box = new Box(1.0F, 1.0F, 1.0F);
		Geometry geometry = new Geometry("box" + boxID++, box);
		unSelect(geometry);
		rootNode.attachChild(geometry);
		shootables.attachChild(geometry);

		new Part(geometry, mirror, xOffset, yOffset);
	}

	public void removeSelectedBox()
	{
		if (selectedGeometry != null)
		{
			selectedGeometry.removeFromParent();
		}
	}

	public void setSelectedBoxVars(String name, float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ, boolean mirror, int xOffset, int yOffset, float rotX, float rotY, float rotZ)
	{
		if (selectedGeometry != null)
		{
			selectedGeometry.setLocalTranslation(posX, posY, posZ);
			selectedGeometry.setLocalScale(sizeX, sizeY, sizeZ);
			selectedGeometry.setLocalRotation(new Quaternion(rotX, rotY, rotZ, 1));
			selectedGeometry.setName(name);
			Part.getPartFor(selectedGeometry).setMirror(mirror);
			Part.getPartFor(selectedGeometry).setXOffset(xOffset);
			Part.getPartFor(selectedGeometry).setYOffset(yOffset);
		}
	}

	protected void initCrossHairs()
	{
		setDisplayStatView(false);
		guiFont = assetManager.loadFont("Interface/Fonts/Default.fnt");
		BitmapText ch = new BitmapText(guiFont, false);
		ch.setSize(guiFont.getCharSet().getRenderedSize() * 2);
		ch.setText("+");
		ch.setLocalTranslation(settings.getWidth() / 2 - ch.getLineWidth() / 2, settings.getHeight() / 2 + ch.getLineHeight() / 2, 0);
		guiNode.attachChild(ch);
	}

	public void generateCode()
	{
		Options.generateCode(shootables);
	}

	private static int boxID = 1;
}
