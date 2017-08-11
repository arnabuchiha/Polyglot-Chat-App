package com.vitualsenseltd.arnab.chatterx;

import android.app.ProgressDialog;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.vitualsenseltd.arnab.chatterx.Translation.Data;
import com.vitualsenseltd.arnab.chatterx.Translation.Translation;
import com.vitualsenseltd.arnab.chatterx.Translation.authPOJO;
import com.vitualsenseltd.arnab.chatterx.Translation.translate;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.vitualsenseltd.arnab.chatterx.language.key;


public class Chat extends AppCompatActivity {
    LinearLayout layout;
    ImageView sendButton;
    EditText messageArea;
    ScrollView scrollView;
    String messageText1;
    Map<String, String> map;
    Firebase reference1, reference2,reference3;
    language l;
private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        layout = (LinearLayout)findViewById(R.id.layout1);
        sendButton = (ImageView)findViewById(R.id.sendButton);
        messageArea = (EditText)findViewById(R.id.messageArea);
        scrollView = (ScrollView)findViewById(R.id.scrollView);
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        DatabaseReference mDatabase;
        mDatabase = FirebaseDatabase.getInstance().getReference();
        Firebase.setAndroidContext(this);

        reference1 = new Firebase("https://chatterx-7db2d.firebaseio.com/messages/" + UserDetails.username + "_" + UserDetails.chatWith);
        reference2 = new Firebase("https://chatterx-7db2d.firebaseio.com/messages/" + UserDetails.chatWith + "_" + UserDetails.username);
        reference3= new Firebase("https://chatterx-7db2d.firebaseio.com/users/"+UserDetails.username);
        /*final lan ln=lan.retrofit.create(lan.class);
        Call<lang> readlang=ln.readlang("language");
        readlang.enqueue(new Callback<lang>() {
            @Override
            public void onResponse(Call<lang> call, Response<lang> response) {
                key=response.body().getLanguage();
            }

            @Override
            public void onFailure(Call<lang> call, Throwable t) {

            }
        });*/

        {
            String url = "https://chatterx-7db2d.firebaseio.com/users.json";
            final ProgressDialog pd = new ProgressDialog(Chat.this);
            pd.setMessage("Loading...");
            pd.show();

            StringRequest request = new StringRequest(Request.Method.GET, url, new com.android.volley.Response.Listener<String>() {
                @Override
                public void onResponse(String s) {

                        try {
                            JSONObject obj = new JSONObject(s);

                            key=obj.getJSONObject(UserDetails.username).getString("language");

                            }
                         catch (JSONException e) {
                            e.printStackTrace();
                        }


                    pd.dismiss();
                }
            }, new com.android.volley.Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError volleyError) {
                    System.out.println("" + volleyError);
                    pd.dismiss();
                }
            });

            RequestQueue rQueue = Volley.newRequestQueue(Chat.this);
            rQueue.add(request);
        }


        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                messageText1 = messageArea.getText().toString();
                //Log.i("Translated:",messageText1);
                if(!messageText1.equals("")){

                    map = new HashMap<String, String>();

                    if(key=="en"){
                        map.put("message", messageText1);
                        map.put("user", UserDetails.username);
                        reference1.push().setValue(map);
                        reference2.push().setValue(map);
                    }
                    else {
                        final translate tn = translate.retrofit.create(translate.class);
                        Call<authPOJO> call = tn.performTranslate("AIzaSyB_nmZJYnq3HaGOD0O8YUip9diuFyCcRBs", messageText1, key, "en");
                        call.enqueue(new Callback<authPOJO>() {
                            @Override
                            public void onResponse(Call<authPOJO> call, Response<authPOJO> response) {
                                Data data = response.body().getData();
                                //See the response
                                System.out.println("HAHA: " + response.raw().toString());
                                List<Translation> list = data.getTranslations();

                                ListIterator<Translation> los = list.listIterator();
                                String txt1 = "";
                                do {
                                    String txt = los.next().getTranslatedText();
                                    txt1 = txt1 + txt;
                                } while (los.hasNext());
                                map.put("message", txt1);
                                map.put("user", UserDetails.username);
                                reference1.push().setValue(map);
                                reference2.push().setValue(map);

                            }


                            @Override
                            public void onFailure(Call<authPOJO> call, Throwable t) {
                                Toast.makeText(Chat.this, "Translate failed", Toast.LENGTH_LONG).show();
                            }
                        });
                    }

                }
                //transl("AIzaSyCHq9a-pe-iVnPXhJ_PBw5BXqKM2l8tQ84",messageText1,"en","de");


            }
        });

        reference1.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                final Map map = dataSnapshot.getValue(Map.class);

                final String message = map.get("message").toString();
                final String userName = map.get("user").toString();
                if(key=="en"){
                    if(userName.equals(UserDetails.username)){
                        addMessageBox("You:-\n" + message, 1);
                    }
                    else{
                        addMessageBox(UserDetails.chatWith + ":-\n" + message, 2);
                    }
                }
                else {
                    final translate tn = translate.retrofit.create(translate.class);
                    //txt1="";
                    Call<authPOJO> call = tn.performTranslate("AIzaSyB_nmZJYnq3HaGOD0O8YUip9diuFyCcRBs", message, "en", key);
                    call.enqueue(new Callback<authPOJO>() {
                        @Override
                        public void onResponse(Call<authPOJO> call, Response<authPOJO> response) {
                            Data data = response.body().getData();
                            //See the response
                            System.out.println("HAHA: " + response.raw().toString());
                            List<Translation> list = data.getTranslations();

                            ListIterator<Translation> los = list.listIterator();
                            String txt1 = "";
                            do {
                                String txt = los.next().getTranslatedText();
                                txt1 = txt1 + txt;
                            } while (los.hasNext());

                            if (userName.equals(UserDetails.username)) {
                                addMessageBox("You:-\n" + txt1, 1);
                            } else {
                                addMessageBox(UserDetails.chatWith + ":-\n" + txt1, 2);
                            }

                        }


                        @Override
                        public void onFailure(Call<authPOJO> call, Throwable t) {
                            Toast.makeText(Chat.this, "Translate failed", Toast.LENGTH_LONG).show();
                        }
                    });
                }

            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }


    public void addMessageBox(String message, int type){
        TextView textView = new TextView(Chat.this);
        textView.setText(message);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.setMargins(0, 0, 0, 10);
        textView.setLayoutParams(lp);

        if(type == 1) {
            textView.setBackgroundResource(R.drawable.rounded_corner1);
        }
        else{
            textView.setBackgroundResource(R.drawable.rounded_corner2);
        }

        layout.addView(textView);
        sendScroll();
    }
    private void sendScroll(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {Thread.sleep(100);} catch (InterruptedException e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        scrollView.fullScroll(View.FOCUS_DOWN);
                    }
                });
            }
        }).start();
    }

}
