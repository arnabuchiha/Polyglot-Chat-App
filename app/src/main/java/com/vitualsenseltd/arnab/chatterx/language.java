package com.vitualsenseltd.arnab.chatterx;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;


import org.json.JSONException;
import org.json.JSONObject;

public class language extends AppCompatActivity {
    Firebase reference3;
    String lang;
    static String key;
    Register m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        Firebase.setAndroidContext(this);
        reference3 = new Firebase("https://chatterx-7db2d.firebaseio.com/users/");
        final String [] code={"af","sq","am","ar","hy","az","eu","be","bn","bs","bg","ca","ceb","zh-CN","zh-TW","co","hr","cs","da","nl","en","eo","et","fi",
                "fr","fy","gl","ka","de","el","gu","ht","ha","haw","iw","hi","hmn","hu","is","ig",
                "id","ga","it","ja","jw","kn","kk","km","ko","ku","ky","lo","la","lv","lt","lb","mk","mg","ms","ml","mi","mr","mn","my","ne","no","ny",
                "ps","fa","pl","pt","ma","ro","ru","sm","gd","sr","st","sn","sd","si","sk","sl","so","es","su","sw","sv","tl","tg","ta","te","th",
                "tr","uk","ur","uz","vi","cy","xh","yi","yo","zu"};



        final ListView listView = (ListView) findViewById(R.id.listview);
        ArrayAdapter<String> mAdapter=new ArrayAdapter<String>(language.this,android.R.layout.simple_list_item_1,getResources().getStringArray(R.array.languages));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                UserDetails userDetails=new UserDetails();

                key=code[i];
                userDetails.setLanguage(key);
                reference3.child(UserDetails.username).child("language").setValue(userDetails.getLanguage());

                Intent mIntent=getIntent();
                String pre_act=mIntent.getStringExtra("Previous_Activity");
                if(pre_act.equals("Register")){
                    Intent intent=new Intent(language.this, Register.class);
                    startActivity(intent);
                }
                else if(pre_act.equals("Users")){
                    Intent intent = new Intent(language.this, Users.class);
                    startActivity(intent);
                }

            }
        });

        listView.setAdapter(mAdapter);
    }
}

