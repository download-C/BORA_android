package com.example.borabook;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;

import com.google.gson.Gson;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.Calendar;
import java.util.Map;


public class Fragment1 extends Fragment {
    // 사용할 변수 초기화
    private View view;
    private String tag = "목록 프래그먼트";
    private MaterialCalendarView calView;

    int selectYear, selectMonth, selectDay;

    // 달력이 있는 프래그먼트 생성
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(tag, "onCreateView");
        view = inflater.inflate(R.layout.fragment1, container, false);
        
        // 프래그먼트의 달력 호출
        calView = view.findViewById(R.id.calendarView);
        // 달력에서 선택한 날의 연/월/일 가져오기 
        calView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
                selectYear = date.getYear();
                selectMonth = date.getMonth()+1;
                selectDay = date.getDay();

//                Toast.makeText(getActivity(), selectYear+"년 "+selectMonth+"월 "+selectDay+"일 선택", Toast.LENGTH_SHORT).show();
                // 사용자가 선택한 날짜의 가계부 목록 서버에서 불러오기

                // 선택한 연/월/일과 사용자 아이디를 담은 DTO 생성

                // dto를 Json 타입으로 변환
                Gson gson = new Gson();
//                String objJson = gson.toJson(dto);



            }
        });

        return view;
    }
    public class NetworkTask extends AsyncTask<Map<String, String>, Integer, String> {
        @Override
        protected String doInBackground(Map<String, String>... maps) { // 내가 전송하고 싶은 파라미터

            // Http 요청 준비 작업
            HttpClient.Builder http = new HttpClient.Builder("GET", "http://192.168.0.133:8088/android/getDetailList");

            // Parameter 를 전송한다.
            http.addAllParameters(maps[0]);


            //Http 요청 전송
            HttpClient post = http.create();
            post.request();

            // 응답 상태코드 가져오기
            int statusCode = post.getHttpStatusCode();

            // 응답 본문 가져오기
            String body = post.getBody();

            return body;
        }

        @Override
        protected void onPostExecute(String s) {

        }
    }

}
