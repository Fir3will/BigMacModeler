package main.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import main.Main;
import main.game.saving.DataTag;
import main.game.utils.FileHelper;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;

public class Options extends JPanel
{
	public static String fileName = "";
	public static final JFrame thisFrame;
	public static boolean inDegrees = true;
	public static float saveMins = 5;
	public static final DataTag save;
	public static boolean saveOnExit;

	static
	{
		boolean ex = new File(System.getProperty("user.dir") + "/options.dat").exists();
		save = new DataTag();
		inDegrees = ex ? save.getBoolean("In Degrees") : true;
		saveMins = ex ? save.getFloat("Save Interval") : 5.0F;

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

		final JTextField saveInterval = new JTextField("" + saveMins);
		saveInterval.setBorder(BorderFactory.createTitledBorder("Mins before Autosaving"));
		saveInterval.setToolTipText("How many minutes before autosaving?");
		saveInterval.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
				keyPressed(e);
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				String text = saveInterval.getText();

				for (int i = 0; i < text.length(); i++)
				{
					if (text.isEmpty()) text = "0";

					if (!Character.isDigit(text.charAt(i)))
					{
						text = text.replace(text.charAt(i) + "", "");
						saveInterval.setText(text);
					}
				}
				if (saveInterval.getText().isEmpty())
				{
					saveInterval.setText("5");
				}

				try
				{
					saveMins = Integer.parseInt(saveInterval.getText());
				}
				catch (NumberFormatException ex)
				{

				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
				keyPressed(e);
			}
		});

		final JCheckBox inDeg = new JCheckBox("Rotation in Degrees", inDegrees);
		inDeg.setToolTipText("Is the Rotation in Degrees?");
		inDeg.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				inDegrees = inDeg.isSelected();
			}
		});

		final JCheckBox saveOnExt = new JCheckBox("In Deg", inDegrees);
		saveOnExt.setToolTipText("Is the Rotation in Degrees?");
		saveOnExt.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				saveOnExit = saveOnExt.isSelected();
			}
		});

		JPanel topPanel = new JPanel(new BorderLayout());
		JPanel midPanel = new JPanel(new BorderLayout());
		JPanel bottomPanel = new JPanel(new BorderLayout());
		topPanel.add(inDeg, BorderLayout.NORTH);
		topPanel.add(saveInterval, BorderLayout.CENTER);

		add(topPanel, BorderLayout.NORTH);
		add(midPanel, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);
	}

	public static void open()
	{
		thisFrame.setVisible(true);
	}

	public static void generateCode(Node shootables)
	{
		String path = new File(Main.getModelsDir(), Main.getSaveFile().getName()).getPath().replaceAll(".dat", "") + ".java";
		String fileName = Main.getSaveFile().getName().replaceAll(".dat", "").replaceAll(".java", "");
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
		FileHelper.writeToFile(path, "package net.minecraft.src;");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelBase;");
		FileHelper.writeToFile(path, "import net.minecraft.client.model.ModelRenderer;");
		FileHelper.writeToFile(path, "import net.minecraft.entity.Entity;");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.Side;");
		FileHelper.writeToFile(path, "import cpw.mods.fml.relauncher.SideOnly;");
		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "@SideOnly(Side.CLIENT)");
		FileHelper.writeToFile(path, "public class " + fileName + " extends ModelBase");
		FileHelper.writeToFile(path, "{");

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Spatial geom = shootables.getChild(i);
			FileHelper.writeToFile(path, "\tprivate ModelRenderer " + geom.getName() + ";");
		}

		FileHelper.writeToFile(path, "");
		FileHelper.writeToFile(path, "\tpublic " + fileName + "()");
		FileHelper.writeToFile(path, "\t{");
		FileHelper.writeToFile(path, "\t\ttextureWidth = 512;");
		FileHelper.writeToFile(path, "\t\ttextureHeight = 256;");

		for (int i = 0; i < shootables.getQuantity(); i++)
		{
			Part geom = (Part) shootables.getChild(i);
			Vector3f loc = geom.getLocalTranslation();
			Vector3f scale = geom.getLocalScale();

			FileHelper.writeToFile(path, "");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + " = new ModelRenderer(this, " + geom.getXOffset() + ", " + geom.getYOffset() + ");");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".addBox(0.0F, 0.0F, 0.0F);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setTextureSize(512, 256);");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".setRotationPoint(" + (loc.x - scale.x / 2) + "F, " + (20 - loc.y) + "F, " + (loc.z - scale.z / 2) + "F, " + (int) scale.x + ", " + (int) scale.y + ", " + (int) scale.z + ");");
			FileHelper.writeToFile(path, "\t\t" + geom.getName() + ".mirror = " + geom.isMirrored() + ";");
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

	public static void saveOptions()
	{
		save.setBoolean("In Degrees", inDegrees);
		save.setFloat("Save Interval", saveMins);
	}

	private static final long serialVersionUID = 1L;
}
