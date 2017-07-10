package com.example.cashbook.history_bill;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_bill);

        //隐藏ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }


        initBills();
        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.history_bill);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        HistoryBillAdapter adapter = new HistoryBillAdapter(billList);
        recyclerView.setAdapter(adapter);

        delete = (Button) findViewById(R.id.history_delete);
        backward = (Button) findViewById(R.id.history_backward);

        //用于清空数据库
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryBillActivity.this, "Delete database", Toast.LENGTH_SHORT).show();
                DataSupport.deleteAll(Consumption.class);

            }
        });

        //返回上一界面
        backward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(HistoryBillActivity.this, "Backward", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

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
