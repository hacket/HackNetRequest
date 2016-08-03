package me.hacket.netrequestpro;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.TextView;
import me.hacket.library.Body;
import me.hacket.library.Header;
import me.hacket.library.Net;
import me.hacket.library.NetConfig;
import me.hacket.library.callback.RequestCallback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.util.L;
import me.hacket.netrequestpro.bean.Register;

public class JsonObjectActivity extends Activity {

    private static final String TAG = "jsonobj";

    //    http://218.17.0.92:51515/cloud_sales/api/v1/user/is_register_sale?client_config_version=1000&timestamp=20160803173157&client_device=android&chain_id=15bdc3584b1756139d960e168e8beecd&client_version=1.0.3.0&client_channel=0110&user_type=sale

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_json_object);

        final TextView id = (TextView) findViewById(R.id.tv_id);
        final TextView title = (TextView) findViewById(R.id.tv_title);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        NetConfig config = new NetConfig.Builder()
                .setContext(getApplicationContext())
                .setBaseUrl("http://218.17.0.92:51515")
                .build();

        Body body = new Body.Builder()
                .add("phone","13510590884")
                .build();

        Header header = new Header.Builder()
                .build();

        TypeToken<Register> typeToken = new TypeToken<Register>() {
        };

        Net.connect()
                .createRequest(config)
                .post(header,body)
                .pathUrl("/cloud_sales/api/v1/user/is_register_sale?client_config_version=1000&timestamp=20160803173157&client_device=android&chain_id=15bdc3584b1756139d960e168e8beecd&client_version=1.0.3.0&client_channel=0110&user_type=sale")
                .fromJsonObject()
                .toBean(typeToken)
                .execute("register_request_tag", new RequestCallback<Register>() {
                    @Override
                    public void onRequestSuccess(Register register) {
                        dialog.dismiss();
                        Register.Result result = register.result;
                        int code = result.code;
                        String desc = result.desc;
                        L.i(TAG, "code:" + code);
                        L.i(TAG, "desc:" + desc);

                        if (code == 0) {
                            Register.Response response = register.response;
                            boolean is_registered = response.is_registered;
                            L.i(TAG, "is_registered:" + is_registered);
                            title.setText(""+is_registered);
                        }
                    }

                    @Override
                    public void onRequestError(RequestError error) {
                        dialog.dismiss();
                        L.e(TAG, "error:" + error.getErrorCode());
                    }
                });

    }
}
