package com.academy.youngcapital.getyourshitdone.util;

import android.content.Context;
import android.graphics.ColorSpace;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.model.Task;

import java.util.ArrayList;
import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;

    public ListAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    @Override
    public int getCount() {
        return taskList.size();
    }

    @Override
    public Object getItem(int position) {
        return taskList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.listview_item, null);

        final TextView textView = (TextView)v.findViewById(R.id.taskTitle);
        final CheckBox checkBox = (CheckBox)v.findViewById(R.id.taskCheckBox);

        //set alle text naar titel van elke task
        textView.setText(taskList.get(position).getTitle());
        checkBox.setChecked(taskList.get(position).isCompleted());

        //set alle tasks doorgestreept als ze completed zijn en anders niet
        if(taskList.get(position).isCompleted()){
            //doorstrepen van de tekst
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }else{
            //streep weghalen van de tekst
            textView.setPaintFlags(0);

        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set de task op done of niet done.
                taskList.get(position).setIsCompleted(checkBox.isChecked());

                if(taskList.get(position).isCompleted()){
                    //tasks die completed zijn onder in de lijst zetten
                    ListSorter.sendToBottom(taskList, taskList.indexOf(taskList.get(position)));
                    notifyDataSetChanged();
                }else{
                    //tasks die niet completed zijn boven in de lijst zetten
                    ListSorter.senToTop(taskList, taskList.indexOf(taskList.get(position)));
                    notifyDataSetChanged();
                }

            }
        });

        //save task id to tag
        v.setTag(taskList.get(position).getId());

        return v;
    }
}