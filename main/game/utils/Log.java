package main.game.utils;

import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Log extends PrintStream
{
	private class SimpleTimer
	{
		private long cachedTime;

		public SimpleTimer()
		{
			reset();
		}

		public long elapsed()
		{
			return System.currentTimeMillis() - cachedTime;
		}

		public void reset()
		{
			cachedTime = System.currentTimeMillis();
		}
	}

	private final DateFormat dateFormat = new SimpleDateFormat();
	private Date cachedDate = new Date();
	private final boolean isErr;

	private final SimpleTimer refreshTimer = new SimpleTimer();

	public Log(PrintStream out, boolean isErr)
	{
		super(out);
		this.isErr = isErr;
	}

	private String getPrefix()
	{
		if (refreshTimer.elapsed() > 1000)
		{
			refreshTimer.reset();
			cachedDate = new Date();
		}
		return (!isErr ? "I-" : "E-") + dateFormat.format(cachedDate).replaceAll(" PM", ":").replaceAll(" AM", ":") + getSecs();
	}

	private String getSecs()
	{
		String secs = "" + Calendar.getInstance().get(Calendar.SECOND);

		if (secs.length() == 1)
		{
			secs = "0" + secs;
		}

		return secs;
	}

	@Override
	public void print(String str)
	{
		String write = "";
		if (str.startsWith(REMOVE_PREFIX))
		{
			write = str.substring(REMOVE_PREFIX.length());
			super.print(str.substring(REMOVE_PREFIX.length()));
		}
		else
		{
			write = "[" + getPrefix() + "]: " + str;
			super.print("[" + getPrefix() + "]: " + str);
		}
		lines.add(write);
	}

	public static final String REMOVE_PREFIX = "::";

	public static void printLog()
	{
		String path = System.getProperty("user.dir") + "/Logs/latest log.txt";

		if (!Files.exists(Paths.get(path)))
		{
			FileHelper.createFile(path);
		}
		else
		{
			FileHelper.resetFile(path);
		}

		for (String line : lines)
		{
			FileHelper.writeToFile(path, line);
		}
	}

	private static final ArrayList<String> lines = new ArrayList<String>();
}