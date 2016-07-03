package com.blogspot.nipunswritings.lkweather.network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by nipun on 6/28/16.
 */
public class NetUtils {

    public static boolean isConnectedToNetwork(Context context){
        boolean isConnected = false;

        ConnectivityManager connectivityManager =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null)
            isConnected = networkInfo.isConnected();

        return isConnected;
    }

}
