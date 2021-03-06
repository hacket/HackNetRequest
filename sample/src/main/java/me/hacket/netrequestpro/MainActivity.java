package me.hacket.netrequestpro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private final int STRING = 0;
    private final int OBJECT = 1;
    private final int ARRAY = 2;
    private final int TEMP = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView listView = (ListView) findViewById(R.id.list);

        String[] values = new String[]
                {
                        "Request String",
                        "Request Json Object",
                        "Request Json Array",
                        "Request Template"
                };

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (position) {
                    case STRING:
                        intent = new Intent(MainActivity.this, StringActivity.class);
                        startActivity(intent);
                        break;
                    case OBJECT:
                        intent = new Intent(MainActivity.this, JsonObjectActivity.class);
                        startActivity(intent);
                        break;
                    case ARRAY:
                        intent = new Intent(MainActivity.this, JsonArrayActivity.class);
                        startActivity(intent);
                        break;
                    case TEMP:
                        intent = new Intent(MainActivity.this, TempActivity.class);
                        startActivity(intent);
                        break;
                }
            }

        });
    }

}
