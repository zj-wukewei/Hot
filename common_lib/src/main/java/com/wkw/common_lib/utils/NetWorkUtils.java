package com.wkw.common_lib.utils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;

import com.wkw.common_lib.Ext;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;
import java.util.HashMap;

/**
 * Created by wukewei on 16/7/25.
 */
public final class NetWorkUtils {
    // ------------------ common -------------------
    public final static int NETWORK_TYPE_NONE = -1;
    public final static int NETWORK_TYPE_UNKNOWN = 1;
    public final static int NETWORK_TYPE_UNKNOWN_MOBILE = 2;
    public final static int NETWORK_TYPE_2G = 3;
    public final static int NETWORK_TYPE_3G = 4;
    public final static int NETWORK_TYPE_4G = 5;
    public final static int NETWORK_TYPE_WIFI = 10;
    /**
     * WIFI的APN名字..
     */
    public final static String APN_NAME_WIFI = "wifi";
    private final static String TAG = "NetworkUtil";
    private final static Uri PREFERRED_APN_URI
            = Uri.parse("content://telephony/carriers/preferapn");
    private final static HashMap<String, NetworkProxy> sAPNProxies
            = new HashMap<String, NetworkProxy>();

    static {
        sAPNProxies.put("cmwap", new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put("3gwap", new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put("uniwap", new NetworkProxy("10.0.0.172", 80));
        sAPNProxies.put("ctwap", new NetworkProxy("10.0.0.200", 80));
    }

    /**
     * 检查当前网络是否连接
     */
    public static boolean isNetworkConnected() {
        return isNetworkConnected(Ext.getContext());
    }

    /**
     * 检查当前网络是否连接
     *
     * @param context Application context.
     * @return 当前网络是否连接
     */
    public static boolean isNetworkConnected(Context context) {
        NetworkInfo info = getActiveNetworkInfo(context);
        return info != null && info.isConnected();
    }

    public static boolean isWifiConnected() {
        return isWifiConnected(Ext.getContext());
    }

    /**
     * 检查当前Wifi网络是否连接
     *
     * @param context Application context.
     * @return Whether wifi network is connected.
     */
    public static boolean isWifiConnected(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_WIFI;
    }

    /**
     * 检查当前移动网络是否连接
     *
     * @param context Application context.
     * @return Whether mobile network is connected.
     */
    public static boolean isMobileConnected(Context context) {
        if (context == null) {
            return false;
        }
        NetworkInfo activeNetworkInfo = getActiveNetworkInfo(context);
        return activeNetworkInfo != null && activeNetworkInfo.getType() == ConnectivityManager.TYPE_MOBILE;
    }

    /**
     * Returns details about the currently active default data network. When
     * connected, this network is the default route for outgoing connections.
     * You should always check {@link NetworkInfo#isConnected()} before initiating
     * network traffic. This may return {@code null} when there is no default
     * network.
     *
     * @param context Application context.
     * @return a {@link NetworkInfo} object for the current default network
     * or {@code null} if no network default network is currently active
     * <p>
     * <p>This method requires the call to hold the permission
     * {@link android.Manifest.permission#ACCESS_NETWORK_STATE}.
     */
    public static NetworkInfo getActiveNetworkInfo(Context context) {
        try {
            ConnectivityManager connMgr = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            return connMgr.getActiveNetworkInfo();
        } catch (Throwable e) {
            Log.e(TAG, "fail to get active network info", e);
            return null;
        }
    }

    /**
     * 返回当前活动网络的类型.
     *
     * @param context Application context.
     * @return type of current active network, the alternation is {@link #NETWORK_TYPE_WIFI}, {@link #NETWORK_TYPE_4G},
     * {@link #NETWORK_TYPE_3G}, {@link #NETWORK_TYPE_2G}, {@link #NETWORK_TYPE_UNKNOWN_MOBILE}, {@link #NETWORK_TYPE_UNKNOWN},
     * {@link #NETWORK_TYPE_NONE}.
     */
    public static int getActiveNetworkType(Context context) {
        return getNetworkType(context, getActiveNetworkInfo(context));
    }

    /**
     * Returns type of corresponding network info.
     *
     * @param context Application context.
     * @param info    Network info.
     * @return type of current active network, the alternation is {@link #NETWORK_TYPE_WIFI}, {@link #NETWORK_TYPE_4G},
     * {@link #NETWORK_TYPE_3G}, {@link #NETWORK_TYPE_2G}, {@link #NETWORK_TYPE_UNKNOWN_MOBILE}, {@link #NETWORK_TYPE_UNKNOWN},
     * {@link #NETWORK_TYPE_NONE}.
     */
    public static int getNetworkType(Context context, NetworkInfo info) {
        if (info != null) {
            if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                return NETWORK_TYPE_WIFI;

            } else if (info.getType() == ConnectivityManager.TYPE_MOBILE) {
                int subType = info.getSubtype();
                switch (subType) {
                    // 2G
                    case TelephonyManager.NETWORK_TYPE_EDGE: // ~25 kbps
                    case TelephonyManager.NETWORK_TYPE_GPRS: // GPRS (2.5G)
                    case TelephonyManager.NETWORK_TYPE_CDMA: // CDMA: Either IS95A or IS95B (2G)
                    case TelephonyManager.NETWORK_TYPE_1xRTT: // 1xRTT (2G - Transitional)
                    case TelephonyManager.NETWORK_TYPE_IDEN: // iDen (2G)
                        return NETWORK_TYPE_2G;

                    // 3G
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0: // EVDO revision 0 (3G)
                    case TelephonyManager.NETWORK_TYPE_HSPA: // HSPA (3G - Transitional)
                        return NETWORK_TYPE_3G;

                    // 3.5G
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: // EVDO revision A (3G - Transitional)
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: // EVDO revision A (3G - Transitional)
                    case TelephonyManager.NETWORK_TYPE_HSPAP: // HSPA+ (3G - Transitional)
                    case TelephonyManager.NETWORK_TYPE_HSDPA: // HSDPA (3G - Transitional)
                    case TelephonyManager.NETWORK_TYPE_HSUPA: // HSUPA (3G - Transitional)
                        return NETWORK_TYPE_3G;

                    // 4G
                    case TelephonyManager.NETWORK_TYPE_LTE:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                        return NETWORK_TYPE_4G;

                    default:
                        return NETWORK_TYPE_UNKNOWN_MOBILE;
                }
            }
            return NETWORK_TYPE_UNKNOWN;
        }
        return NETWORK_TYPE_NONE;
    }

    /**
     * 获取网络代理，需要指定是否通过APN去获取.
     *
     * @param context  Application context.
     * @param apnProxy Control whether determine the proxy by system interface or by current apn.
     * @return Current network proxy.
     */
    public static NetworkProxy getProxy(Context context, boolean apnProxy) {
        return !apnProxy ? getProxy(context) : getProxyByAPN(context);
    }

    /**
     * 通过系统的接口获取网络代理.
     *
     * @param context Application context.
     * @return Current network proxy.
     */
    public static NetworkProxy getProxy(Context context) {
        if (!isMobileConnected(context)) {
            return null;
        }
        String proxyHost = getProxyHost(context);
        int proxyPort = getProxyPort(context);
        if (!isEmpty(proxyHost) && proxyPort >= 0) {
            return new NetworkProxy(proxyHost, proxyPort);
        }
        return null;
    }

    @SuppressWarnings("deprecation")
    private static String getProxyHost(Context context) {
        String host = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            host = Proxy.getDefaultHost();
        } else {
            host = System.getProperty("http.proxyHost");
        }
        return host;
    }

