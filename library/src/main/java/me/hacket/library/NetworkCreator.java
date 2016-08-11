package me.hacket.library;

import com.android.volley.Request;

import android.content.Context;
import android.support.annotation.NonNull;

/**
 * 创建网络请求类型：GET POST or PUT
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
public class NetworkCreator {

    private final Context context;
    private final String baseUrl;

    private final Header header;
    private final HParam param;

    NetworkCreator(@NonNull HNetConfig netConfig) {
        this.context = netConfig.getContext();
        this.baseUrl = netConfig.getBaseUrl();
        this.header = netConfig.getHeader();
        this.param = netConfig.getParam();
    }

    // ========================= GET ========================= //

    /**
     * GET不带headers
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder get() {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET)
                .setHeaders(header.getHeaders()).setParams(param.getParams());
    }

    /**
     * GET不带headers
     *
     * @param param {@link HParam}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder get(@NonNull HParam param) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET)
                .setParams(param.getParams()).setHeaders(header.getHeaders());
    }

    /**
     * GET带headers
     *
     * @param header Headers request . see {@link Header}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder get(@NonNull Header header) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET)
                .setHeaders(header.getHeaders()).setParams(param.getParams());
    }

    /**
     * GET带headers
     *
     * @param header Headers request . see {@link Header}
     * @param hParam {@link HParam}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder get(@NonNull Header header, @NonNull HParam hParam) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.GET)
                .setHeaders(header.getHeaders()).setParams(hParam.getParams());
    }

    // ========================= GET ========================= //

    // ========================= POST ========================= //

    /**
     * POST带Header, 其中body为一个HJsonBody对象
     *
     * @param header    Headers request . see {@link Header}
     * @param hJsonBody it not null, see {@link HJsonBody}
     * @param hParam    see {@link HParam}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public <T> NetworkManager.INetworkManagerBuilder post(@NonNull Header header, @NonNull HJsonBody<T> hJsonBody,
                                                          @NonNull HParam hParam) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setPostJsonBodys(hJsonBody.getBodyString()).setHeaders(header.getHeaders())
                .setParams(hParam.getParams());
    }

    /**
     * POST带Header, 其中body为一个HJsonBody对象
     *
     * @param header    Headers request . see {@link Header}
     * @param hJsonBody it not null, see {@link HJsonBody}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public <T> NetworkManager.INetworkManagerBuilder post(@NonNull Header header, @NonNull HJsonBody<T> hJsonBody) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setPostJsonBodys(hJsonBody.getBodyString()).setHeaders(header.getHeaders())
                .setParams(param.getParams());
    }

    /**
     * POST带Header, 其中body为一个HParam对象
     *
     * @param header Headers request . see {@link Header}
     * @param hParam it not null, see {@link HParam}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder post(@NonNull Header header, @NonNull HParam hParam) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setHeaders(header.getHeaders()).setParams(hParam.getParams());
    }

    /**
     * POST不带Header, 其中Body为一个HJsonBody对象
     *
     * @param hJsonBody it not null, see {@link HJsonBody}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public <T> NetworkManager.INetworkManagerBuilder post(@NonNull HJsonBody<T> hJsonBody) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.POST)
                .setPostJsonBodys(hJsonBody.getBodyString()).setHeaders(header.getHeaders())
                .setParams(param.getParams());
    }

    // ========================= POST ========================= //

    // ========================= PUT ========================= //

    /**
     * PUT
     *
     * @param header    Headers request . see {@link Header}
     * @param hJsonBody Body request, it not null . see {@link HJsonBody}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder put(@NonNull Header header, @NonNull HJsonBody hJsonBody) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setPostJsonBodys(hJsonBody.getBodyString()).setHeaders(header.getHeaders());
    }

    /**
     * @param hJsonBody T , Body request, it not null . see {@link HJsonBody}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public <T> NetworkManager.INetworkManagerBuilder put(@NonNull HJsonBody hJsonBody) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.PUT)
                .setPostJsonBodys(hJsonBody.getBodyString());
    }

    // ========================= PUT ========================= //

    /**
     * @param header Headers request . see {@link Header}
     *
     * @return see {@link NetworkManager.INetworkManagerBuilder}
     */
    public NetworkManager.INetworkManagerBuilder delete(@NonNull Header header) {
        return new NetworkManager.Builder().setBaseUrl(baseUrl).setContext(context).setMethod(Request.Method.DELETE)
                .setHeaders(header.getHeaders());
    }

}
