package main.game.saving;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import main.game.utils.FileHelper;

public class CompoundTag implements Serializable, Cloneable
{
	private static final long serialVersionUID = 1L;
	private final File file;

	public CompoundTag(File saveTo)
	{
		file = saveTo;
		init();
	}

	public void init()
	{
		FileHelper.createFile(file.getPath());
	}

	public DataTag load()
	{
		DataTag tag = null;
		try
		{
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
			tag = (DataTag) in.readObject();
			in.close();
		}
		catch (Exception i)
		{
			if (!i.getClass().equals(EOFException.class))
			{
				System.err.println("Exception: " + i.getClass().getName());
				i.printStackTrace();
			}
		}

		return tag == null ? new DataTag() : tag;
	}

	public void save(DataTag tag)
	{
		try
		{
			file.delete();
			file.createNewFile();
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file));
			out.writeObject(tag);
			out.close();
		}
		catch (IOException e)
		{
			System.err.println("Exception: " + e.getClass().getName());
			e.printStackTrace();
		}
	}
}
