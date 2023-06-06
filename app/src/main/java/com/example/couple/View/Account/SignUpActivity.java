package com.example.couple.View.Account;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.Account.SignUpViewModel;

public class SignUpActivity extends AppCompatActivity implements SignUpView {
    EditText edtName;
    EditText edtPhone;
    EditText edtEmail;
    EditText edtUsername;
    EditText edtPassword;
    EditText edtRepeatPassword;
    Button btnSignUp;

    SignUpViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtName = findViewById(R.id.edtName);
        edtPhone = findViewById(R.id.edtPhone);
        edtEmail = findViewById(R.id.edtEmail);
        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        edtRepeatPassword = findViewById(R.id.edtRepeatPassword);
        btnSignUp = findViewById(R.id.btnSignUp);
        viewModel = new SignUpViewModel(this, this);

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                String phone = edtPhone.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String repeatPassword = edtRepeatPassword.getText().toString().trim();
                if (name.equals("") || email.equals("") || password.equals("") || repeatPassword.equals("")) {
                    ShowError("Bạn cần nhập đầy đủ các trường!");
                } else if (!password.equals(repeatPassword)) {
                    ShowError("Mật khẩu nhập lại không đúng!");
                } else {
                    viewModel.signUp(name, email, password);
                }
            }
        });
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void SignUpSuccess(String name) {
        viewModel.UpdateDisplayName(name);
        Toast.makeText(this, "Đăng ký thành công!", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this, SignInActivity.class));
    }
}
