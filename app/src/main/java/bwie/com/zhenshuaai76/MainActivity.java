package bwie.com.zhenshuaai76;

import android.content.Intent;
import android.graphics.Picture;
import android.icu.util.LocaleData;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.blankj.utilcode.util.SPUtils;
import com.bumptech.glide.Glide;
import com.hb.dialog.myDialog.ActionSheetDialog;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.config.PictureConfig;
import com.luck.picture.lib.config.PictureMimeType;
import com.luck.picture.lib.entity.LocalMedia;
import com.luck.picture.lib.tools.PictureFileUtils;

import org.w3c.dom.Entity;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import bwie.com.zhenshuaai76.entry.DataEntry;
import bwie.com.zhenshuaai76.mvp.Controller;
import bwie.com.zhenshuaai76.mvp.PresenterImpl;
import bwie.com.zhenshuaai76.net.HttpUtils;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends AppCompatActivity implements Controller.View {

    private Controller.Presenter presenter;
    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter=new PresenterImpl();
        presenter.attach(this);
        img = findViewById(R.id.img);
        /*if(!SPUtils.getInstance().getString("Image",null).equals("")){
            String image = SPUtils.getInstance().getString("Image", null);
            Glide.with(MainActivity.this).load(image).into(img);
        }*/
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActionSheetDialog dialog=new ActionSheetDialog(MainActivity.this).builder().setTitle("请选择")
                        .addSheetItem("相册", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int i) {
                                         PictureSelector.create(MainActivity.this)
                                                 //打开相册
                                        .openGallery(PictureMimeType.ofImage())
                                                 //压缩
                                        .compress(true)
                                        .forResult(PictureConfig.CHOOSE_REQUEST);
                            }
                        }).addSheetItem("相机", null, new ActionSheetDialog.OnSheetItemClickListener() {
                            @Override
                            public void onClick(int i) {
                              PictureSelector.create(MainActivity.this)
                                      //打开相机
                             .openCamera(PictureMimeType.ofImage())
                                      //压缩
                            .compress(true)
                            .forResult(PictureConfig.CHOOSE_REQUEST);
                            }});
                dialog.show();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            switch (requestCode){
                case PictureConfig.CHOOSE_REQUEST:
                    List<LocalMedia> selectList=PictureSelector.obtainMultipleResult(data);
                    String compressPath = selectList.get(0).getCompressPath();
                    File file = new File(compressPath);
                    RequestBody requestBody = MultipartBody.create(MediaType.parse("image/*"), file);
                    MultipartBody.Part part = MultipartBody.Part.createFormData("image", file.getName(), requestBody);
                    HashMap<String,String> parms=new HashMap<>();
                    parms.put("userId","4796");
                    parms.put("sessionId","15624028074034796");
                    presenter.getHttp(parms,part);
                    break;
            }
        }
    }

    @Override
    public void getView(DataEntry dataEntry) {
        if (dataEntry.getMessage().equals("上传成功")){
            PictureFileUtils.deleteCacheDirFile(MainActivity.this);
            Glide.with(MainActivity.this).load(dataEntry.getHeadPath()).into(img);
            SPUtils.getInstance().clear();
            SPUtils.getInstance().put("Image",dataEntry.getHeadPath());
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.pttach();
    }

}
