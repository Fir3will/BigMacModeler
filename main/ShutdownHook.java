package main;

public class ShutdownHook extends Thread
{
	public ShutdownHook()
	{
		super(new Runnable()
		{
			@Override
			public void run()
			{
				System.out.println("Shutting Down!");
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
