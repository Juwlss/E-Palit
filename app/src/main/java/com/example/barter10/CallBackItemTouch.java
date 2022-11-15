package com.example.barter10;

import androidx.recyclerview.widget.RecyclerView;

public interface CallBackItemTouch {
    void itemTouchOnMove(int oldPosition, int newPosition);

    void onSwipe(RecyclerView.ViewHolder viewHolder, int position);
}
