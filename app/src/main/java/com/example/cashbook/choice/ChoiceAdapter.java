package com.example.cashbook.choice;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cashbook.R;

import java.util.List;

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {

    private List<Choice> mChoiceList;
    private EditText input_text;

    static public class ViewHolder extends RecyclerView.ViewHolder {
        View choiceView;
        ImageView choiceImg;
        TextView choiceName;

        public ViewHolder(View itemView) {
            super(itemView);
            choiceView = itemView;
            choiceImg = (ImageView) itemView.findViewById(R.id.choice_img);
            choiceName = (TextView) itemView.findViewById(R.id.choice_name);
        }
    }

    public ChoiceAdapter(List<Choice> choiceList, EditText view){
        mChoiceList = choiceList;
        this.input_text = view;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.choiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Choice choice = mChoiceList.get(position);
                input_text.setText(choice.getName()+"  |  ");

                input_text.requestFocus();//输入框获取焦点
                input_text.setSelection(input_text.getText().length());
                InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(input_text, 0);//使软键盘保持弹出状态
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Choice choice = mChoiceList.get(position);
        holder.choiceImg.setImageResource(choice.getImageId());
        holder.choiceName.setText(choice.getName());
    }

    @Override
    public int getItemCount() {
        return mChoiceList.size();
    }

}
