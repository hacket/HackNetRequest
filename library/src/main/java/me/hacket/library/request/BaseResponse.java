package me.hacket.library.request;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

/**
 * BaseResponse
 *
 * @param <T> Created by zengfansheng on 2016/4/14.
 */
public class BaseResponse<T> {

    public static final String RESULT = "result";
    public static final String RESPONSE = "response";
    public static final String CODE = "code";
    public static final String DESC = "desc";

    public static final int RESPONSE_STATE_OK = 0;
    public static final int RESPONSE_STATE_XXX = -1;
    public static final int RESPONSE_STATE_SERVER_ERROR = -2;
    public static final int RESPONSE_STATE_PARAM_ERROR = -3;

    public Result result;

    public T response;

    private BaseResponse(@NonNull Builder<T> builder) {
        if (result == null) {
            result = new Result(builder.code, builder.desc);
        }
        response = builder.response;
    }

    public static class Result {
        @RESULT_CODE
        public int code;
        public String desc;

        public Result(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        @Override
        public String toString() {
            return "Result{" +
                    "code=" + code +
                    ", desc='" + desc + '\'' +
                    '}';
        }
    }

    public static final class Builder<T> {
        private int code;
        private String desc;
        private T response;

        public Builder setCode(int code) {
            this.code = code;
            return this;
        }

        public Builder setDesc(String desc) {
            this.desc = desc;
            return this;
        }

        public Builder setResponse(T response) {
            this.response = response;
            return this;
        }

        public BaseResponse<T> build() {
            return new BaseResponse(this);
        }
    }

    @Documented
    @IntDef({RESPONSE_STATE_OK, RESPONSE_STATE_XXX, RESPONSE_STATE_SERVER_ERROR, RESPONSE_STATE_PARAM_ERROR})
    @Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.LOCAL_VARIABLE, ElementType.METHOD})
    @Retention(RetentionPolicy.SOURCE)
    public @interface RESULT_CODE {

    }

}
