package com.example.notekeeper.Util;

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
    public static void bindCradViewPriorityBackground(CardView cardView, int priority){
        switch (priority){
            case 1:{
                cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.priority_1));
                break;
            }
            case 2:{
                cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.priority_2));
                break;
            }
            case 3:{
                cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.priority_3));
                break;
            }
            case 4:{
                cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.priority_4));
                break;
            }
            case 5:{
                cardView.setBackgroundColor(ContextCompat.getColor(cardView.getContext(), R.color.priority_5));
                break;
            }
        }
    }

}
