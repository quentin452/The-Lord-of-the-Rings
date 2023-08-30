package lotr.common.util;

import java.util.Arrays;

public class CentredSquareArray<T> {
	public int radius;
	public int width;
	public Object[] array;

	public CentredSquareArray(int r) {
		radius = r;
		width = radius * 2 + 1;
		array = new Object[width * width];
	}

	public void fill(T val) {
		Arrays.fill(array, val);
	}

	public T get(int x, int y) {
		int index = getIndex(x, y);
		return (T) array[index];
	}

	public int getIndex(int x, int y) {
		return (y + radius) * width + x + radius;
	}

	public void set(int x, int y, T val) {
		int index = getIndex(x, y);
		array[index] = val;
	}
}
