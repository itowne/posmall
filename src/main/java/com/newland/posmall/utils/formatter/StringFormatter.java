/*
 * Created on 2004-7-11
 *
 * Project: jasine.base
 */
package com.newland.posmall.utils.formatter;

import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * StringFormatter
 * Project: jasine.base
 * @author shen
 *
 * 2004-7-11
 */
public class StringFormatter extends Formatter {

    public StringFormatter(String name) {
        super(name);
    }

    public StringFormatter() {
        super("StringFormatter");
    }

    /**
     * @see com.jasine.format.Formatter#format(java.lang.Object)
     */
    public String format(Object aObject) {
        if (aObject == null) {
            return null;
        }
        //System.err.println(">>>>>>>>>>>"+aObject);
        return ((String) aObject).trim();
    }

    /**
     * @see com.jasine.format.Formatter#unformat(java.lang.String)
     */
    public Object unformat(String aString) {
        if (aString == null) {
            return null;
        }
        return aString.trim();
    }

    @Override
    public void setPattern(String pattern) {
    }

    public static java.util.Date toTime(String aString, String sep) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("HH" + sep + "mm" + sep + "ss");
        return formatter.parse(aString);
    }

    public static java.util.Date toDate(String aString, String sep) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy" + sep + "MM" + sep + "dd");
        return formatter.parse(aString);
    }

    public static java.util.Date toDateTime(String aString, String daySep, String dayTimeSep, String timeSep)
            throws ParseException {
        SimpleDateFormat formatter =
                new SimpleDateFormat(
                "yyyy" + daySep + "MM" + daySep + "dd" + dayTimeSep + "HH" + timeSep + "mm" + timeSep + "ss");
        return formatter.parse(aString);
    }
}
