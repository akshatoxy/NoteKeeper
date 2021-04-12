package com.example.notekeeper.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "note_table")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String title;
    private String description;
    private int priority;
    private String createdOn;

    public Note(String title, String description, int priority, String createdOn) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createdOn = createdOn;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    @NonNull
    @Override
    public String toString() {
        return "Note{" +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
