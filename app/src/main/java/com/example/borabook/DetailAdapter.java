package com.example.borabook;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> implements OnDetailItemClickListener{

    List<DetailDTO> list = new ArrayList<>();
    OnDetailItemClickListener listener;



    static class ViewHolder extends RecyclerView.ViewHolder {

        TextView iow, group, category, money, memo;

        public ViewHolder(@NonNull View itemView, OnDetailItemClickListener listener) {
            super(itemView);
            iow= itemView.findViewById(R.id.recycle_iow);
            group = itemView.findViewById(R.id.recycle_group);
            category = itemView.findViewById(R.id.recycle_category);
            money = itemView.findViewById(R.id.recycle_money);
            memo = itemView.findViewById(R.id.recycle_memo);
        } // ViewHolder 생성자

        public void setItem(DetailDTO dto) {
            iow.setText(dto.getBk_iow());
            group.setText(dto.getBk_group());
            category.setText(dto.getBk_category());
            money.setText(dto.getBk_money());
            memo.setText(dto.getBk_memo());
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.detail_item, parent, false);
        return new ViewHolder(view, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       DetailDTO item = list.get(position);
       holder.setItem(item);
    }

    @Override
    public int getItemCount() {
        Log.i("리스트 사이즈", ""+list.size());
        return list.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {

    }

    public void addItems(List<DetailDTO> items) {
        this.list = items;
    }

    public void addItem(DetailDTO item) {
        list.add(item);
    }

    public DetailDTO getItem(int position) {
        return list.get(position);
    }

    public void setItem(int position, DetailDTO item) {
        list.set(position, item);
    }


}
