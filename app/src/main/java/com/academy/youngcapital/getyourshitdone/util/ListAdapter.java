package com.academy.youngcapital.getyourshitdone.util;

import android.content.Context;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.academy.youngcapital.getyourshitdone.R;
import com.academy.youngcapital.getyourshitdone.data.Tasks;
import com.academy.youngcapital.getyourshitdone.model.Task;

import java.util.List;

public class ListAdapter extends BaseAdapter {

    private Context context;
    private List<Task> taskList;
    private Tasks dataTasks;

    /**
     * Deze listadapter constructor wordt gebruikt door listview en bevat een list met tasks en er
     * kunnen verschillende lists gestuurd worden op basis van category
     *
     * @param context
     * @param taskList
     */
    public ListAdapter(Context context, List<Task> taskList) {
        this.context = context;
        this.taskList = taskList;
    }

    /**
     * Deze listadapter constructor wordt gebruikt om alle tasks te sturen met de methodes van
     * model Tasks.
     * @param context
     * @param dataTasks
     */
    public ListAdapter(Context context, Tasks dataTasks) {
        this.dataTasks = dataTasks;
        this.context = context;
        this.taskList = dataTasks.getAllTasks();
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

    /**
     * Hier maken we een view aan met de layout van listview_item. Voor elke item wordt die layout
     * gebruikt en toegevoegd in een listadapter. Zo heeft elke element in de list
     * een textview en een checkbox.
     *
     * @return view
     */
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = View.inflate(context, R.layout.listview_item, null);

        final TextView textView = v.findViewById(R.id.taskTitle);
        final CheckBox checkBox = v.findViewById(R.id.taskCheckBox);

        //set de text van een list element naar de titel van elke task.
        textView.setText(taskList.get(position).getTitle());

        //set de color van de text naar de color van de category.
        if(taskList.get(position).getCategory() != null) {
            textView.setTextColor(taskList.get(position).getCategory().getColorCode());
        }

        //set de checkbox checked of unchecked op basis van elke task.
        checkBox.setChecked(taskList.get(position).isCompleted());

        //set de text dikgedrukt als de task prioriteit is gevinkt.
        if(taskList.get(position).getPriority())
        {
            textView.setTypeface(null, Typeface.BOLD);
        }

        //set alle tasks doorgestreept als ze completed zijn en anders niet
        if (taskList.get(position).isCompleted()) {
            //doorstrepen van de tekst
            textView.setPaintFlags(textView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            //streep weghalen van de tekst
            textView.setPaintFlags(0);

        }

        //run dit als er op een checkbox wordt geklikt
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //set de task op done of niet done.
                taskList.get(position).setIsCompleted(checkBox.isChecked());

                if (taskList.get(position).isCompleted()) {
                    //tasks die completed zijn onder in de lijst zetten
                    ListSorter.sendToBottom(taskList, taskList.indexOf(taskList.get(position)));
                    notifyDataSetChanged();
                } else {
                    //tasks die niet completed zijn boven in de lijst zetten
                    ListSorter.senToTop(taskList, taskList.indexOf(taskList.get(position)));
                    notifyDataSetChanged();
                }

            }
        });

        //save task id to tag om elke list element te koppelen aan de id.
        v.setTag(taskList.get(position).getId());

        if (dataTasks != null) {
            dataTasks.saveTasks();
        }

        return v;
    }
}