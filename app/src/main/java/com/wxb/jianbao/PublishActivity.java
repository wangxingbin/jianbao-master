package com.wxb.jianbao;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.publish_Bean;
import utils.OkHttpUtilzzq;

/**
 * Created by big_cow on 2016/11/30.
 */

public class PublishActivity extends Activity implements View.OnClickListener{
    private static final String TAG = "PublishActivity";
    private ImageView publish_imgs, publish_imgs2, publish_imgs3, publish_imgs4, publish_imgs5
            ,im_et_cha,im_et_cha2,im_et_cha3,im_et_cha4,im_et_cha5;
    private ImageView[] imageViews = new ImageView[5];
    private List<Fragment> list = new ArrayList<Fragment>();
    private int mCurrentPageIndex;
    private int mScreen1_3;
    private EditText et_jianbao_biaoti, et_jianbao_miaoshu, et_jianbao_number, et_jianbao_price;
    private String str_jianbao_biaoti, str_jianbao_miaoshu, str_jianbao_number, str_jianbao_price, str_jianbao_email;
    private TextView tv_jianbao_fabu;
    private HashMap<String, String> map;
    private EditText et_jianbao_email;
    private ArrayList<String> imgs;
    private ArrayList<Bitmap> bitmaps;

    //  private ScrollableLayout mScrollLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.jianbao_publish_item);
        initView();
        initData();
        intitButton();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        // String img = intent.getStringExtra("img");
        imgs = (ArrayList<String>) bundle.getSerializable("imgs");
        bitmaps = (ArrayList<Bitmap>) bundle.getSerializable("bitmaps");

//        if (imgs != null && !imgs.isEmpty()) {
//            for (int i = 0; i < imgs.size(); i++) {
//                String photo_one = imgs.get(i);
//                String new_photo_one = photo_one.replace("file://", "");
//                Bitmap bitmap_new = BitmapFactory.decodeFile(new_photo_one);
//                imageViews[i].setImageBitmap(bitmap_new);
//

