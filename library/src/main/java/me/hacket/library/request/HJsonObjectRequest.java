package me.hacket.library.request;

import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import android.support.annotation.NonNull;

/**
 * JsonObjectRequest
 * <p/>
 * 其中post body为JSONObject
 * <p/>
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class HJsonObjectRequest extends JsonObjectRequest {

    private Builder mBuilder;

    public HJsonObjectRequest(@NonNull Builder builder) {
        super(builder.method, builder.url, builder.bodyJsonObj, builder.listener, builder.errorListener);
        this.mBuilder = builder;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mBuilder != null && mBuilder.headers != null) {
            return mBuilder.headers;
        }
        return super.getHeaders();
    }

    public final static class Builder {

        private int method = Method.GET;

        private String url;
        private Map<String, String> headers;
        private JSONObject bodyJsonObj;

        private Response.Listener<JSONObject> listener;
        private Response.ErrorListener errorListener;

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setBodyJsonObj(JSONObject bodyJsonObj) {
            this.bodyJsonObj = bodyJsonObj;
            return this;
        }

        public Builder setListener(Response.Listener<JSONObject> listener) {
            this.listener = listener;
            return this;
        }

        public Builder setErrorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public Builder setHeaders(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public HJsonObjectRequest build() {
            return new HJsonObjectRequest(this);
        }
    }

}
