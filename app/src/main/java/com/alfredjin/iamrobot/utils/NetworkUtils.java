package com.alfredjin.iamrobot.utils;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import java.util.List;

/**
 * Created by AlfredJin on 2017/4/10.
 */

public class NetworkUtils {
    private static NetworkUtils networkUtils;

    private NetworkUtils() {
    }

    public static NetworkUtils getInstance() {
        if (networkUtils == null) {
            networkUtils = new NetworkUtils();
        }
        return networkUtils;
    }

    /**
     * 判断网络是否连接
     *
     * @param context
     * @return
     */
    public boolean isNetworkConnect(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            if (cm.getActiveNetworkInfo().isAvailable()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断网络是否可用
     *
     * @param context
     * @return
     */
    public boolean isNetworkAvailable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] allNetworks = cm.getAllNetworkInfo();
            if (allNetworks != null) {
                for (int i = 0; i < allNetworks.length; i++) {
                    if (allNetworks[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 判断GPS是否可用
     *
     * @param context
     * @return
     */
    public boolean isGPSEnable(Context context) {
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        List<String> providers = lm.getProviders(true);
        return providers != null && providers.size() > 0;
    }

    /**
     * 判断WIFI是否打开
     *
     * @param context
     * @return
     */
    public boolean isWifiEnable(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().getState() ==
                NetworkInfo.State.CONNECTED || tm.getNetworkType() == TelephonyManager
                .NETWORK_TYPE_UMTS) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为手机网络
     *
     * @param context
     * @return
     */
    public boolean isMobile(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_MOBILE) {
            return true;
        }
        return false;
    }

    /**
     * 判断连接是wifi还是手机网络
     *
     * @param context
     * @return
     */
    public boolean isWifi(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info != null && info.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
