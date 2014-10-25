package main.game.saving;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import main.game.utils.JavaHelp;

public class DataTag implements Serializable, Cloneable
{
	public static final String USER_FOLDER = "";
	private HashMap<String, int[]> intArrays;
	private HashMap<String, String[]> stringArrays;
	private HashMap<String, boolean[]> booleanArrays;
	private HashMap<String, byte[]> byteArrays;
	private HashMap<String, float[]> floatArrays;
	private HashMap<String, short[]> shortArrays;
	private HashMap<String, double[]> doubleArrays;
	private HashMap<String, long[]> longArrays;
	private HashMap<String, Integer> ints;
	private HashMap<String, String> strings;
	private HashMap<String, Boolean> booleans;
	private HashMap<String, Byte> bytes;
	private HashMap<String, Float> floats;
	private HashMap<String, Short> shorts;
	private HashMap<String, Double> doubles;
	private HashMap<String, Long> longs;
	private ArrayList<DataTag> tags;

	public DataTag()
	{
		ints = JavaHelp.newHashMap();
		strings = JavaHelp.newHashMap();
		booleans = JavaHelp.newHashMap();
		bytes = JavaHelp.newHashMap();
		floats = JavaHelp.newHashMap();
		shorts = JavaHelp.newHashMap();
		doubles = JavaHelp.newHashMap();
		longs = JavaHelp.newHashMap();
		intArrays = JavaHelp.newHashMap();
		stringArrays = JavaHelp.newHashMap();
		booleanArrays = JavaHelp.newHashMap();
		byteArrays = JavaHelp.newHashMap();
		floatArrays = JavaHelp.newHashMap();
		shortArrays = JavaHelp.newHashMap();
		doubleArrays = JavaHelp.newHashMap();
		longArrays = JavaHelp.newHashMap();
		tags = JavaHelp.newArrayList();
	}

	public void appendTag(DataTag tag)
	{
		tags.add(tag);
	}

	public void setInteger(String name, int value)
	{
		ints.put(name, value);
	}

	public void setString(String name, String value)
	{
		strings.put(name, value);
	}

	public void setBoolean(String name, boolean value)
	{
		booleans.put(name, value);
	}

	public void setByte(String name, byte value)
	{
		bytes.put(name, value);
	}

	public void setFloat(String name, float value)
	{
		floats.put(name, value);
	}

	public void setShort(String name, short value)
	{
		shorts.put(name, value);
	}

	public void setDouble(String name, double value)
	{
		doubles.put(name, value);
	}

	public void setLong(String name, long value)
	{
		longs.put(name, value);
	}

	public void setIntegerArray(String name, int[] value)
	{
		intArrays.put(name, value);
	}

	public void setStringArray(String name, String[] value)
	{
		stringArrays.put(name, value);
	}

	public void setBooleanArray(String name, boolean[] value)
	{
		booleanArrays.put(name, value);
	}

	public void setByteArray(String name, byte[] value)
	{
		byteArrays.put(name, value);
	}

	public void setFloatArray(String name, float[] value)
	{
		floatArrays.put(name, value);
	}

	public void setShortArray(String name, short[] value)
	{
		shortArrays.put(name, value);
	}

	public void setDoubleArray(String name, double[] value)
	{
		doubleArrays.put(name, value);
	}

	public void setLongArray(String name, long[] value)
	{
		longArrays.put(name, value);
	}

	public int getInteger(String name)
	{
		return ints.containsKey(name) ? ints.get(name) : 0;
	}

	public String getString(String name)
	{
		return strings.containsKey(name) ? strings.get(name) : "";
	}

	public boolean getBoolean(String name)
	{
		return booleans.containsKey(name) ? booleans.get(name) : false;
	}

	public byte getByte(String name)
	{
		return bytes.containsKey(name) ? bytes.get(name) : 0;
	}

	public float getFloat(String name)
	{
		return floats.containsKey(name) ? floats.get(name) : 0.0F;
	}

	public short getShort(String name)
	{
		return shorts.containsKey(name) ? shorts.get(name) : 0;
	}

	public double getDouble(String name)
	{
		return doubles.containsKey(name) ? doubles.get(name) : 0.0D;
	}

	public long getLong(String name)
	{
		return longs.containsKey(name) ? longs.get(name) : 0L;
	}

	public int[] getIntegerArray(String name)
	{
		return intArrays.containsKey(name) ? intArrays.get(name) : null;
	}

	public String[] getStringArray(String name)
	{
		return stringArrays.containsKey(name) ? stringArrays.get(name) : null;
	}

	public boolean[] getBooleanArray(String name)
	{
		return booleanArrays.containsKey(name) ? booleanArrays.get(name) : null;
	}

