package me.hacket.library;

import java.util.HashMap;

/**
 * 请求参数{@link HParam}
 * <p/>
 * Created by hacket on 2016/8/5.
 */
public class HParam {

    private final HashMap<String, String> params = new HashMap<>();

    private HParam(Builder builder) {
        if (builder != null && builder.params != null && !builder.params.isEmpty()) {
            this.params.putAll(builder.params);
        }
    }

    HashMap<String, String> getParams() {
        return this.params;
    }

    public void add(String key, String value) {
        this.params.put(key, value);
    }

    public void add(HashMap<String, String> params) {
        if (params != null) {
            this.params.putAll(params);
        }
    }

    public static class Builder {
        private HashMap<String, String> params = new HashMap<>();

        /**
         * 添加k,v参数
         *
         * @param key   key
         * @param value value
         *
         * @return Builder
         */
        public Builder add(String key, String value) {
            this.params.put(key, value);
            return this;
        }

        /**
         * 添加HashMap<String, String>参数
         *
         * @param headers HashMap<String, String>
         *
         * @return Builder
         */
        public Builder add(HashMap<String, String> headers) {
            if (headers != null) {
                this.params.putAll(headers);
            }
            return this;
        }

        /**
         * 覆盖参数
         *
         * @param headers HashMap<String, String>
         *
         * @return Builder
         */
        public Builder put(HashMap<String, String> headers) {
            this.params = headers;
            return this;
        }

        public HParam build() {
            return new HParam(this);
        }
    }

}
