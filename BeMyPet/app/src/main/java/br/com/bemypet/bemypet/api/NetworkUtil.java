package br.com.bemypet.bemypet.api;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import br.com.bemypet.bemypet.controller.Constants;

/**
 * Created by Cassio on 21/08/16.
 */
public class NetworkUtil {

    public static int TYPE_WIFI = 1;
    public static int TYPE_MOBILE = 2;
    public static int TYPE_NOT_CONNECTED = 0;

    public static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if(activeNetwork.getType() == ConnectivityManager.TYPE_WIFI)
                return TYPE_WIFI;

            if(activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE)
                return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = Constants.WIFI_ENABLED;
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = Constants.MOBILE_DATA_ENABLED;
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = Constants.NO_INTERNET_CONNECTION;
        }
        return status;
    }
}
