package com.auth0.samples.utils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.auth0.samples.R;
import com.auth0.samples.activities.ApproveActivity;
import com.auth0.samples.models.TimeSheet;

import java.util.ArrayList;

/**
 * Created by ej on 9/11/17.
 */

public class TimeSheetAdapter extends RecyclerView.Adapter<TimeSheetAdapter.ViewHolder> {
    private Context context;
    private ArrayList<TimeSheet> timesheetDataset;
    private int layoutId;

    public TimeSheetAdapter(Context context, ArrayList<TimeSheet> dataset, int layoutId) {
        this.context = context;
        this.timesheetDataset = dataset;
        this.layoutId = layoutId;
    }

    @Override
    public TimeSheetAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);

        ViewHolder holder = new ViewHolder(view);
        holder.cardView = view;
        holder.userId = (TextView) view.findViewById(R.id.tvUserID);
        holder.date = (TextView) view.findViewById(R.id.tvDate);
        holder.projectName = (TextView) view.findViewById(R.id.tvProjectName);
        holder.hours = (TextView) view.findViewById(R.id.tvHours);
        holder.status = (ImageView) view.findViewById(R.id.ivStatus);
        holder.approved = (TextView) view.findViewById(R.id.tvApproved);
        if (layoutId == R.layout.item_timesheet_approve) {
            holder.button = (Button) view.findViewById(R.id.approveButton);
        }
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final TimeSheet timesheet = timesheetDataset.get(position);
        if (holder.button != null) {
            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(context instanceof ApproveActivity){
                        ((ApproveActivity)context).approveTimeSheet(timesheet.getId(), position);
                    }
                }
            });
        }
        holder.userId.setText(timesheet.getUserID());
        holder.date.setText(timesheet.getDate());
        holder.projectName.setText(timesheet.getProjectName());
        holder.hours.setText(Double.toString(timesheet.getHours()));
        if (timesheet.isApproved()) {
            holder.approved.setText("Approved");
            holder.status.setImageResource(R.drawable.circle_approved);
        } else {
            holder.approved.setText("Pending");
            holder.status.setImageResource(R.drawable.circle_pending);
        }
    }

    @Override
    public int getItemCount() {
        return timesheetDataset.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public View cardView;
        public TextView userId;
        public TextView date;
        public TextView projectName;
        public TextView hours;
        public ImageView status;
        public TextView approved;
        public Button button;

        public ViewHolder(View view) {
            super(view);
        }
    }
}
