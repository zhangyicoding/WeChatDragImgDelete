package net.bwie.wechatdragimgdelete;

import android.graphics.Canvas;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;

import net.bwie.wechatdragimgdelete.adapter.ImgAdapter;

import java.util.Collections;
import java.util.List;

/**
 * Item触摸助手的回调
 * 用于实现如何拖拽、拖拽方向等
 */
public abstract class ItemTouchCallback extends ItemTouchHelper.Callback {

    private ImgAdapter mAdapter;
    private List<String> mImgPathList;

        // 是否松手
    public boolean isUp = true;

    public ItemTouchCallback(ImgAdapter adapter) {
        mAdapter = adapter;
        mImgPathList = mAdapter.getImgPathList();

    }

    // 获取item移动标记（指定item拖拽方向）
    // recyclerView：被操作的RecyclerView
    // viewHolder：被拖拽item对应的ViewHolder
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags = 0;
        int swipeFlags = 0;
        // 如果RecyclerView是网格样式，可以实现上下左右4个方向的拖拽
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.START | ItemTouchHelper.END;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    // item拖拽时的监听
    // viewHolder：被拖拽item对应的ViewHolder
    // target：拖拽item和被交换位置item对应的ViewHolder
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        // 被拖拽item的起始位置和终止位置
        int fromPosition = viewHolder.getAdapterPosition();
        int toPosition = target.getAdapterPosition();
        // 从前顶到后面，交换所有item的位置
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                // 不断两两交换位置，实现item的换位效果
                Collections.swap(mImgPathList, i, i + 1);
            }
        } else if (toPosition < fromPosition) {
            // 从后顶到前面，交换所有item的位置
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(mImgPathList, i, i - 1);
            }
        }
        mAdapter.notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    // 当执行轻扫动作时的回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }

    // 当item滑动时频繁绘制item的方法
    // dY：代表item在垂直方向的偏移量
    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        // 如果item垂直方向滑动距离到了一定程度，会触发红色删除区域文字改变，松手也会触发删除item操作
        // RecyclerView高度 - 红色删除区域高度 - item底部到RecyclerView顶部的距离
        if (dY >= (recyclerView.getHeight()
                - viewHolder.itemView.getHeight()
                - viewHolder.itemView.getBottom())) {
            // 改变删除区域文字内容
            onPrepareDelete(true);
            // 松手后适配器删除item在图片路径集合中对应的数据，并刷新UI
                int deleteItemPosition = viewHolder.getAdapterPosition();
            if (isUp && deleteItemPosition != -1) {
                Log.d("1511", "del pos: " + deleteItemPosition);
                mImgPathList.remove(deleteItemPosition);
                mAdapter.notifyItemRemoved(deleteItemPosition);

                // 重置删除区域
                onDragStateChanged(false);
                onPrepareDelete(false);
            }
        } else {
            onPrepareDelete(false);
        }
    }

    // 监听松手后item并移动回原位置
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);

        // 隐藏底部红色删除区域
        onDragStateChanged(false);
    }

    // 可以监听到立即松手并执行该方法
    @Override
    public long getAnimationDuration(RecyclerView recyclerView, int animationType, float animateDx, float animateDy) {
        isUp = true;
        return super.getAnimationDuration(recyclerView, animationType, animateDx, animateDy);
    }

    // 是否正在拖拽的状态
    public abstract void onDragStateChanged(boolean isDragging);

    // 删除区域文字的状态
    public abstract void onPrepareDelete(boolean isPrepareDelete);

}
