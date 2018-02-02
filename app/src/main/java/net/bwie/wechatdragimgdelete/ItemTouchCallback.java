package net.bwie.wechatdragimgdelete;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

/**
 * Item触摸助手的回调
 * 用于实现如何拖拽、拖拽方向等
 */
public class ItemTouchCallback extends ItemTouchHelper.Callback {

    // 获取item移动标记（指定item拖拽方向）
    // recyclerView：被操作的RecyclerView
    // viewHolder：被拖拽item对应的ViewHolder
    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        return 0;
    }

    // item拖拽时的监听
    // viewHolder：被拖拽item对应的ViewHolder
    // target：拖拽item和被交换位置item对应的ViewHolder
    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return false;
    }

    // 当执行轻扫动作时的回调
    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
    }
}
