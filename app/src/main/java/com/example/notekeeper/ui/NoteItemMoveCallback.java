package com.example.notekeeper.ui;

import android.graphics.Canvas;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class NoteItemMoveCallback extends ItemTouchHelper.Callback {

    private final NoteItemTouchHelperContract adapter;
    private boolean removeItem = false;

    public NoteItemMoveCallback(NoteItemTouchHelperContract adapter){
        this.adapter = adapter;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        if(!isCurrentlyActive && adapter.isInDeleteArea(dX, dY, (NoteListAdapter.NoteListViewHolder) viewHolder, removeItem)){
            removeItem = true;
            viewHolder.itemView.setVisibility(View.GONE);
            return;
        }

        viewHolder.itemView.setTranslationX(dX);
        viewHolder.itemView.setTranslationY(dY);
    }

    @Override
    public long getAnimationDuration(@NonNull RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        if(removeItem)
            return 0;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        return makeMovementFlags(dragFlags, 0);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(@Nullable RecyclerView.ViewHolder viewHolder, int actionState) {
        removeItem = false;
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE){
            if(viewHolder instanceof NoteListAdapter.NoteListViewHolder){
                NoteListAdapter.NoteListViewHolder myViewHolder =
                        (NoteListAdapter.NoteListViewHolder) viewHolder;
                adapter.onRowSelected(myViewHolder);
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    @Override
    public void clearView(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        if(viewHolder instanceof NoteListAdapter.NoteListViewHolder){
            NoteListAdapter.NoteListViewHolder myViewHolder =
                    (NoteListAdapter.NoteListViewHolder) viewHolder;
            if(!removeItem) {
                adapter.onRowClear(myViewHolder);
            }
        }
    }

    public interface NoteItemTouchHelperContract {
        void onRowSelected(NoteListAdapter.NoteListViewHolder myViewHolder);
        void onRowClear(NoteListAdapter.NoteListViewHolder myViewHolder);
        boolean isInDeleteArea(float dx, float dy, NoteListAdapter.NoteListViewHolder myViewHolder,boolean isBeingDeleted);
    }

}
