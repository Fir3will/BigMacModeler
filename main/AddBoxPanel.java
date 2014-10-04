package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import main.utils.DataTag;
import main.utils.NumKL;
import main.utils.Part;
import com.jme3.scene.Geometry;

public class AddBoxPanel extends JPanel implements ActionListener
{
	public JCheckBox mirrorButton;
	public static JFrame thisFrame;
	public static final JTextField boxX;
	public static final JTextField boxY;
	public static final JTextField boxZ;
	public static final JTextField sizeX;
	public static final JTextField sizeY;
	public static final JTextField sizeZ;
	public static final JTextField rotX;
	public static final JTextField rotY;
	public static final JTextField rotZ;
	public static final JTextField texOffsetX;
	public static final JTextField texOffsetY;
	public static final JTextField boxName;
	public static boolean mirror;
	public int xOffset, yOffset;
	private final Timer timer;
	private static final long serialVersionUID = 1L;
	private final JButton removeBoxButton, cloneBoxButton;
	public static AddBoxPanel instance;

	static
	{
		boxX = new JTextField("0.0");
		boxX.addKeyListener(new NumKL(boxX));
		boxX.setBorder(BorderFactory.createTitledBorder("X"));

		boxY = new JTextField("0.0");
		boxY.addKeyListener(new NumKL(boxY));
		boxY.setBorder(BorderFactory.createTitledBorder("Y"));

		boxZ = new JTextField("0.0");
		boxZ.addKeyListener(new NumKL(boxZ));
		boxZ.setBorder(BorderFactory.createTitledBorder("Z"));

		sizeX = new JTextField("1.0");
		sizeX.addKeyListener(new NumKL(sizeX));
		sizeX.setBorder(BorderFactory.createTitledBorder("Length"));

		sizeY = new JTextField("1.0");
		sizeY.addKeyListener(new NumKL(sizeY));
		sizeY.setBorder(BorderFactory.createTitledBorder("Height"));

		sizeZ = new JTextField("1.0");
		sizeZ.addKeyListener(new NumKL(sizeZ));
		sizeZ.setBorder(BorderFactory.createTitledBorder("Width"));

		rotX = new JTextField("1.0");
		rotX.addKeyListener(new NumKL(rotX));
		rotX.setBorder(BorderFactory.createTitledBorder("X " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));

		rotY = new JTextField("1.0");
		rotY.addKeyListener(new NumKL(rotY));
		rotY.setBorder(BorderFactory.createTitledBorder("Y " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));

		rotZ = new JTextField("1.0");
		rotZ.addKeyListener(new NumKL(rotZ));
		rotZ.setBorder(BorderFactory.createTitledBorder("Z " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));

		texOffsetX = new JTextField("0");
		texOffsetX.addKeyListener(new NumKL(texOffsetX));
		texOffsetX.setBorder(BorderFactory.createTitledBorder("X Texture Offset"));

		texOffsetY = new JTextField("0");
		texOffsetY.addKeyListener(new NumKL(texOffsetY));
		texOffsetY.setBorder(BorderFactory.createTitledBorder("Y Texture Offset"));

		boxName = new JTextField("");
		boxName.addKeyListener(new NumKL(boxName));
		boxName.setBorder(BorderFactory.createTitledBorder("Box Name"));
	}

	public static void initialize()
	{
		instance = new AddBoxPanel();

		thisFrame = new JFrame("Model" + Options.fileName);
		thisFrame.add(instance);
		thisFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		thisFrame.setSize(600, 400);
		thisFrame.setLocationRelativeTo(null);
		thisFrame.setResizable(false);
		thisFrame.setVisible(false);
		thisFrame.pack();
	}

	public static void startAddBoxPanel()
	{
		thisFrame.setVisible(true);
	}

