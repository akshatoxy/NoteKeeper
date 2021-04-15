package com.example.notekeeper.ui.horizontalcolorpicker;

import android.content.Context;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

public class PickerLinearLayoutManager extends LinearLayoutManager {

    public OnItemSelectedListener callback;
    private RecyclerView recyclerView;

    public PickerLinearLayoutManager(Context context) {
        super(context);
        setOrientation(RecyclerView.HORIZONTAL);
    }

    @Override
    public void onAttachedToWindow(RecyclerView view) {
        super.onAttachedToWindow(view);
        recyclerView = view;

        new LinearSnapHelper().attachToRecyclerView(recyclerView);
    }

    @Override
    public void onScrollStateChanged(int state) {
        super.onScrollStateChanged(state);
        if(state == RecyclerView.SCROLL_STATE_IDLE){
            int recyclerViewCenterX = getRecyclerViewCenterX();
            int minDistance = recyclerView.getWidth();
            int pos = -1;

            for(int i=0;i<recyclerView.getChildCount();i++){
                View child = recyclerView.getChildAt(i);
                int childCenterX = getDecoratedLeft(child) + (getDecoratedRight(child)
                        - getDecoratedLeft(child))/2;
                int childDistanceFromCenter = Math.abs(childCenterX - recyclerViewCenterX);

                if(childDistanceFromCenter < minDistance){
                    minDistance = childDistanceFromCenter;
                    pos = recyclerView.getChildLayoutPosition(child);
                }

                callback.onItemSelected(pos);
            }

        }
    }

    private int getRecyclerViewCenterX(){
        return (recyclerView.getRight() - recyclerView.getLeft())/2 + recyclerView.getLeft();
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        scaleDownView();
    }

    @Override
    public int scrollHorizontallyBy(int dx, RecyclerView.Recycler recycler, RecyclerView.State state) {
        if(getOrientation() == RecyclerView.HORIZONTAL){
            int scrolled = super.scrollHorizontallyBy(dx, recycler, state);
            scaleDownView();
            return scrolled;
        }else{
            return 0;
        }
    }

    private void scaleDownView() {
        float mid = getWidth() / 2.0f;
        for(int i=0;i<getChildCount();i++){
            View child = getChildAt(i);
            float childMid = (getDecoratedLeft(child) + getDecoratedRight(child)) / 2.0f;
            float distaneFromCenter = Math.abs(mid- childMid);

            float scale = 1 - ((float) Math.sqrt(distaneFromCenter / getWidth()))*0.73f;

            child.setScaleX(scale);
            child.setScaleY(scale);

        }
    }
}
