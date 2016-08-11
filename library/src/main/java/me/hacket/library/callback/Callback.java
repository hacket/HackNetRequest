package me.hacket.library.callback;

/**
 * 网络请求回调
 *
 * @param <T> T
 *            <p/>
 *            Created by hacket on  2016年8月4日00:24:15
 */
public interface Callback<T> {

    void onResponseError(RequestError error);

    void onResponseSuccess(T t);

}
