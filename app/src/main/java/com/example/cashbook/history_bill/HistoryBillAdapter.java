package com.example.cashbook.history_bill;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cashbook.Consumption;
import com.example.cashbook.R;

import org.litepal.crud.DataSupport;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class HistoryBillAdapter extends RecyclerView.Adapter<HistoryBillAdapter.ViewHolder> {

    private List<HistoryBill_oneRecord> mHistory;

    public HistoryBillAdapter(List<HistoryBill_oneRecord> billList) {
        mHistory = billList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_bill_item
                , parent, false);
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final HistoryBill_oneRecord bill = mHistory.get(position);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        holder.time.setText(format.format(bill.getTime())); //yyyy-MM-dd格式
//        holder.time.setText(bill.getTime().toString()); //标准格式
        holder.kindImg.setImageResource(bill.getImageId());
        holder.kindName.setText(bill.getName());
        holder.money.setText(String.format(Locale.CHINA, "%.2f", bill.getMoney()) + "￥");
    }

    @Override
    public int getItemCount() {
        return mHistory.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        View itemView;
        TextView time;
        ImageView kindImg;
        TextView kindName;
        TextView money;

        private ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            time = (TextView) itemView.findViewById(R.id.bill_time);
            kindImg = (ImageView) itemView.findViewById(R.id.bill_img);
            kindName = (TextView) itemView.findViewById(R.id.bill_kind);
            money = (TextView) itemView.findViewById(R.id.bill_money);

            //删除单条记录
            Button deleteOne = (Button) itemView.findViewById(R.id.deleteOne);
            deleteOne.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    HistoryBill_oneRecord bill = mHistory.get(position);

                    DataSupport.delete(Consumption.class, bill.getId());
                    mHistory.remove(position);
                    notifyItemRemoved(position);
                }
            });
        }
    }
}
