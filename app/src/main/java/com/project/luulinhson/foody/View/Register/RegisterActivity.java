package com.project.luulinhson.foody.View.Register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.luulinhson.foody.Model.Object.ThanhVien;
import com.project.luulinhson.foody.Presenter.Register.XuLyDangKyThongTinThanhVien;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Login.LoginActivity;
import com.project.luulinhson.foody.View.Reminder.ReminderActivity;

public class RegisterActivity extends AppCompatActivity implements ViewDangKyThongTinThanhVien{

    EditText edMatKhau,edEmail,edNhapLaiMatKhau;
    Button btnDangKy;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;
    XuLyDangKyThongTinThanhVien xuLyDangKyThongTinThanhVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        firebaseAuth = FirebaseAuth.getInstance();

        edEmail = (EditText) findViewById(R.id.edEmail);
        edMatKhau = (EditText) findViewById(R.id.edMatKhau);
        edNhapLaiMatKhau = (EditText) findViewById(R.id.edNhapLaiMatKhau);
        btnDangKy = (Button) findViewById(R.id.btnDangKy);
        progressDialog = new ProgressDialog(this);
        xuLyDangKyThongTinThanhVien = new XuLyDangKyThongTinThanhVien(this);

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = edEmail.getText().toString();
                String password = edMatKhau.getText().toString();
                String rePassword = edNhapLaiMatKhau.getText().toString();
                Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

                if(email.trim().equals("") || !kiemtraemail){
                    Toast.makeText(RegisterActivity.this,"Email không hợp lệ!",Toast.LENGTH_SHORT).show();
                }else if(password.trim().equals("")){
                    Toast.makeText(RegisterActivity.this,"Mật khẩu không được để trống",Toast.LENGTH_SHORT).show();
                }else if(rePassword.trim().equals("") || !rePassword.equals(password)){
                    Toast.makeText(RegisterActivity.this,"Mật khẩu không giống nhau",Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.setMessage("Vui lòng đợi.....");
                    progressDialog.setIndeterminate(true);
                    progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.my_animation));
                    progressDialog.show();
                    firebaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(Task<AuthResult> task) {
                            if(task.isSuccessful()){

                                String userId = task.getResult().getUser().getUid();
                                ThanhVien thanhVien = new ThanhVien();
                                thanhVien.setHoten(email);
                                thanhVien.setHinhanh("user2.png");

                                xuLyDangKyThongTinThanhVien.XuLyDangKyThongTinThanhVien(userId,thanhVien);

                                progressDialog.dismiss();
                                Intent iDangNhap = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(iDangNhap);
                                Toast.makeText(RegisterActivity.this,"Đăng ký thành công!",Toast.LENGTH_SHORT).show();
                                finish();
                            }else {
                                Toast.makeText(RegisterActivity.this,"Đã có lỗi xảy ra trong quá trính đăng ký!Vui lòng thử lại",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            finish();
            Intent iLogin = new Intent(RegisterActivity.this,LoginActivity.class);
            startActivity(iLogin);
        }
        return false;
    }

    @Override
    public void dangKyThanhCong() {

    }

    @Override
    public void dangKyThatBai() {

    }
}
