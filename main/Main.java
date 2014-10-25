package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.AbstractButton;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextField;
import main.game.AddBox;
import main.game.Jme;
import main.game.Options;
import main.game.SelectBox;
import main.game.saving.DataTag;
import main.game.utils.FileHelper;
import main.game.utils.Log;
import main.game.utils.ShutdownHook;
import com.jme3.system.AppSettings;
import com.jme3.system.JmeCanvasContext;

public class Main
{
	private static Main main;
	private final String userDir;
	private final File userDirFile, modelsDirFile, logDirFile, savesDirFile;
	private File saveFile;

	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				main = new Main();
			}
		});
	}

	private Main()
	{
		System.setErr(new Log(System.err, true));
		System.setOut(new Log(System.out, false));
		Runtime.getRuntime().addShutdownHook(new ShutdownHook());

		userDir = System.getProperty("user.dir");
		FileHelper.createFile(userDir + "/Logs", true);
		FileHelper.createFile(userDir + "/Models", true);
		FileHelper.createFile(userDir + "/Saves", true);
		userDirFile = new File(userDir);
		logDirFile = new File(userDir + "/Logs");
		modelsDirFile = new File(userDir + "/Models");
		savesDirFile = new File(userDir + "/Saves");
		AppSettings settings = new AppSettings(true);
		settings.setWidth(640);
		settings.setHeight(480);
		settings.setMinHeight(480);
		settings.setMinWidth(640);
		settings.setTitle("Big Mac Modeler");

		Jme.init();
		Jme.instance.setSettings(settings);
		Jme.instance.createCanvas();
		JmeCanvasContext ctx = (JmeCanvasContext) Jme.instance.getContext();
		ctx.setSystemListener(Jme.instance);
		Dimension dim = new Dimension(640, 480);
		ctx.getCanvas().setPreferredSize(dim);
		ctx.getCanvas().setMaximumSize(dim);

		JFrame window = new JFrame("Big Mac Modeler");
		window.setResizable(false);
		window.setIconImage(new ImageIcon(Main.class.getResource("/images/loading.gif")).getImage());
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		saveFile = new File(savesDirFile, "Model.dat");
		FileHelper.createFile(saveFile.getPath());
		JPanel panel = new JPanel(new BorderLayout());
		addToFrame(panel);
		panel.add(ctx.getCanvas(), BorderLayout.CENTER);
		window.add(panel);
		window.pack();

		window.setVisible(true);
		Jme.instance.startCanvas();
	}

	public static File getUserDir()
	{
		return main.userDirFile;
	}

	public static File getLogDir()
	{
		return main.logDirFile;
	}

	public static File getModelsDir()
	{
		return main.modelsDirFile;
	}

	public static File getSavesDir()
	{
		return main.savesDirFile;
	}

	private void addToFrame(JPanel panel)
	{
		JMenuBar bar = new JMenuBar();
		JMenu file = new JMenu("File");
		JMenu model = new JMenu("Model");
		bar.add(file);
		bar.add(model);

		JMenuItem menuItem = new JMenuItem("Generate Code");
		menuItem.setActionCommand("Generate Code");
		menuItem.addActionListener(al);
		model.add(menuItem);

		menuItem = new JMenuItem("Create Image");
		menuItem.setActionCommand("Create Image");
		menuItem.addActionListener(al);
		model.add(menuItem);

		model.addSeparator();

		JTextField f = new JTextField("Model", 10);
		f.setActionCommand("Set File Name");
		f.addActionListener(al);
		model.add(f);

		menuItem = new JMenuItem("Save");
		menuItem.setActionCommand("Save");
		menuItem.addActionListener(al);
		file.add(menuItem);

		menuItem = new JMenuItem("Load");
		menuItem.setActionCommand("Load");
		menuItem.addActionListener(al);
		file.add(menuItem);

		file.addSeparator();

		menuItem = new JMenuItem("Options");
		menuItem.setActionCommand("Options");
		menuItem.addActionListener(al);
		file.add(menuItem);

		menuItem = new JMenuItem("Quit");
		menuItem.setActionCommand("Quit");
		menuItem.addActionListener(al);
		file.add(menuItem);

		JPanel p1 = new JPanel(new BorderLayout());

		JButton newBox = new JButton("New Box");
		newBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.addBox(false, 0, 0);
			}
		});
		JButton cloneBox = new JButton("Clone Box");
		cloneBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.cloneSelectedBox();
			}
		});
		JButton removeBox = new JButton("Remove Box");
		removeBox.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Jme.instance.removeSelectedBox();
			}
		});

		p1.add(newBox, BorderLayout.WEST);
		p1.add(removeBox, BorderLayout.CENTER);
		p1.add(cloneBox, BorderLayout.EAST);

		panel.add(bar, BorderLayout.NORTH);
		JPanel p = new JPanel(new BorderLayout());
		p.add(new SelectBox(), BorderLayout.NORTH);
		// p.add(new TextureMap(), BorderLayout.CENTER);
		p.add(p1, BorderLayout.SOUTH);
		panel.add(new AddBox(), BorderLayout.WEST);
		panel.add(p, BorderLayout.EAST);
	}

	private final ActionListener al = new ActionListener()
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			if ("Generate Code".equals(e.getActionCommand()))
			{
				Jme.instance.generateCode();
			}
			if ("Create Image".equals(e.getActionCommand()))
			{
				Jme.instance.createImage();
			}
			if ("Quit".equals(e.getActionCommand()))
			{
				System.exit(0);
			}
			if ("Options".equals(e.getActionCommand()))
			{
				Options.open();
			}
			if ("Load".equals(e.getActionCommand()))
			{
				JFileChooser fc = new JFileChooser(new File(DataTag.USER_FOLDER + "/Saves"));
				int returnVal = fc.showOpenDialog((AbstractButton) e.getSource());

				if (returnVal == JFileChooser.APPROVE_OPTION)
				{
					saveFile = fc.getSelectedFile();
				}
				DataTag tag = DataTag.loadFrom(fc.getSelectedFile());
				Jme.instance.loadFromTag(tag);
			}
			if ("Save".equals(e.getActionCommand()))
			{
				DataTag tag = new DataTag();
				Jme.instance.saveToTag(tag);
				DataTag.saveTo(saveFile, tag);
			}
			if ("Set File Name".equals(e.getActionCommand()))
			{
				saveFile = new File(savesDirFile, ((JTextField) e.getSource()).getText() + ".dat");
				System.out.println("Save File: " + saveFile.getPath());
			}
		}
	};

	public static File getSaveFile()
	{
		return main.saveFile;
	}
}
