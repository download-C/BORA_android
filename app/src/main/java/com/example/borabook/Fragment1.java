package com.example.borabook;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.AsyncListDiffer;

import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;


public class Fragment1 extends Fragment {
    // 사용할 변수 초기화
    private View view;
    private String tag = "목록 프래그먼트";
    private MaterialCalendarView calView;

    private Button logoutBtn;
    int selectYear, selectMonth, selectDay;
    DecimalFormat myFormatter = new DecimalFormat("###,###");
    SharedPreferences sp;

    ListView listView;
    DetailDTO detail;
    DetailAdapter adapter;

    // 달력이 있는 프래그먼트 생성
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.i(tag, "onCreateView");
        view = inflater.inflate(R.layout.fragment1, container, false);

        listView = view.findViewById(R.id.listView);

        // 로그아웃
        logoutBtn = view.findViewById(R.id.logoutBtn);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("로그아웃").setMessage("로그아웃 하시겠습니까?")
                        .setPositiveButton("로그아웃", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                SharedPreferences sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                                SharedPreferences.Editor spEdit = sp.edit();
                                spEdit.clear();
                                spEdit.commit();

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                startActivity(intent);
                            }
                        })
                        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .show();
                SharedPreferences sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                SharedPreferences.Editor spEditor = sp.edit();
                spEditor.remove("autoLogin");
                spEditor.commit();
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

                // 선택한 연/월/일과 사용자 아이디를 파라미터로 담아서 서버 호출
                sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
                String id = sp.getString("loginID", "아이디없음");

                List<DetailDTO> detailList = new ArrayList<>();
                Log.i("여기까지?", "잘됨");
                // 달력 특정 날짜 선택 시 해당 날짜의 가계부 정보 불러오기
                new Thread(new Runnable() {
                @Override
                    public void run() {
                        String urlpath = "http://192.168.6.133:8088/android/get/"+id+"/"+selectYear+"/"+selectMonth+"/"+selectDay;
                        final String str = requestPost(urlpath);
                        if (str != "") {
                            try {
                                JSONArray jsonArray = new JSONArray(str);

                                if(jsonArray!=null) {
                                    for(int i=0; i<jsonArray.length(); i++) {
                                        detail = new DetailDTO();
                                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                                        Log.i("인덱스 오브젝트"+i, jsonObject+"");
                                        String bk_iow = jsonObject.getString("bk_iow");
                                        Log.i("iow 가져왔니?",bk_iow);
                                        detail.setBk_iow(bk_iow);
                                        String bk_group = jsonObject.getString("bk_group");
                                        String bk_category = jsonObject.getString("bk_category");
                                        int bk_money = jsonObject.getInt("bk_money");
                                        String bk_memo = jsonObject.getString("bk_memo");
                                        detail.setBk_group(bk_group);
                                        detail.setBk_category(bk_category);
                                        detail.setBk_money(myFormatter.format(bk_money));
                                        detail.setBk_memo(bk_memo);
//                                        detailList.add(String.format("%-10s%-15s%-5s%-20s%-20s",bk_iow, bk_group,bk_category, bk_money, bk_memo));
//                                        Log.i("포맷팅된 정보"+i, detailList.get(i)+"");
                                        detailList.add(detail);

                                    }
                                    if(detailList.size()>0) {
                                        Log.i("가계부 목록", detailList.toString());
                                        Log.i("가계부 목록 띄우기", "시작");

                                        adapter = new DetailAdapter(getActivity(), (ArrayList<DetailDTO>) detailList);

                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listView.setAdapter(adapter);
                                            }
                                        });
                                    }else {
                                        Log.i("가계부 정보 없음", "일루와");
                                        detail = new DetailDTO();
                                        detail.setBk_iow("아직");
                                        detail.setBk_group("가계부가");
                                        detail.setBk_category("없네요😅");
                                        detail.setBk_money("가계부를");
                                        detail.setBk_memo("한 번 써보세요!");
                                        detailList.add(detail);
                                        adapter = new DetailAdapter(getActivity(), (ArrayList<DetailDTO>) detailList);
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                listView.setAdapter(adapter);
                                            }
                                        });

                                    }

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }).start();

            }

        });

        return view;
    }
    public String requestPost(String requestUrl) {
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
