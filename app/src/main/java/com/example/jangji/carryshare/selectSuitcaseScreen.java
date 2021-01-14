package com.example.jangji.carryshare;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class selectSuitcaseScreen extends AppCompatActivity {

    Spinner spinnerSize,spinnerType;
    String[] itemSize,itemType;
    public static String rentSize,rentType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_suitcase_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        spinnerSize = (Spinner)findViewById(R.id.suitcaseSize);
        spinnerType = (Spinner)findViewById(R.id.suitcaseType);

        itemSize = new String[]{"모두보기","12~16인치","17~20인치","21~24인치","25~28인치","29~32인치"};
        itemType = new String[]{"모두보기","하드커버 - 바퀴4","하드커버 - 바퀴2","소프트커버 - 바퀴4","소프트커버 - 바퀴2"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itemSize);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSize.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itemType);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapter2);
    }


    public void showMain(View view){
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
        finish();
    }
    public void showshareinfoSelect(View view){

        rentSize = spinnerSize.getSelectedItem().toString();
        rentType = spinnerType.getSelectedItem().toString();

        Intent intent = new Intent(this, shareInfoSelectScreen.class);
        startActivity(intent);
        finish();
    }

}