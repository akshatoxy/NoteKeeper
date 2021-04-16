package com.example.notekeeper.ui.horizontalcolorpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;
import com.example.notekeeper.util.PriorityUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickViewHolder> {

    public Callback callback;
    private Context context;
    private ArrayList<Integer> data = new ArrayList<Integer>(){
        {
            add(0);
            add(1);
            add(2);
            add(3);
            add(4);
        }
    };

    @NonNull
    @Override
    public PickViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.priority_item, parent, false);
        return new PickViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickViewHolder holder, int position) {
        holder.imageView.setColorFilter(PriorityUtils.getPriorityColor(context, position));
        holder.itemView.setOnClickListener(v -> callback.onItemClicked(v));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class PickViewHolder extends RecyclerView.ViewHolder{
        private CircleImageView imageView;

        public PickViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.circularImageView);
        }
    }
}
