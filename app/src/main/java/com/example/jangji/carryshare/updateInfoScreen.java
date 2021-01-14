package com.example.jangji.carryshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class updateInfoScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    public void showUpdateLocation(View view){
        Intent intent = new Intent(this, update_location.class);
        startActivity(intent);
        finish();
    }
    public void showUpdateSuitInfo(View view){
        Intent intent = new Intent(this, update_suit_info.class);
        startActivity(intent);
        finish();
    }
    public void showMyPage(View view){
        Intent intent = new Intent(this, myPageScreen.class);
        startActivity(intent);
        finish();
    }

}
