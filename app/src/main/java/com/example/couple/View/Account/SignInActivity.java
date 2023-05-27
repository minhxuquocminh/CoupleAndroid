package com.example.couple.View.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.View.Main.MainActivity;
import com.example.couple.ViewModel.Account.SignInViewModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseNetworkException;

public class SignInActivity extends AppCompatActivity implements SignInView {
    Button btnHelp;
    EditText edtUsername;
    EditText edtPassword;
    TextView tvForgetPassword;
    TextView tvSignUpLabel;
    Button btnSignIn;

    SignInViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        btnHelp=findViewById(R.id.btnHelp);
        edtUsername=findViewById(R.id.edtUsername);
        edtPassword=findViewById(R.id.edtPassword);
        tvForgetPassword=findViewById(R.id.tvForgetPassword);
        tvSignUpLabel=findViewById(R.id.tvSignUpLabel);
        btnSignIn=findViewById(R.id.btnSignIn);

        viewModel=new SignInViewModel(this,this);

        btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this,
                        "Vui lòng liên hệ 1900 100 có để dược hướng dẫn!",Toast.LENGTH_SHORT).show();
            }
        });
        tvForgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(SignInActivity.this,
                        "Vui lòng liên hệ 1900 100 có để dược hướng dẫn!",Toast.LENGTH_SHORT).show();
            }
        });

        tvSignUpLabel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SignInActivity.this,SignUpActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if(username.equals("") || password.equals("")){
                    ShowError("Bạn phải nhập đầy đủ tên đăng nhập và mật khẩu!");
                }else {
                    viewModel.SignIn(username, password);
                }
            }
        });

    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SignInSuccess() {
        startActivity(new Intent(this, MainActivity.class));
    }
}
