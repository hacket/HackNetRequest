package me.hacket.library;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import me.hacket.library.callback.RequestCallback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.request.HJsonArrayRequest;
import me.hacket.library.request.HJsonObjectRequest;
import me.hacket.library.request.HStringRequest;
import me.hacket.library.request.HTemplateRequest;
import me.hacket.library.util.L;

/**
 * 请求管理类
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class NetworkManager {

    private static final String TAG = NetworkManager.class.getSimpleName();

    private Gson mGson = new Gson();
    private VolleyManager mVolleyManager;
    private final String baseUrl;
    private final String pathUrl;
    private final int method;
    private final TypeToken<?> classTarget;
    @REQUEST_TYPE
    private int resultType;
    private final HashMap<String, String> headers;
    private final HashMap<String, Object> bodyRequest;
    @BEAN_TYPE
    private int beanType;

    public NetworkManager(Builder builder) {
        mGson = new Gson();
        mVolleyManager = VolleyManager.getInstance(builder.context);

        this.baseUrl = builder.baseUrl;
        this.pathUrl = builder.pathUrl;
        this.method = builder.method;
        this.classTarget = builder.targetType;

        resultType = builder.resultType;
        this.headers = builder.headers;
        this.bodyRequest = builder.bodyRequest;

        this.beanType = builder.beanType;
    }

    // ==================== JsonObjectRequest  ==================== //

    /**
     * 发送一个JsonObjectRequest
     *
     * @param headers         headers
     * @param bodyRequest     请求体
     * @param requestTag      请求tag
     * @param requestCallback 请求回调
     * @param <T> T
     */
    private <T> void fromJsonObject(final HashMap<String, String> headers, HashMap<String, Object> bodyRequest,
                                    String requestTag, final RequestCallback<T> requestCallback) {

        String url = buildUrlConnection();
        int method = this.method;

        Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                if (requestCallback != null) {
                    T t = mGson.fromJson(jsonObject.toString(), classTarget.getType());
                    requestCallback.onRequestSuccess(t);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (requestCallback != null) {
                    requestCallback.onRequestError(new RequestError(volleyError));
                }
            }
        };

        JSONObject bodyJsonObj = bodyRequest == null ? null : new JSONObject(bodyRequest);

        HJsonObjectRequest jsonObjectRequest = new HJsonObjectRequest.Builder()
                .setUrl(url)
                .setMethod(method)
                .setHeaders(headers)
                .setBodyJsonObj(bodyJsonObj)
                .setListener(listener)
                .setErrorListener(errorListener)
                .build();

        L.i(TAG, "fromJsonObject, url:" + url);

        mVolleyManager.addRequestToQueue(jsonObjectRequest, requestTag);
    }

    // ==================== JsonObjectRequest  ==================== //

    // ==================== JsonArrayRequest  ==================== //
    private <T> void fromJsonArray(final Map<String, String> headers, String requestTag,
                                   final RequestCallback<T> requestCallback) {

        String url = buildUrlConnection();
        int method = this.method;

        Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray jsonObject) {
                if (requestCallback != null) {
                    T t = mGson.fromJson(jsonObject.toString(), classTarget.getType());
                    requestCallback.onRequestSuccess(t);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (requestCallback != null) {
                    requestCallback.onRequestError(new RequestError(volleyError));
                }
            }
        };

        JSONObject bodyJsonObj = bodyRequest == null ? null : new JSONObject(bodyRequest);

        HJsonArrayRequest jsonArrayRequest = new HJsonArrayRequest.Builder()
                .setMethod(method)
                .setUrl(url)
                .setHeaders(headers)
                .setBodyJsonObj(bodyJsonObj)
                .setListener(listener)
                .setErrorListener(errorListener)
                .build();

        mVolleyManager.addRequestToQueue(jsonArrayRequest, requestTag);
    }

    // ==================== JsonArrayRequest  ==================== //

    // ==================== StringRequest  ==================== //
    private void fromString(HashMap<String, String> headers, String requestTag,
                            final RequestCallback requestCallback) {

        String url = buildUrlConnection();
        int method = this.method;

        final Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (requestCallback != null) {
                    requestCallback.onRequestSuccess(response);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (requestCallback != null) {
                    requestCallback.onRequestError(new RequestError(volleyError));
                }
            }
        };

        HStringRequest stringRequest = new HStringRequest.Builder()
                .setMethod(method)
                .setUrl(url)
                .setHeaders(headers)
                .setListener(listener)
                .setErrorListener(errorListener)
                .build();

        L.i(TAG, "fromString, url:" + url);

        mVolleyManager.addRequestToQueue(stringRequest, requestTag);
    }

    // ==================== StringRequest  ==================== //

    // ==================== TemplateRequest  ==================== //

    private <T> void fromTemplate(final HashMap<String, String> headers, final String requestTag,
                                  final RequestCallback<T> requestCallback) {

        String url = buildUrlConnection();
        int method = this.method;

        final Response.Listener<T> listener = new Response.Listener<T>() {

            @Override
            public void onResponse(T response) {
                if (requestCallback != null) {
                    requestCallback.onRequestSuccess(response);
                }
            }
        };

        final Response.ErrorListener errorListener = new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                if (requestCallback != null) {
                    requestCallback.onRequestError(new RequestError(volleyError));
                }
            }
        };

        HTemplateRequest templateRequest = new HTemplateRequest.Builder()
                .method(method)
                .url(url)
                .headers(headers)
                .listener(listener)
                .errorListener(errorListener)
                .typeToken(classTarget, beanType == BEAN_TYPE_CLASS ? HTemplateRequest.RESPONSE_TYPE_JSONOBJECT
                        : HTemplateRequest.RESPONSE_TYPE_JSONARRAY)
                .build();
        mVolleyManager.addRequestToQueue(templateRequest, requestTag);
    }
    // ==================== TemplateRequest  ==================== //

    public <T> void execute(String requestTag, final RequestCallback<T> requestCallback) {
        if (resultType == REQUEST_TYPE_DEFAULT) {
            throw new IllegalArgumentException("result type must not be null.");
        }

        if (classTarget == null) {
            throw new IllegalArgumentException("class target must not be null.");
        }

        if (pathUrl == null) {
            throw new IllegalArgumentException("path url must not be null.");
        }

        switch (resultType) {
            case REQUEST_TYPE_JSONOBJECT:
                if (method == Request.Method.POST) {
                    if (bodyRequest == null) {
                        throw new IllegalArgumentException("body request must not be null.");
                    }
                }
                fromJsonObject(headers, bodyRequest, requestTag, requestCallback);
                break;
            case REQUEST_TYPE_JSONARRAY:
                fromJsonArray(headers, requestTag, requestCallback);
                break;
            case REQUEST_TYPE_STRING:
                fromString(headers, requestTag, requestCallback);
                break;
            case REQUEST_TYPE_TEMPLATE:
                fromTemplate(headers, requestTag, requestCallback);
                break;
            case REQUEST_TYPE_DEFAULT:
            default:
                break;
        }

    }

    // ===============  通用 ================ //

    private String buildUrlConnection() {
        StringBuilder builder = new StringBuilder();
        builder.append(baseUrl).append(pathUrl);
        return builder.toString();
    }

    public static class Builder implements INetworkManagerBuilder {
        private Context context;
        private String baseUrl;
        private String pathUrl;
        private int method;
        private TypeToken<?> targetType;

        @REQUEST_TYPE
        private int resultType;
        private HashMap<String, String> headers;
        private HashMap<String, Object> bodyRequest;

        @BEAN_TYPE
        private int beanType;

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

        public Builder setBodyRequest(@NonNull HashMap<String, Object> bodyRequest) {
            this.bodyRequest = bodyRequest;
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
            this.resultType = REQUEST_TYPE_JSONOBJECT;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromJsonArray() {
            this.resultType = REQUEST_TYPE_JSONARRAY;
            return this;
        }

        @Override
        public INetworkManagerBuilder fromTemplate() {
            this.resultType = REQUEST_TYPE_TEMPLATE;
            return this;
        }

        @Override
        public NetworkManager fromString() {
            this.resultType = REQUEST_TYPE_STRING;
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
        public NetworkManager toBean(@NonNull TypeToken typeToken) {
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

        NetworkManager toBean(@NonNull Class classTarget);

        NetworkManager toBean(@NonNull TypeToken typeToken);
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
