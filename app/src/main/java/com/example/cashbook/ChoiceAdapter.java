package com.example.cashbook;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by dsz62 on 2017/7/6.
 */

public class ChoiceAdapter extends RecyclerView.Adapter<ChoiceAdapter.ViewHolder> {

    private List<Choice> mChoiceList;
    private EditText editText;

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

    public ChoiceAdapter(List<Choice> choiceList){
        mChoiceList = choiceList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        editText = (EditText) parent.findViewById(R.id.input_text);
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.choice_item, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.choiceView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = holder.getAdapterPosition();
                Choice choice = mChoiceList.get(position);
//                editText.setText(choice.getName());
                Toast.makeText(view.getContext(), "You clicked view " + choice.getName(), Toast.LENGTH_SHORT).show();
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
