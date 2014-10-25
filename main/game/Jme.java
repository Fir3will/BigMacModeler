package main.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import main.Main;
import main.game.saving.DataTag;
import main.game.saving.Savable;
import main.game.utils.Vars;
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
import com.jme3.renderer.queue.RenderQueue.Bucket;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;

public class Jme extends SimpleApplication implements Savable
{
	public Part selectedPart;
	public Node allParts;
	public static Jme instance;
	public float lastTime;
	public boolean canSave;

	public static void init()
	{
		instance = new Jme();
		instance.setShowSettings(false);
		instance.setDisplayFps(Vars.isDebug);
		instance.setDisplayStatView(Vars.isDebug);
	}

	@Override
	public void simpleInitApp()
	{
		viewPort.setBackgroundColor(ColorRGBA.White);
		initCrossHairs();

		Box floor = new Box(100.0F, 0.05F, 100.0F);
		Geometry floorG = new Geometry("Floor", floor);
		Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture = this.assetManager.loadTexture("/images/grid.png");
		mat.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat.setTexture("ColorMap", texture);
		floorG.setQueueBucket(Bucket.Transparent);
		floorG.setMaterial(mat);
		floorG.setLocalTranslation(0.0F, -4.0F, 0.0F);
		rootNode.attachChild(floorG);

		Box plank = new Box(16.0F, 16.0F, 16.0F);
		Geometry plankG = new Geometry("Plank", plank);
		Material mat2 = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
		Texture texture2 = this.assetManager.loadTexture("/images/planks.png");
		mat2.getAdditionalRenderState().setBlendMode(BlendMode.Alpha);
		mat2.setTexture("ColorMap", texture2);
		plankG.setQueueBucket(Bucket.Transparent);
		plankG.setMaterial(mat2);

		plankG.setLocalTranslation(0.0F, -20.0F, 0.0F);
		rootNode.attachChild(plankG);

		allParts = new Node("Shootables");
		rootNode.attachChild(allParts);

		inputManager.addMapping("Shoot", new MouseButtonTrigger(MouseInput.BUTTON_LEFT));
		inputManager.addListener(al, "Shoot");

		cam.setFrustumFar(270);

		flyCam.setDragToRotate(true);
		flyCam.setMoveSpeed(20.0F);
		inputManager.setCursorVisible(true);

		if (temp.size() <= 0) addBox(false, 0, 0);
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
				allParts.collideWith(new Ray(cam.getLocation(), cam.getDirection()), results);

				for (int i = 0; i < allParts.getQuantity(); i++)
				{
					if (allParts.getChild(i) instanceof Part) unSelect((Part) allParts.getChild(i));
				}
				if (results.getClosestCollision() != null && results.getClosestCollision().getGeometry() instanceof Part)
				{
					select((Part) results.getClosestCollision().getGeometry());
				}
				if (results.size() < 1 && selectedPart != null)
				{
					unSelect(selectedPart);
				}
			}
		}
	};

	public void unSelect(Part part)
	{
		if (part != null)
		{
			part.unSelect(assetManager);
		}
		this.selectedPart = null;
		AddBox.instance.selectedPart(selectedPart);
	}

	public void select(Part part)
	{
		this.selectedPart = part == null ? null : part.select(assetManager);
		AddBox.instance.selectedPart(selectedPart);
	}

	@Override
	public void simpleUpdate(float tpf)
	{
		System.gc();
		AddBox.instance.update();
	}

	public void cloneSelectedBox()
	{
		Part geometry = this.selectedPart.deepClone();
		geometry.setName(geometry.getName() + boxID++);
		unSelect(this.selectedPart);
		select(geometry);
		allParts.attachChild(geometry);
	}

	public void addBox(boolean mirror, int xOffset, int yOffset)
	{
		Box box = new Box(1.0F, 1.0F, 1.0F);
		Part geometry = new Part("box" + boxID++, box);
		select(geometry);
		allParts.attachChild(geometry);

		geometry.setMirror(mirror);
		geometry.setXOffset(xOffset);
		geometry.setYOffset(xOffset);
	}

	public void removeSelectedBox()
	{
		if (selectedPart != null)
		{
			allParts.detachChild(selectedPart);
			rootNode.detachChild(selectedPart);
		}
	}

	public void setSelectedBoxVars(String name, float posX, float posY, float posZ, float sizeX, float sizeY, float sizeZ, boolean mirror, int xOffset, int yOffset, float rotX, float rotY, float rotZ)
	{
		if (selectedPart != null)
		{
			selectedPart.setLocalTranslation(posX, posY, posZ);
			selectedPart.setLocalScale(sizeX, sizeY, sizeZ);
			selectedPart.setLocalRotation(new Quaternion(rotX, rotY, rotZ, 1));
			selectedPart.setName(name);
			selectedPart.setMirror(mirror);
			selectedPart.setXOffset(xOffset);
			selectedPart.setYOffset(yOffset);
		}
	}

	public void initCrossHairs()
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
		Options.generateCode(allParts);
	}

	public void createImage()
	{
		try
		{
			BufferedImage image = new BufferedImage(512, 256, BufferedImage.TYPE_INT_RGB);
			Graphics g = image.getGraphics();
			g.setColor(Color.WHITE);
			g.clearRect(0, 0, 512, 256);
			drawTo(g);
			ImageIO.write(image, "png", new File(Main.getModelsDir(), "texture.png"));
			System.out.println("Created Image At: " + new File(Main.getModelsDir(), "texture.png").getPath());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void drawTo(Graphics g)
	{
		List<Spatial> parts = Jme.instance.allParts.getChildren();
		for (Spatial sp : parts)
		{
			Part part = (Part) sp;
			g.setColor(part == selectedPart ? Color.RED : Color.GREEN);
			int x = part.getXOffset();
			int y = part.getYOffset();
			int width = (int) part.getLocalScale().x;
			int height = (int) part.getLocalScale().y;

			g.drawRect(x, y, width, height);
		}
	}

	@Override
	public void loadFromTag(DataTag tag)
	{
	}

	@Override
	public void saveToTag(DataTag tag)
	{
	}

	private static int boxID = 1;
	private static ArrayList<Object[]> temp = new ArrayList<Object[]>();
}
