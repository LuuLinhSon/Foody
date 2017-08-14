package com.project.luulinhson.foody.View.Splash;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.pdf.PdfDocument;
import android.location.Location;
import android.os.Handler;

import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.instantapps.ActivityCompat;
import com.google.android.gms.location.LocationServices;
import com.project.luulinhson.foody.Manifest;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Login.LoginActivity;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    TextView tvVersion;
    PackageInfo packageInfo;
    GoogleApiClient mGoogleApiClient;
    public static final int REQUEST_CODE_PERMISSON_COURCE_LOCATION = 1111;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        tvVersion = (TextView) findViewById(R.id.tvVersion);

        sharedPreferences = getSharedPreferences("toadohientai",MODE_PRIVATE);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        int checkPermissonCourceLocation = ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION);
        if (checkPermissonCourceLocation != PackageManager.PERMISSION_GRANTED) {
            android.support.v4.app.ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_CODE_PERMISSON_COURCE_LOCATION);

        } else {
            mGoogleApiClient.connect();
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CODE_PERMISSON_COURCE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mGoogleApiClient.connect();
                }
                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        mGoogleApiClient.disconnect();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Location viTriHienTai = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(viTriHienTai != null){
            Log.d("vitrihietai", "onConnected: " + viTriHienTai.getLongitude() + " " + viTriHienTai.getLatitude());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("latitude", String.valueOf(viTriHienTai.getLatitude()));
            editor.putString("longtitude", String.valueOf(viTriHienTai.getLongitude()));
            editor.commit();

        }

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

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
