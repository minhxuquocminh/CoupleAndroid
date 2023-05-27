package com.example.couple.View.Main;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.couple.R;
import com.example.couple.View.Main.CreateNumberArray.CreateNumberArrayFragment;
import com.example.couple.View.Main.HomePage.HomePageFragment;
import com.example.couple.View.Main.NumberPicker.NumberPickerFragment;
import com.example.couple.View.PredictionBridge.PredictionBridgeActivity;
import com.example.couple.View.Main.Personal.PersonalFragment;
import com.example.couple.View.Main.FunctionDisplay.FunctionDisplayFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView navigationView;

    FragmentManager fm = getSupportFragmentManager();
    public static Fragment fragment1 = new HomePageFragment();
    public static Fragment fragment2 = new NumberPickerFragment();
    public static Fragment fragment3 = new CreateNumberArrayFragment();
    public static Fragment fragment4 = new FunctionDisplayFragment();
    public static Fragment fragment5 = new PersonalFragment();
    public static Fragment active = fragment1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navigationView = findViewById(R.id.bottom_navigation);

        fm.beginTransaction().add(R.id.container, fragment5, "5").hide(fragment5).commit();
        fm.beginTransaction().add(R.id.container, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.container, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.container, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.container, fragment1, "1").commit();

//        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem menuItem) {
//                switch (menuItem.getItemId()) {
//                    case R.id.itHome:
//                        if (active != fragment1) {
//                            fm.beginTransaction().show(fragment1).hide(active).commit();
//                            active = fragment1;
//                        }
//                        return true;
//                    case R.id.itCreateNumbers:
//                        if (active != fragment2) {
//                            fm.beginTransaction().show(fragment2).hide(active).commit();
//                            active = fragment2;
//                        }
//                        return true;
//                    case R.id.itPredictionBridge:
//                        if (active != fragment3) {
//                            fm.beginTransaction().show(fragment3).hide(active).commit();
//                            active = fragment3;
//                        }
////                        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
////                        if (firebaseUser == null) {
////                            Toast.makeText(MainActivity.this,
////                                    "Bạn cần phải đăng nhập trước khi sử dụng chức năng này!",
////                                    Toast.LENGTH_SHORT).show();
////                            return false;
////                        } else {
////
////                        }
//                        return true;
//                    case R.id.itFunction:
//                        if (active != fragment4) {
//                            fm.beginTransaction().show(fragment4).hide(active).commit();
//                            active = fragment4;
//                        }
//                        return true;
//                    case R.id.itPersonal:
//                        if (active != fragment5) {
//                            fm.beginTransaction().show(fragment5).hide(active).commit();
//                            active = fragment5;
//                        }
//                        return true;
//                }
//                return false;
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        System.exit(0);
    }

}