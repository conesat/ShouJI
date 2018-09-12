package com.hg.shouji;

import android.app.KeyguardManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.hg.shouji.StaticValues.StaticValues;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private int type = 0;

    private ImageView back;
    private ViewPager viewPager;
    private PagerAdapter mAdpater;

    private List<View> mTabs = new ArrayList<View>();

    private TextView tabText1;
    private ImageView tabImage1;
    private ImageView tabImage3;
    private TextView tabText3;
    private TextView tabText2;
    private ImageView tabImage2;

    //邮箱验证
    private EditText email;
    private EditText verify1;
    private EditText verify2;
    private EditText verify3;
    private EditText verify4;

    private Button getVerify;
    private Button next;
    private Button next1;
    private Button befor;
    private Button befor1;


    //特征三个按钮
    private ImageView finger;
    private ImageView face;
    private ImageView action;

    //指纹
    private FingerprintManagerCompat fingerprintManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initView();
        initData();
        initEvent();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.register_back:
                StaticValues.reset();
                this.finish();
                break;
            case R.id.register_next:
                type = 1;
                viewPager.setCurrentItem(type);
                break;
            case R.id.register_next1:
                type = 2;
                viewPager.setCurrentItem(type);
                break;
            case R.id.register_finger:
                startActivity(new Intent(RegisterActivity.this, RegisterFingerActivity.class));
                break;
            case R.id.register_face:
                startActivity(new Intent(RegisterActivity.this, RegisterFaceActivity.class));
                break;
            case R.id.register_action:
                startActivity(new Intent(RegisterActivity.this,RegisterActionActivity.class));
                break;
            case R.id.register_befor:
                type = 0;
                viewPager.setCurrentItem(type);
                break;
            case R.id.register_befor1:
                type = 1;
                viewPager.setCurrentItem(type);
                break;
        }
    }

    public void initData() {
        //初始化ViewPager的适配器
        mAdpater = new PagerAdapter() {
            @Override
            public int getCount() {
                return mTabs.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mTabs.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mTabs.get(position));
            }
        };
        //设置ViewPager的适配器
        viewPager.setAdapter(mAdpater);

        // Using the Android Support Library v4
        fingerprintManager = FingerprintManagerCompat.from(this);
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(this.KEYGUARD_SERVICE);
        if (!fingerprintManager.isHardwareDetected()) {
            finger.setImageResource(R.drawable.no);
            finger.setEnabled(false);

        } else if (!keyguardManager.isKeyguardSecure()) {
            finger.setImageResource(R.drawable.no);
            finger.setEnabled(false);
            // no fingerprint sensor is detected, show dialog to tell user.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("未启用密码");
            builder.setMessage("在使用指纹识别前需要您前往设置添加pin或图形密码");
            builder.setIcon(R.drawable.icon);
            builder.setCancelable(false);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            // show this dialog.
            builder.create().show();
        } else if (!fingerprintManager.hasEnrolledFingerprints()) {
            finger.setImageResource(R.drawable.no);
            finger.setEnabled(false);
            // no fingerprint sensor is detected, show dialog to tell user.
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("未录入指纹");
            builder.setMessage("在使用指纹识别前需要您前往设置添加指纹");
            builder.setIcon(R.drawable.icon);
            builder.setCancelable(false);
            builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            // show this dialog.
            builder.create().show();
        }
    }

    public void initView() {
        viewPager = (ViewPager) findViewById(R.id.register_viewpager);
        back = (ImageView) findViewById(R.id.register_back);
        LayoutInflater inflater = LayoutInflater.from(this);
        View tab1 = inflater.inflate(R.layout.register_tab1, null);
        View tab2 = inflater.inflate(R.layout.register_tab2, null);
        View tab3 = inflater.inflate(R.layout.register_tab3, null);

        mTabs.add(tab1);
        mTabs.add(tab2);
        mTabs.add(tab3);


        tabText1 = (TextView) findViewById(R.id.register_tabtText1);
        tabText2 = (TextView) findViewById(R.id.register_tabtText2);
        tabText3 = (TextView) findViewById(R.id.register_tabtText3);
        tabImage1 = (ImageView) findViewById(R.id.register_tabImage1);
        tabImage2 = (ImageView) findViewById(R.id.register_tabImage2);
        tabImage3 = (ImageView) findViewById(R.id.register_tabImage3);
        //验证码界面
        email = (EditText) tab1.findViewById(R.id.register_email);
        verify1 = (EditText) tab1.findViewById(R.id.register_verify_num1);
        verify2 = (EditText) tab1.findViewById(R.id.register_verify_num2);
        verify3 = (EditText) tab1.findViewById(R.id.register_verify_num3);
        verify4 = (EditText) tab1.findViewById(R.id.register_verify_num4);
        getVerify = (Button) tab1.findViewById(R.id.register_getverify);
        next = (Button) tab1.findViewById(R.id.register_next);
        next1 = (Button) tab2.findViewById(R.id.register_next1);
        befor = (Button) tab2.findViewById(R.id.register_befor);
        befor1 = (Button) tab3.findViewById(R.id.register_befor1);

        //三个特征按钮
        finger = (ImageView) tab3.findViewById(R.id.register_finger);
        face = (ImageView) tab3.findViewById(R.id.register_face);
        action = (ImageView) tab3.findViewById(R.id.register_action);

    }

    public void initEvent() {
        back.setOnClickListener(this);


        //添加ViewPager的切换Tab的监听事件
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                viewPager.setCurrentItem(type);
                //将所以的ImageButton设置成灰色
                resetTab();
                //将当前Tab对应的ImageButton设置成绿色
                switch (type) {
                    case 0:
                        tabText1.setTextSize(18);
                        tabImage1.setBackgroundColor(Color.argb(255, 25, 154, 222));
                        break;
                    case 1:
                        tabText2.setTextSize(18);
                        tabImage2.setBackgroundColor(Color.argb(255, 25, 154, 222));
                        break;
                    case 2:
                        tabText3.setTextSize(18);
                        tabImage3.setBackgroundColor(Color.argb(255, 25, 154, 222));
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        verify1.addTextChangedListener(new JumpTextWatcher(1));
        verify2.addTextChangedListener(new JumpTextWatcher(2));
        verify3.addTextChangedListener(new JumpTextWatcher(3));
        verify4.addTextChangedListener(new JumpTextWatcher(4));

        next.setOnClickListener(this);
        next1.setOnClickListener(this);

        finger.setOnClickListener(this);
        face.setOnClickListener(this);
        action.setOnClickListener(this);

        befor.setOnClickListener(this);
        befor1.setOnClickListener(this);
    }

    public void resetTab() {
        tabText1.setTextSize(16f);
        tabImage1.setBackgroundColor(Color.argb(0, 25, 154, 222));
        tabText2.setTextSize(16f);
        tabImage2.setBackgroundColor(Color.argb(0, 25, 154, 222));
        tabText3.setTextSize(16f);
        tabImage3.setBackgroundColor(Color.argb(0, 25, 154, 222));
    }

    public class JumpTextWatcher implements TextWatcher {

        private int num;

        public JumpTextWatcher(int num) {
            this.num = num;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String str = s.toString();
            if (str.length() > 0) {
                switch (num) {
                    case 1:
                        verify2.requestFocus();
                        verify2.setSelection(verify2.getText().length());
                        break;
                    case 2:
                        verify3.requestFocus();
                        verify3.setSelection(verify3.getText().length());
                        break;
                    case 3:
                        verify4.requestFocus();
                        verify4.setSelection(verify4.getText().length());
                        break;
                }
            } else if (str.length() == 0) {
                switch (num) {
                    case 2:
                        verify1.requestFocus();
                        verify1.setSelection(verify1.getText().length());
                        break;
                    case 3:
                        verify2.requestFocus();
                        verify2.setSelection(verify2.getText().length());
                        break;
                    case 4:
                        verify3.requestFocus();
                        verify3.setSelection(verify3.getText().length());
                        break;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (StaticValues.finger == 1) {
            finger.setImageResource(R.drawable.right);
        } else if (StaticValues.finger == 2) {
            finger.setImageResource(R.drawable.no);
        }
        if (StaticValues.face == 1) {
            face.setImageResource(R.drawable.right);
        }else if (StaticValues.face == 2) {
            face.setImageResource(R.drawable.no);
        }
        if (StaticValues.action == 1) {
            action.setImageResource(R.drawable.right);
        }else if (StaticValues.action == 2) {
            action.setImageResource(R.drawable.no);
        }
    }

    public void restState() {
        finger.setImageBitmap(null);
        face.setImageBitmap(null);
        action.setImageBitmap(null);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        StaticValues.reset();
    }
}
