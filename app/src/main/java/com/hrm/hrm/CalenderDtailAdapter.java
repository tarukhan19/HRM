package com.hrm.hrm;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hrm.hrm.Bean.DailyAttendence;

import java.util.ArrayList;

/**
 * Created by shree on 3/19/2018.
 */

public class CalenderDtailAdapter extends RecyclerView.Adapter<CalenderDtailAdapter.ViewHolder> {
    private Context mContext;
    private int mSelectedItemPosition=-1;
    ArrayList<DailyAttendence> dailyAttendences;


    public CalenderDtailAdapter(Context mContext, ArrayList<DailyAttendence> dailyAttendences)
    {
        this.mContext = mContext;
        this.dailyAttendences = dailyAttendences;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.calendaritem, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindDataWithViewHolder(dailyAttendences.get(position),position);

        DailyAttendence item = dailyAttendences.get(position);
//        holder.bindDataWithViewHolder(dailyAttendences.get(position),position);

        if (item.getworking_status().equalsIgnoreCase("AB"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsuleab);}
        else if (item.getworking_status().equalsIgnoreCase("P"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulep);}
        else if (item.getworking_status().equalsIgnoreCase("HD"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulehd);}
        if (item.getworking_status().equalsIgnoreCase("WO"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulewo);}
        else if (item.getworking_status().equalsIgnoreCase("LT"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulelm);}
        else if (item.getworking_status().equalsIgnoreCase("PH"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsuleph);}
        else if (item.getworking_status().equalsIgnoreCase("CL"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulecl);}
        else if (item.getworking_status().equalsIgnoreCase("CO"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsuleco);}
        else if (item.getworking_status().equalsIgnoreCase("OD"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsuleod);}
        else if (item.getworking_status().equalsIgnoreCase("PL"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulepl);}
        else if (item.getworking_status().equalsIgnoreCase("SL"))
        {holder.leaveTypeTV.setBackgroundResource(R.drawable.capsulesl);}

        if (item.getworking_status().equalsIgnoreCase("P<LT"))
        {
            holder.lateTV.setVisibility(View.VISIBLE);
            holder.lateTV.setBackgroundResource(R.drawable.capsulep);
            holder.lateTV.setText("LT");
            holder.leaveTypeTV.setText("P");
        }
        else {
            holder.lateTV.setVisibility(View.GONE);
            holder.leaveTypeTV.setText(item.getworking_status());}

        holder.dateTV.setText(item.getatten_date());
        holder.inoutTimeTV.setText(item.gettimein()+" "+item.gettime_out());





    }

    @Override
    public int getItemCount()  {
        return dailyAttendences != null ? dailyAttendences.size() :0;
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView dateTV, leaveTypeTV, inoutTimeTV,lateTV;

        View clickview;

        private DailyAttendence mDataItem=null;
        public ViewHolder(View view) {
            super(view);
            clickview=view;
            dateTV = (TextView) itemView.findViewById(R.id.dateTV);
            leaveTypeTV = (TextView) itemView.findViewById(R.id.leaveTypeTV);
            inoutTimeTV = (TextView) itemView.findViewById(R.id.inoutTimeTV);
            lateTV= (TextView) itemView.findViewById(R.id.lateTV);


//            card_view_payroll.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    //  Toast.makeText(mContext,"You clicked item number "+MyViewHolder.this.getAdapterPosition(),Toast.LENGTH_LONG).show();
//                    //Handling for background selection state changed
//                    int previousSelectState=mSelectedItemPosition;
//                    mSelectedItemPosition = getAdapterPosition();
//                    //notify previous selected item
//                    notifyItemChanged(previousSelectState);
//                    //notify new selected Item
//                    notifyItemChanged(mSelectedItemPosition);
//
//                    //Your other handling in onclick
//                }
//            });
        }

        public void bindDataWithViewHolder(DailyAttendence dailyAttendences, int position) {
            this.mDataItem=dailyAttendences;
//            //Handle selection  state in object View.
//            if(position == mSelectedItemPosition){
//                payrollcardlist.setVisibility(View.GONE);
//                payrollcardremove.setVisibility(View.VISIBLE);
//            }else{
//                payrollcardlist.setVisibility(View.VISIBLE);
//                payrollcardremove.setVisibility(View.GONE);
//            }
        }
    }
}
