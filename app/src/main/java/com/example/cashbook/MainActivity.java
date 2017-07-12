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

import com.example.cashbook.chart.ChartActivity;
import com.example.cashbook.choice.Choice;
import com.example.cashbook.choice.ChoiceAdapter;
import com.example.cashbook.history_bill.HistoryBillActivity;
import com.example.cashbook.message.Msg;
import com.example.cashbook.message.MsgAdapter;
import com.example.cashbook.message.ReceiveMsg;
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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private List<Msg> msgList = new ArrayList<>();
    private List<Choice> choiceList = new ArrayList<>();
    private EditText inputText;
    private Button send, history, graph, setting;
    private RecyclerView msgRecyclerView;
    private MsgAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SqlScoutServer.create(this, getPackageName());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hideActionBar(); //隐藏ActionBar

        reloadMessage(); //  读取丢失输入

//        if(!DataSupport.isExist(Choice.class))
            initChoices(); //初始化kind选项

        loadChoices(); //加载kind选项

        initView(); //加载消息框

        initMsg(); //预设对话

        sendButton(); //发送消息

        historyButton(); //查看历史账单

        settingButton(); //设置, 还未实现

        graphButton(); //报表

        try {
            test_insertData();
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    private void test_insertData() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date date;

//        DataSupport.deleteAll(Consumption.class); //清空数据库
        if(DataSupport.count(Consumption.class)==0) {
            Consumption consumption = new Consumption();
            date = format.parse("2017-07-01");
            consumption.setDate(date);
            consumption.setMoney(10);
            consumption.setImgId(R.drawable.clothes);
            consumption.setKind("服饰");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-02");
            consumption.setDate(date);
            consumption.setMoney(20);
            consumption.setImgId(R.drawable.housing);
            consumption.setKind("住房");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-03");
            consumption.setDate(date);
            consumption.setMoney(18);
            consumption.setImgId(R.drawable.commodity);
            consumption.setKind("日用品");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-04");
            consumption.setDate(date);
            consumption.setMoney(36);
            consumption.setImgId(R.drawable.learning);
            consumption.setKind("学习");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-05");
            consumption.setDate(date);
            consumption.setMoney(17.2f);
            consumption.setImgId(R.drawable.mobile);
            consumption.setKind("通讯");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-06");
            consumption.setDate(date);
            consumption.setMoney(50);
            consumption.setImgId(R.drawable.entertainment);
            consumption.setKind("娱乐");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-07");
            consumption.setDate(date);
            consumption.setMoney(27);
            consumption.setImgId(R.drawable.food);
            consumption.setKind("用餐");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-08");
            consumption.setDate(date);
            consumption.setMoney(11);
            consumption.setImgId(R.drawable.fruit);
            consumption.setKind("水果");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-09");
            consumption.setDate(date);
            consumption.setMoney(30.5f);
            consumption.setImgId(R.drawable.traffic);
            consumption.setKind("交通");
            consumption.save();
            consumption = new Consumption();
            date = format.parse("2017-07-10");
            consumption.setDate(date);
            consumption.setMoney(30.5f);
            consumption.setImgId(R.drawable.snacks);
            consumption.setKind("零食");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-11");
            consumption.setDate(date);
            consumption.setMoney(30.5f);
            consumption.setImgId(R.drawable.general);
            consumption.setKind("一般");
            consumption.save();

            consumption = new Consumption();
            date = format.parse("2017-07-12");
            consumption.setDate(date);
            consumption.setMoney(30.5f);
            consumption.setImgId(R.drawable.medical);
            consumption.setKind("医疗");
            consumption.save();
        }
    }

    private void graphButton() {
        graph = (Button) findViewById(R.id.title_graph);
        graph.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChartActivity.class);
                startActivity(intent);
            }
        });
    }

    private void sendButton() {
        send = (Button) findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String content = inputText.getText().toString();
                //储存聊天信息
                if(!"".equals(content)) {
                    //发送消息
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                    //储存数据至数据库
                    Consumption consumption = saveToDatabase(content);

                    //自动接收消息, 还未实现
                    ReceiveMsg receiveMsg = new ReceiveMsg(consumption.getKind(), consumption.getMoney(), Msg.TYPE_RECEIVED);
                    msgList.add(receiveMsg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                }
            }
        });
    }

    private void reloadMessage() {
        inputText = (EditText) findViewById(R.id.input_text);
        String input = load();
        if(!TextUtils.isEmpty(input)){
            inputText.setText(input);
            inputText.setSelection(input.length());
        }
    }

    private void settingButton() {
    }

    private void historyButton() {
        history = (Button) findViewById(R.id.title_history);
        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, HistoryBillActivity.class);
                startActivity(intent);
            }
        });
    }

    private void hideActionBar() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
    }

    //储存数据至数据库
    //拆分输入的消息，得到consumption的五种属性，并通过引用返回一个Consumption，以决定自动回复的内容
    private Consumption saveToDatabase(String content) {
        java.util.Date currentDate = getCurrentDate();
        int imgId;
        String kind;
        float money;
        Consumption consumption = new Consumption();

        String[] strs = content.split("\\|");
        if(strs.length != 2) {
            Log.e("MainActivity", "split wrong");
            consumption.setKind("Exception");
        }
        else{
            kind = getString_without_space(strs[0]);
            List<Choice> choices = DataSupport.where("name = ?", kind).find(Choice.class);
            imgId = choices.get(0).getImageId();

            money = Float.valueOf(strs[1]);

            consumption.setKind(kind);
            consumption.setMoney(money);
            consumption.setDate(currentDate);
            consumption.setImgId(imgId);
            consumption.saveThrows();
        }
        return consumption;
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
        Log.e("initChoices", "initChoices");
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
        //11
        choice = new Choice("住房", R.drawable.housing);
        choice.save();
        //12
        choice = new Choice("通讯", R.drawable.mobile);
        choice.save();
    }

    private void loadChoices() {
        choiceList = DataSupport.findAll(Choice.class);
        RecyclerView choiceView = (RecyclerView) findViewById(R.id.choice);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 3);
        choiceView.setLayoutManager(layoutManager2);
        ChoiceAdapter choiceAdapter = new ChoiceAdapter(choiceList, (EditText) findViewById(R.id.input_text));
        choiceView.setAdapter(choiceAdapter);
    }

    private void initView() {
        msgRecyclerView =(RecyclerView) findViewById(R.id.msg_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        msgRecyclerView.setLayoutManager(layoutManager);
        adapter = new MsgAdapter(msgList);
        msgRecyclerView.setAdapter(adapter);
    }

    private void initMsg() {
        Msg msg1 = new Msg("零食 | 20", Msg.TYPE_SENT);
        msgList.add(msg1);
        Msg msg2 = new Msg("少吃点，记得减肥！", Msg.TYPE_RECEIVED);
        msgList.add(msg2);
        Msg msg3 = new Msg("交通 | 20",Msg.TYPE_SENT);
        msgList.add(msg3);
        Msg msg4 = new Msg("注意交通安全", Msg.TYPE_RECEIVED);
        msgList.add(msg4);
    }

}
