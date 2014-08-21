package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;

public class LoadingScreen extends JPanel implements ActionListener
{
	private static JFrame frame;
	private final JLabel label;
	private final Timer timer;
	private static int counter;
	private static boolean done;
	private static final int MAX = 100;

	public static void main(String[] args)
	{
		if (args.length > 0 && args[0].equals("skip"))
		{
			counter = MAX;
		}

		frame = new JFrame("Loading...");
		frame.add(new LoadingScreen());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}

	public LoadingScreen()
	{
		super(new BorderLayout());

		timer = new Timer(25, this);
		timer.start();

		label = new JLabel("Loading");
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(150, 100));

		JLabel icon = null;
		icon = new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif")));

		add(icon, BorderLayout.NORTH);
		add(label, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		label.setText("Loading: " + counter / (MAX / 100));

		if (counter++ > MAX && !done)
		{
			Main.start();
			done = true;
			frame.dispose();
		}
	}

	private static final long serialVersionUID = 1L;
}
