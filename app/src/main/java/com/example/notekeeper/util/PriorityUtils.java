package com.example.notekeeper.util;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.notekeeper.R;

public class PriorityUtils {
    public static String getPriorityText(int priority) {
        if (priority == 0) {
            return "Very Low";
        } else if (priority == 1) {
            return "Low";
        } else if (priority == 2) {
            return "Medium";
        } else if (priority == 3) {
            return "High";
        } else {
            return "Very High";
        }
    }

    public static int getPriorityColor(Context context, int priority) {
        if (priority == 0) {
            return ContextCompat.getColor(context, R.color.priority_1);
        } else if (priority == 1) {
            return ContextCompat.getColor(context, R.color.priority_2);
        } else if (priority == 2) {
            return ContextCompat.getColor(context, R.color.priority_3);
        } else if (priority == 3) {
            return ContextCompat.getColor(context, R.color.priority_4);
        } else {
            return ContextCompat.getColor(context, R.color.priority_5);
        }
    }
}