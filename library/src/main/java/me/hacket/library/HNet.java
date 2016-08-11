package me.hacket.library;

import android.support.annotation.NonNull;

/**
 * 网络请求入口
 * Created by hacket on 2016/8/2 0002.
 */
public class HNet {

    private static volatile HNet mInstance;
    private HNetConfig mNetConfig;

    private HNet(HNetConfig netConfig) {
        this.mNetConfig = netConfig;
    }

    /**
     * 初始化网络连接配置
     *
     * @param HNetConfig
     *
     * @return
     */
    public static HNet init(HNetConfig HNetConfig) {
        if (mInstance == null) {
            synchronized(HNet.class) {
                if (mInstance == null) {
                    mInstance = new HNet(HNetConfig);
                }
            }
        }
        return mInstance;
    }

    /**
     * 获取网络连接对象
     *
     * @return
     */
    public synchronized static HNet connect() {
        if (mInstance == null) {
            throw new IllegalArgumentException("Net not instance yet.");
        }
        return mInstance;
    }

    /**
     * 创建请求, 使用默认全局的HNetConfig
     *
     * @return NetworkCreator
     */
    public NetworkCreator createRequest() {
        if (mNetConfig == null) {
            throw new IllegalArgumentException("Config must not be null! should call init() first!");
        }
        HNetConfig.Builder builder = new HNetConfig.Builder().clone(mNetConfig);
        return new NetworkCreator(builder.build());
    }

    /**
     * 创建请求, 使用HNetConfig覆盖默认的设置
     *
     * @param netConfig HNetConfig
     *
     * @return NetworkCreator
     */
    public NetworkCreator createRequest(HNetConfig netConfig) {
        if (netConfig == null) {
            throw new IllegalArgumentException("netConfig must not be null !");
        }
        return new NetworkCreator(netConfig);
    }

    /**
     * 创建请求, 使用默认全局的HNetConfig, 并添加Header和HParam
     *
     * @param header Header
     * @param hParam HParam
     *
     * @return NetworkCreator
     */
    public NetworkCreator createRequest(@NonNull Header header, @NonNull HParam hParam) {
        if (mNetConfig == null) {
            throw new IllegalArgumentException("netConfig must not be null , should call init() first!");
        }
        HNetConfig.Builder builder = new HNetConfig.Builder().clone(mNetConfig).addHeader(header).addParam(hParam);

        // 这种会址传递
        //        HNetConfig.Builder builder =
        //                new HNetConfig.Builder().clone(mNetConfig).addHeader(header).addParam(hParam);
        return new NetworkCreator(builder.build());
    }

    /**
     * 取消请求
     *
     * @param tag 请求tag,用于取消,为null取消所有默认tag的请求
     */
    public void cancelRequest(Object tag) {
        VolleyManager.getInstance(mNetConfig.getContext()).cancelPendingRequests(tag);
    }

    /**
     * 请求缓存失效，还会用cache对象
     *
     * @param url        url
     * @param fullExpire true还用cache对象
     */
    public void invalidateCache(String url, boolean fullExpire) {
        VolleyManager.getInstance(mNetConfig.getContext()).invalidateCache(url, fullExpire);
    }

    /**
     * 移除某个url的cache
     *
     * @param url url
     */
    public void removeCache(String url) {
        VolleyManager.getInstance(mNetConfig.getContext()).removeCache(url);
    }

    /**
     * 清空所有的cache
     */
    public void clearCache() {
        VolleyManager.getInstance(mNetConfig.getContext()).clearCache();
    }

}
