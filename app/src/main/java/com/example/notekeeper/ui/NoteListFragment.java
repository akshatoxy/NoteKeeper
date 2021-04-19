package com.example.notekeeper.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.ItemTouchHelper;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.example.notekeeper.R;
import com.example.notekeeper.databinding.FragmentNoteListBinding;
import com.example.notekeeper.model.Note;
import com.example.notekeeper.util.ScreenUtils;
import com.example.notekeeper.viewmodel.NoteListViewModel;

public class NoteListFragment extends Fragment {

    private FragmentNoteListBinding binding;
    private NoteListViewModel noteListViewModel;
    private NoteListAdapter noteListAdapter;
    private Animation slideDown;
    private Animation slideUp;
    private SearchView searchView;


    public NoteListFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteListBinding.inflate(inflater);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        hideKeyboard(view);

        slideDown = AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_down);

        slideUp = AnimationUtils.loadAnimation(requireActivity().getApplicationContext(), R.anim.slide_up);

        noteListViewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(requireActivity().getApplication())).get(NoteListViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setNoteListViewModel(noteListViewModel);

        NoteClickListener noteClickListener = this::goToNote;

        noteListAdapter = new NoteListAdapter(noteClickListener);

        ItemTouchHelper touchHelper = new ItemTouchHelper(new NoteItemMoveCallback(new NoteItemMoveCallback.NoteItemTouchHelperContract() {
            private int[] noteLocation = new int[2];

            @Override
            public void onRowSelected(NoteListAdapter.NoteListViewHolder myViewHolder) {
                View view = myViewHolder.itemView;

                view.getLocationOnScreen(noteLocation);

                view.setOnClickListener(null);
                view.setBackgroundColor(Color.GRAY);

                binding.deleteIcon.startAnimation(slideUp);
                binding.deleteBackView.startAnimation(slideUp);

                binding.deleteIcon.setVisibility(View.VISIBLE);
                binding.deleteBackView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onRowClear(NoteListAdapter.NoteListViewHolder myViewHolder) {
                binding.deleteIcon.startAnimation(slideDown);
                binding.deleteBackView.startAnimation(slideDown);
                
                binding.deleteIcon.setVisibility(View.GONE);
                binding.deleteBackView.setVisibility(View.GONE);
                
                myViewHolder.bind(noteListAdapter.getCurrentList().get(myViewHolder.getAdapterPosition()), noteClickListener);
            }

            @Override
            public boolean isInDeleteArea(float dx, float dy, NoteListAdapter.NoteListViewHolder myViewHolder, boolean isBeingDeleted) {

                if(!isBeingDeleted && (dy + noteLocation[1]) >= (ScreenUtils.getScreenHeight(requireActivity()) - 300)){
                    binding.deleteIcon.startAnimation(slideDown);
                    binding.deleteBackView.startAnimation(slideDown);
                    
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

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_list_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchView = (SearchView) searchItem.getActionView();
        searchView.setSubmitButtonEnabled(true);
        searchView.setQueryHint("Search...");
        EditText searchEditText = (EditText) searchView.findViewById(R.id.search_src_text);
        searchEditText.setHintTextColor(getResources().getColor(R.color.text_color));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(query != null){
                    searchDatabase(query);
                }
                return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                if(query != null){
                    searchDatabase(query);
                }
                return true;
            }
        });
    }

    private void searchDatabase(String query) {
        noteListViewModel.getSearchedNotes(query).observe(getViewLifecycleOwner(), notes -> {
            noteListAdapter.submitList(notes);
        });
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if(itemId == R.id.action_sort){
            showAlertDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showAlertDialog() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getContext());
        String items[] = {"Latest","Priority"};
        int checkedItem;
        if(noteListViewModel.getSortBy().equals("id")){
            checkedItem = 0;
        }else{
            checkedItem = 1;
        }

        alertDialog.setTitle("Sort By");
        alertDialog.setSingleChoiceItems(items, checkedItem, (dialog, which) -> {
            if(which == 0){
                noteListViewModel.updateSortBy("id");
            }else if(which == 1){
                noteListViewModel.updateSortBy("priority");
            }
            if(searchView.isIconified()){
                noteListViewModel.refreshNotes().observe(getViewLifecycleOwner(), notes -> {
                    noteListAdapter.submitList(notes);
                });
            }else {
                searchDatabase(searchView.getQuery().toString());
            }

        });

        AlertDialog alert = alertDialog.create();;
        alert.show();

    }
}