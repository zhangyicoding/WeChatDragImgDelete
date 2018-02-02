package net.bwie.wechatdragimgdelete;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.bwie.wechatdragimgdelete.adapter.ImgAdapter;

import java.util.ArrayList;

import me.iwf.photopicker.PhotoPicker;

/**
 * 今天的另外一个又简单有不重要的东西：评分条RatingBar
 * <p>
 * 仿微信选择多图，长按图片拖拽删除功能
 * 1、第一个界面：多图选择界面（可以使用第三方框架）
 * 1.1、导入PhotoPicker库，并且在清单文件中添加权限以及对应activity
 * 2、微信准备上传图片界面，可以实现图片拖拽删除功能
 * 2.1、给RecyclerView的item添加长点击监听
 * 2.2、使用ItemTouchHelper触摸助手帮我们实现拖拽item
 * 2.3、需要给触摸助手一个回调Callback，我们用来实现拖拽方向，以及实现如何拖拽
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    protected Button mStartPhotoPickerBtn;
    // 展示从多图页面返回的图片
    protected RecyclerView mImgRv;
    private ImgAdapter mAdapter;

    // item拖拽使用的触摸助手
    private ItemTouchHelper mItemTouchHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mStartPhotoPickerBtn = (Button) findViewById(R.id.start_photo_picker_btn);
        mStartPhotoPickerBtn.setOnClickListener(MainActivity.this);
        mImgRv = (RecyclerView) findViewById(R.id.img_rv);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this, 4);
        mImgRv.setLayoutManager(layoutManager);

        mAdapter = new ImgAdapter(this);
        mImgRv.setAdapter(mAdapter);

        mItemTouchHelper = new ItemTouchHelper(null);
        // 将触摸助手依附到RecyclerView上（RecyclerView绑定触摸助手）
        mItemTouchHelper.attachToRecyclerView(mImgRv);
        // item点击/长点击监听
        mImgRv.addOnItemTouchListener(new RecyclerViewOnItemTouchListener(mImgRv) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder holder) {
//                Toast.makeText(MainActivity.this, "点击", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(RecyclerView.ViewHolder holder) {
//                Toast.makeText(MainActivity.this, "长点击", Toast.LENGTH_LONG).show();
                // 拖拽item
                mItemTouchHelper.startDrag(holder);
            }
        });

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start_photo_picker_btn) {
            // 跳转多图选择界面（PhotoPicker提供）
            startPhotoPickerActivity();
        }
    }

    // 跳转多图选择界面（PhotoPicker提供）
    private void startPhotoPickerActivity() {
        PhotoPicker.builder()
                .setPhotoCount(9)// 一次最多选择9张图片
                .setShowCamera(true)// 是否可使用相机现场拍一张照片
                .setShowGif(true)// 是否展示gif图
                .setPreviewEnabled(true)// 是否可预览图片
                .setGridColumnCount(4)// 设置多图页面的网格列数
                .start(this, PhotoPicker.REQUEST_CODE);// startActivityForResult

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 关闭多图选择页面，接收被选中图片的路径集合
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                ArrayList<String> photos =
                        data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);

                mAdapter.addImgPaths(photos);

                for (String path : photos) {
                    Log.d("1511", "path: " + path);
        }
    }
        }
    }
}
