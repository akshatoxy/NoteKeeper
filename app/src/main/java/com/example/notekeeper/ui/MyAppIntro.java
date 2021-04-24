package com.example.notekeeper.ui;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import com.example.notekeeper.R;
import com.example.notekeeper.util.SharedPreferenceUtils;
import com.github.appintro.AppIntro;
import com.github.appintro.AppIntroFragment;
import com.github.appintro.AppIntroPageTransformerType;

import org.jetbrains.annotations.Nullable;

public class MyAppIntro extends AppIntro {

    public MyAppIntro(){
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                R.drawable.ic_logo,
                getResources().getColor(R.color.app_background),
                Color.BLACK,
                Color.BLACK));

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                R.drawable.ic_logo,
                getResources().getColor(R.color.priority_1),
                Color.BLACK,
                Color.BLACK));

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                R.drawable.ic_logo,
                getResources().getColor(R.color.priority_2),
                Color.BLACK,
                Color.BLACK));

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                0,
                getResources().getColor(R.color.priority_3),
                Color.BLACK,
                Color.BLACK));

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                R.drawable.ic_logo,
                getResources().getColor(R.color.priority_4),
                Color.BLACK,
                Color.BLACK));

        addSlide(AppIntroFragment.newInstance("Welcome...",
                "This is the first slide of the example",
                R.drawable.ic_logo,
                getResources().getColor(R.color.priority_5),
                Color.BLACK,
                Color.BLACK));


        setTransformer(new AppIntroPageTransformerType.Parallax(-1.0,-1.0,-1.0));

        setIndicatorEnabled(true);

        setColorSkipButton(Color.BLACK);

        setColorDoneText(Color.BLACK);

        setNextArrowColor(Color.BLACK);

        setIndicatorColor(Color.BLACK,Color.GRAY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            setImmersive(true);
        }
    }

    @Override
    protected void onSkipPressed(@Nullable Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        SharedPreferenceUtils.getInstance(this).updateIsIntroDisplayed(true);
        finish();
    }

    @Override
    protected void onDonePressed(@Nullable Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        SharedPreferenceUtils.getInstance(this).updateIsIntroDisplayed(true);
        finish();
    }
}
