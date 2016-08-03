package me.hacket.library;

import java.lang.reflect.Type;
import java.util.HashMap;

import com.android.volley.Request;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 创建网络请求
 * <p/>
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class NetworkCreator {

    private final String baseUrl;
    private final Context context;

    NetworkCreator(Context context, String baseUrl) {
        this.context = context;
        this.baseUrl = baseUrl;
    }

    /**
     * GET
     *
     * @return NetworkManager.INetworkManagerBuilder
     */
    public NetworkManager.INetworkManagerBuilder get() {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET);
    }

    /**
     * GET
     *
     * @param header Headers request, it can be null
     *
     * @return NetworkManager.INetworkManagerBuilder
     */
    public NetworkManager.INetworkManagerBuilder get(@NonNull Header header) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET)
                .setHeaders(header.getHeaders());
    }

    /**
     * POST
     *
     * @param header Headers request, it can be null
     * @param body   Body request, it not null
     *
     * @return NetworkManager.INetworkManagerBuilder
     */
    public NetworkManager.INetworkManagerBuilder post(@NonNull Header header, @NonNull Body body) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setBodyRequest(body.getBodys()).setHeaders(header.getHeaders());
    }

    /**
     * POST
     *
     * @param headers    Headers request, it can be null
     * @param bodyObject Body request, it not null
     *
     * @returnNetworkManager.INetworkManagerBuilder
     */
    public NetworkManager.INetworkManagerBuilder post(@NonNull Header headers, @NonNull Object bodyObject) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setBodyRequest(generateBodyRequest(bodyObject)).setHeaders(headers.getHeaders());
    }

    /**
     * POST
     *
     * @param bodyObject Body request, it not null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder post(@NonNull Object bodyObject) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setBodyRequest(generateBodyRequest(bodyObject));
    }

    /**
     * PUT
     *
     * @param headers    Headers request, it can be null
     * @param bodyObject Body request, it not null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder put(@NonNull Header headers, @NonNull Object bodyObject) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setBodyRequest(generateBodyRequest(generateBodyRequest(bodyObject))).setHeaders(headers.getHeaders());
    }

    /**
     * PUT
     *
     * @param headers     Headers request, it can be null
     * @param bodyRequest Body request, it not null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder put(@NonNull Header headers, @NonNull Body bodyRequest) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setBodyRequest(generateBodyRequest(bodyRequest.getBodys())).setHeaders(headers.getHeaders());
    }

    /**
     * PUT
     *
     * @param bodyObject Body request, it not null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder put(@NonNull Object bodyObject) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setBodyRequest(generateBodyRequest(bodyObject));
    }

    /**
     * @param bodyRequest Body request, it not null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder put(@NonNull Body bodyRequest) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setBodyRequest(bodyRequest.getBodys());
    }

    /**
     * @param headers Headers request, it can be null
     *
     * @return
     */
    public NetworkManager.INetworkManagerBuilder delete(@NonNull Header headers) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.DELETE)
                .setHeaders(headers.getHeaders());
    }

    private HashMap<String, Object> generateBodyRequest(Object bodyRequest) {
        String bodyJson = new Gson().toJson(bodyRequest);
        Type type = new TypeToken<HashMap<String, String>>() {
        }.getType();
        HashMap<String, Object> body = new Gson().fromJson(bodyJson, type);
        return body;
    }

}
