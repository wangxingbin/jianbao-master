package com.wxb.jianbao;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import utils.ImageTools;

/**
 * Created by big_cow on 2016/11/29.
 */

public class TakePhotoActivity extends Activity implements View.OnClickListener {

    private static final int TAKE_PICTURE = 0; // 相机
    private static final int CHOOSE_PICTURE = 1; // 图片
    //private static final int SCALE = 5;//照片缩小比例
    private ImageView takePhoto_picture, takePhotot_photo, takePhoto_picture2,
            takePhoto_picture3, takePhoto_picture4, takePhoto_picture5, yichu, yichu2, yichu3, yichu4, yichu5;
    private ImageView takePhotot_text;
    private Button takePhoto_jump;
    //定义一个临时变量，用于存储拍照后的图片名字
    private int takePhotoName = 0;
    //拍照的路径
    private String takePhotoPath;

    // 存放图片路径
    private ArrayList<String> mList = new ArrayList<String>();
    private ArrayList<Bitmap> mBitmapList = new ArrayList<Bitmap>();

    //    private HashMap<ImageView, PictureType> map = new HashMap<ImageView, PictureType>();
    private ImageView[] imageViews = new ImageView[5];
    private ImageView[] xImageViews = new ImageView[5];

    private Uri originalUri;
    private Bitmap smallBitmap;
    private Bitmap newBitmap;
    private RelativeLayout rl_xx1;
    private RelativeLayout rl_xx2;
    private RelativeLayout rl_xx3;
    private RelativeLayout rl_xx4;
    private RelativeLayout rl_xx5;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.takephoto_item);
        initView(); // 查找控件
        initButton_photo();  // 拍照
        initButoon_jump(); // 跳转
        hideImageView(); // 隐藏控件

    }

    // 点击移除图片
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.im_x1:
                resetImageView(0);
                break;
            case R.id.im_x2:
//                takePhoto_picture2.setImageBitmap(null);
//                yichu2.setImageBitmap(null);
                resetImageView(1);
                break;
            case R.id.im_x3:
//                takePhoto_picture3.setImageBitmap(null);
//                yichu3.setImageBitmap(null);
//                map.remove(2);
                resetImageView(2);
                break;
            case R.id.im_x4:
//                takePhoto_picture4.setImageBitmap(null);
//                yichu4.setImageBitmap(null);
//                 map.remove(3);
                resetImageView(3);
                break;
            case R.id.im_x5:
