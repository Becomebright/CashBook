package com.example.cashbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

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

        initMsg();
        inputText = (EditText) findViewById(R.id.input_text);
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
        ChoiceAdapter choiceAdapter = new ChoiceAdapter(choiceList);
        choiceView.setAdapter(choiceAdapter);
    }

    private void initChoices() {
        for (int i=0; i<20; i++){
            Choice food = new Choice("Food", R.drawable.timg);
            choiceList.add(food);
        }
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
