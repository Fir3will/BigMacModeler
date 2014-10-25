package main.game;

import java.awt.LayoutManager;
import javax.swing.JPanel;

public abstract class SidePanel extends JPanel
{
	private static final long serialVersionUID = 1L;

	public SidePanel()
	{
		super();
	}

	public SidePanel(boolean isDoubleBuffered)
	{
		super(isDoubleBuffered);
	}

	public SidePanel(LayoutManager layout, boolean isDoubleBuffered)
	{
		super(layout, isDoubleBuffered);
	}

	public SidePanel(LayoutManager layout)
	{
		super(layout);
	}

	public abstract void update(boolean isFocus);
}
