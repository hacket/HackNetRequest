package me.hacket.netrequestpro;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import me.hacket.library.HJsonBody;
import me.hacket.library.HNet;
import me.hacket.library.HNetConfig;
import me.hacket.library.HParam;
import me.hacket.library.Header;
import me.hacket.library.callback.Callback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.util.L;
import me.hacket.netrequestpro.bean.RegisterObj;

public class JsonObjectActivity extends Activity {

    private static final String TAG = "jsonobj";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_object);

        final TextView id = (TextView) findViewById(R.id.tv_id);
        final TextView title = (TextView) findViewById(R.id.tv_title);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        HNetConfig config = new HNetConfig.Builder()
                .setContext(getApplicationContext())
                .setBaseUrl("http://172.17.1.123:8080")
                .build();

        HJsonBody<String> jsonBody = new HJsonBody.Builder<String>()
                .setBody("name")
                .build();

        Header header = new Header.Builder()
                .add("name", "hacket_json_obj")
//                .add("name1", "hacket1_json_obj")
//                .add("name2", "hacket2_json_obj")
                .build();

        TypeToken<RegisterObj> typeToken = new TypeToken<RegisterObj>() {
        };

        HParam params = new HParam.Builder()
                .add("param1", "hacket_param1")
//                .add("param2", "hacket_param2")
//                .add("param3", "hacket_param3")
                .build();

        HNet.connect()
                //                .createRequest(config)
                .createRequest(header, params)
                //                .get()
                .get()
                .baseUrl("http://192.168.199.233:8080")
                .pathUrl("/test_obj.json")
                .fromJsonObject()
                .toBean(typeToken)
                .execute("register_request_tag", new Callback<RegisterObj>() {
                    @Override
                    public void onResponseSuccess(RegisterObj register) {
                        dialog.dismiss();
                        RegisterObj.Result result = register.result;
                        int code = result.code;
                        String desc = result.desc;
                        L.i(TAG, "code:" + code);
                        L.i(TAG, "desc:" + desc);

                        if (code == 0) {
                            RegisterObj.Response response = register.response;
                            title.setText(response.toString());
                            L.i(TAG, "response:" + response);
                        }
                    }

                    @Override
                    public void onResponseError(RequestError error) {
                        dialog.dismiss();
                        L.e(TAG, "error:" + error.getErrorCode() + error.getErrorMsg());
                    }
                });

    }
}
