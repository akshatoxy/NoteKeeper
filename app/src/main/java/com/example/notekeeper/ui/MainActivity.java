package com.example.notekeeper.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.notekeeper.R;
import com.example.notekeeper.viewmodel.NoteListViewModel;

public class MainActivity extends AppCompatActivity {

    private NoteListViewModel noteListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }
}