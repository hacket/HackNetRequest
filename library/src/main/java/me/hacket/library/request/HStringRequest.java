package me.hacket.library.request;

import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

/**
 * StringRequest
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class HStringRequest extends StringRequest {

    private Builder mBuilder;

    public HStringRequest(Builder builder) {
        super(builder.method, builder.url, builder.listener, builder.errorListener);
        this.mBuilder = builder;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mBuilder != null && mBuilder.headers != null) {
            return mBuilder.headers;
        }
        return super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return super.getParams();
    }

    public final static class Builder {

        private int method = Method.GET;

        private String url;

        private Response.Listener<String> listener;
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

        public Builder setListener(Response.Listener<String> listener) {
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

        public HStringRequest build() {
            return new HStringRequest(this);
        }
    }

}

