package main.game;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;

public class AddBox extends JPanel implements SwingConstants
{
	public final JTextField boxX;
	public final JTextField boxY;
	public final JTextField boxZ;
	public final JTextField sizeX;
	public final JTextField sizeY;
	public final JTextField sizeZ;
	public final JTextField rotX;
	public final JTextField rotY;
	public final JTextField rotZ;
	public final JTextField texOffsetX;
	public final JTextField texOffsetY;
	public final JTextField boxName;
	public final JCheckBox mirrored;
	private Part prevPart;
	public int xOffset, yOffset;
	public static AddBox instance;

	public AddBox()
	{
		super(new BorderLayout());
		setFocusable(true);
		instance = this;
		JPanel top = new JPanel(new BorderLayout());
		top.setBorder(BorderFactory.createTitledBorder("Location"));
		JPanel mid1 = new JPanel(new BorderLayout());
		mid1.setBorder(BorderFactory.createTitledBorder("Rotation"));
		JPanel mid2 = new JPanel(new BorderLayout());
		mid2.setBorder(BorderFactory.createTitledBorder("Scale"));
		JPanel bottom = new JPanel(new BorderLayout());
		bottom.setBorder(BorderFactory.createTitledBorder("Other"));

		boxX = new JTextField("0", 10);
		boxX.addKeyListener(new Num(boxX, true));
		boxX.setBorder(BorderFactory.createTitledBorder("X"));
		top.add(boxX, BorderLayout.NORTH);

		boxY = new JTextField("0", 10);
		boxY.addKeyListener(new Num(boxY, true));
		boxY.setBorder(BorderFactory.createTitledBorder("Y"));
		top.add(boxY, BorderLayout.CENTER);

		boxZ = new JTextField("0", 10);
		boxZ.addKeyListener(new Num(boxZ, true));
		boxZ.setBorder(BorderFactory.createTitledBorder("Z"));
		top.add(boxZ, BorderLayout.SOUTH);

		sizeX = new JTextField("1", 10);
		sizeX.addKeyListener(new Num(sizeX, false));
		sizeX.setBorder(BorderFactory.createTitledBorder("Length"));
		mid2.add(sizeX, BorderLayout.NORTH);

		sizeY = new JTextField("1", 10);
		sizeY.addKeyListener(new Num(sizeY, false));
		sizeY.setBorder(BorderFactory.createTitledBorder("Height"));
		mid2.add(sizeY, BorderLayout.CENTER);

		sizeZ = new JTextField("1", 10);
		sizeZ.addKeyListener(new Num(sizeZ, false));
		sizeZ.setBorder(BorderFactory.createTitledBorder("Width"));
		mid2.add(sizeZ, BorderLayout.SOUTH);

		rotX = new JTextField("0", 10);
		rotX.addKeyListener(new Num(rotX, true));
		rotX.setBorder(BorderFactory.createTitledBorder("X " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));
		mid1.add(rotX, BorderLayout.NORTH);

		rotY = new JTextField("0", 10);
		rotY.addKeyListener(new Num(rotY, true));
		rotY.setBorder(BorderFactory.createTitledBorder("Y " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));
		mid1.add(rotY, BorderLayout.CENTER);

		rotZ = new JTextField("0", 10);
		rotZ.addKeyListener(new Num(rotZ, true));
		rotZ.setBorder(BorderFactory.createTitledBorder("Z " + (Options.inDegrees ? "Deg" : "Rad") + " Rotation"));
		mid1.add(rotZ, BorderLayout.SOUTH);

		texOffsetX = new JTextField("0", 10);
		texOffsetX.addKeyListener(new Num(texOffsetX, true));
		texOffsetX.setBorder(BorderFactory.createTitledBorder("X Texture Offset"));
		bottom.add(texOffsetX, BorderLayout.NORTH);

		texOffsetY = new JTextField("0", 10);
		texOffsetY.addKeyListener(new Num(texOffsetY, true));
		texOffsetY.setBorder(BorderFactory.createTitledBorder("Y Texture Offset"));
		bottom.add(texOffsetY, BorderLayout.CENTER);

		boxName = new JTextField("Box", 10);
		boxName.addKeyListener(new JavaOnly(boxName));
		boxName.setBorder(BorderFactory.createTitledBorder("Box Name"));
		bottom.add(boxName, BorderLayout.SOUTH);

		mirrored = new JCheckBox("Mirrored Texture");

		JPanel center = new JPanel(new BorderLayout());
		center.add(mid1, BorderLayout.NORTH);
		center.add(mid2, BorderLayout.CENTER);
		center.add(mirrored, BorderLayout.SOUTH);

		add(top, BorderLayout.NORTH);
		add(center, BorderLayout.CENTER);
		add(bottom, BorderLayout.SOUTH);
	}

