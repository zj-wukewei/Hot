package com.wkw.hot.ui;

import android.app.Application;

import com.facebook.react.ReactApplication;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.shell.MainReactPackage;
import com.wkw.common_lib.Ext;
import com.wkw.common_lib.image.ImageLoader;
import com.wkw.common_lib.image.glide.GlideImageLoaderStrategy;
import com.wkw.common_lib.network.Network;
import com.wkw.common_lib.utils.ProcessUtils;
import com.wkw.common_lib.utils.ViewUtils;
import com.wkw.hot.BuildConfig;
import com.wkw.hot.reject.component.AppComponent;
import com.wkw.hot.reject.component.DaggerAppComponent;
import com.wkw.hot.reject.module.AppModule;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wukewei on 16/5/26.
 */
public class App extends Application implements ReactApplication{

    private static App appContext;
    private static AppComponent mAppComponent;

    private final ReactNativeHost mReactNativeHost = new ReactNativeHost(this) {
        @Override
        protected boolean getUseDeveloperSupport() {
            return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
            return Arrays.<ReactPackage>asList(
                    new MainReactPackage()
            );
        }
    };
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = this;
        if (!ProcessUtils.isMainProcess(this)) {
            return;
        }
        mAppComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
        initImageLoader();
        initExtension();
    }


    private void initImageLoader() {
        ImageLoader.getInstance().setImageLoaderStragety(new GlideImageLoaderStrategy());
    }


    private void initExtension() {
        Ext.init(this, new ExtImpl());
    }

    @Override
    public ReactNativeHost getReactNativeHost() {
        return mReactNativeHost;
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
