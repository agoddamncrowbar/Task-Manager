package com.example.taskorganizer;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

import java.util.Date;
import java.util.Locale;

public class reportListAdapter extends FirebaseRecyclerAdapter<reportModel,reportListAdapter.myViewHolder> {
    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public reportListAdapter(@NonNull FirebaseRecyclerOptions<reportModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull reportModel model) {
        holder.tskTle.setText(model.getEmail());
        holder.emTle.setText(model.getTitle());
        holder.rpt= model.getReport();
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        String currentDte = df.format(c);
        holder.datehlpr= model.getUploaded();
        int daygtr = Integer.parseInt(holder.datehlpr);
        int curd = Integer.parseInt(currentDte);
        int days_ago = (curd-daygtr);
        holder.upDate.setText("uploaded "+days_ago+" days ago");
        holder.detA= model.getReport();
        holder.details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder b =new AlertDialog.Builder(holder.tskTle.getContext());
                b.setTitle(model.getTitle());
                b.setMessage(holder.detA);
                b.setNeutralButton("Back", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                b.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.report_items,parent,false);
        return new reportListAdapter.myViewHolder(v);
    }

    class myViewHolder extends RecyclerView.ViewHolder {
        TextView tskTle, emTle, upDate;
        String rpt,datehlpr, detA;
        Button details;
        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tskTle=itemView.findViewById(R.id.tasktle);
            emTle=itemView.findViewById(R.id.emTxt);
            rpt="";
            datehlpr="";
            detA="";
            upDate=itemView.findViewById(R.id.upDateTxt);
            details =itemView.findViewById(R.id.Detailsbtn);
        }
    }
    private void detsPopup(){


    }
}
