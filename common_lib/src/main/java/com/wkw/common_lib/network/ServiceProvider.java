package com.wkw.common_lib.network;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wukewei on 16/7/25.
 */
public enum  ServiceProvider {
    /**
     * 运营商不可用，非移动网络或无网络
     */
    NONE("N/A"),
    /**
     * 未知运营商，通常在未知接入点时
     */
    NEVER_HEARD("Unknown"),
    /**
     * 中国移动 China Mobile
     */
    CHINA_MOBILE("China Mobile"),
    /**
     * 中国联通 China Unicom
     */
    CHINA_UNICOM("China Unicom"),
    /**
     * 中国电信 China Telecom
     */
    CHINA_TELECOM("China Telecom"),;

    private static final Map<String, ServiceProvider> IMSI_PROVIDER_MAP = new HashMap<String, ServiceProvider>();

    static {
        // 中国移动
        IMSI_PROVIDER_MAP.put("46000", CHINA_MOBILE);
        IMSI_PROVIDER_MAP.put("46002", CHINA_MOBILE);
        IMSI_PROVIDER_MAP.put("46007", CHINA_MOBILE);

        // 中国电信
        IMSI_PROVIDER_MAP.put("46003", CHINA_TELECOM);
        IMSI_PROVIDER_MAP.put("46005", CHINA_TELECOM);

        // 中国联通
        IMSI_PROVIDER_MAP.put("46001", CHINA_UNICOM);
        IMSI_PROVIDER_MAP.put("46006", CHINA_UNICOM);

        // 中国铁通，算作移动
        IMSI_PROVIDER_MAP.put("46020", CHINA_MOBILE);
    }

    private String name;

    ServiceProvider(String name) {
        setName(name);
    }

    /**
     * 根据IMSI中的MNC和MCC记录，计算运营商
     *
     * @return 运营商
     */
    public static ServiceProvider fromIMSI(String IMSI) {
        if (IMSI == null) {
            return ServiceProvider.NONE;
        }

        if (IMSI.length() < 5) {
            return ServiceProvider.NONE;
        }

        ServiceProvider provider = IMSI_PROVIDER_MAP.get(IMSI.substring(0, 5));

        if (provider != null) {
            return provider;
        } else {
            return ServiceProvider.NEVER_HEARD;
        }
    }

    public String getName() {
        return name;
    }

    private void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
