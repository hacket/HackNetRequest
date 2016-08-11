package me.hacket.library.callback;

/**
 * 网络请求回调 {@link me.hacket.library.request.HTemplateRequest}专用
 *
 * @param <T> T
 *            <p/>
 *            Created by hacket on  2016年8月4日00:24:15
 */
public interface TemplateCallback<T> extends Callback<T> {

    void onResponseStatus(int code, String desc);

}
