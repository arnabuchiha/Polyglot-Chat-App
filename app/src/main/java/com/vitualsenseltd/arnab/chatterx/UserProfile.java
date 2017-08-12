package com.vitualsenseltd.arnab.chatterx;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import static com.vitualsenseltd.arnab.chatterx.Users.endTime;
import static com.vitualsenseltd.arnab.chatterx.Users.startTime;
import static com.vitualsenseltd.arnab.chatterx.Users.totalTime;

public class UserProfile extends AppCompatActivity {
    public static long activity;
    TextView textView,textView1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        textView=(TextView)findViewById(R.id.textView);
        textView1=(TextView)findViewById(R.id.textView2);
        textView.setText(UserDetails.chatWith);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String url = "https://chatterx-7db2d.firebaseio.com/users.json";


        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String s) {

                    try {
                        JSONObject obj = new JSONObject(s);
                       textView1.setText(obj.getJSONObject(UserDetails.chatWith).getString("activityScore"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(UserProfile.this);
        rQueue.add(request);


        Button button=(Button)findViewById(R.id.startchatButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(UserProfile.this,Chat.class);
                startActivity(intent);
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
