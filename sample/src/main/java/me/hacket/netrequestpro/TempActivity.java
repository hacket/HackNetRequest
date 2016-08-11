package me.hacket.netrequestpro;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.os.Bundle;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.hacket.library.HParam;
import me.hacket.library.Header;
import me.hacket.library.HNet;
import me.hacket.library.callback.RequestError;
import me.hacket.library.callback.TemplateCallback;
import me.hacket.library.util.L;
import me.hacket.netrequestpro.bean.Person;
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

        //        headers.put("Content-Type", "application/x-javascript");
        //        headers.put("Accept-Encoding", "gzip,deflate");

        String baseUrl = "http://192.168.199.233:8080/"; //"http://172.17.1.123:8080/"

        // 添加headers
        Header header = new Header.Builder()
                .add("Content-Type", "application/x-javascript")
                .add("Accept-Encoding", "gzip,deflate")
                .build();

        Person person = new Person("hacket", 24);

        //        TypeToken<TempObj> tempObj = TypeToken.get(TempObj.class);

        HParam hParam = new HParam.Builder()
                .add("client_id", "11111")
                .build();

        HNet.connect()
                .createRequest()
                //                .post(header, person)
                .get(hParam)
                .baseUrl(baseUrl)
                .pathUrl("test_temp.json")
                .fromTemplate()
                //                .toBean(tempObj)
                .toBean(TempObj.class)
                .execute("temp_tag", new TemplateCallback<TempObj>() {
                    @Override
                    public void onResponseStatus(int code, String desc) {
                        L.i(code + "---" + desc);
                    }

                    @Override
                    public void onResponseSuccess(TempObj tempObj) {
                        L.i("onResponseSuccess:" + tempObj);
                    }

                    @Override
                    public void onResponseError(RequestError error) {
                        L.e("onResponseError:" + error.getErrorMsg());
                    }
                });
    }

    @OnClick(R.id.template_array)
    public void template_array() {

        TypeToken<List<TempObj>> typeToken = new TypeToken<List<TempObj>>() {
        };

        HNet.connect()
                .createRequest()
                .get()
                .baseUrl("http://172.17.1.123:8080/")
                .pathUrl("test_temp_array.json")
                .fromTemplate()
                .toBean(typeToken)
                .execute("", new TemplateCallback<List<TempObj>>() {
                    @Override
                    public void onResponseStatus(int code, String desc) {
                        L.i(code + "---" + code);
                    }

                    @Override
                    public void onResponseSuccess(List<TempObj> tempObjList) {
                        L.i("tempObjList.size():" + tempObjList.size());
                        for (int i = 0; i < tempObjList.size(); i++) {
                            L.i(tempObjList.get(i));
                        }
                    }

                    @Override
                    public void onResponseError(RequestError error) {
                        L.e(error.getErrorMsg());
                    }
                });
    }

}
