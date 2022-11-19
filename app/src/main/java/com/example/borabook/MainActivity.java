package com.example.borabook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {
    EditText et1, et2;
    Button login_btn;
    TextView tv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et1 = findViewById(R.id.et1);
        et2 = findViewById(R.id.et2);
        tv1 = findViewById(R.id.tv1);


        login_btn = (Button) findViewById(R.id.loginBtn);
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String urlpath = "http://10.0.2.2:8088/android/login?"
                                + "id=" + et1.getText().toString() + "&pw=" + et2.getText().toString();
                        final String str = requestPost(urlpath);
                        if(str != "") {
                            try {
                                JSONObject jsonObject = new JSONObject(str);
                                Log.i("main: ",jsonObject.toString());
                                String loginID = jsonObject.getString("loginID");
                                String nick = jsonObject.getString("nick");
                                Log.i("mytag", nick);
                                tv1.setText("loginID: "+loginID+", nick: "+nick);
                                if (loginID != "") {
                                    Log.i("로그인 ", "로그인 성공, BookActivity로 전환");
                                    Intent intent = new Intent(getApplicationContext(), BookActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(MainActivity.this, "아이디가 없거나 아이디 또는 비밀번호가 일치하지 않습니다.", Toast.LENGTH_LONG).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Toast.makeText(MainActivity.this, "서버 오류. 잠시 후 다시 시작해주세요.", Toast.LENGTH_LONG).show();
                        }
                    }
                }).start();
            }// onClick()
        }); //login_btn
    } // onCreate()

    public String requestPost(String requestUrl) {
        String str = null;
        BufferedReader br;
        StringBuffer buff = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestMethod("POST");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                int code = conn.getResponseCode();
                br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                str = br.readLine();
                while (str != null) {
                    buff.append(str);
                    Log.i("mytag", str);
                    str = br.readLine();
                }
                br.close();
                conn.disconnect();
            }
        } catch (Exception e) {
//            tv1.append(e.getLocalizedMessage());
            Log.i("mytag", e.getLocalizedMessage());
        }
        return buff.toString();
    }

}

