package com.project.luulinhson.foody.View.Reminder;

import android.content.Intent;
import android.graphics.Paint;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Register.RegisterActivity;

public class ReminderActivity extends AppCompatActivity{

    EditText edEmailKP;
    Button btnKhoiPhucMatKhau;
    TextView tvDangKyMoiKP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        edEmailKP = (EditText) findViewById(R.id.edEmailKP);
        btnKhoiPhucMatKhau = (Button) findViewById(R.id.btnNhanMatKhau);
        tvDangKyMoiKP = (TextView) findViewById(R.id.tvDangKyMoiKP);

        tvDangKyMoiKP.setPaintFlags(tvDangKyMoiKP.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnKhoiPhucMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmailKP.getText().toString();
                Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if(email.trim().equals("") || !kiemtraemail){
                    Toast.makeText(ReminderActivity.this,"Email không hợp lệ!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        tvDangKyMoiKP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent iRegister = new Intent(ReminderActivity.this, RegisterActivity.class);
                startActivity(iRegister);
                finish();
            }
        });
    }


}
