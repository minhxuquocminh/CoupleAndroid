package com.example.couple.View.Main.Personal;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.couple.Base.Handler.InternetBase;
import com.example.couple.R;
import com.example.couple.View.Account.ChangePasswordActivity;
import com.example.couple.View.Account.SignInActivity;
import com.example.couple.View.Account.SignUpActivity;
import com.example.couple.ViewModel.Main.Personal.PersonalViewModel;

public class PersonalFragment extends Fragment implements PersonalView {
    TextView tvName;
    TextView tvUserName;
    TextView tvSignIn;
    TextView tvSignUp;
    LinearLayout linearBaseUrl;
    LinearLayout linearAddData;
    LinearLayout linearChangePassword;
    Button btnSignOut;

    PersonalViewModel viewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal, container, false);

        tvName = view.findViewById(R.id.tvName);
        tvUserName = view.findViewById(R.id.tvUserName);
        tvSignIn = view.findViewById(R.id.tvSignIn);
        tvSignUp = view.findViewById(R.id.tvSignUp);
        linearBaseUrl = view.findViewById(R.id.linearBaseUrl);
        linearAddData = view.findViewById(R.id.linearAddData);
        linearChangePassword = view.findViewById(R.id.linearChangePassword);
        btnSignOut = view.findViewById(R.id.btnSignOut);

        viewModel = new PersonalViewModel(getActivity(), this);

        viewModel.CheckLoggedIn();

        linearBaseUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), UrlAndParamsActivity.class));
            }
        });

        linearAddData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), AddJackpotManyYearsActivity.class));
            }
        });

        return view;
    }

    @Override
    public void ShowError(String message) {
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void LoggedIn(String name, String email) {
        tvName.setVisibility(View.VISIBLE);
        tvUserName.setVisibility(View.VISIBLE);
        tvSignIn.setVisibility(View.GONE);
        tvSignUp.setVisibility(View.GONE);
        tvName.setText(name);
        tvUserName.setText(email);
        linearChangePassword.setVisibility(View.VISIBLE);
        btnSignOut.setVisibility(View.VISIBLE);

        linearChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
            }
        });

        btnSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (InternetBase.isNetworkAvailable(getActivity())) {
                    viewModel.SignOut();
                } else {
                    ShowError("Bạn đang offline.");
                }
            }
        });
    }

    @Override
    public void NotLoggedIn() {
        tvName.setVisibility(View.GONE);
        tvUserName.setVisibility(View.GONE);
        tvSignIn.setVisibility(View.VISIBLE);
        tvSignUp.setVisibility(View.VISIBLE);
        linearChangePassword.setVisibility(View.GONE);
        btnSignOut.setVisibility(View.GONE);

        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignInActivity.class));
            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), SignUpActivity.class));
            }
        });
    }

    @Override
    public void SignOutSuccess() {
        Toast.makeText(getActivity(), "Bạn đã đăng xuất!", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(getActivity(), SignInActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}
