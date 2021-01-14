package com.example.jangji.carryshare;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatDialogFragment;

public class shareInfoSelectScreen extends AppCompatActivity {

    Button btnList, btnMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_info_select_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        btnList = (Button) findViewById(R.id.btnList);
        btnMap = (Button) findViewById(R.id.btnMap);

        if (findViewById(R.id.container) != null) {
            if (savedInstanceState != null) {
                return;
            }
            Fragment_Map first = new Fragment_Map();
            first.setArguments(getIntent().getExtras());
            getFragmentManager().beginTransaction().add(R.id.container, first).commit();
        }
    }
    public void select(View view){
        Fragment fragment=null;
        switch(view.getId()){
            case R.id.btnList:fragment=new Fragment_List();
            break;
            case R.id.btnMap:fragment=new Fragment_Map();
            break;}

        FragmentManager fm=getFragmentManager();
        FragmentTransaction transaction=fm.beginTransaction();
        transaction.replace(R.id.container,fragment);
        transaction.addToBackStack(null);
        transaction.commit();}

    public void showSelectSuitcase(View view){
        Intent intent = new Intent(this, selectSuitcaseScreen.class);
        startActivity(intent);
        finish();
    }

}