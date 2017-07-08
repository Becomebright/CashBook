package com.example.cashbook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.idescout.sql.SqlScoutServer;

import org.litepal.crud.DataSupport;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();

    private List<Choice> choiceList = new ArrayList<>();

    private EditText inputText;

    private Button send, history;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //隐藏ActionBar
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }

        //查看历史账单
        history = (Button) findViewById(R.id.title_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryBillActivity.class);
                startActivity(intent);
            }
        });

        //设置, 还未实现

        //  读取丢失输入
        inputText = (EditText) findViewById(R.id.input_text);
        String input = load();
        if(!TextUtils.isEmpty(input)){
            inputText.setText(input);
            inputText.setSelection(input.length());
        }

        initChoices();

        initMsg();
        send = (Button) findViewById(R.id.send);
        msgRecyclerView =(RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();

                /*//接收imgId数据
                Intent intent = getIntent();
                int imgId = intent.getIntExtra("imgId", -1);
                Log.e("MainActivity", String.valueOf(imgId));*/

                //储存聊天信息
                if(!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");

                    //储存数据至数据库
                    saveToDatabase(content);
                }
            }
        });

        loadChoices();
        RecyclerView choiceView = (RecyclerView) findViewById(R.id.choice);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 3);
        choiceView.setLayoutManager(layoutManager2);
        ChoiceAdapter choiceAdapter = new ChoiceAdapter(choiceList, (EditText) findViewById(R.id.input_text));
        choiceView.setAdapter(choiceAdapter);
    }

    //储存数据至数据库
    private void saveToDatabase(String content) {
        java.util.Date currentDate = getCurrentDate();
        int imgId;
        String kind;
        double money;
        Consumption consumption = new Consumption();

        String[] strs = content.split("\\|");
        if(strs.length != 2) Log.e("MainActivity", "split wrong");
        else{
            kind = getString_without_space(strs[0]);
            List<Choice> choices = DataSupport.where("name = ?", kind).find(Choice.class);
            imgId = choices.get(0).getImageId();

            money = Double.valueOf(strs[1]);

            consumption.setKind(kind);
            consumption.setMoney(money);
            consumption.setDate(currentDate);
            consumption.setImgId(imgId);
            consumption.saveThrows();
        }
    }

    private String getString_without_space(String str) {
        int len = str.length();
        int st = 0;
        while(st < len && str.charAt(st) == ' ') st++;
        while(st < len && str.charAt(len-1) ==' ') len--;
        return ((st > 0) || (len < str.length())) ? str.substring(st, len) : str;
    }

    private java.util.Date getCurrentDate() {
        /*SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString =  formatter.format(currentDate);
        ParsePosition pos = new ParsePosition(8);
        Date currentDate_2 = (Date) formatter.parse(dateString, pos);
        return currentDate_2;*/
        return new java.util.Date();
    }

    //返回储存的输入
    private String load() {
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try {
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        String input = inputText.getText().toString();
        save(input);
    }

    //储存输入
    private void save(String input) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try{
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(input);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try{
                if(writer != null){
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void initChoices(){
        DataSupport.deleteAll(Choice.class);
        //1
        Choice choice = new Choice("一般", R.drawable.general);
        choice.save();
        //2
        choice = new Choice("用餐", R.drawable.food);
        choice.save();
        //3
        choice = new Choice("交通", R.drawable.traffic);
        choice.save();
        //4
        choice = new Choice("水果", R.drawable.fruit);
        choice.save();
        //5
        choice = new Choice("服饰", R.drawable.clothes);
        choice.save();
        //6
        choice = new Choice("日用品", R.drawable.commodity); //日用品
        choice.save();
        //7
        choice = new Choice("娱乐", R.drawable.entertainment);
        choice.save();
        //8
        choice = new Choice("零食", R.drawable.snacks);
        choice.save();
        //9
        choice = new Choice("学习", R.drawable.learning);
        choice.save();
        //10
        choice = new Choice("医疗", R.drawable.medical);
        choice.save();
    }

    private void loadChoices() {
        choiceList = DataSupport.findAll(Choice.class);
    }

    private void initMsg() {
        Msg msg1 = new Msg("零食 20", Msg.TYPE_SENT);
        msgList.add(msg1);
        Msg msg2 = new Msg("少吃点，记得减肥！", Msg.TYPE_RECEIVED);
        msgList.add(msg2);
        Msg msg3 = new Msg("交通 20",Msg.TYPE_SENT);
        msgList.add(msg3);
        Msg msg4 = new Msg("注意交通安全", Msg.TYPE_RECEIVED);
        msgList.add(msg4);
    }

}
