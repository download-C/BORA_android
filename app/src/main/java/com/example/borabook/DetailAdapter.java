package com.example.borabook;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends BaseAdapter {

    Context dContext = null;
    LayoutInflater dLayoutInflater = null;
    ArrayList<DetailDTO> detailList;

    public DetailAdapter(Context context, ArrayList<DetailDTO> detailList) {
        dContext = context;
        this.detailList = detailList;
        dLayoutInflater = LayoutInflater.from(dContext);
    }

    @Override
    public int getCount() {
        return detailList.size();
    }

    @Override
    public Object getItem(int i) {
        return detailList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = dLayoutInflater.inflate(R.layout.detail_list, viewGroup, false);
        TextView iow, group, category, money, memo;
        iow= view.findViewById(R.id.list_iow);
        group = view.findViewById(R.id.list_group);
        category = view.findViewById(R.id.list_category);
        money = view.findViewById(R.id.list_money);
        memo = view.findViewById(R.id.list_memo);

        iow.setText(detailList.get(i).getBk_iow());
        group.setText(detailList.get(i).getBk_group());
        category.setText(detailList.get(i).getBk_category());
        money.setText(detailList.get(i).getBk_money());
        memo.setText(detailList.get(i).getBk_memo());

        return view;
    }
}
