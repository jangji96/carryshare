package com.example.jangji.carryshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class myPageScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_page_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void showUpdateMember(View view){
        Intent intent = new Intent(this, updateMemberScreen.class);
        startActivity(intent);
        finish();
    }
    public void showUpdateShare(View view){
        Intent intent = new Intent(this, updateInfoScreen.class);
        startActivity(intent);
        finish();
    }
    public void showCheckRent(View view){
        Intent intent = new Intent(this, checkRentScreen.class);
        startActivity(intent);
        finish();
    }
    public void showCheckShareInfo(View view){
        Intent intent = new Intent(this, checkShareIfoScreen.class);
        startActivity(intent);
        finish();
    }
    public void showCurrentRent(View view){
        Intent intent = new Intent(this, currentRentScreen.class);
        startActivity(intent);
        finish();
    }
    public void showMain(View view){
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
        finish();
    }


}
