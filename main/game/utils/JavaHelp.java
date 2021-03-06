package main.game.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class JavaHelp
{
	private JavaHelp()
	{
	}

	public static <T> T[] growArrayByOne(T[] obj)
	{
		return growArray(obj, 1);
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] growArray(T[] obj, int amountToGrow)
	{
		assert amountToGrow > 0 : "Amount to Grow must be positive";
		Object[] tmp = new Object[obj.length + amountToGrow];
		for (int i = 0; i < obj.length; i++)
			tmp[i] = obj[i];
		return (T[]) tmp;
	}

	@SuppressWarnings("unchecked")
	public static <T> T[] growArrayByAt(T[] obj, int indexToGrowAt, int amountToGrow)
	{
		assert amountToGrow > 0 : "Amount to Grow must be positive";
		assert indexToGrowAt > 0 : "Index to Grow must be positive";
		assert indexToGrowAt < obj.length : "Index to Grow to Grow must be less than the size";

		Object[] tmp = new Object[obj.length + amountToGrow];
		for (int i = indexToGrowAt + 1; i < obj.length + amountToGrow; i++)
			tmp[i] = obj[i];
		return (T[]) tmp;
	}

	public static <T> T[] growArrayAt(T[] obj, int indexToGrowAt)
	{
		return growArrayByAt(obj, indexToGrowAt, 1);
	}

	public static <T> ArrayList<T> newArrayList()
	{
		return new ArrayList<T>();
	}

	public static <T, V> HashMap<T, V> newHashMap()
	{
		return new HashMap<T, V>();
	}

	public static <T, V> HashMap<T, V> cloneHashMap(HashMap<T, V> map)
	{
		HashMap<T, V> copy = newHashMap();
		copy.putAll(map);
		return null;
	}

	@SafeVarargs
	public static <T> T getRandomElementFrom(T... objs)
	{
		if (objs.length > 0) return objs[new Random().nextInt(objs.length - 1)];
		return null;
	}

	public static boolean isVowel(char ch)
	{
		return ch == 'a' || ch == 'e' || ch == 'i' || ch == 'o' || ch == 'u' || ch == 'A' || ch == 'E' || ch == 'I' || ch == 'O' || ch == 'U';
	}

	public static boolean startsWithVowel(String string)
	{
		return string.length() > 0 && isVowel(string.charAt(0));
	}
}
