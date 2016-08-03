package me.hacket.netrequestpro;

import java.util.List;

import com.google.gson.reflect.TypeToken;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import me.hacket.library.Net;
import me.hacket.library.NetConfig;
import me.hacket.library.callback.RequestCallback;
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

        NetConfig config = new NetConfig.Builder()
                .setContext(getApplicationContext())
                .setBaseUrl("http://192.168.199.233:8080/")
                .build();

        TypeToken<List<Person>> typeToken = new TypeToken<List<Person>>() {
        };

        Net.connect()
                .createRequest(config)
                .get()
                .pathUrl("/test_array.json")
                .fromJsonArray()
                .toBean(typeToken)
                .execute("request_array_tag", new RequestCallback<List<Person>>() {
                    @Override
                    public void onRequestSuccess(List<Person> persons) {
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
                    public void onRequestError(RequestError error) {
                        dialog.dismiss();
                    }
                });

    }
}
