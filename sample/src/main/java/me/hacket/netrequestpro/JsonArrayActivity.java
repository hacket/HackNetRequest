package me.hacket.netrequestpro;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import me.hacket.library.HNet;
import me.hacket.library.HNetConfig;
import me.hacket.library.callback.Callback;
import me.hacket.library.callback.RequestError;
import me.hacket.netrequestpro.bean.Person;

public class JsonArrayActivity extends Activity {
    ArrayAdapter<String> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final ListView listView = (ListView) findViewById(R.id.list);

        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.show();

        String baseUrl = "http://192.168.199.233:8080/"; //"http://172.17.1.123:8080/"

        HNetConfig config = new HNetConfig.Builder()
                .setContext(getApplicationContext())
                .setBaseUrl(baseUrl)
                .build();

        TypeToken<List<Person>> typeToken = new TypeToken<List<Person>>() {
        };

        HNet.connect()
                .createRequest()
                .get()
                .pathUrl("/test_array.json")
                .fromJsonArray()
                .toBean(typeToken)
                .execute("request_array_tag", new Callback<List<Person>>(){

                    @Override
                    public void onResponseSuccess(List<Person> persons) {
                        dialog.dismiss();
                        String[] values = new String[persons.size()];
                        for (int i = 0; i < persons.size(); i++) {
                            values[i] = persons.get(i).name + "_" + persons.get(i).age;
                        }

                        adapter = new ArrayAdapter<>(JsonArrayActivity.this,
                                android.R.layout.simple_list_item_1, android.R.id.text1, values);

                        listView.setAdapter(adapter);
                    }

                    @Override
                    public void onResponseError(RequestError error) {
                        dialog.dismiss();
                    }
                });

    }
}
