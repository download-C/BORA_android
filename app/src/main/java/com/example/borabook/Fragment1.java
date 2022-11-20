package com.example.borabook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.LinearLayoutManager;
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
import java.util.List;


public class Fragment1 extends Fragment {

    // 사용할 변수 초기화
    private View view;
    private String tag = "목록 프래그먼트";
    private MaterialCalendarView calView;
    private RecyclerView detailView;
    private RecyclerView.Adapter detailAdapter;
    private Button logoutBtn;
    private List<DetailDTO> list;

    int selectYear, selectMonth, selectDay;

    // 달력이 있는 프래그먼트 생성
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(tag, "onCreateView");
        view = inflater.inflate(R.layout.fragment1, container, false);
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.remove("autoLogin");
                editor.apply();
                Log.i("로그아웃 버튼", "로그아웃 성공");
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        // 프래그먼트의 달력 호출
        calView = view.findViewById(R.id.calendarView);
        // 달력에서 선택한 날의 연/월/일 가져오기 
        calView.setOnDateChangedListener(new OnDateSelectedListener() {
            @Override
            public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {

                selectYear = date.getYear();
                selectMonth = date.getMonth()+1;
                selectDay = date.getDay();

                Toast.makeText(getActivity(), selectYear+"년 "+selectMonth+"월 "+selectDay+"일 선택", Toast.LENGTH_SHORT).show();
                // 사용자가 선택한 날짜의 가계부 목록 서버에서 불러오기

                // 저장한 정보 가져오는 방법
                // 1. SP sp = getSP("데이터이름","Activity모드");
                // 2. String 데이터 =  sp.getString("데이터이름", "데이터가 없을 때 설정할 기본값");

                // 데이터를 보내기 위한 로그인 아이디 불러오기
                SharedPreferences sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                String loginID = sp.getString("loginID", "아이디 없음");
                Log.i("아이디 있나요?",loginID);

                // 데이터 보내기
               new Thread(new Runnable() {
                    @Override
                    public void run() {
                        // 가계부 목록 요청에 필요한 내용을 주소에 담아 보내기
                        String urlpath = "http://192.168.35.136:8088/android/get/"+loginID+"/"+selectYear+"/"+selectMonth+"/"+selectDay;
                        Log.i("요청 주소", urlpath);
                        final String data = requestPOST(urlpath);
                        // 요청 후 돌아온 데이터가 있을 때
                        Log.i("데이터", data);
                        if(data != "") {
                            try {
                                // 제이슨 데이터에서 보내는 배열의 제목을 어레이에 담음
                                JSONArray list = new JSONArray(data);
                                Log.i("제이슨", "제이슨 어레이 성공");
                                if(list!=null) {
                                   // 어레이를 하나씩 오브젝트로 꺼냄
                                    detailView = view.findViewById(R.id.detailList);
                                    Log.i("디테일 뷰는?", detailView+"");
                                    detailAdapter = new DetailAdapter();
                                    Log.i("디테일 어댑터는?", detailAdapter+"");
                                    detailView.setAdapter(detailAdapter);
                                    detailView.setLayoutManager(new LinearLayoutManager(view.getContext()));

                                    for(int i=0; i<list.length(); i++) {
                                        JSONObject detailObj = list.getJSONObject(i);
                                        Log.i("어레이 뽑기"+i, detailObj.toString());

                                        DetailDTO dto = new DetailDTO();
                                        dto.setBk_iow(detailObj.getString("bk_iow"));
                                        dto.setBk_category(detailObj.getString("bk_category"));
                                        dto.setBk_group(detailObj.getString("bk_group"));
                                        dto.setBk_memo(detailObj.getString("bk_memo"));
                                        dto.setBk_money(detailObj.getInt("bk_money"));

                                        list.put(dto);

                                    }
//                                    detailAdapter.set

                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }).start();
            }//onDateSelected
        });

        return view;
    }

    // GET 방식으로 데이터 전송
    public String requestPOST(String requestUrl) {
        String str = null;
        BufferedReader br;
        StringBuffer buff = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestMethod("GET");
                conn.setDoInput(true);
                conn.setDoOutput(true);
                int code = conn.getResponseCode();
                    br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                str = br.readLine();
                while (str != null) {
                    buff.append(str);
                    Log.i("POST 성공", str);
                    str = br.readLine();
                }
                br.close();
                conn.disconnect();
            }
        } catch (Exception e) {
//            tv1.append(e.getLocalizedMessage());
            e.printStackTrace();
        }
        return buff.toString();
    }


}
