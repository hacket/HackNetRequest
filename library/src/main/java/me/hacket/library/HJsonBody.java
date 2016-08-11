package me.hacket.library;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.support.annotation.NonNull;
import me.hacket.library.util.L;

/**
 * POST或PUT的请求Body
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class HJsonBody<T> {

    //    private final HashMap<String, T> bodys;

    private T body;

    private HJsonBody(Builder<T> builder) {
        this.body = builder.body;

    }

    public String getBodyString() {
        return generateBodyString(body);
    }

    //    HashMap<String, T> getBodys() {
    //        return bodys;
    //    }

    public static class Builder<T> {

        private T body;

        //        private HashMap<String, T> body = new HashMap<>();
        //        public Builder add(String key, T value) {
        //            body.put(key, value);
        //            return this;
        //        }
        //
        //        public Builder add(HashMap<String, T> headers) {
        //            if (headers != null) {
        //                this.body.putAll(headers);
        //            }
        //            return this;
        //        }
        //
        //        public Builder put(HashMap<String, T> body) {
        //            this.body = body;
        //            return this;
        //        }

        public Builder setBody(@NonNull T body) {
            this.body = body;
            return this;
        }

        public HJsonBody build() {
            return new HJsonBody(this);
        }
    }

    /**
     * 生成body request
     *
     * @param bodyRequest bodyRequest
     *
     * @return HashMap<String, Object>
     */
    private <T> String generateBodyString(T bodyRequest) {
        String bodyJson = new Gson().toJson(bodyRequest);
        L.i(HNetConfig.TAG, "post generateBodyRequest :" + bodyJson);
        return bodyJson;
    }

    /**
     * 生成body request
     *
     * @param bodyRequest bodyRequest
     *
     * @return HashMap<String, Object>
     */
    private <T> HashMap<String, T> generateBodyMap(T bodyRequest) {
        String bodyJson = new Gson().toJson(bodyRequest);
        L.i(HNetConfig.TAG, "post generateBodyRequest :" + bodyJson);
        Type type = new TypeToken<HashMap<String, T>>() {
        }.getType();
        HashMap<String, T> body = new Gson().fromJson(bodyJson, type);
        return body;
    }

}
