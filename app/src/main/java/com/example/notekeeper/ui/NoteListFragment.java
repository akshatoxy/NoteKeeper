package com.example.notekeeper.ui;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;

import com.example.notekeeper.databinding.FragmentNoteListBinding;
import com.example.notekeeper.util.ScreenUtils;
import com.example.notekeeper.viewmodel.NoteListViewModel;

public class NoteListFragment extends Fragment {

    private FragmentNoteListBinding binding;
    private NoteListViewModel noteListViewModel;
    private NoteListAdapter noteListAdapter;

    public NoteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteListBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideKeyboard(view);

        noteListViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NoteListViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setNoteListViewModel(noteListViewModel);

        NoteClickListener noteClickListener = this::goToNote;

        noteListAdapter = new NoteListAdapter(noteClickListener);

//        ItemTouchHelper touchHelper = new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            @Override
//            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//                return false;
//            }
//
//            @Override
//            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
//                noteListViewModel.getDeleteNote().setValue(noteListAdapter.getCurrentList().get(viewHolder.getAdapterPosition()));
//            }
//        });
//
//        touchHelper.attachToRecyclerView(binding.noteList);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new NoteItemMoveCallback(new NoteItemMoveCallback.NoteItemTouchHelperContract() {
            private int[] noteLocation = new int[2];

            @Override
            public void onRowSelected(NoteListAdapter.NoteListViewHolder myViewHolder) {
                View view = myViewHolder.getBinding().getRoot();

                view.getLocationOnScreen(noteLocation);

                view.setOnClickListener(null);
                view.setBackgroundColor(Color.GRAY);

                binding.deleteIcon.setVisibility(View.VISIBLE);
                binding.deleteBackView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRowClear(NoteListAdapter.NoteListViewHolder myViewHolder) {
                binding.deleteIcon.setVisibility(View.GONE);
                binding.deleteBackView.setVisibility(View.GONE);
                myViewHolder.bind(noteListAdapter.getCurrentList().get(myViewHolder.getAdapterPosition()), noteClickListener);
            }

            @Override
            public boolean isInDeleteArea(float dx, float dy, NoteListAdapter.NoteListViewHolder myViewHolder, boolean isBeingDeleted) {

                if(!isBeingDeleted && (dy + noteLocation[1]) >= (ScreenUtils.getScreenHeight(requireActivity()) - 300)){
                    binding.deleteIcon.setVisibility(View.GONE);
                    binding.deleteBackView.setVisibility(View.GONE);
                    noteListViewModel.getDeleteNote().setValue(noteListAdapter.getCurrentList().get(myViewHolder.getAdapterPosition()));
                    return true;
                }
                return false;
            }
        }));

        touchHelper.attachToRecyclerView(binding.noteList);

        binding.noteList.setAdapter(noteListAdapter);

        binding.addNoteBtn.setOnClickListener(v -> goToNote(0));

        observeData();
    }

    private void observeData() {
        noteListViewModel.getDeleteNote().observe(getViewLifecycleOwner(), deleteNote -> {
            if(deleteNote != null){
                noteListViewModel.getDeleteNote().setValue(null);
                noteListViewModel.deleteNote(deleteNote);
            }
        });
    }

    private void goToNote(int noteId){
        NavHostFragment.findNavController(this).navigate(NoteListFragmentDirections.actionNoteListFragmentToNoteFragment().setNoteId(noteId));
    }

    private void hideKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}