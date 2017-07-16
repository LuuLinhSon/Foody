package com.project.luulinhson.foody.View.Register;

import android.app.ProgressDialog;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.project.luulinhson.foody.R;

public class RegisterActivity extends AppCompatActivity {

    EditText edMatKhau,edEmail,edNhapLaiMatKhau;
    Button btnDangKy;
    FirebaseAuth firebaseAuth;
    ProgressDialog progressDialog;

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

        btnDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edEmail.getText().toString();
                String password = edMatKhau.getText().toString();
                String rePassword = edNhapLaiMatKhau.getText().toString();

                if(email.trim().equals("")){
                    Toast.makeText(RegisterActivity.this,"Email không được để trống",Toast.LENGTH_SHORT).show();
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
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            progressDialog.dismiss();
                            Toast.makeText(RegisterActivity.this,"Đăng ký thành công",Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }
}
