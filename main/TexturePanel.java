package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import main.utils.Part;
import com.jme3.scene.Spatial;

public class TexturePanel extends JPanel implements ActionListener
{
	public static JFrame thisFrame;
	public static TexturePanel instance;
	private final Timer timer;
	private static final long serialVersionUID = 1L;

	public static void initialize()
	{
		instance = new TexturePanel();

		thisFrame = new JFrame("Texture Map");
		thisFrame.add(instance);
		thisFrame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		thisFrame.setSize(512, 256);
		thisFrame.setLocationRelativeTo(null);
		thisFrame.setResizable(false);
		thisFrame.setVisible(false);
		// thisFrame.pack();
	}

	public static void startTexturePanel()
	{
		thisFrame.setVisible(true);
	}

	public TexturePanel()
	{
		super(new BorderLayout());
		setFocusable(true);
		// setDoubleBuffered(true);

		timer = new Timer(400, this);
		timer.start();
	}

	@Override
	public final void paint(Graphics g)
	{
		if (Jme.instance == null || Jme.instance.shootables == null) return;

		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setBackground(Color.WHITE);

		for (Spatial geom : Jme.instance.shootables.getChildren())
		{
			if (geom == null) return;

			if (Jme.instance.selectedGeometry == geom)
			{
				g2d.setColor(Color.RED);
			}
			else
			{
				g2d.setColor(Color.GREEN);
			}
			int x = Part.getPartFor(geom).getXOffset();
			int y = Part.getPartFor(geom).getYOffset();
			int length = (int) geom.getLocalScale().x;
			int width = (int) geom.getLocalScale().y;
			int height = (int) geom.getLocalScale().z;

			g2d.drawRect(x + length, y, length, width);
			g2d.drawRect(x + length * 2, y, length, width);
			g2d.drawRect(x + length, y + height, length, width);
			g2d.drawRect(x + length * 2, y + height, length, width);
			g2d.drawRect(x, y + height, length, width);
			g2d.drawRect(x + length * 3, y + height, length, width);
		}

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (this.isFocusOwner()) repaint();
	}
}