        if (bitmaps != null && !bitmaps.isEmpty()) {
            for (int i = 0; i < bitmaps.size(); i++) {
                Bitmap bitmap = bitmaps.get(i);
                imageViews[i].setBackground(new BitmapDrawable(bitmap));
            }
        } else {
            Toast.makeText(this, "添加图片效果会更加哟", Toast.LENGTH_SHORT).show();
        }
    }

    //查找控件
    private void initView() {
        publish_imgs = (ImageView) findViewById(R.id.iv_publish_picture); // 存放图片的Imgs
        publish_imgs2 = (ImageView) findViewById(R.id.iv_publish_picture2);
        publish_imgs3 = (ImageView) findViewById(R.id.iv_publish_picture3);
        publish_imgs4 = (ImageView) findViewById(R.id.iv_publish_picture4);
        publish_imgs5 = (ImageView) findViewById(R.id.iv_publish_picture5);
        imageViews[0] = publish_imgs;
        imageViews[1] = publish_imgs2;
        imageViews[2] = publish_imgs3;
        imageViews[3] = publish_imgs4;
        imageViews[4] = publish_imgs5;
        et_jianbao_biaoti = (EditText) findViewById(R.id.et_jiianbao_biaoti); //标题
        et_jianbao_miaoshu = (EditText) findViewById(R.id.et_jianbao_miaoshu); //描述
        et_jianbao_number = (EditText) findViewById(R.id.et_jianbao_number);//手机号
        et_jianbao_price = (EditText) findViewById(R.id.et_jianbao_price);// 价格;
        et_jianbao_email = (EditText) findViewById(R.id.et_jianbao_email); //社交软件
        tv_jianbao_fabu = (TextView) findViewById(R.id.tv_jianbao_fabu);
        et_jianbao_biaoti.addTextChangedListener(textWatcher);
        et_jianbao_miaoshu.addTextChangedListener(textWatcher);
        im_et_cha = (ImageView) findViewById(R.id.im_et_x1); // x
        im_et_cha2 = (ImageView) findViewById(R.id.im_et_x4); // x
        im_et_cha3 = (ImageView) findViewById(R.id.im_et_x3); // x
        im_et_cha4 = (ImageView) findViewById(R.id.im_et_x2); // x
        im_et_cha5 = (ImageView) findViewById(R.id.im_et_x5); // x
        im_et_cha.setOnClickListener(PublishActivity.this);
        im_et_cha2.setOnClickListener(PublishActivity.this);
        im_et_cha3.setOnClickListener(PublishActivity.this);
        im_et_cha4.setOnClickListener(PublishActivity.this);
        im_et_cha5.setOnClickListener(PublishActivity.this);

    }
    // 点击×  清除EditTextq
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.im_et_x1:
                et_jianbao_biaoti.setText("");
                break;
            case R.id.im_et_x3:
                et_jianbao_price.setText("");
                break;
            case R.id.im_et_x4:
                et_jianbao_number.setText("");
                break;
            case R.id.im_et_x2:
                et_jianbao_miaoshu.setText("");
                break;
            case R.id.im_et_x5:
                et_jianbao_email.setText("");
                break;
        }

    }

    // 上传数据
    private void initUploading() {
        //intiData();
        // OkHttp接口回调
        OkHttpUtilzzq.setGetEntiydata(new OkHttpUtilzzq.EntiyData() {
            @Override
            public void getEntiy(Object obj) {

                publish_Bean publish_o1 = (publish_Bean) obj;
                String status = publish_o1.getStatus();
                if (status.equals("200")) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(PublishActivity.this, "发布成功", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        OkHttpUtilzzq.postMuti(map, "http://192.168.4.188/Goods/"
                + "app/item/issue.json", PublishActivity.this, publish_Bean.class, imgs);
    }

    private void intiData() {
        str_jianbao_biaoti = et_jianbao_biaoti.getText().toString().trim();
        str_jianbao_miaoshu = et_jianbao_miaoshu.getText().toString().trim();
        str_jianbao_price = et_jianbao_price.getText().toString().trim();
        str_jianbao_number = et_jianbao_number.getText().toString().trim();
        str_jianbao_email = et_jianbao_email.getText().toString().trim();
        map = new HashMap<>();
        map.put("title", str_jianbao_biaoti);
        map.put("description", str_jianbao_miaoshu);
        map.put("price", str_jianbao_price);
        map.put("mobile", str_jianbao_number);
        map.put("email", str_jianbao_email);
        SharedPreferences share= getSharedPreferences("TOKEN",MODE_PRIVATE);
        String token = share.getString("token", "ll");
        map.put("token",token);

    }

    private void intitButton() { //点击发布

        tv_jianbao_fabu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intiData();
                if ("".equals(str_jianbao_biaoti)) {
                    Toast.makeText(PublishActivity.this, "亲，怎们可以没有标题呢", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_miaoshu)) {
                    Toast.makeText(PublishActivity.this, "亲，描述下你的宝贝", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_price)) {
                    Toast.makeText(PublishActivity.this, "无价之宝，别闹了，留个价格呗", Toast.LENGTH_SHORT).show();
                } else if ("".equals(str_jianbao_number)) {
                    Toast.makeText(PublishActivity.this, "亲，留下电话方便联系呦", Toast.LENGTH_SHORT).show();
                } else {
                    initUploading();
                }
                finish();
            }
        });
    }

    // 判断Edittext最多输入多少字符
    TextWatcher textWatcher = new TextWatcher() {

        private Toast toast;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            //TODO
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                                  int count) {
            //TODO
        }

        @Override
        public void afterTextChanged(Editable s) {

            Log.d("TAG", "afterTextChanged    " + "str=" + s.toString());
            int len = et_jianbao_biaoti.getText().toString().length();
            int len2 = et_jianbao_miaoshu.getText().toString().length();
            if (len >= 9) {
                Toast.makeText(PublishActivity.this, "亲,最多输入10个字呦", Toast.LENGTH_SHORT).show();
                et_jianbao_biaoti.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)}); //最多输入10的字符
            }
            if (len >= 29) {
                Toast.makeText(PublishActivity.this, "亲,最多输入30个字呦", Toast.LENGTH_SHORT).show();
                et_jianbao_miaoshu.setFilters(new InputFilter[]{new InputFilter.LengthFilter(30)}); //最多输入30的字符
            }
        }
    };


    /**
     * 验证手机格式
     */
    public static boolean isMobileNO(String mobiles) {
    /*
    移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
    联通：130、131、132、152、155、156、185、186
    电信：133、153、180、189、（1349卫通）
    总结起来就是第一位必定为1，第二位必定为3或5或8，其他位置的可以为0-9
    */
        String et_jianbao_number = "[1][358]\\d{9}";//"[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
        if (TextUtils.isEmpty(mobiles)) {
            return false;
        } else {
            return mobiles.matches(et_jianbao_number);
        }
    }
    /*
     * @param event the event
     * @return true, if successful
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (PublishActivity.this.getCurrentFocus() != null) {
                if (PublishActivity.this.getCurrentFocus().getWindowToken() != null) {
                    imm.hideSoftInputFromWindow(PublishActivity.this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                }
            }
        }
        return super.onTouchEvent(event);
    }
}


