package me.hacket.library.request;

import java.util.Map;

import org.json.JSONArray;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;

import android.support.annotation.NonNull;

/**
 * JsonArrayRequest
 * <p/>
 * 其中post body为JSONArray  --body还未处理好
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class HJsonArrayRequest extends JsonArrayRequest {

    private Builder mBuilder;

    public HJsonArrayRequest(@NonNull Builder builder) {
        super(builder.url, builder.listener, builder.errorListener);
        //        super(builder.method, builder.url, builder.bodyJsonArray, builder.listener, builder.errorListener);
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
        private JSONArray bodyJsonArray;

        private Response.Listener<JSONArray> listener;
        private Response.ErrorListener errorListener;

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setBodyJsonArray(JSONArray bodyJsonArray) {
            this.bodyJsonArray = bodyJsonArray;
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
