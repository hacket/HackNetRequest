package me.hacket.netrequestpro;

import android.app.Application;
import me.hacket.library.HNet;
import me.hacket.library.HNetConfig;
import me.hacket.library.HParam;
import me.hacket.library.Header;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        icenetConfig();
    }

    private void icenetConfig() {

        // 配置通用的请求header
        Header header = new Header.Builder()
                .add("Content-Type", "UTF-8")
                .add("User-Agent", "common_hacket_ua")
                .add("common_header", "common_hacket_header")
                .build();

        // 配置通用的请求参数param
        HParam param = new HParam.Builder()
                .add("common_userid", "hacket_id")
                .add("common_client_id", "client_id")
                .add("common_location", "shenzhen")
                .build();

        // 全局配置baseurl, context, header, param, logtag
        HNetConfig config = new HNetConfig.Builder()
                .setBaseUrl("http://192.168.199.233:8080")
                .setContext(getApplicationContext())
                .setHeader(header)
                .setParam(param)
                .setLogTag("hacket")
                .isLogDebug(true)
                .build();

        // 初始化
        HNet.init(config);
    }

}
