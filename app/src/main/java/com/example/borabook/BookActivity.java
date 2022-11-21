package com.example.borabook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BookActivity extends AppCompatActivity implements OnTabItemSelectedListener{

    private String tag = "메인";
    private Fragment1 fragment1; // 왼쪽 가계부 목록 화면
    private Fragment2 fragment2; // 오른쪽 가계부 쓰기 화면
    private BottomNavigationView bottomNavigation; // 하단 네비
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        // loginID를 가져와서 이 앱에서만 사용 가능하도록
        Context context = fragment2.getActivity();
        pref = context.getSharedPreferences("loginID", Context.MODE_PRIVATE);

        fragment1 = new Fragment1();
        fragment2 = new Fragment2();

        getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();

        bottomNavigation = findViewById(R.id.bottomNavigation);
        bottomNavigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.tab1 :
                                Toast.makeText(getApplicationContext(), "가계부 목록 선택", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment1).commit();
                            return true;
                            case R.id.tab2:
                                Toast.makeText(getApplicationContext(), "가계부 쓰기 선택", Toast.LENGTH_SHORT).show();
                                getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment2).commit();
                            return true;
                        }
                        return false;
                    }
                }
        );
    }

    public void onTabSelected(int position) {
        if(position==0) {
            bottomNavigation.setSelectedItemId(R.id.tab1);
        } else if(position==1) {
            bottomNavigation.setSelectedItemId(R.id.tab2);
        }


    }
}