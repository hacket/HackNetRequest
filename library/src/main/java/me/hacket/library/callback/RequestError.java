package me.hacket.library.callback;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

import com.android.volley.NetworkResponse;
import com.android.volley.VolleyError;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;

/**
 * 请求回调错误
 * <p/>
 * Created by zengfansheng on  2016年8月4日00:24:15
 */
public final class RequestError {

    public final static int REQUEST_RESPONSE_OK = 200;
    public final static int REQUEST_RESPONSE_CREATED = 201;
    public final static int REQUEST_RESPONSE_ACCEPTED = 202;
    public final static int REQUEST_RESPONSE_NO_CONTENT = 204;
    public final static int REQUEST_RESPONSE_BAD_REQUEST = 400;
    public final static int REQUEST_RESPONSE_UNAUTHORIZED = 401;
    public final static int REQUEST_RESPONSE_FORBIDDEN = 403;
    public final static int REQUEST_RESPONSE_PAYMENT_REQUIRED = 402;
    public final static int REQUEST_RESPONSE_NOT_FOUND = 404;
    public final static int REQUEST_RESPONSE_GONE = 410;
    public final static int REQUEST_RESPONSE_UNPROCESSABLE_ENTITY = 422;
    public final static int REQUEST_RESPONSE_INTERNAL_SERVER_ERROR = 500;
    public final static int REQUEST_RESPONSE_SERVICE_UNAVAILABLE = 503;
    public final static int REQUEST_RESPONSE_MULTIPLE_DEVICE = 429;
    public final static int REQUEST_RESPONSE_NOT_PERMITTED = 301;
    public final static int REQUEST_RESPONSE_RESET_PASSWORD_SUCCESS = 204;

    private Map<String, String> headers;
    private int errorCode;
    private String errorMsg;
    private Throwable cause;

    private VolleyError mVolleyError;

    public RequestError(VolleyError volleyError) {
        this.mVolleyError = volleyError;
        NetworkResponse networkResponse = volleyError.networkResponse;
        if (networkResponse != null) {
            this.errorCode = networkResponse.statusCode;
            this.headers = networkResponse.headers;
        }
        this.errorMsg = volleyError.getMessage();
        this.cause = volleyError.getCause();
    }

    @ERROR_CODE
    public int getErrorCode() {
        return errorCode;
    }

    @Nullable
    public String getErrorMsg() {
        return errorMsg;
    }

    @Nullable
    public Throwable getCause() {
        return cause;
    }

    @Nullable
    public Map<String, String> getHeaders() {
        return headers;
    }

    public void printStackTrace() {
        mVolleyError.printStackTrace();
    }

    @Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @IntDef({REQUEST_RESPONSE_OK, REQUEST_RESPONSE_CREATED, REQUEST_RESPONSE_ACCEPTED, REQUEST_RESPONSE_NO_CONTENT,
            REQUEST_RESPONSE_BAD_REQUEST, REQUEST_RESPONSE_UNAUTHORIZED, REQUEST_RESPONSE_FORBIDDEN,
            REQUEST_RESPONSE_PAYMENT_REQUIRED, REQUEST_RESPONSE_NOT_FOUND, REQUEST_RESPONSE_GONE,
            REQUEST_RESPONSE_UNPROCESSABLE_ENTITY, REQUEST_RESPONSE_INTERNAL_SERVER_ERROR,
            REQUEST_RESPONSE_SERVICE_UNAVAILABLE, REQUEST_RESPONSE_MULTIPLE_DEVICE, REQUEST_RESPONSE_NOT_PERMITTED,
            REQUEST_RESPONSE_RESET_PASSWORD_SUCCESS})
    @Retention(RetentionPolicy.SOURCE)
    @Documented
    public @interface ERROR_CODE {

    }

}
