package com.example.borabook;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Date;


public class Fragment2 extends Fragment {
    ArrayAdapter<CharSequence> adapter;

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
    String bk_group;
    String bk_category;
    String bk_memo;
    int bk_money;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//---------------------------------------정상----------------------------------------
        // 가계부 쓰기 프래그먼트 띄우기
        view = inflater.inflate(R.layout.fragment2, container, false);
//---------------------------------------정상----------------------------------------
        // 오늘 날짜 불러오기
        today = new Date(System.currentTimeMillis());
        // today = yyyy-MM-dd
        Log.i(tag, format.format(today));
        int today_year = Integer.parseInt(format.format(today).substring(0,4));
        int today_month = Integer.parseInt(format.format(today).substring(5,7));
        int today_day = Integer.parseInt(format.format(today).substring(8,10));
        Log.i(tag,"오늘: "+today_year+"년 "+today_month+"월 "+today_day+"일");
//---------------------------------------정상----------------------------------------
        // 수입/지출/이체 라디오
        radioGroup = view.findViewById(R.id.radioGroup);
        RadioGroup.OnCheckedChangeListener radioButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 선택한 라디오 버튼에 따라 그룹, 카테고리 어레이 설정하기
                groupSpinner = view.findViewById(R.id.iow);
                categorySpinner = view.findViewById(R.id.category);
                ArrayAdapter iowAdapter;
                ArrayAdapter categoryAdapter;

                if(i == R.id.radio1) {
                    detail.setBk_iow("수입");

                    iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income, android.R.layout.simple_spinner_dropdown_item);
                    iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(iowAdapter);

                    categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_category, android.R.layout.simple_spinner_dropdown_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);

                } else if(i==R.id.radio2) {
                    detail.setBk_iow("지출");

                    iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume, android.R.layout.simple_spinner_dropdown_item);
                    iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    groupSpinner.setAdapter(iowAdapter);

                    categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume_category, android.R.layout.simple_spinner_dropdown_item);
                    categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    categorySpinner.setAdapter(categoryAdapter);
                } else {
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
        for (int i=0; i<3; i++) {
            if(today_year==2021+i) bk_yearSpinner.setSelection(i);
        }

        // 월 스피너 설정
        bk_monthSpinner = view.findViewById(R.id.bk_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R. array.bk_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_monthSpinner.setAdapter(monthAdapter);

        // 1월을 기준으로 오늘 날짜를 가져와서 인덱스 0(1월)~11(12월) 미리 선택
        for(int i=0; i<12; i++){
            if(today_month==i+1) bk_monthSpinner.setSelection(i);
        }

        // 일 스피너 설정
        bk_daySpinner = view.findViewById(R.id.bk_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_daySpinner.setAdapter(dayAdapter);

        // 1일을 기준으로 오늘 날짜를 가져와서 인덱스 0(1일) ~30(31일) 미리 선택
        for(int i=0; i<31; i++) {
            if(today_day==i+1) bk_daySpinner.setSelection(i);
        }
//---------------------------------------정상----------------------------------------
        book = new BookDTO();
        Log.i("book 객체 생성", book+"");
        detail = new DetailDTO();
        Log.i("detail 객체 생성", detail+"");
//---------------------------------------정상----------------------------------------
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1  = view.findViewById(R.id.radio1);
        radio2  = view.findViewById(R.id.radio2);
        radio3  = view.findViewById(R.id.radio3);
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

        Log.i(tag, "onCreateView");



        // 선택한 카테고리 정보 가져오기
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                bk_category = categorySpinner.getItemAtPosition(i).toString();
                Log.i("선택한 카테고리 ", bk_category);
                detail.setBk_category(bk_category);
                Log.i("set category", detail+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }

        });

        // 선택한 그룹 정보 가져오기
        if (groupSpinner != null) {
            groupSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    bk_group = groupSpinner.getItemAtPosition(i).toString();
                    Log.i("선택한 그룹 ", bk_group);
                    detail.setBk_group(bk_group);
                    Log.i("set group", detail + "");
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }

            });
        }

        // 선택한 연 정보 담기
        bk_yearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(view.getContext(), "선택한 연: "+bk_yearSpinner.getItemAtPosition(i), Toast.LENGTH_SHORT).show();
                String bk_year = bk_yearSpinner.getItemAtPosition(i).toString().substring(0,5);
                Log.i("선택한 연 ", bk_year+"");
                book.setBk_year(Integer.parseInt(bk_year.substring(0,4)));
                Log.i("set year", book+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        // 선택한 월 정보 담기
        bk_monthSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bk_month = Integer.toString(today_month);
                if(i<=8) {
                    bk_month = bk_monthSpinner.getItemAtPosition(i).toString().substring(0, 1);
                } else {
                    bk_month = bk_monthSpinner.getItemAtPosition(i).toString().substring(0,2);
                }
                book.setBk_month(Integer.parseInt(bk_month));
                Log.i("찍은 월 숫자", Integer.parseInt(bk_month)+"");
                Log.i("set month", book+"");
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        // 선택한 일 정보 담기
        bk_daySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String bk_day = Integer.toString(today_day);
                if(i<=8) {
                    bk_day = bk_daySpinner.getItemAtPosition(i).toString().substring(0, 1);
                } else {
                    bk_day = bk_daySpinner.getItemAtPosition(i).toString().substring(0,2);
                }
                detail.setBk_day(Integer.parseInt(bk_day));
                Log.i("찍은 일 숫자", Integer.parseInt(bk_day)+"");
                Log.i("set day", detail+"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });


        // 가계부 쓰기 버튼
        writeBtn = view.findViewById(R.id.writeBtn);

        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int bk_money = Integer.parseInt(bk_moneyEt.getText().toString());
                detail.setBk_money(bk_money);
                if(bk_memoEt != null) {
                    if (bk_memoEt.getText().toString() != null) {
                        bk_memo = bk_memoEt.getText().toString();
                    }
                }
                Log.i("돈이랑 메모", bk_money+ " "+bk_memo);
                Log.i("가계부 입력 정보 "  ,book+" ////// "+detail);
                detail.setBook(book);
                Log.i("가계부 입력 정보 "  ,"///"+detail);
            }
        });


        return view;
    }

}
