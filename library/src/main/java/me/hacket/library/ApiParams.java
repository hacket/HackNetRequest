//package me.hacket.library;
//
//import java.io.UnsupportedEncodingException;
//import java.lang.annotation.Documented;
//import java.lang.annotation.ElementType;
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//import java.lang.annotation.Target;
//import java.lang.reflect.Field;
//import java.net.URLEncoder;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//
//import android.os.Build;
//import android.support.annotation.CheckResult;
//import android.support.annotation.NonNull;
//import android.text.TextUtils;
//import me.hacket.library.util.L;
//import me.hacket.library.util.RunningContext;
//
///**
// * 构建请求参数
// * Created by zengfansheng on 2016/4/14.
// */
//class ApiParams {
//
//    private static final String TAG = ApiParams.class.getSimpleName();
//
//    private static final String QMARK = "?";
//    private static final String ANDMARK = "&";
//    private static final String EQUALS_MARK = "=";
//    private static final String ENCODE_CHARSET = "UTF-8";
//
//    public static class ParamKey {
//        // common param key
//        public static final String PACKAGE_VERSION = "av";
//        public static final String UUID = "uuid";
//        public static final String LANG = "la";
//        public static final String COUNTRY = "co";
//        public static final String CHANNEL = "ch";
//        public static final String SDK_VERSION = "sv";
//        public static final String PRODUCT = "pt";
//    }
//
//    @Documented
//    @Target(ElementType.FIELD)
//    @Retention(RetentionPolicy.RUNTIME)
//    public @interface CommonParam {
//        String value() default "";
//    }
//
//    @CommonParam(ParamKey.PACKAGE_VERSION)
//    private String mVersionName = RunningContext.getAppContext().getPackageName();
//
//    @CommonParam(ParamKey.UUID)
//    private String mUuid =  "uuid-hacket";
//
//    @CommonParam(ParamKey.LANG)
//    private String mLanguage = "en";
//
//    @CommonParam(ParamKey.COUNTRY)
//    private String mCountry = "gb";
//
//    @CommonParam(ParamKey.CHANNEL)
//    private String mChannel = "gp";
//
//    @CommonParam(ParamKey.SDK_VERSION)
//    private String mSdkVersion = String.valueOf(Build.VERSION.SDK_INT);
//
//    @CommonParam(ParamKey.PRODUCT)
//    private String mProduct = "ma";
//
//    /**
//     * 保存所有的参数
//     */
//    private Map<String, String> paramsMap = new HashMap<>();
//
//    @CheckResult
//    public ApiParams addCustomParam(@NonNull String key, String value) {
//        if (!TextUtils.isEmpty(key)) {
//            paramsMap.put(key, value);
//        }
//        return this;
//    }
//
//    @CheckResult
//    public ApiParams addCustomParam(Map<String, String> params) {
//        if (params != null && !params.isEmpty()) {
//            paramsMap.putAll(params);
//        }
//        return this;
//    }
//
//    @CheckResult
//    public String buildUrl(@NonNull String hostUrl) {
//        return buildUrl(hostUrl, true);
//    }
//
//    /**
//     * 构建url
//     *
//     * @param hostUrl           host+path
//     * @param isNeedCommonParam 是否需要通用参数
//     *
//     * @return 带参数的url
//     */
//    @CheckResult
//    public String buildUrl(@NonNull String hostUrl, boolean isNeedCommonParam) {
//        StringBuilder sb = new StringBuilder();
//        if (TextUtils.isEmpty(hostUrl)) {
//            return sb.toString();
//        }
//        sb.append(hostUrl);
//
//        if (isNeedCommonParam) {
//            paramsMap.putAll(getCommonParam());
//        }
//
//        if (!paramsMap.isEmpty()) {
//            sb.append(QMARK);
//            sb.append(buildParam(paramsMap));
//        }
//
//        L.i(TAG, "buildUrl , url : " + sb.toString());
//
//        return sb.toString();
//    }
//
//    /**
//     * 构建通用参数+自定义参数
//     *
//     * @param isNeedCommonParam 是否需要通用参数
//     *
//     * @return 通用参数+自定义参数的Map
//     */
//    @CheckResult
//    public Map<String, String> buildParam(boolean isNeedCommonParam) {
//        if (isNeedCommonParam) {
//            paramsMap.putAll(getCommonParam());
//        }
//        return paramsMap;
//    }
//
//    /**
//     * 构建通用参数+自定义参数
//     *
//     * @param params params
//     *
//     * @return 通用参数+自定义参数的String
//     */
//    @CheckResult
//    private String buildParam(Map<String, String> params) {
//        StringBuilder sb = new StringBuilder();
//        Iterator<Map.Entry<String, String>> iterator = params.entrySet().iterator();
//        while (iterator.hasNext()) {
//            Map.Entry<String, String> entry = iterator.next();
//            String key = entry.getKey();
//            Object valueObj = entry.getValue();
//            if (valueObj == null) {
//                continue;
//            }
//            sb.append(generateParam(key, valueObj.toString(), iterator.hasNext()));
//        }
//        return sb.toString();
//    }
//
//    @CheckResult
//    private String generateParam(String key, String value, boolean isAnd) {
//        try {
//            return key + EQUALS_MARK + URLEncoder.encode(value, ENCODE_CHARSET) + (isAnd ? ANDMARK : "");
//        } catch (UnsupportedEncodingException e) {
//            L.printStackTrace(e);
//        }
//        return "";
//    }
//
//    /**
//     * 构建默认参数，最后一个参数没有&
//     *
//     * @return common参数
//     */
//    @Deprecated
//    @CheckResult
//    public String buildCommonParamToString() {
//        StringBuilder sb = new StringBuilder();
//        Field[] fields = ApiParams.class.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            if (field.isAnnotationPresent(CommonParam.class)) {
//                String key = field.getAnnotation(CommonParam.class).value();
//                boolean accessible = field.isAccessible();
//                if (!accessible) {
//                    field.setAccessible(true);
//                }
//                String value = "";
//                try {
//                    value = (String) field.get(ApiParams.this);
//                } catch (IllegalAccessException e) {
//                    L.printStackTrace(e);
//                } finally {
//                    field.setAccessible(false);
//                }
//                if (TextUtils.isEmpty(value)) {
//                    continue;
//                }
//
//                sb.append(generateParam(key, value, (i != fields.length - 1)));
//
//            }
//        }
//        return sb.toString();
//    }
//
//    /**
//     * 获取通用参数
//     *
//     * @return Map<String, String>
//     */
//    @CheckResult
//    private Map<String, String> getCommonParam() {
//        Map<String, String> commonParams = new HashMap<>();
//        Field[] fields = ApiParams.class.getDeclaredFields();
//        for (int i = 0; i < fields.length; i++) {
//            Field field = fields[i];
//            if (field.isAnnotationPresent(CommonParam.class)) {
//                String key = field.getAnnotation(CommonParam.class).value();
//                boolean accessible = field.isAccessible();
//                if (!accessible) {
//                    field.setAccessible(true);
//                }
//                String value;
//                try {
//                    value = (String) field.get(ApiParams.this);
//                    if (TextUtils.isEmpty(value)) {
//                        continue;
//                    }
//                    commonParams.put(key, value);
//                } catch (IllegalAccessException e) {
//                    L.printStackTrace(e);
//                } finally {
//                    field.setAccessible(false);
//                }
//            }
//        }
//        return commonParams;
//    }
//
//}