package com.wkw.common_lib.network;

import com.wkw.common_lib.utils.StringUtils;

import java.util.HashMap;

/**
 * Created by wukewei on 16/7/25.
 */
enum  AccessPoint {
    /**
     * 接入点不可用，通常是无网络或者非移动网络
     */
    NONE(StringUtils.EMPTY, ServiceProvider.NONE, false),
    /**
     * 未知的接入点
     */
    NEVER_HEARD("I don't know", ServiceProvider.NEVER_HEARD, false), // 未知接入点
    /**
     * 中国移动 cmnet
     */
    CMNET("cmnet", ServiceProvider.CHINA_MOBILE, false), // 中国移动 NET
    /**
     * 中国移动 cmwap
     */
    CMWAP("cmwap", ServiceProvider.CHINA_MOBILE, true), // 中国移动 WAP
    /**
     * 中国联通 2G uninet
     */
    UNINET("uninet", ServiceProvider.CHINA_UNICOM, false), // 中国联通 2G NET
    /**
     * 中国联通 2G uniwap
     */
    UNIWAP("uniwap", ServiceProvider.CHINA_UNICOM, true), // 中国联通 2G WAP
    /**
     * 中国联通 3G 3gnet
     */
    _3GNET("3gnet", ServiceProvider.CHINA_UNICOM, false), // 中国联通 3G NET
    /**
     * 中国联通 3G 3gwap
     */
    _3GWAP("3gwap", ServiceProvider.CHINA_UNICOM, true), // 中国联通 3G WAP
    /**
     * 中国电信 ctnet
     */
    CTNET("ctnet", ServiceProvider.CHINA_TELECOM, false), // 中国电信 NET
    /**
     * 中国电信 ctwap
     */
    CTWAP("ctwap", ServiceProvider.CHINA_TELECOM, true), // 中国电信 WAP
    /**
     * 中国电信 #777
     */
    SHARP777("#777", ServiceProvider.CHINA_TELECOM, false), // 中国电信 #777
    ;

    private static HashMap<String, AccessPoint> ACCESS_POINT_MAP = new HashMap<String, AccessPoint>();

    static {
        // 初始化所有已知的接入点
        for (AccessPoint accessPoint : AccessPoint.values()) {
            ACCESS_POINT_MAP.put(accessPoint.getName(), accessPoint);
        }
    }

    private String name;
    private ServiceProvider provider;
    private boolean wap;

    AccessPoint(String name, ServiceProvider provider, boolean isWap) {
        setName(name);
        setProvider(provider);
        setWap(isWap);
    }

    /**
     * 获得对应的接入点对象
     *
     * @param name 接入点名称
     * @return 接入点枚举对象<br>
     * <br>
     * 如果名称为空，得到{@link AccessPoint}.NONE<br>
     * 如果并非此枚举中的范围，得到{@link AccessPoint}.NEVER_HEARD
     */
    public static AccessPoint forName(String name) {
        if (name == null) {
            return NONE;
        }

        AccessPoint accessPoint = ACCESS_POINT_MAP.get(name.toLowerCase());

        return ((accessPoint == null) ? NEVER_HEARD : accessPoint);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取接入点所属于的服务运营商
     *
     * @return 运营商枚举
     */
    public ServiceProvider getProvider() {
        return provider;
    }

    public void setProvider(ServiceProvider provider) {
        this.provider = provider;
    }

    /**
     * 判断是否是WAP接入点
     *
     * @return 是否是WAP接入点
     */
    public boolean isWap() {
        return wap;
    }

    public void setWap(boolean wap) {
        this.wap = wap;
    }
}
