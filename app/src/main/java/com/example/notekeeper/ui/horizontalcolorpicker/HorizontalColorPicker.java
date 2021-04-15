package com.example.notekeeper.ui.horizontalcolorpicker;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.notekeeper.R;

public class HorizontalColorPicker extends ConstraintLayout {
    public HorizontalColorPicker(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        inflate(context, R.layout.custom_horiontal_picker, this);
    }

}
