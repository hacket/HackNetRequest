package me.hacket.library.request;

import java.io.UnsupportedEncodingException;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import me.hacket.library.HNetConfig;
import me.hacket.library.util.HUtils;

/**
 * 模板HTemplateRequest
 * 请求参数params:
 * <p/>
 * GET: baseurl+ pathurl + params
 * <p/>
 * POST:  params和bodyJsonObj优先JsonObject, 没有bodyJsonObj由params构建请求body参数
 * <p/>
 * <b>解析形如</br>
 * <pre>
 * {
 *     "response": {}/[],
 *      "result": {
 *          "code": int,
 *          "desc": string
 *      }
 * }
 * </pre>
 *
 * @param <T> T
 *            <p/>
 *            Created by hacket on 2016年8月3日23:06:23
 *            <br/>
 *            Also see
 *            <br/>
 *            {@link  BaseResponse }
 */
public class HTemplateRequest<T> extends Request<BaseResponse<T>> {

    private static final String TAG = HNetConfig.TAG;

    private Builder mBuilder;
    private JSONObject mRequestBody;

    public HTemplateRequest(@NonNull Builder builder) {
        super(builder.method, builder.url, builder.errorListener);
        this.mBuilder = builder;
        this.mRequestBody = builder.bodyJsonObj;
    }

    @Override
    protected Response<BaseResponse<T>> parseNetworkResponse(NetworkResponse networkResponse) {
        try {
            String jsonString =
                    new String(networkResponse.data, HttpHeaderParser.parseCharset(networkResponse.headers));

            JSONObject rootJsonObj = new JSONObject(jsonString);

            JSONObject resultObj = rootJsonObj.optJSONObject(BaseResponse.RESULT);

            @BaseResponse.RESULT_CODE
            int code = resultObj.optInt(BaseResponse.CODE);
            String desc = resultObj.optString(BaseResponse.DESC);

            BaseResponse.Builder<T> builder = new BaseResponse.Builder<>();
            builder.setCode(code);
            builder.setDesc(desc);

            switch (code) {
                case BaseResponse.RESPONSE_STATE_OK:
                    if (mBuilder.responseType == RESPONSE_TYPE_JSONOBJECT) {
                        JSONObject responseJsonObj = rootJsonObj.optJSONObject(BaseResponse.RESPONSE);
                        if (null != responseJsonObj) {
                            T reponse =
                                    mBuilder.mGson.fromJson(responseJsonObj.toString(), mBuilder.typeToken.getType());
                            builder.setResponse(reponse);
                        }
                    } else {
                        JSONArray responseJsonArray = rootJsonObj.optJSONArray(BaseResponse.RESPONSE);
                        if (null != responseJsonArray) {
                            T reponse =
                                    mBuilder.mGson.fromJson(responseJsonArray.toString(), mBuilder.typeToken.getType());
                            builder.setResponse(reponse);
                        }
                    }
                    break;
                case BaseResponse.RESPONSE_STATE_XXX:
                    builder.setResponse(null);
                    break;
                case BaseResponse.RESPONSE_STATE_SERVER_ERROR:
                    builder.setResponse(null);
                    break;
                case BaseResponse.RESPONSE_STATE_PARAM_ERROR:
                    builder.setResponse(null);
                    break;
                default:
                    break;
            }

            return Response.success(builder.build(), HttpHeaderParser.parseCacheHeaders(networkResponse));
        } catch (UnsupportedEncodingException e) {
            return Response.error(new ParseError(e));
        } catch (JSONException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return mBuilder.headers != null ? mBuilder.headers : super.getHeaders();
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

    @Override
    protected void deliverResponse(BaseResponse<T> response) {
        if (mBuilder.listener != null) {
            mBuilder.listener.onResponse(response);
        }
    }

    @Override
    public Priority getPriority() {
        return mBuilder.mPriority;
    }

    public final static class Builder<T> {

        private Gson mGson = new Gson();
        private int method = Method.GET;
        private String url;

        private Response.Listener<BaseResponse<T>> listener;
        private Response.ErrorListener errorListener;

        private Map<String, String> headers;
        private Map<String, String> params;
        private JSONObject bodyJsonObj;

        @RESPONSE_TYPE
        private int responseType;
        private TypeToken<T> typeToken;

        /**
         * 设置优先级
         */
        private Priority mPriority = Priority.NORMAL;

        public Builder gson(Gson gson) {
            this.mGson = gson;
            return this;
        }

        public Builder method(int method) {
            this.method = method;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder errorListener(Response.ErrorListener errorListener) {
            this.errorListener = errorListener;
            return this;
        }

        public Builder listener(Response.Listener<BaseResponse<T>> listener) {
            this.listener = listener;
            return this;
        }

        public Builder headers(Map<String, String> headers) {
            this.headers = headers;
            return this;
        }

        /**
         * for a POST or PUT request body
         * <p/>
         * or GET params
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

        public Builder typeToken(@NonNull Class classTarget) {
            this.typeToken = TypeToken.get(classTarget);
            this.responseType = RESPONSE_TYPE_JSONOBJECT;
            return this;
        }

        public Builder typeToken(@NonNull TypeToken typeToken, @RESPONSE_TYPE int responseType) {
            this.typeToken = typeToken;
            this.responseType = responseType;
            return this;
        }

        public Builder priority(Priority priority) {
            this.mPriority = priority;
            return this;
        }

        public HTemplateRequest build() {
            return new HTemplateRequest(this);
        }
    }

    public static final int RESPONSE_TYPE_JSONOBJECT = 0;
    public static final int RESPONSE_TYPE_JSONARRAY = 1;

    @Documented
    @IntDef({RESPONSE_TYPE_JSONOBJECT, RESPONSE_TYPE_JSONARRAY})
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RESPONSE_TYPE {

    }

}
