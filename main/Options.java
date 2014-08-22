package main;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Options extends JPanel
{
	public static String fileName = "";
	public static final JFrame thisFrame;
	public static File file;

	static
	{
		thisFrame = new JFrame("Options");
		thisFrame.add(new Options());
		thisFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		thisFrame.setSize(600, 400);
		thisFrame.setLocationRelativeTo(null);
		thisFrame.setResizable(false);
		thisFrame.setVisible(false);
		thisFrame.pack();
	}

	public Options()
	{
		super(new BorderLayout());

		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel midPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new BorderLayout());

		new JLabel("The Model is going to, almost always, seem MUCH smaller in game!");
		new JLabel("Adding a new box will always be in the middle and a 1x1x1 Cube!");
		new JLabel("The Rotation isn't by Degrees, But actually by Radian! So 2 PI is a full rotation!");
		new JLabel("FINALLY! A MODELER FOR MAC AND LINUX!");
		new JLabel("Look at that Spider GO!");
		new JLabel("Cloning a box doesn't clone the exact name!");
		new JLabel("The '+' in the middle of the screen is how you select Boxes!");
		new JLabel("I'm always open to suggestions! Leave a comment on my MCF Topic!");
		new JLabel("Got a Crash? Get the file in the Logs folder and send that to me with a quick Before and After!");
		new JLabel("A 1x1x1 Box is just One Texture Pixel in Game!");
		new JLabel("Use the Plank below the surface for a Guide on how it'll look!");
		new JLabel("Let me know any questions you have reguarding function and features!");
		new JLabel("Sorry about no Texture Map, I will try my best to get that in future updates!");
		new JLabel("YOU'VE BEEN WAITING FOREVER! I KNOW! I CAN'T BELIEVE IT EITHER!");
		new JLabel("Gotta LOVE the Name!");

		midPanel.add(new JLabel("No Options available yet! Sorry! Nothing to customize really..."));

		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public static void generateCode(Node shootables)
	{
		String path = file.getPath();
		System.err.println("Generating Code at: " + path);

		if (!Files.exists(Paths.get(path)))
		{
			FileHelper.createFile(path);
		}
		else
		{
			FileHelper.resetFile(path);
		}

		FileHelper.writeToFile(path, "/*");
		FileHelper.writeToFile(path, " * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FileHelper.writeToFile(path, " * AUTOMATICALLY GENERATED FILE!");
		FileHelper.writeToFile(path, " * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FileHelper.writeToFile(path, " * Generated using Big Mac Modeler!");
		FileHelper.writeToFile(path, " * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FileHelper.writeToFile(path, " * The Open Source Modeler using Java!");
		FileHelper.writeToFile(path, " * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FileHelper.writeToFile(path, " * ~Multi-Platform~ Mac, PC, and (maybe) Linux!");
		FileHelper.writeToFile(path, " * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
		FileHelper.writeToFile(path, " */");
		FileHelper.writeToFile(path, "package models;");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelBase;");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelRenderer;");
		FileHelper.writeToFile(path, "import net.minecraft.entity.Entity;");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.Side;");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.SideOnly;");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "@SideOnly(Side.CLIENT)");
		FileHelper.writeToFile(path, "public class " + file.getName().replaceAll(".java", "") + " extends ModelBase");
		FileHelper.writeToFile(path, "{");

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Spatial geom = shootables.getChild(i);
			FileHelper.writeToFile(path, "\tprivate ModelRenderer " + geom.getName() + ";");
		}

		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "\tpublic " + file.getName().replaceAll(".java", "") + "()");
		FileHelper.writeToFile(path, "\t{");
		FileHelper.writeToFile(path, "\t\ttextureWidth = 512;");
		FileHelper.writeToFile(path, "\t\ttextureHeight = 256;");

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Spatial geom = shootables.getChild(i);
			Vector3f loc = geom.getLocalTranslation();
			Vector3f scale = geom.getLocalScale();
			Part part = Part.getPartFor(geom);

			FileHelper.writeToFile(path, "");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + " = new ModelRenderer(this, " + part.getXOffset() + ", " + part.getYOffset() + ");");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".addBox(" + (loc.x - scale.x / 2) + "F, " + (20 - loc.y) + "F, " + (loc.z - scale.z / 2) + "F, " + (int) scale.x + ", " + (int) scale.y + ", " + (int) scale.z + ");");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setTextureSize(512, 256);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setRotationPoint(0.0F, 0.0F, 0.0F);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".mirror = " + part.isMirror() + ";");
			FileHelper.writeToFile(path, "\t\tsetRotation(" + geom.getName() + ", " + geom.getLocalRotation().getX() + "F, " + geom.getLocalRotation().getY() + "F, " + geom.getLocalRotation().getZ() + "F);");
		}

		FileHelper.writeToFile(path, "\t}");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "\tpublic void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)");
		FileHelper.writeToFile(path, "\t{");
		FileHelper.writeToFile(path, "\t\tsuper.render(entity, f, f1, f2, f3, f4, f5);");

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Spatial geom = shootables.getChild(i);
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".render(f5);");
		}

		FileHelper.writeToFile(path, "\t}");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "\tpublic void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5, Entity entity)");
		FileHelper.writeToFile(path, "\t{");
		FileHelper.writeToFile(path, "\t\tsuper.setRotationAngles(f, f1, f2, f3, f4, f5, entity);");
		FileHelper.writeToFile(path, "\t\t//Set rotation angles here!");
		FileHelper.writeToFile(path, "\t}");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "\tprivate void setRotation(ModelRenderer model, float x, float y, float z)");
		FileHelper.writeToFile(path, "\t{");
		FileHelper.writeToFile(path, "\t\tmodel.rotateAngleX = x;");
		FileHelper.writeToFile(path, "\t\tmodel.rotateAngleY = y;");
		FileHelper.writeToFile(path, "\t\tmodel.rotateAngleZ = z;");
		FileHelper.writeToFile(path, "\t}");
		FileHelper.writeToFile(path, "}");
	}

	public static void open()
	{
		thisFrame.setVisible(true);
	}

	private static final long serialVersionUID = 1L;
}
