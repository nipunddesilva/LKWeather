package com.blogspot.nipunswritings.lkweather.network;

import java.io.InputStream;

/**
 * Created by nipun on 6/28/16.
 */
public interface HttpDataProcessor {
    public boolean processReceivedData(InputStream is);
}
