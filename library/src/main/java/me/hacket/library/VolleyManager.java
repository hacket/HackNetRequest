package me.hacket.library;

import java.io.File;
import java.util.concurrent.TimeUnit;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import me.hacket.library.external.OkHttpStack;

/**
 * Volley RequestQueue请求队列的初始化，添加，取消，删除缓存，清空缓存
 * <p/>
 * Created by hacket on 2016/8/2 0002.
 */
class VolleyManager {

    private static final String TAG = HNetConfig.TAG;

    /**
     * Default on-disk cache directory.
     */
    private static final String DEFAULT_CACHE_DIR = "volley";

    /**
     * Number of network requestAsync dispatcher threads to start.
     */
    private static final int DEFAULT_NETWORK_THREAD_POOL_SIZE = 4;

    /**
     * 默认请求tag
     */
    private static final Object DEFAULT_REQUEST_TAG = "volley_default_request_tag";

    private RequestQueue mRequestQueue;

    public RequestQueue getRequestQueue() {
        return mRequestQueue;
    }

    private void initRequestQueue(@NonNull Context context) {
        if (mRequestQueue != null) {
            return;
        }
        Network network = new BasicNetwork(new OkHttpStack());
        File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
        mRequestQueue = new RequestQueue(new DiskBasedCache(cacheDir), network, DEFAULT_NETWORK_THREAD_POOL_SIZE);
        mRequestQueue.start();
    }

    /**
     * 异步请求
     *
     * @param request Request
     * @param <T>     T
     */
    public <T> void addRequestToQueue(Request<T> request) {
        addRequestToQueue(request, null);
    }

    /**
     * 异步请求
     *
     * @param request Request
     * @param tag     tag用于区分是否同一个请求
     * @param <T>     T
     */
    public <T> void addRequestToQueue(Request<T> request, Object tag) {
        request.setTag(tag != null ? tag : DEFAULT_REQUEST_TAG);
        request.setRetryPolicy(retryPolicy());
        mRequestQueue.add(request);
    }

    private DefaultRetryPolicy retryPolicy() {
        return new DefaultRetryPolicy((int) TimeUnit.SECONDS.toMillis(20), 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    public void cancelRequest(Request request) {
        if (request != null) {
            request.cancel();
        }
    }

    /**
     * 取消请求
     *
     * @param tag tag
     */
    public void cancelPendingRequests(Object tag) {
        mRequestQueue.cancelAll(tag != null ? tag : DEFAULT_REQUEST_TAG);
    }

    /**
     * 请求缓存失效，还会用cache对象
     *
     * @param key        url
     * @param fullExpire fullExpire
     */
    public void invalidateCache(String key, boolean fullExpire) {
        if (mRequestQueue != null && !TextUtils.isEmpty(key)) {
            mRequestQueue.getCache().invalidate(key, fullExpire);
        }
    }

    /**
     * 请求缓存失效，继续用cache对象
     *
     * @param key url
     */
    public void invalidateCache(String key) {
        if (mRequestQueue != null && !TextUtils.isEmpty(key)) {
            mRequestQueue.getCache().invalidate(key, true);
        }
    }

    /**
     * 移除key的cache
     *
     * @param key url
     */
    public void removeCache(String key) {
        if (mRequestQueue != null && !TextUtils.isEmpty(key)) {
            mRequestQueue.getCache().remove(key);
        }
    }

    /**
     * 清空所有cache
     */
    public void clearCache() {
        if (mRequestQueue != null) {
            mRequestQueue.getCache().clear();
        }
    }

    private static volatile VolleyManager mInstance;

    private VolleyManager(Context context) {
        initRequestQueue(context);
    }

    public static VolleyManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized(VolleyManager.class) {
                if (mInstance == null) {
                    mInstance = new VolleyManager(context);
                }
            }
        }
        return mInstance;
    }

}