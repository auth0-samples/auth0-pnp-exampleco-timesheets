package com.auth0.samples.utils;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.auth0.samples.R;
import com.auth0.samples.models.TimeSheet;

import java.util.ArrayList;

/**
 * Created by ej on 7/10/17.
 */

public class TimeSheetAdapter extends ArrayAdapter<TimeSheet> {

    private ArrayList<TimeSheet> timesheetData;
    private Activity context;

    public TimeSheetAdapter(Context context, int resource, ArrayList<TimeSheet> timesheets) {
        super(context, resource, timesheets);
        this.context = (Activity) context;
        this.timesheetData = timesheets;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        ViewHolder holder;

        if (view == null) {
            LayoutInflater viewInflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = viewInflater.inflate(R.layout.item_entry, parent, false);

            holder = new ViewHolder();
            holder.id = (TextView) view.findViewById(R.id.tvUserID);
            holder.date = (TextView) view.findViewById(R.id.tvDate);
            holder.projectName = (TextView) view.findViewById(R.id.tvProjectName);
            holder.hours = (TextView) view.findViewById(R.id.tvHours);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        TimeSheet timesheet = timesheetData.get(position);

        if (timesheet != null) {

            if (holder.id != null && holder.date != null && holder.projectName != null && holder.hours != null) {
                holder.id.setText(timesheet.getUserID());
                holder.date.setText(timesheet.getDateString());
                holder.projectName.setText(timesheet.getProjectName());
                holder.hours.setText(Double.toString(timesheet.getHours()));
            }

        }

        return view;
    }

    private static class ViewHolder {
        TextView id;
        TextView date;
        TextView projectName;
        TextView hours;
    }
}
