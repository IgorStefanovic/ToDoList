package com.sigor.todolist;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by sigor on 25/10/2016.
 */

public class ListViewAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<ListItem> tasks;

    public ListViewAdapter(Context context) {
        this.context = context;
        tasks = new ArrayList<ListItem>();
    }

    public void addTasks(ListItem task) {
        tasks.add(task);
        notifyDataSetChanged();
    }

    public void removeTask(int position) {
        tasks.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return tasks.size();
    }

    @Override
    public Object getItem(int position) {
        return tasks.get(position);
    }

    public void clear() {
        tasks.clear();
    }

    public int getPosition(ListItem item) {
        for (int i = 0; i < tasks.size(); i++)
        {
            if (item.equals(tasks.get(i))) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null)
        {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_item, null);
            ViewHolder holder = new ViewHolder();
            holder.tName = (TextView) view.findViewById(R.id.listItemTextView);
            view.setTag(holder);
        }

        ListItem lItem = (ListItem) getItem(position);
        ViewHolder holder = (ViewHolder) view.getTag();
        holder.tName.setText(lItem.name);
        return view;
    }

    private class ViewHolder {
        public TextView tName = null;
    }

    public void setTasks(ArrayList<ListItem> tasks) {
        this.tasks = tasks;
        notifyDataSetChanged();
    }
}
