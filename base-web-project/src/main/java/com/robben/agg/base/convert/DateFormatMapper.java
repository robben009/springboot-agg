package com.robben.agg.base.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormatMapper {
 
    public String asString(Date date) {
        return date != null ? new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
            .format( date ) : null;
    }
 
    public Date asDate(String date) {
        try {
            return date != null ? new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss")
                .parse( date ) : null;
        }
        catch ( ParseException e ) {
            throw new RuntimeException( e );
        }
    }

    public Long dateAsLong(Date date) {
        return date != null ? date.getTime() : null;
    }



}