//                takePhoto_picture5.setImageBitmap(null);
//                yichu5.setImageBitmap(null);
//                map.remove(4);
                resetImageView(4);
                break;
        }
    }

    private void resetImageView(int position) {

//        takePhoto_picture.setImageBitmap(null);
//        takePhoto_picture2.setImageBitmap(null);
//        takePhoto_picture3.setImageBitmap(null);
//        takePhoto_picture4.setImageBitmap(null);
//        takePhoto_picture5.setImageBitmap(null);
//        yichu.setImageBitmap(null);
//        yichu2.setImageBitmap(null);
//        yichu3.setImageBitmap(null);
//        yichu4.setImageBitmap(null);
//        yichu5.setImageBitmap(null);

        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i].setVisibility(View.INVISIBLE);
            xImageViews[i].setVisibility(View.INVISIBLE);
        }
        mBitmapList.remove(position);
        for (int i = 0; i < mBitmapList.size(); i++) {
            imageViews[i].setImageBitmap(mBitmapList.get(i));
            imageViews[i].setVisibility(View.VISIBLE);
            xImageViews[i].setVisibility(View.VISIBLE);
        }
    }


    private void initView() {
        rl_xx1 = (RelativeLayout) findViewById(R.id.rl_xx1);
        rl_xx2 = (RelativeLayout) findViewById(R.id.rl_xx2);
        rl_xx3 = (RelativeLayout) findViewById(R.id.rl_xx3);
        rl_xx4 = (RelativeLayout) findViewById(R.id.rl_xx4);
        rl_xx5 = (RelativeLayout) findViewById(R.id.rl_xx5);
        takePhotot_photo = (ImageView) findViewById(R.id.takephoto_photo);// 相机
        takePhotot_text = (ImageView) findViewById(R.id.takephoto_text);  // 相册
        takePhoto_picture = (ImageView) findViewById(R.id.takephoto_picture); // 图片
        takePhoto_picture2 = (ImageView) findViewById(R.id.takephoto_picture2);   // 图片
        takePhoto_picture3 = (ImageView) findViewById(R.id.takephoto_picture3); // 图片
        takePhoto_picture4 = (ImageView) findViewById(R.id.takephoto_picture4); // 图片
        takePhoto_picture5 = (ImageView) findViewById(R.id.takephoto_picture5); // 图片
        imageViews[0] = takePhoto_picture;
        imageViews[1] = takePhoto_picture2;
        imageViews[2] = takePhoto_picture3;
        imageViews[3] = takePhoto_picture4;
        imageViews[4] = takePhoto_picture5;


        yichu = (ImageView) findViewById(R.id.im_x1); // x图片
        yichu2 = (ImageView) findViewById(R.id.im_x2); // x图片
        yichu3 = (ImageView) findViewById(R.id.im_x3); // x图片
        yichu4 = (ImageView) findViewById(R.id.im_x4); // x图片
        yichu5 = (ImageView) findViewById(R.id.im_x5); // x图片


        xImageViews[0] = yichu;
        xImageViews[1] = yichu2;
        xImageViews[2] = yichu3;
        xImageViews[3] = yichu4;
        xImageViews[4] = yichu5;

        yichu.setOnClickListener(TakePhotoActivity.this);
        yichu2.setOnClickListener(TakePhotoActivity.this);
        yichu3.setOnClickListener(TakePhotoActivity.this);
        yichu4.setOnClickListener(TakePhotoActivity.this);
        yichu5.setOnClickListener(TakePhotoActivity.this);
        takePhoto_jump = (Button) findViewById(R.id.takephtot_jump); //发表

        /*
        imageViews = new ImageView[]
                {takePhoto_picture, takePhoto_picture2, takePhoto_picture3, takePhoto_picture4, takePhoto_picture5};*/

    }

    // 跳转发布界面
    private void initButoon_jump() {
        takePhoto_jump.setOnClickListener(new View.OnClickListener() {
            @Override
            // ISLOGIN
            public void onClick(View view) {
              /*  if ("ISLOGIN".equals(bean.getStatus())){
                    // 跳转到发布界面
                }else{
                    //跳转到登录界面
                }*/
                Intent intent2 = new Intent(TakePhotoActivity.this, PublishActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("imgs", mList);
                bundle.putSerializable("bitmaps", mBitmapList);
                intent2.putExtras(bundle);
                startActivity(intent2);
                finish();
            }
        });
    }

    // 相机相册点击事件
    private void initButton_photo() {
        takePhotot_photo.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

                takePhotoName++;
                takePhotoPath = Environment.getExternalStorageDirectory() + "/image" + takePhotoName + ".jpg";
                Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                Uri imageUri = Uri.fromFile(new File(takePhotoPath));
                //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                startActivityForResult(openCameraIntent, TAKE_PICTURE);

            }
        });
        takePhotot_text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT);
                openAlbumIntent.setType("image/*");
                startActivityForResult(openAlbumIntent, CHOOSE_PICTURE);
            }
        });
    }

    //返回的时候
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    takePhoto();
                    break;

                case CHOOSE_PICTURE:
                    takePicture(data);
                    break;

                default:
                    break;
            }
        }
    }

    private void takePicture(Intent data) {
        ContentResolver resolver = getContentResolver();
        //照片的原始资源地址
        originalUri = data.getData();
        try {
            //使用ContentProvider通过URI获取原始图片
            Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
            if (photo != null) {
                //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
//                smallBitmap = ImageTools.zoomLittleBitmap(photo, photo.getWidth() / SCALE,
//                        photo.getHeight() / SCALE);
                smallBitmap = ImageTools.zoomLittleBitmap(photo);
                //释放原始图片占用的内存，防止out of memory异常发生
                photo.recycle();
                switch (mBitmapList.size()) {
                    case 0:


                        takePhoto_picture.setImageBitmap(smallBitmap);
                        takePhoto_picture.setVisibility(View.VISIBLE);
                        rl_xx1.setVisibility(View.VISIBLE);
                        yichu.setVisibility(View.VISIBLE);
                        //     map.put(takePhoto_picture, PictureType.PICTURE);
                        //map.put(yichu, PictureType.PICTURE);
                        String s = originalUri.toString();
                        mList.add(s);
                        mBitmapList.add(smallBitmap);

                        break;
                    case 1:
                        takePhoto_picture2.setImageBitmap(smallBitmap);
                        takePhoto_picture2.setVisibility(View.VISIBLE);
                        rl_xx2.setVisibility(View.VISIBLE);
                        yichu2.setVisibility(View.VISIBLE);
                        //  map.put(takePhoto_picture2, PictureType.PICTURE);
                        //map.put(yichu2, PictureType.PICTURE);
                        Uri picture_uri2 = data.getData();
                        // 把Uri转化成String类型
                        String s2 = originalUri.toString();
                        mList.add(s2);
                        mBitmapList.add(smallBitmap);
                        break;
                    case 2:
                        takePhoto_picture3.setImageBitmap(smallBitmap);
                        takePhoto_picture3.setVisibility(View.VISIBLE);
                        rl_xx3.setVisibility(View.VISIBLE);
                        yichu3.setVisibility(View.VISIBLE);
                        //     map.put(takePhoto_picture3, PictureType.PICTURE);
                        // map.put(yichu3, PictureType.PICTURE);
                        Uri picture_uri3 = data.getData();
                        String s3 = originalUri.toString();
                        mList.add(s3);
                        mBitmapList.add(smallBitmap);
                        break;
                    case 3:
                        takePhoto_picture4.setImageBitmap(smallBitmap);
                        takePhoto_picture4.setVisibility(View.VISIBLE);
                        rl_xx4.setVisibility(View.VISIBLE);
                        yichu4.setVisibility(View.VISIBLE);
                        //    map.put(takePhoto_picture4, PictureType.PICTURE);
                        //map.put(yichu4, PictureType.PICTURE);
                        Uri picture_uri4 = data.getData();
                        String s4 = originalUri.toString();
                        mList.add(s4);
                        mBitmapList.add(smallBitmap);
                        break;
                    case 4:
                        takePhoto_picture5.setImageBitmap(smallBitmap);
                        takePhoto_picture5.setVisibility(View.VISIBLE);
                        rl_xx5.setVisibility(View.VISIBLE);
                        yichu5.setVisibility(View.VISIBLE);
                        //     map.put(takePhoto_picture5, PictureType.PICTURE);
                        // map.put(yichu5, PictureType.PICTURE);
                        Uri picture_uri5 = data.getData();
                        String s5 = originalUri.toString();
                        mList.add(s5);
                        mBitmapList.add(smallBitmap);
                        break;
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    private void takePhoto() {
        //将保存在本地的图片取出并缩小后显示在界面上
        Bitmap bitmap = BitmapFactory.decodeFile(takePhotoPath);
        newBitmap = ImageTools.zoomLittleBitmap(bitmap);
        //由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常
        bitmap.recycle();
        //将处理过的图片显示在界面上，
        switch (mBitmapList.size()) {
            // 如果位置等于0的时候我们往HashMap中添加第一个图片，以后就以此类推
            case 0:
                takePhoto_picture.setImageBitmap(newBitmap);
                takePhoto_picture.setVisibility(View.VISIBLE);
                rl_xx1.setVisibility(View.VISIBLE);
                yichu.setVisibility(View.VISIBLE);
                //yichu.setVisibility(View.VISIBLE);
                //  map.put(takePhoto_picture, PictureType.PHOTO);
                //map.put(yichu, PictureType.PICTURE);
                mList.add(takePhotoPath);
                mBitmapList.add(newBitmap);
                break;
            case 1:
                takePhoto_picture2.setImageBitmap(newBitmap);
                takePhoto_picture2.setVisibility(View.VISIBLE);
                rl_xx2.setVisibility(View.VISIBLE);
                yichu2.setVisibility(View.VISIBLE);
                //yichu2.setVisibility(View.VISIBLE);
                //  map.put(takePhoto_picture2, PictureType.PHOTO);
                //map.put(yichu2, PictureType.PICTURE);
                mList.add(takePhotoPath);
                mBitmapList.add(newBitmap);
                break;
            case 2:
                takePhoto_picture3.setImageBitmap(newBitmap);
                takePhoto_picture3.setVisibility(View.VISIBLE);
                rl_xx3.setVisibility(View.VISIBLE);
                yichu3.setVisibility(View.VISIBLE);
                //   map.put(takePhoto_picture3, PictureType.PHOTO);
                //map.put(yichu3, PictureType.PICTURE);
                mList.add(takePhotoPath);
                mBitmapList.add(newBitmap);
                break;
            case 3:
                takePhoto_picture4.setImageBitmap(newBitmap);
                takePhoto_picture4.setVisibility(View.VISIBLE);
                rl_xx4.setVisibility(View.VISIBLE);
                yichu4.setVisibility(View.VISIBLE);
                //    map.put(takePhoto_picture4, PictureType.PHOTO);
                //map.put(yichu4, PictureType.PICTURE);
                mList.add(takePhotoPath);
                mBitmapList.add(newBitmap);
                break;
            case 4:
                takePhoto_picture5.setImageBitmap(newBitmap);
                takePhoto_picture5.setVisibility(View.VISIBLE);
                rl_xx5.setVisibility(View.VISIBLE);
                yichu5.setVisibility(View.VISIBLE);
                //     map.put(takePhoto_picture5, PictureType.PHOTO);
                //map.put(yichu5, PictureType.PICTURE);
                mList.add(takePhotoPath);
                mBitmapList.add(newBitmap);
                break;
        }

        // 并保存到本地

        ImageTools.savePhotoToSDCard(newBitmap, Environment.getExternalStorageDirectory().getAbsolutePath(),
                String.valueOf(System.currentTimeMillis()));
    }


    private void hideImageView() {

        //隐藏空间但是还占用位置
        rl_xx1.setVisibility(View.INVISIBLE);
        rl_xx2.setVisibility(View.INVISIBLE);
        rl_xx3.setVisibility(View.INVISIBLE);
        rl_xx4.setVisibility(View.INVISIBLE);
        rl_xx5.setVisibility(View.INVISIBLE);

    }


    // 第一个枚举
    enum PictureType {
        PHOTO, PICTURE
    }

}
