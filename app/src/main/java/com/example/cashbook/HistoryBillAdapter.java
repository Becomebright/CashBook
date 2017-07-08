package com.example.cashbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class HistoryBillAdapter extends RecyclerView.Adapter<HistoryBillAdapter.ViewHolder> {

    private List<HistoryBill_oneRecord> mHistory;

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView time;
        ImageView kindImg;
        TextView kindName;
        TextView money;

        private ViewHolder(View itemView) {
            super(itemView);
            time = (TextView) itemView.findViewById(R.id.bill_time);
            kindImg = (ImageView) itemView.findViewById(R.id.bill_img);
            kindName = (TextView) itemView.findViewById(R.id.bill_kind);
            money = (TextView) itemView.findViewById(R.id.bill_money);
        }
    }

    public HistoryBillAdapter(List<HistoryBill_oneRecord> mHistory) {
        this.mHistory = mHistory;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_bill_item
                , parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        HistoryBill_oneRecord bill = mHistory.get(position);
        holder.time.setText(bill.getTime().toString());
        holder.kindImg.setImageResource(bill.getImageId());
        holder.kindName.setText(bill.getName());
        holder.money.setText(Double.toString(bill.getMoney()) + "￥");

    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }
}
