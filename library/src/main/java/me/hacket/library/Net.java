package me.hacket.library;

/**
 * 网络请求入口
 * Created by zengfansheng on 2016/8/2 0002.
 */
public class Net {

    private static volatile Net mInstance;
    private NetConfig mNetConfig;

    private Net(NetConfig netConfig) {
        this.mNetConfig = netConfig;
    }

    /**
     * 初始化网络连接配置
     *
     * @param netConfig
     *
     * @return
     */
    public static Net init(NetConfig netConfig) {
        if (mInstance == null) {
            synchronized(Net.class) {
                if (mInstance == null) {
                    mInstance = new Net(netConfig);
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
    public synchronized static Net connect() {
        if (mInstance == null) {
            throw new IllegalArgumentException("Net not instance yet.");
        }
        return mInstance;
    }

    /**
     * 创建请求
     *
     * @return NetworkCreator
     */
    public NetworkCreator createRequest() {
        if (mNetConfig == null) {
            throw new IllegalArgumentException("Config must not be null! should call init() first!");
        }
        return new NetworkCreator(mNetConfig.getContext(), mNetConfig.getBaseUrl());
    }

    /**
     * 创建请求
     *
     * @param netConfig NetConfig
     *
     * @return NetworkCreator
     */
    public NetworkCreator createRequest(NetConfig netConfig) {
        if (netConfig == null) {
            throw new IllegalArgumentException("netConfig must not be null !");
        }
        return new NetworkCreator(netConfig.getContext(), netConfig.getBaseUrl());
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
