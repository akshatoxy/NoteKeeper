package com.example.notekeeper.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.notekeeper.model.Note;
import com.example.notekeeper.repository.NoteRepository;

public class NoteViewModel extends AndroidViewModel {
    private MutableLiveData<Note> note = new MutableLiveData<>();
    private LiveData<Note> noteFromDB;
    private NoteRepository noteRepository;

    private MutableLiveData<Boolean> saveThisNote = new MutableLiveData<>();
    private int noteId;

    public NoteViewModel(@NonNull Application application, int id) {
        super(application);

        this.noteId = id;
        this.noteRepository = new NoteRepository(getApplication());
        this.noteFromDB =  noteRepository.getNoteById(id);
        this.saveThisNote.setValue(false);
    }


    public void saveNote() {
        Note thisNote = note.getValue();
        Log.d("tzuyu", thisNote.getTitle() + " " + thisNote.getDescription() + " " + thisNote.getPriority() + " " + thisNote.getCreatedOn());
        if(noteId == 0){
            Log.d("tzuyu",String.valueOf(noteId));
            noteRepository.insert(thisNote);
        }else{
            Log.d("tzuyu",String.valueOf(noteId));
            thisNote.setId(noteId);
            noteRepository.update(thisNote);
        }
    }

    public MutableLiveData<Note> getNote() {
        return note;
    }

    public LiveData<Note> getNoteFromDB() {
        return noteFromDB;
    }

    public MutableLiveData<Boolean> getSaveThisNote() {
        return saveThisNote;
    }

}