	public void update()
	{

	}

	public void selectedPart(Part part)
	{
		System.gc();
		if (prevPart == part) return;
		if (part != null)
		{
			Vector3f loc = part.getLocalTranslation();
			Vector3f scl = part.getLocalScale();
			Quaternion rot = part.getLocalRotation();

			boxX.setText("" + loc.x);
			boxY.setText("" + loc.y);
			boxZ.setText("" + loc.z);

			sizeX.setText("" + scl.x);
			sizeY.setText("" + scl.y);
			sizeZ.setText("" + scl.z);

			rotX.setText("" + rot.getX());
			rotY.setText("" + rot.getY());
			rotZ.setText("" + rot.getZ());

			mirrored.setSelected(part.isMirrored());
			texOffsetX.setText("" + part.getXOffset());
			texOffsetY.setText("" + part.getYOffset());
			boxName.setText(part.getName());
			setEnabledAll(true);
		}
		else
		{
			setEnabledAll(false);
		}
		prevPart = part;
	}

	public void setEnabledAll(boolean enable)
	{
		boxX.setEnabled(enable);
		boxY.setEnabled(enable);
		boxZ.setEnabled(enable);
		sizeX.setEnabled(enable);
		sizeY.setEnabled(enable);
		sizeZ.setEnabled(enable);
		rotX.setEnabled(enable);
		rotY.setEnabled(enable);
		rotZ.setEnabled(enable);
		mirrored.setEnabled(enable);
		texOffsetX.setEnabled(enable);
		texOffsetY.setEnabled(enable);
		boxName.setEnabled(enable);
	}

	protected float p(JTextField f)
	{
		return Float.parseFloat(removeLetters(f.getText()));
	}

	private String removeLetters(String s)
	{
		String str = "";
		for (int i = 0; i < s.length(); i++)
		{
			if (Character.isDigit(s.charAt(i)) || s.charAt(i) == '.')
			{
				str += s.charAt(i);
			}
		}
		return str;
	}

	private class Num implements KeyListener
	{
		private final JTextField f;
		private final boolean allowZero;

		public Num(JTextField f, boolean allowZero)
		{
			this.f = f;
			this.allowZero = allowZero;
			f.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					System.gc();
					Part p = Jme.instance.selectedPart;
					if (p != null)
					{
						p.setLocalScale(p(sizeX), p(sizeY), p(sizeZ));
						p.setLocalTranslation(p(boxX), p(boxY), p(boxZ));
						p.setLocalRotation(new Quaternion(p(rotX), p(rotY), p(rotZ), 1));
						p.setXOffset((int) p(texOffsetX));
						p.setYOffset((int) p(texOffsetY));
						p.setMirror(mirrored.isSelected());
						p.setName(boxName.getText());
					}
				}
			});
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			keyPressed(e);
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if (!f.isFocusOwner() && !Character.isDigit(e.getKeyChar()))
			{
				f.setText(f.getText().replaceAll("" + e.getKeyChar(), ""));
			}
			if (!f.isFocusOwner() && f.getText().isEmpty() || (!allowZero && (p(f) <= 0)))
			{
				f.setText(allowZero ? "0" : "1");
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			keyPressed(e);
		}
	}

	private class JavaOnly implements KeyListener
	{
		private final JTextField f;

		public JavaOnly(JTextField f)
		{
			this.f = f;
			f.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(ActionEvent e)
				{
					System.gc();
					Part p = Jme.instance.selectedPart;
					if (p != null)
					{
						p.setLocalScale(p(sizeX), p(sizeY), p(sizeZ));
						p.setLocalTranslation(p(boxX), p(boxY), p(boxZ));
						p.setLocalRotation(new Quaternion(p(rotX), p(rotY), p(rotZ), 1));
						p.setXOffset((int) p(texOffsetX));
						p.setYOffset((int) p(texOffsetY));
						p.setMirror(mirrored.isSelected());
						p.setName(boxName.getText());
					}
				}
			});
		}

		@Override
		public void keyTyped(KeyEvent e)
		{
			keyPressed(e);
		}

		@Override
		public void keyPressed(KeyEvent e)
		{
			if (!f.isFocusOwner() && !Character.isJavaIdentifierStart(e.getKeyChar()))
			{
				f.setText(f.getText().replaceAll("" + e.getKeyChar(), ""));
			}
			if (f.getText().isEmpty())
			{
				f.setText("Box");
			}
		}

		@Override
		public void keyReleased(KeyEvent e)
		{
			keyPressed(e);
		}
	}

	private static final long serialVersionUID = 1L;
}
