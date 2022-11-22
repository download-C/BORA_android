package com.example.borabook;

import static com.example.borabook.R.layout.*;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.RequestQueue;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;


public class Fragment2 extends Fragment {

    private AlertDialog dialog;
    SharedPreferences sp;

    // iow용 라디오 그룹과 라디오 버튼
    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;

    // 가계부 쓰기 프래그먼트
    private View view;
    private String tag = "가계부 쓰기 프래그먼트";

    // 스피너
    private Spinner groupSpinner, categorySpinner;
    private Spinner bk_yearSpinner, bk_monthSpinner, bk_daySpinner;

    // 금액, 메모
    private EditText bk_moneyEt, bk_memoEt;

    // 가계부 쓰기 버튼
    private Button writeBtn;
    // 오늘 날짜 담을 객체
    Date today;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    BookDTO book;
    DetailDTO detail;


    int bk_year;
    int bk_month;
    int bk_day;
    String bk_iow;
    String bk_group;
    String bk_category;
    String bk_memo;
    int bk_money;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//---------------------------------------정상----------------------------------------
        // 가계부 쓰기 프래그먼트 띄우기
        view = inflater.inflate(fragment2, container, false);
//---------------------------------------정상----------------------------------------
        // 오늘 날짜 불러오기
        today = new Date(System.currentTimeMillis());
        // today = yyyy-MM-dd
        Log.i(tag, format.format(today));
        int today_year = Integer.parseInt(format.format(today).substring(0, 4));
        int today_month = Integer.parseInt(format.format(today).substring(5, 7));
        int today_day = Integer.parseInt(format.format(today).substring(8, 10));
        Log.i(tag, "오늘: " + today_year + "년 " + today_month + "월 " + today_day + "일");
//---------------------------------------정상----------------------------------------
        // 수입/지출/이체 라디오
        radioGroup = view.findViewById(R.id.radioGroup);
        RadioGroup.OnCheckedChangeListener radioButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 선택한 라디오 버튼에 따라 그룹, 카테고리 어레이 설정하기
                groupSpinner = view.findViewById(R.id.group);
                categorySpinner = view.findViewById(R.id.category);
                ArrayAdapter iowAdapter;
                ArrayAdapter categoryAdapter;

                if (i == R.id.radio1) {
                    bk_iow= "수입";
                    detail.setBk_iow("수입");

                    iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income, android.R.layout.simple_spinner_dropdown_item);
                    iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(iowAdapter);

                    categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_category, android.R.layout.simple_spinner_dropdown_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);

                } else if (i == R.id.radio2) {
                    bk_iow= "지출";
                    detail.setBk_iow("지출");

                    iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume, android.R.layout.simple_spinner_dropdown_item);
                    iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(iowAdapter);

                    categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume_category, android.R.layout.simple_spinner_dropdown_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);
                } else {
                    bk_iow= "이체";
                    detail.setBk_iow("이체");

                    iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.transfer, android.R.layout.simple_spinner_dropdown_item);
                    iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(iowAdapter);

                    categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.transfer_category, android.R.layout.simple_spinner_dropdown_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);
                }
            }
        };
        radioGroup.setOnCheckedChangeListener(radioButtonChangeListener);
