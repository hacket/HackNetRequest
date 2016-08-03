package me.hacket.library.callback;

/**
 * 网络请求回调
 *
 * @param <T> T
 *            <p/>
 *            Created by zengfansheng on  2016年8月4日00:24:15
 */
public interface RequestCallback<T> {

    void onRequestSuccess(T t);

    void onRequestError(RequestError error);

}
