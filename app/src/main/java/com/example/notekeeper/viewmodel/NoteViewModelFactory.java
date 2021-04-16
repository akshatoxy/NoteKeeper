package com.example.notekeeper.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class NoteViewModelFactory implements ViewModelProvider.Factory {

    private Application application;
    private int pos;

    public  NoteViewModelFactory(Application application, int pos){
        this.application = application;
        this.pos = pos;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass.isAssignableFrom(NoteViewModel.class)){
            return (T) new NoteViewModel(application, pos);
        }
        throw new IllegalArgumentException("Unknown class");
    }
}
