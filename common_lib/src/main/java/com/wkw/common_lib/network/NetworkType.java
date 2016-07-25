package com.wkw.common_lib.network;

/**
 * Created by wukewei on 16/7/25.
 */
public enum NetworkType {
    /**
     * 无网络，网络不可用
     */
    NONE("NONE", false),
    /**
     * Wifi网络
     */
    WIFI("WIFI", true),
    /**
     * 2G网络 / 低速移动网络
     */
    MOBILE_2G("2G", true),
    /**
     * 3G网络 / 高速移动网络
     */
    MOBILE_3G("3G", true),

    /**
     * 4G网络 / 超高速移动网络(Yeah!)
     */
    MOBILE_4G("4G", true),
    /**
     * 有线网路
     */
    ETHERNET("ETHERNET", true),
    /**
     * 其他网络，含蓝牙、WIFI P2P等
     */
    OTHERS("UNKNOWN", true),;

    private String name;
    private boolean available;

    NetworkType(String friendlyName, boolean available) {
        setName(friendlyName);
        setAvailable(available);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }
}
