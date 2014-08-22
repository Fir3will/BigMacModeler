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
		System.setErr(new Logger(System.err, true));
		System.setOut(new Logger(System.out, false));
		System.err.println("Initializing: Completed");
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

		frame = new JFrame("Loading");
		frame.add(new LoadingScreen());
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	public LoadingScreen()
	{
		super(new BorderLayout());

		System.out.println("Loading...");

		timer = new Timer(25, this);
		timer.start();

		String tip = getRandomTip(new Random().nextInt(10));

		label = new JLabel("Loading");
		label.setVerticalAlignment(SwingConstants.CENTER);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(150, 100));

		JLabel info = new JLabel(tip);
		info.setVerticalAlignment(SwingConstants.CENTER);
		info.setHorizontalAlignment(SwingConstants.CENTER);

		JLabel icon = new JLabel(new ImageIcon(getClass().getResource("/images/loading.gif")));

		add(icon, BorderLayout.NORTH);
		add(info, BorderLayout.CENTER);
		add(label, BorderLayout.SOUTH);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		label.setText("Loading: " + counter / (MAX / 100));

		if (counter++ > MAX && !done)
		{
			System.out.println("Finished Loading, Starting up Program!");
			BigMac.start();
			done = true;
			frame.dispose();
		}
	}

	public static String getRandomTip(int rand)
	{
		switch (rand)
		{
			case 0:
				return "The Model is always going to be smaller in game!";
			case 1:
				return "Adding a new box will always set it defaults! 0 x, y, z, rotation and size is 1 for everything!";
			case 2:
				return "The Rotation might be off in the game! Working on it!";
			case 3:
				return "Works on all UNIX Systems!";
			case 4:
				return "How do you like my dancing mobs?";
			case 5:
				return "Cloning a box doesn't clone the name exactly!";
			case 6:
				return "The '+' in the middle of the screen is how you select Boxes!";
			case 7:
				return "I'm always open to suggestions! Leave a comment on my MCF Topic!";
			case 8:
				return "If you have a crash, look for the latest log file in the folder next to the jar and send that to me with what happened!";
			case 9:
				return "A 1x1x1 Box is just One pixel in Game! REMEMBER THAT!";
		}

		return "Have Fun!";
	}

	private static final long serialVersionUID = 1L;
}