	public byte[] getByteArray(String name)
	{
		return byteArrays.containsKey(name) ? byteArrays.get(name) : null;
	}

	public float[] getFloatArray(String name)
	{
		return floatArrays.containsKey(name) ? floatArrays.get(name) : null;
	}

	public short[] getShortArray(String name)
	{
		return shortArrays.containsKey(name) ? shortArrays.get(name) : null;
	}

	public double[] getDoubleArray(String name)
	{
		return doubleArrays.containsKey(name) ? doubleArrays.get(name) : null;
	}

	public long[] getLongArray(String name)
	{
		return longArrays.containsKey(name) ? longArrays.get(name) : null;
	}

	public DataTag copy()
	{
		DataTag tag = new DataTag();
		tag.ints = JavaHelp.cloneHashMap(ints);
		tag.longs = JavaHelp.cloneHashMap(longs);
		tag.doubles = JavaHelp.cloneHashMap(doubles);
		tag.shorts = JavaHelp.cloneHashMap(shorts);
		tag.floats = JavaHelp.cloneHashMap(floats);
		tag.strings = JavaHelp.cloneHashMap(strings);
		tag.bytes = JavaHelp.cloneHashMap(bytes);
		tag.booleans = JavaHelp.cloneHashMap(booleans);
		tag.intArrays = JavaHelp.cloneHashMap(intArrays);
		tag.longArrays = JavaHelp.cloneHashMap(longArrays);
		tag.doubleArrays = JavaHelp.cloneHashMap(doubleArrays);
		tag.shortArrays = JavaHelp.cloneHashMap(shortArrays);
		tag.floatArrays = JavaHelp.cloneHashMap(floatArrays);
		tag.stringArrays = JavaHelp.cloneHashMap(stringArrays);
		tag.byteArrays = JavaHelp.cloneHashMap(byteArrays);
		tag.booleanArrays = JavaHelp.cloneHashMap(booleanArrays);
		tag.tags = JavaHelp.newArrayList();
		tag.tags.addAll(tags);
		return tag;
	}

	public DataTag getTagAt(int index)
	{
		return index < tagSize() && index >= 0 ? tags.get(index) : new DataTag();
	}

	public int tagSize()
	{
		return tags.size();
	}

	@Override
	public Object clone()
	{
		try
		{
			return super.clone();
		}
		catch (CloneNotSupportedException e)
		{
			e.printStackTrace(); // not possible
		}
		return null;
	}

	public static DataTag loadFrom(File file)
	{
		CompoundTag comp = new CompoundTag(file);
		return comp.load();
	}

	public static void saveTo(File file, DataTag tag)
	{
		CompoundTag comp = new CompoundTag(file);
		comp.save(tag);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (obj instanceof DataTag)
		{
			DataTag tag = (DataTag) obj;
			if (!tag.ints.equals(ints)) return false;
			if (!tag.longs.equals(longs)) return false;
			if (!tag.doubles.equals(doubles)) return false;
			if (!tag.shorts.equals(shorts)) return false;
			if (!tag.strings.equals(strings)) return false;
			if (!tag.floats.equals(floats)) return false;
			if (!tag.bytes.equals(bytes)) return false;
			if (!tag.booleans.equals(booleans)) return false;
			if (!tag.intArrays.equals(intArrays)) return false;
			if (!tag.longArrays.equals(longArrays)) return false;
			if (!tag.doubleArrays.equals(doubleArrays)) return false;
			if (!tag.shortArrays.equals(shortArrays)) return false;
			if (!tag.stringArrays.equals(stringArrays)) return false;
			if (!tag.floatArrays.equals(floatArrays)) return false;
			if (!tag.byteArrays.equals(byteArrays)) return false;
			if (!tag.booleanArrays.equals(booleanArrays)) return false;
			if (!tag.tags.equals(tags)) return false;
			return true;
		}
		return false;
	}

	@Override
	public int hashCode()
	{
		int hash = 0;
		hash += ints.hashCode();
		hash += longs.hashCode();
		hash += doubles.hashCode();
		hash += shorts.hashCode();
		hash += strings.hashCode();
		hash += floats.hashCode();
		hash += bytes.hashCode();
		hash += booleans.hashCode();
		hash += intArrays.hashCode();
		hash += longArrays.hashCode();
		hash += doubleArrays.hashCode();
		hash += shortArrays.hashCode();
		hash += stringArrays.hashCode();
		hash += floatArrays.hashCode();
		hash += byteArrays.hashCode();
		hash += booleanArrays.hashCode();
		hash += tags.hashCode();
		return hash;
	}

	private static final long serialVersionUID = 1L;
}
