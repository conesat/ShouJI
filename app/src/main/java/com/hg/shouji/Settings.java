package com.hg.shouji;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private ImageView back;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        back = (ImageView) findViewById(R.id.settings_back);
        login = (Button) findViewById(R.id.login);
        back.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.settings_back:
                this.finish();
                break;
            case R.id.login:
                startActivity(new Intent(Settings.this, LoginActivity.class));
                break;
        }
    }
}
