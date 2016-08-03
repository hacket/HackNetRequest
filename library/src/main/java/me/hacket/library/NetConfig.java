package me.hacket.library;

import android.content.Context;

/**
 * 网络请求基本配置
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class NetConfig {

    private String baseUrl;
    private Context context;

    NetConfig(Builder builder) {
        this.baseUrl = builder.baseUrl;
        this.context = builder.context;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Context getContext() {
        return context;
    }

    public static final class Builder {
        private String baseUrl;
        private Context context;

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public NetConfig build() {
            return new NetConfig(this);
        }
    }

}
