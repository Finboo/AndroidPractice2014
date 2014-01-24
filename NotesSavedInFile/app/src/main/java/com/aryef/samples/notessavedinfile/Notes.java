package com.aryef.samples.notessavedinfile;

/**
 * Created by arye on 23/12/13.
 */
public class Notes {

    public String mLabel;
    public Long mTimeStamp;

    public Notes(String labelString)
    {
        mLabel = labelString;
        mTimeStamp = System.currentTimeMillis();
    }

    public Notes(String labelString, long lo)
    {
        mLabel = labelString;
        mTimeStamp = lo;
    }


}