    @SuppressWarnings("deprecation")
    private static int getProxyPort(Context context) {
        int port = -1;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            port = Proxy.getDefaultPort();
        } else {
            String portStr = System.getProperty("http.proxyPort");
            if (!isEmpty(portStr)) {
                try {
                    port = Integer.parseInt(portStr);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
        if (port < 0 || port > 65535) {
            // ensure valid port.
            port = -1;
        }
        return port;
    }

    /**
     * 通过APN获取当前网络代理.
     *
     * @param context Application context.
     * @return Current network proxy.
     */
    public static NetworkProxy getProxyByAPN(Context context) {
        if (!isMobileConnected(context)) {
            return null;
        }
        String apn = getAPN(context);
        NetworkProxy proxy = sAPNProxies.get(apn);
        return proxy == null ? null : proxy.copy();
    }

    /**
     * 获得当前APN.
     *
     * @param context Application context.
     * @return Current apn. If device is connected to wifi, then it will be {@link #APN_NAME_WIFI}.
     */
    public static String getAPN(Context context) {
        NetworkInfo activeNetInfo = getActiveNetworkInfo(context);
        if (activeNetInfo == null) {
            // no active network.
            return null;
        }

        String apn = null;
        if (activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            apn = APN_NAME_WIFI;

        } else if (activeNetInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                Cursor cursor = null;
                try {
                    cursor = context.getContentResolver().query(PREFERRED_APN_URI, null, null, null, null);
                    while (cursor != null && cursor.moveToNext()) {
                        apn = cursor.getString(cursor.getColumnIndex("apn"));
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
            if (TextUtils.isEmpty(apn)) {
                apn = activeNetInfo.getExtraInfo();
            }
        }
        if (apn != null) {
            // convert apn to lower case.
            apn = apn.toLowerCase();
        }

        return apn;
    }

    /**
     * 获得当前DNS.
     *
     * @param context Application context.
     * @return 当前DNS.
     */
    @RequiresPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
    public static DNS getDNS(Context context) {
        DNS dns = new DNS();
        if (context != null) {
            if (isWifiConnected(context)) {
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                DhcpInfo dhcpInfo = wifiManager.getDhcpInfo();
                if (dhcpInfo != null) {
                    dns.primary = int32ToIPStr(dhcpInfo.dns1);
                    dns.secondary = int32ToIPStr(dhcpInfo.dns2);
                }
            }
        }
        if (dns.primary == null && dns.secondary == null) {
            // retrieve dns with property.
            dns.primary = PropertyUtils.get(PropertyUtils.PROPERTY_DNS_PRIMARY, null);
            dns.secondary = PropertyUtils.get(PropertyUtils.PROPERTY_DNS_SECONDARY, null);
        }
        return dns;
    }

    private static String int32ToIPStr(int ip) {
        StringBuffer buffer = new StringBuffer();

        buffer.append(ip & 0xFF).append(".");
        buffer.append((ip >> 8) & 0xFF).append(".");
        buffer.append((ip >> 16) & 0xFF).append(".");
        buffer.append((ip >> 24) & 0xFF);

        return buffer.toString();
    }

    /**
     * 获得当前本地IP
     */
    public static String getLocalIP() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface ni = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = ni.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }

        } catch (Throwable ex) {
            // ignore.
        }
        return null;
    }

    // ---------------- utils ------------------
    private static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    /**
     * 网络代理定义.
     */
    public static class NetworkProxy implements Cloneable {

        public final String host;
        public final int port;

        NetworkProxy(String host, int port) {
            this.host = host;
            this.port = port;
        }

        final NetworkProxy copy() {
            try {
                return (NetworkProxy) clone();
            } catch (CloneNotSupportedException e) {
                // ignore.
            }
            return null;
        }

        @Override
        public String toString() {
            return host + ":" + port;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;

            if (obj != null && obj instanceof NetworkProxy) {
                NetworkProxy proxy = (NetworkProxy) obj;
                if (TextUtils.equals(this.host, proxy.host) && this.port == proxy.port)
                    return true;
            }

            return false;
        }
    }

    /**
     * DNS定义.
     */
    public final static class DNS {
        public String primary;
        public String secondary;

        DNS() {
        }

        @Override
        public String toString() {
            return primary + "," + secondary;
        }
    }
}
