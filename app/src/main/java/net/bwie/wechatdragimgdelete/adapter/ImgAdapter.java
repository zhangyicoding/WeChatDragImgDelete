package net.bwie.wechatdragimgdelete.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import net.bwie.wechatdragimgdelete.R;

import java.util.ArrayList;
import java.util.List;

public class ImgAdapter extends RecyclerView.Adapter<ImgAdapter.ViewHolder> {

    private Context mContext;
    // 图片路径集合
    private List<String> mDatas;

    public ImgAdapter(Context context) {
        mContext = context;
        mDatas = new ArrayList<>();
    }

    // 添加图片路径
    public void addImgPaths(List<String> imgPathList) {
        mDatas.addAll(imgPathList);
        notifyDataSetChanged();
    }

    // 获取图片路径
    public List<String> getImgPathList() {
        return mDatas;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext)
                .inflate(R.layout.item_img, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String imgPath = mDatas.get(position);

        Glide.with(mContext)
                .load(imgPath)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public ViewHolder(View itemView) {
            super(itemView);

            mImageView = itemView.findViewById(R.id.image_view);
        }
    }

}
