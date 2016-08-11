package me.hacket.netrequestpro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import me.hacket.library.HNet;
import me.hacket.library.callback.Callback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.util.L;

public class StringActivity extends Activity {

    private static final String REQUEST_TAG_STRING = "REQUEST_TAG_STRING";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);

        final TextView result = (TextView) findViewById(R.id.tv_result);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        String baseUrl = "http://192.168.199.233:8080/"; //"http://172.17.1.123:8080/"

        HNet.connect()
                .createRequest()
                .get() // get or post
                .baseUrl(baseUrl) // baseurl
                .pathUrl("test.string.txt") // path url
                .fromString() // 请求转为String
                .execute(REQUEST_TAG_STRING, new Callback<String>() { // 回调
                    @Override
                    public void onResponseSuccess(String str) {
                        dialog.dismiss();
                        result.setText(str);
                    }

                    @Override
                    public void onResponseError(RequestError error) {
                        dialog.dismiss();
                        L.e("error:" + error.getErrorMsg());
                        error.printStackTrace();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 退出清除请求
        HNet.connect().cancelRequest(REQUEST_TAG_STRING);
    }

}
