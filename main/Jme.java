package main;

import java.io.File;
import java.util.ArrayList;
import main.utils.DataTag;
import main.utils.Part;
import main.utils.Vars;
import com.jme3.app.SimpleApplication;
import com.jme3.collision.CollisionResults;
import com.jme3.font.BitmapText;
import com.jme3.input.MouseInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.MouseButtonTrigger;
import com.jme3.material.Material;
import com.jme3.material.RenderState.BlendMode;
import com.jme3.math.ColorRGBA;
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

public class Jme extends SimpleApplication
{
	public Geometry selectedGeometry;
	public Node shootables;
	public static Jme instance;
	public float lastTime;
	public File saveFile;
	public boolean canSave;

	public static void init()
	{
		instance = new Jme();
		instance.setSettings(new AppSettings(true));
		instance.settings.setTitle("Big Mac Modeler");
		instance.setShowSettings(false);
		instance.setDisplayFps(Vars.isDebug);
		instance.setDisplayStatView(Vars.isDebug);
	}

	public static void startJme()
	{
		instance.start();
	}

	@Override
	public void simpleInitApp()
	{
		viewPort.setBackgroundColor(ColorRGBA.White);
		initCrossHairs();

		Box floor = new Box(100.0F, 0.05F, 100.0F);
		Geometry floorG = new Geometry("Floor", floor);
		setGridTexFor(floorG);
		floorG.setLocalTranslation(0.0F, -4.0F, 0.0F);
		rootNode.attachChild(floorG);

		Box plank = new Box(16.0F, 16.0F, 16.0F);
		Geometry plankG = new Geometry("Plank", plank);
		setPlankTexFor(plankG);
		plankG.setLocalTranslation(0.0F, -20.0F, 0.0F);
		rootNode.attachChild(plankG);

		shootables = new Node("Shootables");
		rootNode.attachChild(shootables);
		for (Object[] obj : temp)
		{
			unSelect((Spatial) obj[0]);
			shootables.attachChild((Spatial) obj[0]);

			new Part((Spatial) obj[0], (boolean) obj[1], (int) obj[2], (int) obj[3]);
		}

		inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(al, "Shoot");

		cam.setFrustumFar(270);

		flyCam.setDragToRotate(true);
		flyCam.setMoveSpeed(20.0F);
		inputManager.setCursorVisible(true);

		if (temp.size() == 0) addBox(false, 0, 0);
		canSave = true;
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
					unSelect(Jme.this.selectedGeometry);
				}
			}
		}
	};

	public void unSelect(Spatial geom)
	{
		if (geom != null)
		{
			Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			Texture texture = this.assetManager.loadTexture("/images/frame.png");
			mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			mat.setTexture("ColorMap", texture);
			geom.setQueueBucket(Bucket.Transparent);
			geom.setMaterial(mat);
		}
		this.selectedGeometry = null;
	}

	public void select(Geometry geom)
	{
		if (geom != null)
		{
			Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
			Texture texture = this.assetManager.loadTexture("/images/frame_selected.png");
			mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
			mat.setTexture("ColorMap", texture);
			geom.setQueueBucket(Bucket.Transparent);
			geom.setMaterial(mat);
		}
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

	public void setPlankTexFor(Geometry geom)
	{
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = this.assetManager.loadTexture("/images/planks.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		geom.setQueueBucket(Bucket.Transparent);
		geom.setMaterial(mat);
	}

	@Override
	public void simpleUpdate(float tpf)
	{
		Vector3f loc = this.cam.getLocation();

		if (loc.x > 100 || loc.x < -100)
		{
			cam.setLocation(loc.setX(loc.x < -100 ? -100 : 100));
		}

		if (loc.y > 100 || loc.y < -100)
		{
			cam.setLocation(loc.setY(loc.y < -100 ? -100 : 100));
		}

		if (loc.z > 100 || loc.z < -100)
		{
			cam.setLocation(loc.setZ(loc.z < -100 ? -100 : 100));
		}

		if ((timer.getTimeInSeconds() - lastTime) / 60 > Options.saveMins)
		{
			lastTime = timer.getTimeInSeconds();
			save();
			Options.saveOptions();
		}

		System.gc();
	}

	public void cloneSelectedBox(boolean mirror, int xOffset, int yOffset)
	{
		Geometry geometry = (Geometry) this.selectedGeometry.deepClone();
		geometry.setName(geometry.getName() + boxID++);
		unSelect(this.selectedGeometry);
		select(geometry);
		shootables.attachChild(geometry);

		new Part(geometry, mirror, xOffset, yOffset);
	}

	public void addBox(boolean mirror, int xOffset, int yOffset)
	{
		Box box = new Box(1.0F, 1.0F, 1.0F);
		Geometry geometry = new Geometry("box" + boxID++, box);
		select(geometry);
		shootables.attachChild(geometry);

		new Part(geometry, mirror, xOffset, yOffset);
	}

	public void removeSelectedBox()
	{
		if (selectedGeometry != null)
		{
			shootables.detachChild(selectedGeometry);
			Part.getPartFor(selectedGeometry).remove();
		}
	}

	public void setSelectedBoxVars(String name, float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ, boolean mirror, int xOffset, int yOffset, float rotX, float rotY, float rotZ)
	{
		if (selectedGeometry != null)
		{
			selectedGeometry.setLocalTranslation(posX, posY, posZ);
			selectedGeometry.setLocalScale(sizeX, sizeY, sizeZ);
			selectedGeometry.setLocalRotation(new Quaternion(Options.setRotation(rotX), Options.setRotation(rotY), Options.setRotation(rotZ), 1));
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

	public void load()
	{
		DataTag tag = new DataTag(saveFile);
		int size = tag.getInteger("Size");

		if (size < 1) return;

		for (int i = 0; i < size; i++)
		{
			DataTag tag1 = tag.getTag("Tag-" + i);

			Box box = new Box(tag1.getFloat("Geom Width"), tag1.getFloat("Geom Height"), tag1.getFloat("Geom Length"));
			Geometry geometry = new Geometry(tag1.getString("Geom Name"), box);
			geometry.setLocalRotation((Quaternion) tag1.getSerializable("Geom Rotation"));
			geometry.setLocalScale((Vector3f) tag1.getSerializable("Geom Scale"));
			geometry.setLocalTranslation((Vector3f) tag1.getSerializable("Geom Position"));
			temp.add(new Object[] {
			geometry, tag1.getBoolean("Mirror"), tag1.getInteger("X Offset"), tag1.getInteger("Y Offset")
			});
		}
	}

	public void save()
	{
		DataTag tag = new DataTag(saveFile);
		tag.setInteger("Size", shootables.getQuantity());

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Spatial spat = shootables.getChild(i);
			DataTag tag1 = new DataTag(tag);

			tag1.setBoolean("Mirror", Part.getPartFor(spat).isMirror());
			tag1.setInteger("X Offset", Part.getPartFor(spat).getXOffset());
			tag1.setInteger("Y Offset", Part.getPartFor(spat).getYOffset());
			tag1.setString("Geom Name", spat.getName());
			tag1.setFloat("Geom Width", ((Box) ((Geometry) spat).getMesh()).xExtent);
			tag1.setFloat("Geom Height", ((Box) ((Geometry) spat).getMesh()).yExtent);
			tag1.setFloat("Geom Length", ((Box) ((Geometry) spat).getMesh()).zExtent);
			tag1.setSerializable("Geom Rotation", spat.getLocalRotation());
			tag1.setSerializable("Geom Scale", spat.getLocalScale());
			tag1.setSerializable("Geom Position", spat.getLocalTranslation());

			tag.setTag("Tag-" + i, tag1);
		}
	}

	private static ArrayList<Object[]> temp = new ArrayList<Object[]>();
}
