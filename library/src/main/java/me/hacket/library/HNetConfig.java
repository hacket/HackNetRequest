package me.hacket.library;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

/**
 * 网络请求基本配置
 * Created by hacket on 2016/8/2 0002.
 */
public class HNetConfig {

    public static String TAG = "HNet";

    public static boolean IS_LOG_DEBUG;

    private Context context;
    private String baseUrl;
    private Header header;
    private HParam param;

    private HNetConfig(Builder builder) {
        this.context = builder.context;
        this.baseUrl = builder.baseUrl;
        this.header = builder.header;
        this.param = builder.param;

        if (this.context == null) {
            throw new IllegalArgumentException("the context must not be null !");
        }

        if (this.baseUrl == null) {
            throw new IllegalArgumentException("the baseUrl must not be null !");
        }

        if (this.header == null) {
            header = new Header.Builder().build();
        }

        if (this.param == null) {
            param = new HParam.Builder().build();
        }

        IS_LOG_DEBUG = builder.isLogDebug;

        if (!TextUtils.isEmpty(builder.logTag) && TextUtils.isEmpty(builder.logTag.trim())) {
            TAG = builder.logTag;
        }
    }

    public Header getHeader() {
        return header;
    }

    public HParam getParam() {
        return param;
    }

    public String getBaseUrl() {
        return baseUrl;
    }

    public Context getContext() {
        return context;
    }

    public static final class Builder {

        private Context context;
        private String baseUrl;
        private Header header;
        private HParam param;
        private boolean isLogDebug = true;
        private String logTag;

        public Builder setLogTag(@NonNull String logTag) {
            this.logTag = logTag;
            return this;
        }

        /**
         * 是否输出Log
         *
         * @param isLogDebug isLogDebug
         *
         * @return Builder
         */
        public Builder isLogDebug(boolean isLogDebug) {
            this.isLogDebug = isLogDebug;
            return this;
        }

        public Builder setParam(HParam param) {
            this.param = param;
            return this;
        }

        public Builder setHeader(Header header) {
            this.header = header;
            return this;
        }

        public Builder setBaseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setContext(@NonNull Context context) {
            this.context = context;
            return this;
        }

        public Builder addHeader(@NonNull Header header) {
            if (this.header == null) {
                this.header = header;
            } else {
                if (header != null && header.getHeaders() != null) {
                    this.header.add(header.getHeaders());
                }
            }
            return this;
        }

        public Builder addParam(@NonNull HParam param) {
            if (this.param == null) {
                this.param = param;
            } else {
                if (param != null && param.getParams() != null) {
                    this.param.add(param.getParams());
                }
            }
            return this;
        }

        public Builder clone(HNetConfig netConfig) {
            this.context = netConfig.context;
            this.baseUrl = netConfig.baseUrl;
            this.header = netConfig.header;
            this.param = netConfig.param;
            return this;
        }

        public HNetConfig build() {
            return new HNetConfig(this);
        }

    }

}
