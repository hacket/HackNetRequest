package me.hacket.library;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import me.hacket.library.callback.Callback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.callback.TemplateCallback;
import me.hacket.library.request.BaseResponse;
import me.hacket.library.request.HJsonArrayRequest;
import me.hacket.library.request.HJsonObjectRequest;
import me.hacket.library.request.HStringRequest;
import me.hacket.library.request.HTemplateRequest;
import me.hacket.library.util.HUtils;
import me.hacket.library.util.L;

/**
 * 请求管理类
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class NetworkManager {

    private static final String TAG = HNetConfig.TAG;

    private Gson mGson = new Gson();
    private VolleyManager mVolleyManager;
    private final String baseUrl;
    private final String pathUrl;
    private final int method;
    private final TypeToken<?> targetType;
    @REQUEST_TYPE
    private int resultType;
    private final HashMap<String, String> headers;
    private final HashMap<String, String> params;
    private final String bodyRequest;
    @BEAN_TYPE
    private int beanType;

    public NetworkManager(Builder builder) {
        mGson = new Gson();
        mVolleyManager = VolleyManager.getInstance(builder.context);

        this.baseUrl = builder.baseUrl;
        this.pathUrl = builder.pathUrl;
        this.method = builder.method;
        this.targetType = builder.targetType;

        resultType = builder.requestType;
        this.headers = builder.headers;
        this.params = builder.params;
        this.bodyRequest = builder.postJsonBodys;

        this.beanType = builder.beanType;
    }

    // ==================== JsonObjectRequest  ==================== //

    /**
     * JsonObject请求 , see {@link HJsonObjectRequest}
     *
     * @param requestTag 请求tag
     * @param callback   请求回调
     * @param <T>        T
     */
    private <T> void fromJsonObject(String requestTag, final Callback<T> callback) {

        String url = buildUrlStr();

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (callback != null) {
                    T t = mGson.fromJson(jsonObject.toString(), targetType.getType());
                    callback.onResponseSuccess(t);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callback != null) {
                    callback.onResponseError(new RequestError(volleyError));
                }
            }
        };

        JSONObject bodyJsonObj = null;
        try {
            bodyJsonObj = bodyRequest == null ? null : new JSONObject(bodyRequest);
        } catch (JSONException e) {
            L.printStackTrace(e);
        }

        HJsonObjectRequest jsonObjectRequest = new HJsonObjectRequest.Builder()
                .setUrl(url)
                .setMethod(this.method)
                .setHeaders(this.headers)
                .setBodyJsonObj(bodyJsonObj)
                .setListener(listener)
                .setErrorListener(errorListener)
                .build();

        mVolleyManager.addRequestToQueue(jsonObjectRequest, requestTag);

        L.i(TAG, ">>>>>>request JsonObject, url:" + url + "<<<<<<");
    }

    // ==================== JsonObjectRequest  ==================== //

    // ==================== JsonArrayRequest  ==================== //
    private <T> void fromJsonArray(String requestTag, final Callback<T> callback) {

        String url = buildUrlStr();

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                if (callback != null) {
                    T t = mGson.fromJson(jsonObject.toString(), targetType.getType());
                    callback.onResponseSuccess(t);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callback != null) {
                    callback.onResponseError(new RequestError(volleyError));
                }
            }
        };

        JSONArray bodyJsonArray = null;
        try {
            bodyJsonArray = bodyRequest == null ? null : new JSONArray(bodyRequest);
        } catch (JSONException e) {
            L.printStackTrace(e);
        }

        HJsonArrayRequest jsonArrayRequest = new HJsonArrayRequest.Builder()
                .setMethod(this.method)
                .setUrl(url)
                .setHeaders(this.headers)
                .setBodyJsonArray(bodyJsonArray)
                .setListener(listener)
                .setErrorListener(errorListener)
                .build();

        mVolleyManager.addRequestToQueue(jsonArrayRequest, requestTag);

        L.i(TAG, ">>>>>>request JsonArray, url:" + url + "<<<<<<");
    }

    // ==================== JsonArrayRequest  ==================== //

    // ==================== StringRequest  ==================== //
    private void fromString(String requestTag, final Callback callback) {

        String url = buildUrlStr();

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (callback != null) {
                    callback.onResponseSuccess(response);
                }
            }
        };

        Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callback != null) {
                    callback.onResponseError(new RequestError(volleyError));
                }
            }
        };

        JSONObject bodyJsonObj = null;
        try {
            bodyJsonObj = bodyRequest == null ? null : new JSONObject(bodyRequest);
        } catch (JSONException e) {
            L.printStackTrace(e);
            L.w(TAG, ">>>>>>request String bodyJsonObj is null or malformed!");
        }

        HStringRequest stringRequest = new HStringRequest.Builder()
                .method(this.method)
                .url(url)
                .headers(this.headers)
                .params(this.params)
                .bodyJsonObj(bodyJsonObj)
                .listener(listener)
                .errorListener(errorListener)
                .build();

        mVolleyManager.addRequestToQueue(stringRequest, requestTag);

        L.i(TAG, ">>>>>>request String, url:" + url + "<<<<<<");
    }

    // ==================== StringRequest  ==================== //

    // ==================== TemplateRequest  ==================== //

    private <T> void fromTemplate(final String requestTag, final Callback<T> callback) {

        String url = buildUrlStr();

        final Response.Listener<BaseResponse<T>> listener = new Response.Listener<BaseResponse<T>>() {

            @Override
            public void onResponse(BaseResponse<T> baseResponse) {

                if (callback != null) {

                    BaseResponse.Result result = baseResponse.result;
                    if (baseResponse.isSuccessful()) {
                        callback.onResponseSuccess(baseResponse.response);
                    }

                    if (callback instanceof TemplateCallback) {
                        TemplateCallback<T> templateCallback = (TemplateCallback<T>) callback;
                        templateCallback.onResponseStatus(result.code, result.desc);
                    }
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (callback != null) {
                    callback.onResponseError(new RequestError(volleyError));
                }
            }
        };

        HTemplateRequest templateRequest = new HTemplateRequest.Builder()
                .method(this.method)
                .url(url)
                .headers(this.headers)
                .priority(Request.Priority.NORMAL)
                .listener(listener)
                .errorListener(errorListener)
                .typeToken(targetType, beanType == BEAN_TYPE_CLASS ? HTemplateRequest.RESPONSE_TYPE_JSONOBJECT
                        : HTemplateRequest.RESPONSE_TYPE_JSONARRAY)
                .build();

        mVolleyManager.addRequestToQueue(templateRequest, requestTag);

        L.i(TAG, ">>>>>>request Json Template, url:" + url + "<<<<<<");
    }

    // ==================== TemplateRequest  ==================== //

    /**
     * execute
     *
     * @param requestTag 请求tag
     * @param callback   callback , 如果使用模板{@link HTemplateRequest}可用{@link TemplateCallback}
     * @param <T>        T
     */
    public <T> void execute(String requestTag, Callback<T> callback) {
        if (resultType == REQUEST_TYPE_DEFAULT) {
            throw new IllegalArgumentException("request type must not be null.");
        }

        if (targetType == null) {
            throw new IllegalArgumentException("targetType must not be null.");
        }

        //        if (pathUrl == null) {
        //            throw new IllegalArgumentException("pathUrl url must not be null.");
        //        }

        switch (resultType) {
            case REQUEST_TYPE_JSONOBJECT:
                if (method == Request.Method.POST) {
                    if (bodyRequest == null) {
                        throw new IllegalArgumentException("body request must not be null.");
                    }
                }
                fromJsonObject(requestTag, callback);
                break;
            case REQUEST_TYPE_JSONARRAY:
                if (method == Request.Method.POST) {
                    if (bodyRequest == null) {
                        throw new IllegalArgumentException("body request must not be null.");
                    }
                }
                fromJsonArray(requestTag, callback);
                break;
            case REQUEST_TYPE_STRING:
                fromString(requestTag, callback);
                break;
            case REQUEST_TYPE_TEMPLATE:
                fromTemplate(requestTag, callback);
                break;
            case REQUEST_TYPE_DEFAULT:
            default:
                break;
        }

    }

    // ===============  通用 ================ //

    /**
     * 构建 url
     * <p/>
     * post : baseurl + pathurl
     * <p/>
     * get : baseurl + pathurl + param
     *
     * @return String
     */
    private String buildUrlStr() {
        StringBuilder builder = new StringBuilder();
        if (method == Request.Method.GET) {
            String encodeParams = HUtils.encodeParamsToStr(this.params);
            builder.append(baseUrl).append(pathUrl).append("?").append(encodeParams);
        } else {
            builder.append(baseUrl).append(pathUrl);
        }
        return builder.toString();
    }

    // ===============  通用 ================ //

    public static class Builder implements INetworkManagerBuilder {

        private Context context;

        private String baseUrl;
        private String pathUrl;
        private int method;
        private HashMap<String, String> headers;
        /**
         * 参数
         */
        private HashMap<String, String> params;
        /**
         * POST或PUT的JSON BODY
         */
        private String postJsonBodys;

        /**
         * 请求类型 see {@link REQUEST_TYPE}
         */
        @REQUEST_TYPE
        private int requestType;

        /**
         * 请求转换的bean类型 see {@link BEAN_TYPE}
         */
        @BEAN_TYPE
        private int beanType;

        private TypeToken<?> targetType;

        public Builder setContext(Context context) {
            this.context = context;
            return this;
        }

        public Builder setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        public Builder setMethod(int method) {
            this.method = method;
            return this;
        }

        public Builder setHeaders(@NonNull HashMap<String, String> headers) {
            this.headers = headers;
            return this;
        }

        public Builder setPostJsonBodys(@NonNull String postJsonBodys) {
            this.postJsonBodys = postJsonBodys;
            return this;
        }

        public Builder setParams(HashMap<String, String> params) {
            this.params = params;
            return this;
        }

        @Override
        public INetworkManagerBuilder baseUrl(@NonNull String baseUrl) {
            this.baseUrl = baseUrl;
            return this;
        }

        @Override
        public INetworkManagerBuilder pathUrl(@NonNull String pathUrl) {
            this.pathUrl = pathUrl;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromJsonObject() {
            this.requestType = REQUEST_TYPE_JSONOBJECT;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromJsonArray() {
            this.requestType = REQUEST_TYPE_JSONARRAY;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromTemplate() {
            this.requestType = REQUEST_TYPE_TEMPLATE;
            return this;
        }

        @Override
        public NetworkManager fromString() {
            this.requestType = REQUEST_TYPE_STRING;
            this.targetType = TypeToken.get(String.class);
            return new NetworkManager(this);
        }

        @Override
        public NetworkManager toBean(@NonNull Class classTarget) {
            this.targetType = TypeToken.get(classTarget);
            this.beanType = BEAN_TYPE_CLASS;
            return new NetworkManager(this);
        }

        @Override
        public NetworkManager toBean(@NonNull TypeToken<?> typeToken) {
            this.targetType = typeToken;
            this.beanType = BEAN_TYPE_TYPETOKEN;
            return new NetworkManager(this);
        }

        public NetworkManager build() {
            return new NetworkManager(this);
        }
    }

    public interface INetworkManagerBuilder {

        INetworkManagerBuilder baseUrl(@NonNull String baseUrl);

        INetworkManagerBuilder pathUrl(@NonNull String pathUrl);

        INetworkManagerBuilder fromJsonObject();

        INetworkManagerBuilder fromJsonArray();

        INetworkManagerBuilder fromTemplate();

        NetworkManager fromString();

        /**
         * 类用
         *
         * @param classTarget Class
         *
         * @return NetworkManager
         */
        NetworkManager toBean(@NonNull Class classTarget);

        /**
         * 集合用
         *
         * @param typeToken TypeToken
         *                  <p/>
         *                  TypeToken<List<TempObj>> typeToken = new TypeToken<List<TempObj>>(){};
         *
         * @return NetworkManager
         */
        NetworkManager toBean(@NonNull TypeToken<?> typeToken);
    }

    private static final int REQUEST_TYPE_DEFAULT = -1;
    private static final int REQUEST_TYPE_JSONOBJECT = 0;
    private static final int REQUEST_TYPE_JSONARRAY = 1;
    private static final int REQUEST_TYPE_STRING = 2;
    private static final int REQUEST_TYPE_TEMPLATE = 3;

    @Documented
    @IntDef({REQUEST_TYPE_DEFAULT, REQUEST_TYPE_JSONOBJECT, REQUEST_TYPE_JSONARRAY, REQUEST_TYPE_STRING,
            REQUEST_TYPE_TEMPLATE})
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface REQUEST_TYPE {

    }

    private static final int BEAN_TYPE_CLASS = 0;
    private static final int BEAN_TYPE_TYPETOKEN = 1;

    @Documented
    @IntDef({BEAN_TYPE_CLASS, BEAN_TYPE_TYPETOKEN})
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface BEAN_TYPE {

    }

}
