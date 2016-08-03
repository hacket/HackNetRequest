package me.hacket.netrequestpro;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import me.hacket.library.Net;
import me.hacket.library.callback.RequestCallback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.util.L;

public class StringActivity extends Activity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_string);

        final TextView result = (TextView) findViewById(R.id.tv_result);
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        Net.connect()
                .createRequest()
                .get()
                .baseUrl("http://192.168.199.233:8080/")
                .pathUrl("test.string.txt")
                .fromString()
                .execute("request_string", new RequestCallback<String>() {
                    @Override
                    public void onRequestSuccess(String str) {
                        dialog.dismiss();
                        result.setText(str);
                    }

                    @Override
                    public void onRequestError(RequestError error) {
                        dialog.dismiss();
                        L.e("error:"+error.getErrorMsg());
                        error.printStackTrace();
                    }
                });
    }

}
