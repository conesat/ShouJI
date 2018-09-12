package com.hg.shouji;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;
import android.support.v4.os.CancellationSignal;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.hg.shouji.StaticValues.StaticValues;


public class RegisterFingerActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private Button buttonBreak;
    private Button buttonFinsh;
    private ImageView finger_img;

    //指纹
    private FingerprintManagerCompat fingerprintManager;

    private CancellationSignal mCancellationSignal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_finger);
        initView();
        initData();
        initEvent();
    }

    public void initData() {
        fingerprintManager = FingerprintManagerCompat.from(this);
    }

    public void initView() {
        back = (ImageView) findViewById(R.id.finger_back);
        buttonBreak = (Button) findViewById(R.id.finger_break);
        finger_img = (ImageView) findViewById(R.id.finger_img);
        buttonFinsh = (Button) findViewById(R.id.finger_finish);
        buttonFinsh.setEnabled(false);
        if (StaticValues.finger == 1) {
            finger_img.setImageTintList(ColorStateList.valueOf(Color.rgb(25, 152, 222)));
            buttonFinsh.setEnabled(true);
        }
    }

    public void initEvent() {
        back.setOnClickListener(this);
        buttonBreak.setOnClickListener(this);
        buttonFinsh.setOnClickListener(this);
        FingerprintManagerCompat.AuthenticationCallback myPingerCalled = new FingerprintManagerCompat.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errMsgId, CharSequence errString) {
                super.onAuthenticationError(errMsgId, errString);
                finger_img.setImageTintList(ColorStateList.valueOf(Color.rgb(255, 60, 0)));
                StaticValues.finger = 2;
                buttonFinsh.setEnabled(true);
                RegisterFingerActivity.this.finish();
            }

            @Override
            public void onAuthenticationHelp(int helpMsgId, CharSequence helpString) {
                super.onAuthenticationHelp(helpMsgId, helpString);
            }

            @Override
            public void onAuthenticationSucceeded(FingerprintManagerCompat.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                StaticValues.finger = 1;
                RegisterFingerActivity.this.finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                finger_img.setImageTintList(ColorStateList.valueOf(Color.rgb(200, 200, 0)));
                StaticValues.finger = 2;
                buttonFinsh.setEnabled(true);
            }
        };

        mCancellationSignal = new CancellationSignal();

        fingerprintManager.authenticate(null, 0, mCancellationSignal, myPingerCalled, null);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.finger_back:
                mCancellationSignal.cancel();
                this.finish();
                break;
            case R.id.finger_break:
                StaticValues.finger=2;
                mCancellationSignal.cancel();
                this.finish();
                break;
            case R.id.finger_finish:
                mCancellationSignal.cancel();
                this.finish();
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mCancellationSignal.cancel();
    }
}
