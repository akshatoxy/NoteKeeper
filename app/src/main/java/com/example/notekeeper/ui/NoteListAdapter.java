package com.example.notekeeper.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.databinding.NoteItemBinding;
import com.example.notekeeper.model.Note;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteListViewHolder> {

    protected NoteListAdapter() {
        super(new NoteListDiffUtil());
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteItemBinding binding = NoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        holder.bind(getItem(position));
    }


    class NoteListViewHolder extends RecyclerView.ViewHolder{
        private NoteItemBinding binding;

        public NoteListViewHolder(@NonNull NoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Note note){
            binding.setNote(note);
        }
    }
}