//---------------------------------------정상----------------------------------------
        // 연 스피너 설정
        bk_yearSpinner = view.findViewById(R.id.bk_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_yearSpinner.setAdapter(yearAdapter);

        // 작년을 기준으로 오늘의 날짜를 가져와서 인덱스 0(2021), 1(2022), 2(2023) 미리 선택
        for (int i = 0; i < 3; i++) {
            if (today_year == 2021 + i) bk_yearSpinner.setSelection(i);
        }

        // 월 스피너 설정
        bk_monthSpinner = view.findViewById(R.id.bk_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_monthSpinner.setAdapter(monthAdapter);

        // 1월을 기준으로 오늘 날짜를 가져와서 인덱스 0(1월)~11(12월) 미리 선택
        for (int i = 0; i < 12; i++) {
            if (today_month == i + 1) bk_monthSpinner.setSelection(i);
        }

        // 일 스피너 설정
        bk_daySpinner = view.findViewById(R.id.bk_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_daySpinner.setAdapter(dayAdapter);

        // 1일을 기준으로 오늘 날짜를 가져와서 인덱스 0(1일) ~30(31일) 미리 선택
        for (int i = 0; i < 31; i++) {
            if (today_day == i + 1) bk_daySpinner.setSelection(i);
        }
//---------------------------------------정상----------------------------------------
        book = new BookDTO();
        sp = getActivity().getSharedPreferences("autoLogin", Activity.MODE_PRIVATE);
        String loginID = sp.getString("loginID", "아이디없음");
        book.setId(loginID);
        Log.i("book 객체 생성", book + "");
        detail = new DetailDTO();

        Log.i("detail 객체 생성", detail + "");
//---------------------------------------정상----------------------------------------
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1 = view.findViewById(R.id.radio1);
        radio2 = view.findViewById(R.id.radio2);
        radio3 = view.findViewById(R.id.radio3);
//---------------------------------------정상----------------------------------------

        // 그룹 스피너
        groupSpinner = view.findViewById(R.id.group); // -> 이상함
        // 카테고리 스피너 -> 정상
        categorySpinner = view.findViewById(R.id.category);
        // 연/월/일 스피너 -> 정상
        bk_yearSpinner = view.findViewById(R.id.bk_year);
        bk_monthSpinner = view.findViewById(R.id.bk_month);
        bk_daySpinner = view.findViewById(R.id.bk_day);

        // 금액, 메모 에딧텍스트
        bk_moneyEt = view.findViewById(R.id.bk_money);
        bk_memoEt = view.findViewById(R.id.bk_memo); // -> 이상함

        writeBtn = view.findViewById(R.id.writeBtn);

        Log.i(tag, "onCreateView");


        // 선택한 카테고리 정보 가져오기
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bk_category = categorySpinner.getItemAtPosition(i).toString();
                Log.i("선택한 카테고리 ", bk_category);
                if(!bk_category.equals("카테고리 없음")){
                    detail.setBk_category(bk_category);
                } else {
                    detail.setBk_category("");
                }
                Log.i("set category", detail + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        // 선택한 그룹 정보 가져오기
        groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bk_group = groupSpinner.getItemAtPosition(i).toString();
                Log.i("선택한 그룹 ", bk_group.toString());
                detail.setBk_group(bk_group);
                Log.i("set group", detail + "");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });


        // 선택한 연 정보 담기 -> 정상
        bk_yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(view.getContext(), "선택한 연: " + bk_yearSpinner.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                String bk_year = bk_yearSpinner.getItemAtPosition(i).toString().substring(0, 5);
                Log.i("선택한 연 ", bk_year + "");
                book.setBk_year(Integer.parseInt(bk_year.substring(0, 4)));
                Log.i("set year", book + "");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 선택한 월 정보 담기 -> 정상
        bk_monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bk_month = Integer.toString(today_month);
                if (i <= 8) {
                    bk_month = bk_monthSpinner.getItemAtPosition(i).toString().substring(0, 1);
                } else {
                    bk_month = bk_monthSpinner.getItemAtPosition(i).toString().substring(0, 2);
                }
                book.setBk_month(Integer.parseInt(bk_month));
                Log.i("찍은 월 숫자", Integer.parseInt(bk_month) + "");
                Log.i("set month", book + "");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 선택한 일 정보 담기 -> 정상
        bk_daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bk_day = Integer.toString(today_day);
                if (i <= 8) {
                    bk_day = bk_daySpinner.getItemAtPosition(i).toString().substring(0, 1);
                } else {
                    bk_day = bk_daySpinner.getItemAtPosition(i).toString().substring(0, 2);
                }
                detail.setBk_day(Integer.parseInt(bk_day));
                Log.i("찍은 일 숫자", Integer.parseInt(bk_day) + "");
                Log.i("set day", detail + "");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 가계부 쓰기 버튼

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 유효성 체크
                if (bk_iow==null){
                    detail.setBk_money(bk_moneyEt.getText().toString());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("수입/지출/이체를 선택하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(detail.getBk_group().equals("자산 선택")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("자산을 선택하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(detail.getBk_category().equals("카테고리 선택")) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("카테고리를 선택하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if(bk_moneyEt.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("금액을 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }
                if (bk_memoEt.getText().toString().trim().equals("")){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    dialog = builder.setMessage("메모를 입력하세요.").setPositiveButton("확인", null).create();
                    dialog.show();
                    return;
                }

//                Toast.makeText(getActivity(), "글쓰기 버튼 누름", Toast.LENGTH_SHORT).show();
//---------------------------------------정상----------------------------------------


                detail.setBk_money(bk_moneyEt.getText().toString());
                detail.setBk_memo(bk_memoEt.getText().toString());
                Log.i("set memo", detail+"");



                detail.setBook(book);

                String url = "http://192.168.6.133:8088/android/write";

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            requestPost(url, detail.toString());

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }).start();

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                dialog = builder.setMessage("가계부 쓰기 성공😍").setPositiveButton("확인", null).create();
                dialog.show();

                bk_moneyEt.setText("");
                bk_memoEt.setText("");

            }
        });
        return view;
    }

    public void requestPost(String requestUrl, String data) {
        String str = null;
        BufferedWriter bw;
        StringBuffer buff = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            if (conn != null) {
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json; UTF-8");
                conn.setConnectTimeout(5000); // 연결 타임아웃 설정(5초)
                conn.setReadTimeout(5000); // 읽기 타임아웃 설정(5초)
                conn.setDoOutput(true);

               // conn.setFixedLengthStreamingMode(data.length());

                OutputStream os = conn.getOutputStream();
                byte[] input = data.getBytes(StandardCharsets.UTF_8);
                os.write(input, 0, input.length);

//                BufferedOutputStream out = new BufferedOutputStream(conn.getOutputStream());
//                bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
//                Log.i("가계부 data", data+"");
//                bw.write(data.toString());
                os.flush();
                int code = conn.getResponseCode();
                Log.i("responseCode", code+"");
                os.close();
                conn.disconnect();
            }
        } catch (Exception e) {
//            tv1.append(e.getLocalizedMessage());
            Log.i("POST로 보내기 에러", e.getLocalizedMessage());
        }

    }



}
