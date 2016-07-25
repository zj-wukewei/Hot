package com.wkw.hot.ui;

import android.app.Application;

import com.wkw.common_lib.Ext;
import com.wkw.common_lib.network.Network;
import com.wkw.common_lib.utils.ViewUtils;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.component.DaggerAppComponent;
import com.wkw.hot.reject.module.AppModule;

/**
 * Created by wukewei on 16/5/26.
 */
public class App extends Application {

    private static App appContext;
    private static AppComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        initExtension();
    }

    private void initExtension() {
        Ext.init(this, new ExtImpl());
    }

    public static final class ExtImpl extends Ext {

        @Override
        public String getCurOpenId() {
            return null;
        }

        @Override
        public String getDeviceInfo() {
            return null;
        }

        @Override
        public String getPackageNameForResource() {
            return "com.wkw.hot";
        }

        @Override
        public int getScreenHeight() {
            return ViewUtils.getScreenHeight();
        }

        @Override
        public int getScreenWidth() {
            return ViewUtils.getScreenWidth();
        }

        @Override
        public boolean isAvailable() {
            return Network.isAvailable();
        }

        @Override
        public boolean isWap() {
            return Network.isWap();
        }

        @Override
        public boolean isMobile() {
            return Network.isMobile();
        }

        @Override
        public boolean is2G() {
            return Network.is2G();
        }

        @Override
        public boolean is3G() {
            return Network.is3G();
        }

        @Override
        public boolean isWifi() {
            return Network.isWifi();
        }

        @Override
        public boolean isEthernet() {
            return Network.isEthernet();
        }
    }

    public static AppComponent getAppComponent() {
        return mAppComponent;
    }
    public static App getAppContext() {
        return appContext;
    }

}
