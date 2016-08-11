package me.hacket.library.request;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import me.hacket.library.util.HUtils;

/**
 * StringRequest  请求返回String
 * <p/>
 * 请求参数params:
 * <p/>
 * GET: baseurl+ pathurl + params
 * <p/>
 * POST:  params和bodyJsonObj优先JsonObject, 没有bodyJsonObj由params构建请求body参数
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class HStringRequest extends StringRequest {

    private Builder mBuilder;

    private JSONObject mRequestBody;

    public HStringRequest(Builder builder) {
        super(builder.method, builder.url, builder.listener, builder.errorListener);
        this.mBuilder = builder;

        this.mRequestBody = builder.bodyJsonObj;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        if (mBuilder != null && mBuilder.headers != null) {
            return mBuilder.headers;
        }
        return super.getHeaders();
    }

    // for a POST or PUT request.
    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        if (mBuilder != null && mBuilder.params != null) {
            return mBuilder.params;
        }
        return super.getParams();
    }

    /**
     * Returns the raw POST or PUT body to be sent.
     * <p/>
     * <p>By default, the body consists of the request parameters in
     * application/x-www-form-urlencoded format. When overriding this method, consider overriding
     * {@link #getBodyContentType()} as well to match the new body format.
     *
     * @throws AuthFailureError in the event of auth failure
     */
    @Override
    public byte[] getBody() throws AuthFailureError {
        try {
            if (mRequestBody != null) {
                return mRequestBody.toString().getBytes(HUtils.PROTOCOL_CHARSET);
            }
            Map<String, String> params = getParams();
            if (params != null && params.size() > 0) {
                return HUtils.encodeParamsToBytes(params, getParamsEncoding());
            }
        } catch (UnsupportedEncodingException uee) {
            // can't reach...
            return null;
        }
        return null;
    }

    public final static class Builder {

        private int method = Method.GET;

        private String url;

        private Response.Listener<String> listener;
        private Response.ErrorListener errorListener;

        private Map<String, String> headers;

        private Map<String, String> params;
        private JSONObject bodyJsonObj;

        public Builder method(int method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder listener(Response.Listener<String> listener) {
            this.listener = listener;
            return this;
        }

        public Builder errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * for a POST or PUT request body
         * <p/>
         * for GET params
         *
         * @param params Map<String, String>
         *
         * @return Builder
         */
        public Builder params(Map<String, String> params) {
            this.params = params;
            return this;
        }

        /**
         * for a POST or PUT request body
         *
         * @param bodyJsonObj JSONObject
         *
         * @return Builder
         */
        public Builder bodyJsonObj(JSONObject bodyJsonObj) {
            this.bodyJsonObj = bodyJsonObj;
            return this;
        }

        public HStringRequest build() {
            return new HStringRequest(this);
        }
    }

}

