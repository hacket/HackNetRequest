package me.hacket.library.request;

import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import android.support.annotation.NonNull;

/**
 * JsonArrayRequest
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class HJsonArrayRequest extends JsonArrayRequest {

    private Builder mBuilder;

    public HJsonArrayRequest(@NonNull Builder builder) {
        this(builder.url, builder.listener, builder.errorListener);
        this.mBuilder = builder;
    }

    public HJsonArrayRequest(String url, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        super(url, listener, errorListener);
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mBuilder != null && mBuilder.headers != null) {
            return mBuilder.headers;
        }
        return super.getHeaders();
    }

    public final static class Builder {

        private int method;

        private String url;

        private JSONObject bodyJsonObj;

        private Response.Listener<JSONArray> listener;
        private Response.ErrorListener errorListener;

        private Map<String, String> headers;

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

        public Builder setListener(Response.Listener<JSONArray> listener) {
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

        public HJsonArrayRequest build() {
            return new HJsonArrayRequest(this);
        }
    }

}
