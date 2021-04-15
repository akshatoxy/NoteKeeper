package com.example.notekeeper.ui.horizontalcolorpicker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PickerAdapter extends RecyclerView.Adapter<PickerAdapter.PickViewHolder> {

    public Callback callback;
    private Context context;
    private ArrayList<Integer> data = new ArrayList<Integer>(){
        {
            add(1);
            add(2);
            add(3);
            add(4);
            add(5);
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
        holder.imageView.setColorFilter(getPriorityColor(position));
        holder.itemView.setOnClickListener(v -> callback.onItemClicked(v));
    }

    public int getPriorityColor(int pos){
        if(pos == 0) {
            return ContextCompat.getColor(context, R.color.priority_1);
        }else if(pos == 1){
            return ContextCompat.getColor(context, R.color.priority_2);
        }else if(pos == 2){
            return ContextCompat.getColor(context, R.color.priority_3);
        }else if(pos == 3){
            return ContextCompat.getColor(context, R.color.priority_4);
        }else {
            return ContextCompat.getColor(context, R.color.priority_5);
        }
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
