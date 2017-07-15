package com.project.luulinhson.foody.View.Splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Login.LoginActivity;

public class SplashActivity extends AppCompatActivity {
    TextView tvVersion;
    PackageInfo packageInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvVersion = (TextView) findViewById(R.id.tvVersion);

        try {
            packageInfo = getPackageManager().getPackageInfo(getPackageName(),0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        tvVersion.setText("Version" + " " + packageInfo.versionName);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iLogin = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(iLogin);
                finish();
            }
        },2000);


    }
}
