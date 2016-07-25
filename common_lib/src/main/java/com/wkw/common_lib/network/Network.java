package com.wkw.common_lib.network;

import android.support.annotation.RequiresPermission;

import com.wkw.common_lib.Ext;
import com.wkw.common_lib.utils.NetWorkUtils;

/**
 * Created by wukewei on 16/7/25.
 */
public class Network extends NetworkDash {
    /**
     * 系统代理信息
     */
    public static abstract class Proxy {
        public static int getPort() {
            return android.net.Proxy.getDefaultPort();
        }

        public static String getHost() {
            return android.net.Proxy.getDefaultHost();
        }
    }

    /**
     * WIFI网卡信息
     */
    public static class Wifi extends WifiDash {

    }

    public static class Dns {
        @RequiresPermission(android.Manifest.permission.ACCESS_WIFI_STATE)
        public static NetWorkUtils.DNS getDNS() {
            return NetWorkUtils.getDNS(Ext.getContext());
        }
    }
}