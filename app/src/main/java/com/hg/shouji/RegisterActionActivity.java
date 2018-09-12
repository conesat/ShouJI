package com.hg.shouji;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.hg.shouji.StaticValues.StaticValues;
import com.hg.shouji.view.ActionView;


public class RegisterActionActivity extends AppCompatActivity implements View.OnClickListener{

    public static RegisterActionActivity registerActionActivity;
    private Button buttonFinish;
    private Button buttonReset;
    private ImageView back;
    private ActionView actionView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_action);
        initView();
        initData();
        initEvent();
    }

    public void initView() {
        buttonFinish = (Button) findViewById(R.id.action_finish);
        buttonReset = (Button) findViewById(R.id.action_reset);
        back = (ImageView) findViewById(R.id.action_back);
        actionView=(ActionView)findViewById(R.id.actionView);
        ViewGroup.LayoutParams lp=actionView.getLayoutParams();
        lp.height=this.getWindowManager().getDefaultDisplay().getWidth();
        Log.d("hgD",lp.height+"");
        actionView.setLayoutParams(lp);
        buttonFinish.setEnabled(false);
        buttonFinish.setBackgroundColor(Color.GRAY);
        buttonReset.setEnabled(false);
        buttonReset.setBackgroundColor(Color.GRAY);

    }

    public void initData() {
        registerActionActivity = this;
    }

    public void initEvent() {
        buttonFinish.setOnClickListener(this);
        buttonReset.setOnClickListener(this);
        back.setOnClickListener(this);
    }

    public void finishAction() {
        buttonFinish.setEnabled(true);
        buttonFinish.setBackgroundColor(Color.rgb(25, 154, 222));
        buttonReset.setEnabled(true);
        buttonReset.setBackgroundColor(Color.rgb(25, 154, 222));
        actionView.setEnabled(false);
    }

    public void resetAction(){
        buttonFinish.setEnabled(false);
        buttonFinish.setBackgroundColor(Color.GRAY);
        buttonReset.setEnabled(false);
        buttonReset.setBackgroundColor(Color.GRAY);
        actionView.setEnabled(true);
        actionView.reSet();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.action_reset:
                resetAction();
                break;
            case R.id.action_finish:
                StaticValues.action=1;
                this.finish();
                break;
            case R.id.action_back:
                this.finish();
                break;
        }
    }
}
