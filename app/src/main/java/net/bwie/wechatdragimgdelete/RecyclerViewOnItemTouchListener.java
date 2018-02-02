package net.bwie.wechatdragimgdelete;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

/**
 * RecyclerView Item的点击事件/长点击事件
 * Created by zhangyi
 */
public abstract class RecyclerViewOnItemTouchListener
        extends RecyclerView.SimpleOnItemTouchListener {

    private RecyclerView mRecyclerView;
    private GestureDetectorCompat mGestureDetector;

    public RecyclerViewOnItemTouchListener(RecyclerView recyclerView) {
        mRecyclerView = recyclerView;
        mGestureDetector = new GestureDetectorCompat(recyclerView.getContext(),
                new ItemTouchOnGestureListener(this));
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        mGestureDetector.onTouchEvent(e);
    }

    private static class ItemTouchOnGestureListener
            extends GestureDetector.SimpleOnGestureListener {

        private RecyclerViewOnItemTouchListener mListener;

        private ItemTouchOnGestureListener(RecyclerViewOnItemTouchListener listener) {
            mListener = listener;
        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View child = mListener.mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = mListener.mRecyclerView.getChildViewHolder(child);
                mListener.onItemClick(vh);
            }
            return true;
        }

        @Override
        public void onLongPress(MotionEvent e) {
            View child = mListener.mRecyclerView.findChildViewUnder(e.getX(), e.getY());
            if (child != null) {
                RecyclerView.ViewHolder vh = mListener.mRecyclerView.getChildViewHolder(child);
                mListener.onItemLongClick(vh);
            }
        }
    }

    public abstract void onItemClick(RecyclerView.ViewHolder holder);

    public abstract void onItemLongClick(RecyclerView.ViewHolder holder);

}
