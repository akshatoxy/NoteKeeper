package com.example.notekeeper.util;

import android.util.Log;

import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.R;
import com.example.notekeeper.model.Note;
import com.example.notekeeper.ui.NoteListAdapter;

import java.util.List;

public class BindingAdapters {

    @BindingAdapter("listData")
    public static void bindRecyclerViewListItem(RecyclerView recyclerView, List<Note> allNotes){
        if(allNotes != null){
            NoteListAdapter adapter = (NoteListAdapter)(recyclerView.getAdapter());
            adapter.submitList(allNotes);
        }
    }


    @BindingAdapter("priorityColor")
    public static void bindCardViewPriorityBackground(CardView cardView, int priority){
        cardView.setBackgroundColor(PriorityUtils.getPriorityColor(cardView.getContext(), priority));
    }

    @BindingAdapter("setPos")
    public static void bindRecyclerViewWithPriority(RecyclerView view, Integer priority){
        if(priority != null) {
            view.scrollToPosition(priority);
        }
    }

}
