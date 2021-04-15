package com.example.notekeeper.util;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.example.notekeeper.R;

public class PriorityUtils {
    public static String getPriorityText(int pos) {
        if (pos == 0) {
            return "Very High";
        } else if (pos == 1) {
            return "High";
        } else if (pos == 2) {
            return "Medium";
        } else if (pos == 3) {
            return "Low";
        } else {
            return "Very Low";
        }
    }

    public static int getPriorityColor(Context context, int pos) {
        if (pos == 0) {
            return ContextCompat.getColor(context, R.color.priority_1);
        } else if (pos == 1) {
            return ContextCompat.getColor(context, R.color.priority_2);
        } else if (pos == 2) {
            return ContextCompat.getColor(context, R.color.priority_3);
        } else if (pos == 3) {
            return ContextCompat.getColor(context, R.color.priority_4);
        } else {
            return ContextCompat.getColor(context, R.color.priority_5);
        }
    }
}