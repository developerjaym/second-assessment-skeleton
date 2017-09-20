package com.cooksys.second.utility;

import java.sql.Timestamp;
import java.util.GregorianCalendar;

public class TimeStamper {

	public static Timestamp getTimestamp()
	{
		return new Timestamp(GregorianCalendar.getInstance().getTimeInMillis());
	}
}
