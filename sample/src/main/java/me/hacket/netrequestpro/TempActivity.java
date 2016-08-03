package me.hacket.netrequestpro;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hacket.library.Net;
import me.hacket.library.callback.RequestCallback;
import me.hacket.library.callback.RequestError;
import me.hacket.library.request.BaseResponse;
import me.hacket.library.util.L;
import me.hacket.netrequestpro.bean.TempObj;

public class TempActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temp);

        ButterKnife.bind(this);

    }

    @OnClick(R.id.template_obj)
    public void template_obj() {

        Net.connect()
                .createRequest()
                .get()
                .baseUrl("http://192.168.199.233:8080/")
                .pathUrl("test_temp.json")
                .fromTemplate()
                .toBean(TempObj.class)
                .execute("temp_tag", new RequestCallback<BaseResponse<TempObj>>() {
                    @Override
                    public void onRequestSuccess(BaseResponse<TempObj> tempObjBaseResponse) {
                        BaseResponse.Result result = tempObjBaseResponse.result;
                        TempObj response = tempObjBaseResponse.response;
                        L.i(result);
                        L.i(response);
                    }

                    @Override
                    public void onRequestError(RequestError error) {
                        L.e(error.getErrorMsg());
                    }
                });
    }

    @OnClick(R.id.template_array)
    public void template_array() {

        TypeToken<List<TempObj>> typeToken = new TypeToken<List<TempObj>>() {

        };

        Net.connect()
                .createRequest()
                .get()
                .baseUrl("http://192.168.199.233:8080/")
                .pathUrl("test_temp_array.json")
                .fromTemplate()
                .toBean(typeToken)
                .execute("", new RequestCallback<BaseResponse<List<TempObj>>>() {
                    @Override
                    public void onRequestSuccess(BaseResponse<List<TempObj>> listBaseResponse) {
                        BaseResponse.Result result = listBaseResponse.result;
                        L.i(result);
                        List<TempObj> tempObjList = listBaseResponse.response;
                        L.i("tempObjList.size():" + tempObjList.size());
                        for (int i = 0; i < tempObjList.size(); i++) {
                            L.i(tempObjList.get(i));
                        }
                    }

                    @Override
                    public void onRequestError(RequestError error) {
                        L.e(error.getErrorMsg());
                    }
                });
    }

}
