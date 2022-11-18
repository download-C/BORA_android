package com.example.borabook;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;
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
    private Spinner iow, category;
    private Spinner bk_year, bk_month, bk_day;

    // 가계부 쓰기 버튼
    private Button writeBtn;
    // 오늘 날짜 담을 객체
    Date today;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(tag, "onCreateView");

        // 가계부 쓰기 프래그먼트
        view = inflater.inflate(R.layout.fragment2, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1  = view.findViewById(R.id.radio1);
        radio2  = view.findViewById(R.id.radio2);
        radio3  = view.findViewById(R.id.radio3);

        // 수입/지출/이체 라디오
        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioButtonChangeListener);

        // 가계부 쓰기 버튼
        writeBtn = view.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "가계부 쓰기 버튼 클릭", Toast.LENGTH_SHORT).show();
            }
        });

        // 오늘 날짜 불러오기
        today = new Date(System.currentTimeMillis());
        // today = yyyy-MM-dd
        Log.i(tag, format.format(today));
        int today_year = Integer.parseInt(format.format(today).substring(0,4));
        int today_month = Integer.parseInt(format.format(today).substring(5,7));
        int today_day = Integer.parseInt(format.format(today).substring(8,10));
        Log.i(tag,"오늘: "+today_year+"년 "+today_month+"월 "+today_day+"일");

        // 연 스피너 설정
        bk_year = view.findViewById(R.id.bk_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_year.setAdapter(yearAdapter);

        // 작년을 기준으로 오늘의 날짜를 가져와서 인덱스 0(2021), 1(2022), 2(2023) 미리 선택
        for (int i=0; i<3; i++) {
            if(today_year==2021+i) bk_year.setSelection(i);
        }

        // 월 스피너 설정
        bk_month = view.findViewById(R.id.bk_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R. array.bk_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_month.setAdapter(monthAdapter);

        // 1월을 기준으로 오늘 날짜를 가져와서 인덱스 0(1월)~11(12월) 미리 선택
        for(int i=0; i<12; i++){
            if(today_month==i+1) bk_month.setSelection(i);
        }

        // 일 스피너 설정
        bk_day = view.findViewById(R.id.bk_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_day.setAdapter(dayAdapter);

        // 1일을 기준으로 오늘 날짜를 가져와서 인덱스 0(1일) ~30(31일) 미리 선택
        for(int i=0; i<31; i++) {
            if(today_day==i+1) bk_day.setSelection(i);
        }

        return view;
    }

    // 라디오 버튼 선택
    RadioButton.OnClickListener radioButtonClickListener = new RadioButton.OnClickListener(){
        @Override
        public void onClick(View view) {
            Toast.makeText(getActivity(), "radio1: "+radio1.isChecked() +", radio2: " +radio2.isChecked()
                    +", radio3: "+radio3.isChecked(), Toast.LENGTH_SHORT).show();
        }
    };

    RadioGroup.OnCheckedChangeListener radioButtonChangeListener = new RadioGroup.OnCheckedChangeListener() {

        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {
            iow = view.findViewById(R.id.iow);
            category = view.findViewById(R.id.category);
            ArrayAdapter iowAdapter;
            ArrayAdapter categoryAdapter;

            if(i == R.id.radio1) {
                Toast.makeText(getActivity(), "수입 선택", Toast.LENGTH_SHORT).show();

                iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income, android.R.layout.simple_spinner_dropdown_item);
                iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                iow.setAdapter(iowAdapter);

                categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.income_category, android.R.layout.simple_spinner_dropdown_item);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);

            } else if(i==R.id.radio2) {
                Toast.makeText(getActivity(), "지출 선택", Toast.LENGTH_SHORT).show();
                iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume, android.R.layout.simple_spinner_dropdown_item);
                iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                iow.setAdapter(iowAdapter);

                categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.consume_category, android.R.layout.simple_spinner_dropdown_item);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);
            } else {
                Toast.makeText(getActivity(), "이체 선택", Toast.LENGTH_SHORT).show();
                iowAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.transfer, android.R.layout.simple_spinner_dropdown_item);
                iowAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                iow.setAdapter(iowAdapter);

                categoryAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.transfer_category, android.R.layout.simple_spinner_dropdown_item);
                categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                category.setAdapter(categoryAdapter);
            }

        }
    };



}
