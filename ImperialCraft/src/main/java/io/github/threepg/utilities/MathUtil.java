package io.github.threepg.utilities;

import java.text.NumberFormat;
import java.text.ParsePosition;

public class MathUtil {
	public static boolean isNumber(String str)
	{
	  NumberFormat formatter = NumberFormat.getInstance();
	  ParsePosition pos = new ParsePosition(0);
	  formatter.parse(str, pos);
	  return str.length() == pos.getIndex();
	}
}
