package main;

import java.awt.BorderLayout;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Options extends JPanel
{
	public static String fileName = "";
	public static final JFrame thisFrame;
	public static JTextField xDimension;
	public static JTextField yDimension;
	public static File file;

	static
	{
		thisFrame = new JFrame("Boxes");
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

		xDimension = new JTextField("640");
		xDimension.setBorder(BorderFactory.createTitledBorder("X Size"));
		yDimension = new JTextField("480");
		yDimension.setBorder(BorderFactory.createTitledBorder("Y Size"));

		JPanel topPanel = new JPanel(new BorderLayout());
		topPanel.add(xDimension, BorderLayout.NORTH);
		topPanel.add(yDimension, BorderLayout.SOUTH);
		JPanel midPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new BorderLayout());

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
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "package models;");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.Side;");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.SideOnly;");
		FileHelper.writeToFile(path, "import net.minecraft.entity.Entity;");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelBase;");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelRenderer;");
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
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".addBox(" + loc.x + "F, " + loc.y + "F, " + loc.z + "F, " + (int) scale.x + ", " + (int) scale.y + ", " + (int) scale.z + ");");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setTextureSize(512, 256);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setRotationPoint(" + geom.getLocalRotation().getX() + "F, " + geom.getLocalRotation().getY() + "F, " + geom.getLocalRotation().getZ() + "F);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".mirror = " + part.isMirror() + ";");
			FileHelper.writeToFile(path, "\t\tsetRotation(" + geom.getName() + ", 0.0F, 0.0F, 0.0F);");
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

	public static int getXResolution()
	{
		return Integer.parseInt(xDimension.getText());
	}

	public static int getYResolution()
	{
		return Integer.parseInt(yDimension.getText());
	}

	private static final long serialVersionUID = 1L;
}
