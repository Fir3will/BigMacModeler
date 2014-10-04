package main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import main.utils.DataTag;

public class MainScreen extends JPanel
{
	private static JButton newSave, loadSave, options, quit;
	private static final long serialVersionUID = 1L;

	public static void start()
	{
		JFrame frame = new JFrame("Modeler");
		frame.add(new MainScreen(frame));
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	public MainScreen(JFrame mainFrame)
	{
		super(new BorderLayout());
		System.out.println("Initializing programs!");
		AddBoxPanel.initialize();
		SelectedBoxesPanel.initialize();
		TexturePanel.initialize();
		Jme.init();

		newSave = new JButton("New Model");
		newSave.setToolTipText("Create a Model and a .BM File!");
		newSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				newSave();
			}
		});

		loadSave = new JButton("Load Model");
		loadSave.setToolTipText("Load a Model from a .BM File!");
		loadSave.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				loadSave();
			}
		});

		options = new JButton("Options");
		options.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Opened Options!");
				Options.open();
			}
		});

		quit = new JButton("Quit");
		quit.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				System.out.println("Exiting...");
				System.exit(0);
			}
		});

		JPanel pan = new JPanel(new BorderLayout());
		pan.add(newSave, BorderLayout.NORTH);
		pan.add(loadSave, BorderLayout.SOUTH);

		add(pan, BorderLayout.NORTH);
		add(options, BorderLayout.CENTER);
		add(quit, BorderLayout.SOUTH);
	}

	private static void startWith(File saveFile)
	{
		System.out.println("Begining Jme System");
		Options.file = saveFile;
		Jme.startJme();
		AddBoxPanel.startAddBoxPanel();
		SelectedBoxesPanel.startSelectedBoxesPanel();
		TexturePanel.startTexturePanel();
	}

	private static void newSave()
	{
		final JFrame frame = new JFrame("Modeler");
		final JTextField fileName = new JTextField("Model");
		fileName.setBorder(BorderFactory.createTitledBorder("File Name"));
		fileName.addKeyListener(new KeyListener()
		{
			@Override
			public void keyTyped(KeyEvent e)
			{
			}

			@Override
			public void keyPressed(KeyEvent e)
			{
				for (int i = 0; i < fileName.getText().length(); i++)
				{
					if (!Character.isJavaIdentifierPart(fileName.getText().charAt(i)) && !Character.isJavaIdentifierStart(fileName.getText().charAt(i)))
					{
						fileName.setText(fileName.getText().replaceAll("" + fileName.getText().charAt(i), ""));
					}
				}

				if (e.getKeyCode() == KeyEvent.VK_ENTER)
				{
					Jme.instance.saveFile = new File(System.getProperty("user.dir") + "/Saves/" + fileName.getText() + ".dat");
					File file = new File(System.getProperty("user.dir") + "/Models/" + fileName.getText() + ".java");
					frame.dispose();
					startWith(file);
				}
			}

			@Override
			public void keyReleased(KeyEvent e)
			{
			}
		});
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(fileName);
		frame.add(panel);
		frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		frame.setSize(600, 400);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.pack();
	}

	private void loadSave()
	{
		JFileChooser fc = new JFileChooser(new File(DataTag.USER_FOLDER, "Saves"));
		int returnVal = fc.showOpenDialog(this);

		if (returnVal == JFileChooser.APPROVE_OPTION)
		{
			startWith(new File(System.getProperty("user.dir") + "/Models/" + fc.getSelectedFile().getName().replaceAll(".dat", "") + ".java"));
			Jme.instance.saveFile = fc.getSelectedFile();
			Jme.instance.load();
		}
	}
}