	public AddBoxPanel()
	{
		super(new BorderLayout());

		timer = new Timer(400, this);
		timer.start();

		JPanel buttonPanel = new JPanel(new BorderLayout());
		buttonPanel.setBorder(BorderFactory.createTitledBorder("Model Options"));

		JPanel boxPanel = new JPanel(new BorderLayout());
		boxPanel.setBorder(BorderFactory.createTitledBorder("Box Options"));

		JButton addBoxButton = new JButton("Add Box");
		addBoxButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.addBox(mirror, xOffset, yOffset);
			}
		});

		cloneBoxButton = new JButton("Clone Box");
		cloneBoxButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.cloneSelectedBox(mirror, xOffset, yOffset);
			}
		});

		removeBoxButton = new JButton("Remove Box");
		removeBoxButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.removeSelectedBox();
			}
		});

		mirrorButton = new JCheckBox("Mirror");
		mirrorButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Part.getPartFor(Jme.instance.selectedGeometry).setMirror(AddBoxPanel.this.mirrorButton.isSelected());
			}
		});

		buttonPanel.add(addBoxButton, BorderLayout.NORTH);
		buttonPanel.add(removeBoxButton, BorderLayout.CENTER);
		buttonPanel.add(cloneBoxButton, BorderLayout.SOUTH);

		JPanel topVarsPanel = new JPanel(new BorderLayout());
		topVarsPanel.add(boxX, BorderLayout.NORTH);
		topVarsPanel.add(boxY, BorderLayout.CENTER);
		topVarsPanel.add(boxZ, BorderLayout.SOUTH);
		JPanel topPan = new JPanel(new BorderLayout());
		topPan.add(rotX, BorderLayout.NORTH);
		topPan.add(rotY, BorderLayout.CENTER);
		topPan.add(rotZ, BorderLayout.SOUTH);
		JPanel bottomPan = new JPanel(new BorderLayout());
		bottomPan.add(sizeX, BorderLayout.NORTH);
		bottomPan.add(sizeY, BorderLayout.CENTER);
		bottomPan.add(sizeZ, BorderLayout.SOUTH);
		JPanel bottomVarsPanel = new JPanel(new BorderLayout());
		bottomVarsPanel.add(mirrorButton, BorderLayout.NORTH);
		bottomVarsPanel.add(texOffsetX, BorderLayout.CENTER);
		bottomVarsPanel.add(texOffsetY, BorderLayout.SOUTH);

		JPanel middleVarsPanel = new JPanel(new BorderLayout());
		middleVarsPanel.add(topPan, BorderLayout.NORTH);
		middleVarsPanel.add(bottomPan, BorderLayout.SOUTH);

		boxPanel.add(topVarsPanel, BorderLayout.NORTH);
		boxPanel.add(middleVarsPanel, BorderLayout.CENTER);
		boxPanel.add(bottomVarsPanel, BorderLayout.SOUTH);

		JButton generateCode = new JButton("Generate Java Code");
		generateCode.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.generateCode();
				Jme.instance.save();
			}
		});

		JButton createMap = new JButton("Create Image");
		createMap.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				try
				{
					BufferedImage image = new BufferedImage(512, 256, BufferedImage.TYPE_INT_BGR);
					TexturePanel.instance.paintAll(image.createGraphics());

					if (ImageIO.write(image, "png", new File(DataTag.USER_FOLDER + "/Models/texture.png")))
					{
						System.out.println("Successfully saved the Image!");
					}
				}
				catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});

		JPanel mid = new JPanel(new BorderLayout());
		mid.add(buttonPanel, BorderLayout.NORTH);
		mid.add(boxName, BorderLayout.CENTER);
		mid.add(boxPanel, BorderLayout.SOUTH);

		add(mid, BorderLayout.NORTH);
		add(createMap, BorderLayout.CENTER);
		add(generateCode, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (Options.file != null) thisFrame.setTitle(Options.file.getName());

		if (Jme.instance == null) return;

		Geometry geom = Jme.instance.selectedGeometry;
		boolean flag = geom != null;

		removeBoxButton.setEnabled(flag);
		cloneBoxButton.setEnabled(flag);
		boxX.setEnabled(flag);
		boxY.setEnabled(flag);
		boxZ.setEnabled(flag);
		sizeX.setEnabled(flag);
		sizeY.setEnabled(flag);
		sizeZ.setEnabled(flag);
		mirrorButton.setEnabled(flag);
		texOffsetX.setEnabled(flag);
		texOffsetY.setEnabled(flag);
		rotX.setEnabled(flag);
		rotY.setEnabled(flag);
		rotZ.setEnabled(flag);
		boxName.setEnabled(flag);

		if (flag && !boxX.isFocusOwner() && !boxY.isFocusOwner() && !boxZ.isFocusOwner() && !sizeX.isFocusOwner() && !sizeY.isFocusOwner() && !sizeZ.isFocusOwner() && !texOffsetX.isFocusOwner() && !texOffsetY.isFocusOwner() && !mirrorButton.isFocusOwner() && !rotX.isFocusOwner() && !rotY.isFocusOwner() && !rotZ.isFocusOwner() && !boxName.isFocusOwner())
		{
			boxX.setText("" + geom.getLocalTranslation().x);
			boxY.setText("" + geom.getLocalTranslation().y);
			boxZ.setText("" + geom.getLocalTranslation().z);

			sizeX.setText("" + geom.getLocalScale().x);
			sizeY.setText("" + geom.getLocalScale().y);
			sizeZ.setText("" + geom.getLocalScale().z);

			texOffsetX.setText("" + Part.getPartFor(geom).getXOffset());
			texOffsetY.setText("" + Part.getPartFor(geom).getYOffset());

			mirrorButton.setSelected(Part.getPartFor(geom).isMirror());
			mirror = mirrorButton.isSelected();

			rotX.setText("" + (int) Options.getRotation(geom, 1));
			rotY.setText("" + (int) Options.getRotation(geom, 2));
			rotZ.setText("" + (int) Options.getRotation(geom, 3));

			boxName.setText(geom.getName());
		}
	}
}
