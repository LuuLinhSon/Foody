package com.project.luulinhson.foody.View.Reminder;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Login.LoginActivity;
import com.project.luulinhson.foody.View.Register.RegisterActivity;

public class ReminderActivity extends AppCompatActivity{

    EditText edEmailKP;
    Button btnKhoiPhucMatKhau;
    TextView tvDangKyMoiKP,tvBanKhongPhaiThanhVien;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        firebaseAuth = FirebaseAuth.getInstance();

        edEmailKP = (EditText) findViewById(R.id.edEmailKP);
        btnKhoiPhucMatKhau = (Button) findViewById(R.id.btnNhanMatKhau);
        tvDangKyMoiKP = (TextView) findViewById(R.id.tvDangKyMoiKP);
        tvBanKhongPhaiThanhVien = (TextView) findViewById(R.id.tvBanKhongPhaiThanhVien);
        progressDialog = new ProgressDialog(this);

        tvDangKyMoiKP.setPaintFlags(tvDangKyMoiKP.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnKhoiPhucMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmailKP.getText().toString();
                Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();
                if(email.trim().equals("") || !kiemtraemail){
                    Toast.makeText(ReminderActivity.this,"Email không hợp lệ!",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Vui lòng đợi.....");
                    progressDialog.setIndeterminate(true);
                    progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.my_animation));
                    progressDialog.show();
                    firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(ReminderActivity.this,"Vui lòng kiểm tra Email để lấy lại mật khẩu",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                                Intent iLogin = new Intent(ReminderActivity.this, LoginActivity.class);
                                startActivity(iLogin);
                                finish();
                            }else {
                                Toast.makeText(ReminderActivity.this,"Đã xảy ra lỗi trong quá trình gửi email.Vui lòng thử lại",Toast.LENGTH_LONG).show();
                                progressDialog.dismiss();
                            }
                        }
                    });
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

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            Intent iLogin = new Intent(ReminderActivity.this,LoginActivity.class);
            startActivity(iLogin);
        }
        return false;
    }
}
