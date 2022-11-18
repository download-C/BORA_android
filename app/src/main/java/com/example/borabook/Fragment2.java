package com.example.borabook;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Array;


public class Fragment2 extends Fragment {
    ArrayAdapter<CharSequence> adapter;

    RadioGroup radioGroup;
    RadioButton radio1, radio2, radio3;
    private View view;
    private String tag = "가계부 쓰기 프래그먼트";
    private Spinner iow, category;
    private Spinner bk_year, bk_month, bk_day;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i(tag, "onCreateView");

        view = inflater.inflate(R.layout.fragment2, container, false);
        radioGroup = view.findViewById(R.id.radioGroup);
        radio1  = view.findViewById(R.id.radio1);
        radio2  = view.findViewById(R.id.radio2);
        radio3  = view.findViewById(R.id.radio3);

        radioGroup = view.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(radioButtonChangeListener);

        // 연 스피너 설정
        bk_year = view.findViewById(R.id.bk_year);
        ArrayAdapter yearAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_year, android.R.layout.simple_spinner_item);
        yearAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_year.setAdapter(yearAdapter);

        // 월 스피너 설정
        bk_month = view.findViewById(R.id.bk_month);
        ArrayAdapter monthAdapter = ArrayAdapter.createFromResource(getActivity(), R. array.bk_month, android.R.layout.simple_spinner_item);
        monthAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_month.setAdapter(monthAdapter);

        // 일 스피너 설정
        bk_day = view.findViewById(R.id.bk_day);
        ArrayAdapter dayAdapter = ArrayAdapter.createFromResource(getActivity(), R.array.bk_day, android.R.layout.simple_spinner_item);
        dayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        bk_day.setAdapter(dayAdapter);

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
