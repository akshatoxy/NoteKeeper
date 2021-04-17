package com.example.notekeeper.ui;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notekeeper.databinding.NoteItemBinding;
import com.example.notekeeper.model.Note;

public class NoteListAdapter extends ListAdapter<Note, NoteListAdapter.NoteListViewHolder>{

    private NoteClickListener clickListener;

    protected NoteListAdapter(NoteClickListener clickListener) {
        super(new NoteListDiffUtil());
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NoteListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NoteItemBinding binding = NoteItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new NoteListViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteListViewHolder holder, int position) {
        holder.bind(getItem(position), clickListener);
    }

    class NoteListViewHolder extends RecyclerView.ViewHolder{
        private NoteItemBinding binding;

        public NoteListViewHolder(@NonNull NoteItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Note note, NoteClickListener clickListener){
            binding.setNote(note);
            binding.getRoot().setOnClickListener(v -> clickListener.onCLick(note.getId()));
        }

        public NoteItemBinding getBinding() {
            return binding;
        }
    }
}

