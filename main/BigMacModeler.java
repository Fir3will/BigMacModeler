package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import main.utils.FileHelper;
import main.utils.Log;
import main.utils.ShutdownHook;
import main.utils.Vars;

public class BigMacModeler extends JPanel implements ActionListener
{
	private static JFrame frame;
	private final JLabel label;
	private final Timer timer;
	private static int counter;
	private static boolean done;
	private static final int MAX = 500;

	public static void main(String[] args)
	{
		if (args.length > 0)
		{
			for (String str : args)
			{
				if (str.equals("skip"))
				{
					counter = MAX;
				}

				if (str.equals("debug"))
				{
					Vars.isDebug = true;
				}
			}
		}

		FileHelper.createFile(System.getProperty("user.dir") + "/Logs", true);
		FileHelper.createFile(System.getProperty("user.dir") + "/Models", true);
		FileHelper.createFile(System.getProperty("user.dir") + "/Saves", true);
		System.setErr(new Log(System.err, true));
		System.setOut(new Log(System.out, false));
		System.err.println("Initializing: Completed");
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

		frame = new JFrame("Loading");
		frame.add(new BigMacModeler());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();

		frame.setIconImage(new ImageIcon(BigMacModeler.class.getResource("/images/loading.gif")).getImage());
	}

	public BigMacModeler()
	{
		super(new BorderLayout());

		System.out.println("Loading...");

		timer = new Timer(25, this);
		timer.start();

		String tip = getRandomTip();

		label = new JLabel("Loading");
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(150, 100));

		JLabel info = new JLabel("Tip: " + tip);
		info.setVerticalAlignment(SwingConstants.CENTER);
		info.setHorizontalAlignment(SwingConstants.CENTER);
		info.setPreferredSize(new Dimension(info.getFontMetrics(info.getFont()).stringWidth(tip) + 100, 100));

		JLabel version = new JLabel("Big Mac Modeler Version: 0.4b");
		version.setVerticalAlignment(SwingConstants.CENTER);
		version.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif")));

		JPanel panel = new JPanel(new BorderLayout());
		panel.add(icon, BorderLayout.NORTH);
		panel.add(info, BorderLayout.SOUTH);

		add(panel, BorderLayout.NORTH);
		add(version, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		label.setText("Loading: " + counter / (MAX / 100) + "%");

		if (true && !done)// counter++ > MAX && !done)
		{
			System.out.println("Finished Loading, Starting up Program!");
			MainScreen.start();
			done = true;
			frame.dispose();
		}
	}

	public static String getRandomTip()
	{
		int rand = new Random().nextInt(16);

		switch (rand)
		{
			case 0:
				return "The Model is going to, almost always, seem MUCH smaller in game!";
			case 1:
				return "Adding a new box will always be in the middle and a 1x1x1 Cube!";
			case 2:
				return "The Rotation isn't by Degrees, But actually by Radian! So 2 PI is a full rotation!";
			case 3:
				return "FINALLY! A MODELER FOR MAC AND LINUX!";
			case 4:
				return "Look at that Spider GO!";
			case 5:
				return "Cloning a box doesn't clone the exact name!";
			case 6:
				return "The '+' in the middle of the screen is how you select Boxes!";
			case 7:
				return "I'm always open to suggestions! Leave a comment on my MCF Topic!";
			case 8:
				return "Got a Crash? Get the file in the Logs folder and send that to me with a quick Before and After!";
			case 9:
				return "A 1x1x1 Box is just One Texture Pixel in Game!";
			case 10:
				return "Use the Plank below the surface for a Guide on how it'll look!";
			case 11:
				return "Let me know any questions you have reguarding function and features!";
			case 12:
				return "Sorry about no Texture Map, I will try my best to get that in future updates!";
			case 13:
				return "YOU'VE BEEN WAITING FOREVER! I KNOW! I CAN'T BELIEVE IT EITHER!";
			case 14:
				return "Gotta LOVE the Name!";
		}

		return "You BETTER be enjoying yourself with this!";
	}

	private static final long serialVersionUID = 1L;
}
