package main.game.utils;

import main.Main;
import main.game.Jme;
import main.game.Options;
import main.game.saving.DataTag;

public class ShutdownHook extends Thread
{
	public ShutdownHook()
	{
		super(new Runnable()
		{
			@Override
			public void run()
			{
				Options.saveOptions();
				if (Jme.instance.canSave)
				{
					DataTag tag = new DataTag();
					Jme.instance.saveToTag(tag);
					DataTag.saveTo(Main.getSaveFile(), tag);
				}
				System.out.println("Shutting Down!");
				Log.printLog();
			}
		});

		System.out.println("Registering Shutdown Hook!");
		System.out.println("Specs:");
		System.out.println("System.getProperty(\"user.dir\") == \"" + System.getProperty("user.dir") + "\"");
		System.out.println("System.getProperty(\"java.version\") == \"" + System.getProperty("java.version") + "\"");
		System.out.println("System.getProperty(\"java.vendor\") == \"" + System.getProperty("java.vendor") + "\"");
		System.out.println("System.getProperty(\"os.name\") == \"" + System.getProperty("os.name") + "\"");
		System.out.println("System.getProperty(\"os.arch\") == \"" + System.getProperty("os.arch") + "\"");
		System.out.println("System.getProperty(\"os.version\") == \"" + System.getProperty("os.version") + "\"");
	}
}
