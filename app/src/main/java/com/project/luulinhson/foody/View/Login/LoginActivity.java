package com.project.luulinhson.foody.View.Login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Paint;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.project.luulinhson.foody.R;
import com.project.luulinhson.foody.View.Main.MainActivity;
import com.project.luulinhson.foody.View.Register.RegisterActivity;
import com.project.luulinhson.foody.View.Reminder.ReminderActivity;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener,View.OnClickListener,FirebaseAuth.AuthStateListener {

    GoogleApiClient googleApiClient;
    public static final int REQUEST_SIGIN_GOOGLE = 1111;
    public static int KIEMTRA_PROVIDER_DANG_NHAP = 0;
    Button btnSignInGoogle,btnSignInFacebook;
    FirebaseAuth firebaseAuth;
    CallbackManager callbackManager;
    LoginManager loginManager;
    List<String> permissionFacebook = Arrays.asList("email","public_profile");
    TextView tvQuenMatKhau,tvDangKyMoi;
    EditText edEmailLogin,edMatKhauLogin;
    Button btnDangNhap;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_login);

        firebaseAuth = FirebaseAuth.getInstance();
        loginManager = LoginManager.getInstance();
        firebaseAuth.signOut();

        callbackManager = CallbackManager.Factory.create();

        btnSignInGoogle = (Button) findViewById(R.id.btnSignInGoogle);
        btnSignInFacebook = (Button) findViewById(R.id.btnSignInFacebook);
        tvQuenMatKhau = (TextView) findViewById(R.id.tvQuenMatKhau);
        tvDangKyMoi = (TextView) findViewById(R.id.tvDangKyMoi);
        edEmailLogin = (EditText) findViewById(R.id.edEmailLogin);
        edMatKhauLogin = (EditText) findViewById(R.id.edMatKhauLogin);
        btnDangNhap = (Button) findViewById(R.id.btnDangNhap);
        progressDialog = new ProgressDialog(this);

        tvQuenMatKhau.setPaintFlags(tvQuenMatKhau.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        tvDangKyMoi.setPaintFlags(tvDangKyMoi.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);

        btnSignInGoogle.setOnClickListener(this);
        btnSignInFacebook.setOnClickListener(this);
        tvQuenMatKhau.setOnClickListener(this);
        tvDangKyMoi.setOnClickListener(this);
        btnDangNhap.setOnClickListener(this);

        TaoCliendGoogle();
    }
// Khởi tạo cliend Google dưới thiết bị
    private void TaoCliendGoogle(){
        GoogleSignInOptions signInOptions = new GoogleSignInOptions.Builder()
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this,this)
                .addApi(Auth.GOOGLE_SIGN_IN_API,signInOptions)
                .build();
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id){
            case R.id.btnSignInGoogle:
                DangNhapGoogle(googleApiClient);
                break;
            case R.id.btnSignInFacebook:
                DangNhapFacebook();
                break;
            case R.id.tvDangKyMoi:
                Intent iDangKy = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(iDangKy);
                finish();
                break;
            case R.id.tvQuenMatKhau:
                Intent iReminder = new Intent(LoginActivity.this, ReminderActivity.class);
                startActivity(iReminder);
                finish();
                break;
            case R.id.btnDangNhap:
                DangNhapEmailPassword();
                break;
        }
    }

    private void DangNhapEmailPassword(){
        String email = edEmailLogin.getText().toString();
        String password = edMatKhauLogin.getText().toString();
        Boolean kiemtraemail = Patterns.EMAIL_ADDRESS.matcher(email).matches();

        if(email.trim().equals("") || !kiemtraemail){
            Toast.makeText(LoginActivity.this,"Email không hợp lệ",Toast.LENGTH_SHORT).show();
        }else if(password.trim().equals("")){
            Toast.makeText(LoginActivity.this,"Mật khẩu không được để trống",Toast.LENGTH_SHORT).show();
        }else {
            progressDialog.setMessage("Vui lòng đợi.....");
            progressDialog.setIndeterminate(true);
            progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.my_animation));
            progressDialog.show();
            firebaseAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(LoginActivity.this,"Email hoặc mật khẩu không đúng",Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            });
        }
    }

    private void DangNhapFacebook(){
        loginManager.logInWithReadPermissions(this,permissionFacebook);
        loginManager.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                KIEMTRA_PROVIDER_DANG_NHAP = 2;
                String tokenId = loginResult.getAccessToken().getToken();
                ChungThucDangNhapFirebase(tokenId);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });
    }

// Khởi tạo form đăng nhập của google.Ở đây bạn sẽ điền các thông tin về user và password tài khoản google của bạn
    private void DangNhapGoogle(GoogleApiClient googleApiClient){
        KIEMTRA_PROVIDER_DANG_NHAP = 1; // Đánh dấu để biết chúng ta đang đâng nhập bằng Google
        Intent iGoogle = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(iGoogle,REQUEST_SIGIN_GOOGLE);
    }

// Sau khi điền đầy đủ thông tin đăng nhập google thì dữ liệu sẽ được trả về ở hàm onActivityResult.
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_SIGIN_GOOGLE){
            if(resultCode == RESULT_OK){
                GoogleSignInResult signInResult = Auth.GoogleSignInApi.getSignInResultFromIntent(data); // Lấy dữ liệu trả về
                GoogleSignInAccount account = signInResult.getSignInAccount(); // Từ dữ liệu trả về lấy ra account
                String tokenId = account.getIdToken(); // Từ account lấy ra tokenId
                ChungThucDangNhapFirebase(tokenId);
            }
        }else {
            callbackManager.onActivityResult(requestCode,resultCode,data);
        }
    }

 // Sau khi lấy được tokenId của account ta tiến hành chứng thực tokenId đó đã đăng nhập vào Firebase
    private void ChungThucDangNhapFirebase(String tokenId){
        if(KIEMTRA_PROVIDER_DANG_NHAP == 1){
            AuthCredential authCredential = GoogleAuthProvider.getCredential(tokenId,null);
            firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                }
            });
        }else if(KIEMTRA_PROVIDER_DANG_NHAP == 2){
            AuthCredential authCredential = FacebookAuthProvider.getCredential(tokenId);
            firebaseAuth.signInWithCredential(authCredential);
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(this);
    }
// Hàm lắng nghe sự thay đổi đăng nhập hay đăng xuất của account
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if(firebaseUser != null){
            progressDialog.dismiss();
            Intent iTrangChu = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(iTrangChu);
            finish();
        }else {

        }
    }
}
