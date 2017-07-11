package com.example.cashbook.history_bill;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.cashbook.Consumption;
import com.example.cashbook.R;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

public class HistoryBillActivity extends AppCompatActivity {

    private List<HistoryBill_oneRecord> billList = new ArrayList<>();

    private Button delete, backward;

    private HistoryBillAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);

        hideActionBar(); //隐藏ActionBar

        initBills();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_bill);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new HistoryBillAdapter(billList);
        recyclerView.setAdapter(adapter);

        delete = (Button) findViewById(R.id.history_delete);
        backward = (Button) findViewById(R.id.history_backward);

        deleteAllButton();//用于清空数据库

        backButton(); //返回上一界面
    }

    private void backButton() {
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryBillActivity.this, "Backward", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void deleteAllButton() {
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(HistoryBillActivity.this);
                dialog.setTitle("确定要删除所有支出信息么？");
                dialog.setMessage("一旦确认删除，支出信息将无法恢复");
                dialog.setCancelable(false); //无法通过点击外部取消
                dialog.setPositiveButton("删除", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        DataSupport.deleteAll(Consumption.class);

                        billList.clear();
                        adapter.notifyDataSetChanged();

                        Toast.makeText(HistoryBillActivity.this, "删除完毕", Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }

    private void hideActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    //从数据库中读取数据，初始化billList
    private void initBills() {
        List<Consumption> consumptionList = DataSupport.findAll(Consumption.class);
        for(Consumption consumption : consumptionList){
            HistoryBill_oneRecord bill = new HistoryBill_oneRecord(consumption.getDate(),
                    consumption.getImgId(), consumption.getKind(), consumption.getMoney());
            billList.add(bill);
        }
    }
}
