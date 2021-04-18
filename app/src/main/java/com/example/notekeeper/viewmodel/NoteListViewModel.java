package com.example.notekeeper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notekeeper.model.Note;
import com.example.notekeeper.repository.NoteRepository;
import com.example.notekeeper.util.SharedPreferenceUtils;

import java.util.List;

public class NoteListViewModel extends AndroidViewModel {

    private NoteRepository noteRepository;
    private LiveData<List<Note>> allNotes;

    private MutableLiveData<Note> deleteNote = new MutableLiveData<Note>();

    private LiveData<List<Note>> searchedNotes;

    private SharedPreferenceUtils sharedPreferenceUtils;

    public NoteListViewModel(@NonNull Application application) {
        super(application);
        this.sharedPreferenceUtils = SharedPreferenceUtils.getInstance(application);
        this.noteRepository = new NoteRepository(getApplication());
        this.deleteNote.setValue(null);
        this.allNotes = noteRepository.getAllNotes(sharedPreferenceUtils.getSortBy());
    }

    public void deleteNote(Note note){
        noteRepository.delete(note);
    }

    public LiveData<List<Note>> getSearchedNotes(String query) {
        String formattedQuery = "%" + query.toLowerCase() + "%";
        searchedNotes = noteRepository.getSearchedNotes(formattedQuery,sharedPreferenceUtils.getSortBy());
        return searchedNotes;
    }

    public MutableLiveData<Note> getDeleteNote() {
        return deleteNote;
    }

    public LiveData<List<Note>> getAllNotes() {
        return allNotes;
    }

    public void updateSortBy(String sortBy){
        sharedPreferenceUtils.updateSortBy(sortBy);
    }

    public String getSortBy(){
        return sharedPreferenceUtils.getSortBy();
    }

    public LiveData<List<Note>> refreshNotes() {
        allNotes = noteRepository.getAllNotes(sharedPreferenceUtils.getSortBy());
        return allNotes;
    }
}
