package me.hacket.library;

import java.util.HashMap;

/**
 * 请求Header
 * <p>
 * Created by hacket on 2016/8/2 0002.
 */
public class Header {

    private final HashMap<String, String> headers = new HashMap<>();

    private Header(Builder builder) {
        if (builder != null && builder.headers != null && !builder.headers.isEmpty()) {
            this.headers.putAll(builder.headers);
        }
    }

    HashMap<String, String> getHeaders() {
        return this.headers;
    }

    public void add(String key, String value) {
        this.headers.put(key, value);
    }

    public void add(HashMap<String, String> headers) {
        if (headers != null) {
            this.headers.putAll(headers);
        }
    }

    public static class Builder {
        private HashMap<String, String> headers = new HashMap<>();

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

        public Builder put(HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Header build() {
            return new Header(this);
        }
    }

}
