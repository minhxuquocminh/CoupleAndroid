package com.example.couple.View.Account;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.couple.R;
import com.example.couple.ViewModel.Account.ChangePasswordViewModel;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePasswordView {
    EditText edtOldPassword;
    EditText edtNewPassword;
    EditText edtRepeatPassword;
    Button btnUpdate;

    ChangePasswordViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        edtOldPassword = findViewById(R.id.edtOldPassword);
        edtNewPassword = findViewById(R.id.edtNewPassword);
        edtRepeatPassword = findViewById(R.id.edtRepeatPassword);
        btnUpdate = findViewById(R.id.btnUpdate);

        viewModel = new ChangePasswordViewModel(this, this);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = edtOldPassword.getText().toString().trim();
                String newPassword = edtOldPassword.getText().toString().trim();
                String repeatPassword = edtRepeatPassword.getText().toString().trim();

                if (oldPassword.isEmpty() || newPassword.isEmpty() || repeatPassword.isEmpty()) {
                    showMessage("Bạn phải nhập đầy đủ các trường!");
                } else if (!newPassword.equals(repeatPassword)) {
                    showMessage("Mật khẩu nhập lại không khớp!");
                } else {
                    viewModel.checkOldPassword(oldPassword, newPassword);
                }
            }
        });

    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void checkOldPasswordSuccess(String newPassword) {
        viewModel.updatePassword(newPassword);
    }

    @Override
    public void updatePasswordSuccess(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        finish();
    }
}
