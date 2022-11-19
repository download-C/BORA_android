package com.example.borabook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
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
import androidx.recyclerview.widget.RecyclerView;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;



public class Fragment1 extends Fragment {
    // 사용할 변수 초기화
    private View view;
    private String tag = "목록 프래그먼트";
    private MaterialCalendarView calView;
    private RecyclerView detail;

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

                // 저장한 정보 가져오는 방법
                // 1. SP sp = getSP("데이터이름","Activity모드");
                // 2. String 데이터 =  sp.getString("데이터이름", "데이터가 없을 때 설정할 기본값");

                // 데이터를 보내기 위한 로그인 아이디 불러오기
                SharedPreferences sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                String loginID = sp.getString("loginID", "아이디 없음");

                // 데이터 보내기
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 가계부 목록 요청에 필요한 내용을 주소에 담아 보내기
                        String urlpath = "http://10.0.2.2/android/getBookList?"
                                +"bk_year="+selectYear+"bk_month="+selectMonth
                                +"bk_day="+selectDay+"loginID="+loginID;
                        final String data = requestPost(urlpath);
                        // 요청 후 돌아온 데이터가 있을 때
                        if(data != "") {
                            try {
                                JSONObject jsonObj = new JSONObject(data);
                                Log.i("제이슨 데이터", jsonObj.toString());
                                if(jsonObj!=null) {
                                    // 제이슨 데이터에서 보내는 배열의 제목을 어레이에 담음
                                    JSONArray jsonArray =   new JSONArray(jsonObj.getString("list"));
                                    // 어레이를 하나씩 오브젝트로 꺼냄
                                    for(int i=0; i<jsonArray.length(); i++) {
                                        AlertDialog.Builder readDetail = new AlertDialog.Builder(view.getContext());
                                        readDetail.setTitle(selectYear+"."+selectMonth+"."+selectDay+". 가계부 목록");
//                                        readDetail.setMessage();
                                        JSONObject detailObj = jsonArray.getJSONObject(i);
                                        // 해당 배열의 정보를 꺼내서 리사이클러뷰로 표시


                                    }

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });


            }
        });

        return view;
    }

    // post 방식으로 데이터 전송
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
