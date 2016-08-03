package me.hacket.netrequestpro;

import android.app.Application;
import me.hacket.library.Net;
import me.hacket.library.NetConfig;
import me.hacket.library.util.RunningContext;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initRunningContext();

        icenetConfig();
    }

    private void initRunningContext() {
        RunningContext.initContext(getApplicationContext());
        RunningContext.initLogDebug(BuildConfig.LOG_DEBUG);
    }

    private void icenetConfig() {
        NetConfig config = new NetConfig.Builder()
                .setBaseUrl("http://private-f942-icenetsample.apiary-mock.com")
                .setContext(getApplicationContext())
                .build();

        Net.init(config);
    }

}
