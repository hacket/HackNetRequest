package me.hacket.library;

import java.util.HashMap;

/**
 * 请求Header
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class Header {

    private final HashMap<String, String> headers = new HashMap<>();

    public Header(Builder builder) {
        if (builder != null && builder.headers != null) {
            this.headers.putAll(builder.headers);
        }
    }

    HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public static class Builder {
        private HashMap<String, String> headers = new HashMap<String, String>();
        private String key;
        private String value;

        public Builder add(String key, String value) {
            this.headers.put(key, value);
            return this;
        }

        public Builder add(HashMap<String, String> headers) {
            if (headers != null) {
                this.headers.putAll(headers);
            }
            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }

}
