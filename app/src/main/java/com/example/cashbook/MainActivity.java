package com.example.cashbook;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

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

    private Button send;

    private RecyclerView msgRecyclerView;

    private MsgAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //  读取丢失输入
        inputText = (EditText) findViewById(R.id.input_text);
        String input = load();
        if(!TextUtils.isEmpty(input)){
            inputText.setText(input);
            inputText.setSelection(input.length());
        }

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
                if(!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SENT);
                    msgList.add(msg);
                    adapter.notifyItemInserted(msgList.size() - 1);
                    msgRecyclerView.scrollToPosition(msgList.size() - 1);
                    inputText.setText("");
                }
            }
        });

        initChoices();
        RecyclerView choiceView = (RecyclerView) findViewById(R.id.choice);
        GridLayoutManager layoutManager2 = new GridLayoutManager(this, 3);
        choiceView.setLayoutManager(layoutManager2);
        ChoiceAdapter choiceAdapter = new ChoiceAdapter(choiceList, (EditText) findViewById(R.id.input_text));
        choiceView.setAdapter(choiceAdapter);
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

    private void initChoices() {
        //1
        Choice choice = new Choice("一般", R.drawable.timg);
        choiceList.add(choice);
        //2
        choice = new Choice("用餐", R.drawable.timg);
        choiceList.add(choice);
        //3
        choice = new Choice("交通", R.drawable.timg);
        choiceList.add(choice);
        //4
        choice = new Choice("水果", R.drawable.timg);
        choiceList.add(choice);
        //5
        choice = new Choice("服饰", R.drawable.timg);
        choiceList.add(choice);
        //6
        choice = new Choice("日用品", R.drawable.timg);
        choiceList.add(choice);
        //7
        choice = new Choice("娱乐", R.drawable.timg);
        choiceList.add(choice);
        //8
        choice = new Choice("零食", R.drawable.timg);
        choiceList.add(choice);
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