package com.example.borabook;

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

    List<DetailDTO> items = new ArrayList<>();
    OnDetailItemClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public DetailAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        // 부모 뷰 그룹에서 해당 내용 가져오기
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.detail_item, viewGroup, false);
        return new DetailAdapter.ViewHolder(itemView,this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

    }

    public int getItemCount() {
        return items.size();
    }

    public void addItem(DetailDTO item) {
        items.add(item);
    }

    public void setItems(List<DetailDTO> items) {
        this.items = items;
    }

    public DetailDTO getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnDetailItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener!=null) {
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout1;
        TextView iowView;
        TextView moneyView;
        TextView memoView;

        public ViewHolder(View itemView, final OnDetailItemClickListener listener, int layoutType) {
            super(itemView);

            layout1 = itemView.findViewById(R.id.layout1);
            iowView = itemView.findViewById(R.id.iow);
            moneyView = itemView.findViewById(R.id.money);
            memoView = itemView.findViewById(R.id.memo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if(listener != null) {

                    }
                }
            });
        }

        public void setItem(DetailDTO item) {
            iowView .setText(item.getBk_iow());
            moneyView.setText(item.getBk_money());
            memoView.setText(item.getBk_memo());
        }
    }




}
