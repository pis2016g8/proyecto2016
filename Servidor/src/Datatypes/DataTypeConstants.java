package Datatypes;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class DataTypeConstants {
	
	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	
	public static DateFormat getDateFormat() {
		return dateFormat;
	}

}
