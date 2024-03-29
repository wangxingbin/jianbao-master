package mainframe;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.wxb.jianbao.R;
import com.wxb.jianbao.TakePhotoActivity;

import fragment.MineFragment;
import fragment.ShowFragment;

/**
 * Created by 诺古 on 2016/11/26.
 */
/*
整个程序最底层的框架Activity
 */
public class MainFrameAcitvity extends FragmentActivity {
    PublishDialog pDialog;
    RadioGroup rg;
    private MineFragment mine;//个人主页
    private android.support.v4.app.Fragment contentFragment;
    private FragmentManager fragmentManager;
    private RadioButton shouye_tv;
    private RadioButton mine_tv;
    private RadioButton add_iv;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_frame);
        pDialog = new PublishDialog(MainFrameAcitvity.this);
        initView();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        contentFragment = new ShowFragment();
        transaction.replace(R.id.main_frame_layout, contentFragment);
        transaction.commit();
        initData();
        initEvent();
    }

    private void initData() {

        add_iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pDialog.setArticleBtnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainFrameAcitvity.this, TakePhotoActivity.class);
                        Bundle bundle = new Bundle();
                        intent.putExtras(bundle);
                        startActivity(intent);
                        pDialog.dismiss();
                    }
                });
                pDialog.setPhotoBtnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        Toast toast = Toast.makeText(getApplicationContext(),
                                "功能正在完善，请关注下个版本", Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.FILL_HORIZONTAL, 200, -50);
                        toast.show();
                    }
                });
                pDialog.show();
            }
        });

    }

    // 获取相机相册需要从写这个方法


    private void initEvent() {
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                switch (checkedId) {
                    case R.id.tv_zhuye:
                        initShouye();
                        contentFragment = new ShowFragment();
                        transaction.replace(R.id.main_frame_layout, contentFragment);
                        break;
                    case R.id.tv_mine:
                        initMine();
                        contentFragment = new MineFragment();
                        transaction.replace(R.id.main_frame_layout, contentFragment);
                        break;
                    default:
                        break;
                }
                transaction.commit();
            }
        });
    }

    private void initView() {
        rg = (RadioGroup) findViewById(R.id.id_rg);
        shouye_tv = (RadioButton) findViewById(R.id.tv_zhuye);
        mine_tv = (RadioButton) findViewById(R.id.tv_mine);
        add_iv = (RadioButton) findViewById(R.id.tv_add);

        fragmentManager = getSupportFragmentManager();
    }

    private void initShouye() {
        shouye_tv.setTextColor(Color.parseColor("#000000"));
        mine_tv.setTextColor(Color.parseColor("#a9b7b7"));
    }

    private void initMine() {
        shouye_tv.setTextColor(Color.parseColor("#a9b7b7"));
        mine_tv.setTextColor(Color.parseColor("#000000"));
    }

}
