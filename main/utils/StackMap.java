package main.utils;

import java.io.Serializable;
import java.util.HashMap;

public class StackMap<E> extends HashMap<String, E> implements Serializable
{
	private static final long serialVersionUID = 1L;

	public static <E> StackMap<E> newMap()
	{
		return new StackMap<E>();
	}
}
