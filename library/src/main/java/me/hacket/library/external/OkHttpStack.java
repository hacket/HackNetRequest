package me.hacket.library.external;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.toolbox.HurlStack;

import okhttp3.OkHttpClient;
import okhttp3.OkUrlFactory;

/**
 * OkHttpStack
 *
 * OkHttp3
 */
public class OkHttpStack extends HurlStack {

    private OkUrlFactory mFactory;
    private OkHttpClient internalClient;

    public OkHttpStack() {
        if (internalClient == null) {
            internalClient = new OkHttpClient.Builder().build();
        }
        mFactory = new OkUrlFactory(internalClient);
    }

    public OkHttpStack(OkHttpClient client) {
        if (client == null) {
            throw new NullPointerException("Client must not be null.");
        }
        mFactory = new OkUrlFactory(client);
    }

    @Override
    protected HttpURLConnection createConnection(URL url) throws IOException {
        return mFactory.open(url);
    }

}
