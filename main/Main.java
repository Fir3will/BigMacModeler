package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

public class Main extends JPanel
{
	private static final long serialVersionUID = 1L;

	public static void start()
	{
		JFrame frame = new JFrame("Modeler");
		frame.add(new Main(frame));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	public Main(JFrame mainFrame)
	{
		super(new BorderLayout());
		AddBoxPanel.initialize();
		SelectedBoxesPanel.initialize();
		JME3Start.init();

		JButton start = new JButton("Start");
		start.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				JME3Start.startJME3();
				AddBoxPanel.startAddBoxPanel();
				SelectedBoxesPanel.startSelectedBoxesPanel();
			}
		});

		JButton options = new JButton("Options");
		options.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Options.open();
			}
		});

		JButton quit = new JButton("Exit");
		quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.exit(0);
			}
		});

		add(start, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
		add(quit, BorderLayout.SOUTH);
	}
}
