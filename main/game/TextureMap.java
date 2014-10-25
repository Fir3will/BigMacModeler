package main.game;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.util.List;
import javax.swing.JPanel;
import com.jme3.scene.Spatial;

public class TextureMap extends JPanel
{
	public static TextureMap instance;

	public TextureMap()
	{
		super(true);
		instance = this;
		setFocusable(true);
	}

	@Override
	public final void paint(Graphics g)
	{
		super.paint(g);
		Graphics2D g2d = (Graphics2D) g;

		drawScreen(g2d);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();
	}

	public void drawScreen(Graphics g2d)
	{
		if (Jme.instance != null && Jme.instance.allParts != null)
		{
			List<Spatial> parts = Jme.instance.allParts.getChildren();
			for (Spatial sp : parts)
			{
				Part part = (Part) sp;
				int x = part.getXOffset();
				int y = part.getYOffset();
				int width = (int) part.getLocalScale().x * 10;
				int height = (int) part.getLocalScale().y * 10;

				g2d.drawRect(x, y, width, height);
			}
		}
	}

	public void update()
	{
		if (isFocusOwner())
		{

		}
	}

	private static final long serialVersionUID = 1L;
}
