package hector.developers.birthdaywishes.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hector.developers.birthdaywishes.R;
import hector.developers.birthdaywishes.activities.BirthDetailActivity;
import hector.developers.birthdaywishes.model.Staff;

public class BirthdayAdapter extends RecyclerView.Adapter<BirthdayAdapter.ViewHolder>{

    Context context;
    List<Staff> staffList;

    public BirthdayAdapter(List<Staff> staffList, Context context) {
        this.staffList = staffList;
        this.context = context;
    }

    @NonNull
    @Override
    public BirthdayAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.stafflist, parent, false);
        return new BirthdayAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull BirthdayAdapter.ViewHolder holder, int position) {
        Staff staff = staffList.get(position);
        holder.tvFirstName.setText(staff.getFirstname());
        holder.tvEmail.setText(staff.getEmail());
        holder.tvDob.setText(staff.getDateOfBirth());
        holder.tvDoe.setText(staff.getEmploymentDate());

        holder.cardView.setAnimation(AnimationUtils.loadAnimation(context, R.anim.fade_scale));
        if (position % 2 == 1) {
            holder.itemView.setBackgroundColor(Color.parseColor("#E8EAF6"));
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#BBDEFB"));
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BirthDetailActivity.class);
                intent.putExtra("key", staff);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return staffList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFirstName;
        private final TextView tvEmail;
        private final TextView tvDob;
        private final TextView tvDoe;
        CardView cardView;


        public ViewHolder(View itemView) {
            super(itemView);
            tvFirstName = itemView.findViewById(R.id.tvFirstname);
            tvEmail = itemView.findViewById(R.id.tvEmail);
            tvDob = itemView.findViewById(R.id.tvDob);
            tvDoe = itemView.findViewById(R.id.tvDoe);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
