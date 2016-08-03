package me.hacket.library;

import java.util.HashMap;

/**
 * 请求Body
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class Body {

    private final HashMap<String, Object> bodys;

    public Body(Builder builder) {
        this.bodys = builder.body;
    }

    HashMap<String, Object> getBodys() {
        return bodys;
    }

    public static class Builder {
        private HashMap<String, Object> body = new HashMap<String, Object>();
        private String key;
        private String value;

        public Builder add(String key, Object value) {
            body.put(key, value);
            return this;
        }

        public Builder add(HashMap<String, Object> headers) {
            if (headers != null) {
                this.body.putAll(headers);
            }
            return this;
        }

        public Body build() {
            return new Body(this);
        }
    }

}
