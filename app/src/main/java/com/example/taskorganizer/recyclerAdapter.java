package com.example.taskorganizer;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.icu.text.NumberFormat;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.text.ParseException;
import java.util.Date;
import java.util.Locale;

public class recyclerAdapter extends FirebaseRecyclerAdapter<taskModel,recyclerAdapter.myViewHolder> {

    private final Context context;

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public recyclerAdapter(@NonNull FirebaseRecyclerOptions<taskModel> options, Context context) {
        super(options);
        this.context=context;
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull taskModel model) {
        holder.taskTitle.setText(model.getTask());
        holder.taskSupervisor.setText(model.getSupervisor());
        holder.daysgettr= model.getUploaded();
        Date c = Calendar.getInstance().getTime();
        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDte = df.format(c);
        int daygtr;
        try {
            daygtr= NumberFormat.getInstance().parse(holder.daysgettr).intValue();
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        int curd = Integer.parseInt(currentDte);
        int days_ago = (curd-daygtr);

        holder.uploadDate.setText("Uploaded "+days_ago+" days ago");
        holder.taskDetails = model.getDetails();
        holder.detailsbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context,taskDetails.class);
                i.putExtra("Title", model.getTask());
                i.putExtra("Details", model.getDetails());
                i.putExtra("Supervisor", model.getSupervisor());
                if (!(context instanceof taskRecycler)) {
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                }
                context.startActivity(i);

            }
        });

    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_items,parent,false);
        return new myViewHolder(v);
    }

    class myViewHolder extends RecyclerView.ViewHolder{
        TextView taskTitle, taskSupervisor, uploadDate;
        String taskDetails;
        String daysgettr;
        Button detailsbtn, completebtn;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            taskDetails="";
            taskTitle = itemView.findViewById(R.id.task_txt);
            taskSupervisor = itemView.findViewById(R.id.supTv);
            uploadDate = itemView.findViewById(R.id.dayTxt);
            daysgettr="";
            detailsbtn =  itemView.findViewById(R.id.details_btn);

            //completebtn = itemView.findViewById(R.id.completeBtn);
        }

    }
}
