package com.example.notekeeper.ui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.example.notekeeper.R;
import com.example.notekeeper.databinding.FragmentNoteBinding;
import com.example.notekeeper.model.Note;
import com.example.notekeeper.ui.horizontalcolorpicker.PickerAdapter;
import com.example.notekeeper.ui.horizontalcolorpicker.PickerLinearLayoutManager;
import com.example.notekeeper.util.DateUtils;
import com.example.notekeeper.util.PriorityUtils;
import com.example.notekeeper.util.ScreenUtils;
import com.example.notekeeper.viewmodel.NoteViewModel;
import com.example.notekeeper.viewmodel.NoteViewModelFactory;

public class NoteFragment extends Fragment {

    private RecyclerView recyclerView;
    private FragmentNoteBinding binding;
    int noteID;

    private NoteViewModelFactory noteViewModelFactory;
    private NoteViewModel noteViewModel;

    public NoteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentNoteBinding.inflate(inflater);
        setHasOptionsMenu(true);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        if(getArguments() != null){
            noteID = NoteFragmentArgs.fromBundle(getArguments()).getNoteId();
        }

        recyclerView = view.findViewById(R.id.horizontal_color_picker);
        setUpHorizontalPicker();

        getFocus(binding.noteDescText);

        noteViewModelFactory = new NoteViewModelFactory(getActivity().getApplication(), noteID);
        noteViewModel = new ViewModelProvider(this, noteViewModelFactory).get(NoteViewModel.class);

        binding.setLifecycleOwner(this);
        binding.setNoteViewModel(noteViewModel);

        observeData();
    }

    private void observeData() {
        noteViewModel.getSaveThisNote().observe(getViewLifecycleOwner(), isClicked -> {
            if(isClicked) {
                noteViewModel.getNote().setValue(new Note(binding.noteTitleText.getText().toString() , binding.noteDescText.getText().toString() , PriorityUtils.getPriorityInt(binding.priorityText.getText().toString()),
                        DateUtils.getCurrentDate()));
                noteViewModel.saveNote();
                noteViewModel.getSaveThisNote().setValue(false);
                goToNoteList();
            }
        });

        noteViewModel.getNoteFromDB().observe(getViewLifecycleOwner(), note ->{
            noteViewModel.getNote().setValue(note);
        });
    }

    void goToNoteList(){
        NavHostFragment.findNavController(this).navigate(NoteFragmentDirections.actionNoteFragmentToNoteListFragment());
    }


    void getFocus(View view){
        if(view.requestFocus()){
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.showSoftInput(binding.noteDescText, InputMethodManager.SHOW_IMPLICIT);
        }
    }

    void setUpHorizontalPicker(){
        PickerLinearLayoutManager plm = new PickerLinearLayoutManager(getContext());
        plm.callback = pos -> {
            binding.priorityText.setText(PriorityUtils.getPriorityText(pos));
        };
        recyclerView.setLayoutManager(plm);

        PickerAdapter pa = new PickerAdapter();
        pa.callback = v -> {
            int pos = recyclerView.getChildLayoutPosition(v);
            recyclerView.smoothScrollToPosition(pos);
        };
        recyclerView.setAdapter(pa);

        int padding = ScreenUtils.getScreenWidth(getContext())/2 -
                ScreenUtils.dpToPx(getContext(), 63);
        recyclerView.setPadding(padding, 0, padding, 0);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_done){
            noteViewModel.getSaveThisNote().setValue(true);
            Toast.makeText(getContext(), "Note saved :)", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}