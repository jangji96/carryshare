package com.example.jangji.carryshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.jangji.carryshare.loginScreen.LID;
import static com.example.jangji.carryshare.registerLocationScreen.latitude1;
import static com.example.jangji.carryshare.registerLocationScreen.longitude1;

public class shareInfoScreen extends AppCompatActivity {

    Spinner spinnerSize,spinnerType;
    String[] itemSize,itemType;
    EditText won;
    String strLong = Double.toString(longitude1);
    String strLati = Double.toString(latitude1);
    boolean suc = false;
    public static final String regiID = LID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_info_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        spinnerSize = (Spinner)findViewById(R.id.suitcaseSize);
        spinnerType = (Spinner)findViewById(R.id.suitcaseType);

        itemSize = new String[]{"선택하세요","12~16인치","17~20인치","21~24인치","25~28인치","29~32인치"};
        itemType = new String[]{"선택하세요","하드커버 - 바퀴4","하드커버 - 바퀴2","소프트커버 - 바퀴4","소프트커버 - 바퀴2"};

        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itemSize);
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerSize.setAdapter(adapter1);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,itemType);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinnerType.setAdapter(adapter2);

        won = findViewById(R.id.won);
        won.addTextChangedListener(new NumberTextWatcher(won));



    }
    private void insertoToDatabase(String LID, String size1, String type1, String cost1, String Latitude1, String Longitude1) {
        Log.d("로그인2", LID);

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(shareInfoScreen.this,
                        "Please Wait", null, true, true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Log.d("asdfasdfasdf", s);
                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("zzzzzzzz", String.valueOf(s.length()));
                if (s.length() == 7) {
                    suc = false;
                } else {
                    suc = true;
                }
                if (suc) {
                    Intent registerIntent = new Intent(shareInfoScreen.this, mainScreen.class);
                    shareInfoScreen.this.startActivity(registerIntent);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    Log.d("로그인 파람", params[0]);
                    String LID = (String) params[0];
                    String size1 = (String) params[1];
                    String type1 = (String) params[2];
                    String cost1 = (String) params[3];
                    String Latitude1 = (String) params[4];
                    String Longitude1 = (String) params[5];


                    String link = "http://192.168.43.249/register.php";
                    String data = URLEncoder.encode("userID", "UTF-8") + "=" + URLEncoder.encode(LID, "UTF-8");
                    data += "&" + URLEncoder.encode("size", "UTF-8") + "=" + URLEncoder.encode(size1, "UTF-8");
                    data += "&" + URLEncoder.encode("type", "UTF-8") + "=" + URLEncoder.encode(type1, "UTF-8");
                    data += "&" + URLEncoder.encode("cost", "UTF-8") + "=" + URLEncoder.encode(cost1, "UTF-8");
                    data += "&" + URLEncoder.encode("Latitude", "UTF-8") + "=" + URLEncoder.encode(Latitude1, "UTF-8");
                    data += "&" + URLEncoder.encode("Longitude", "UTF-8") + "=" + URLEncoder.encode(Longitude1, "UTF-8");
                    Log.d("테스트", String.valueOf(params[1]));
                    Log.d("dafsdf", String.valueOf(params));
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    Log.d("테스트", "테스트");

                    conn.setDoOutput(true);

                    Log.d("테스트1", "테스트1");

                    OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream());

                    wr.write(data);
                    wr.flush();

                    Log.d("테스트2", "테스트2");
                    Log.d("테스트 msg", String.valueOf(conn.getInputStream()));
                    BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line = null;
                    Log.d("테스트3", "테스트2");

                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                        Log.d("테스트4", "테스트2");
                        break;
                    }
                    return sb.toString();

                } catch (Exception e) {
                    e.printStackTrace();
                    return new String("Error: " + e.getMessage());
                } finally {
                    Log.d("fuck", "adfadfadfadf");
                }
            }
        }
        InsertData task = new InsertData();
        Log.d("테스트 task123", String.valueOf(task));
        task.execute(LID, size1, type1, cost1, Latitude1, Longitude1);
    }
    public void showRegisterLocation(View view){
        Intent intent = new Intent(this, registerLocationScreen.class);
        startActivity(intent);
        finish();
    }
    public void showMain(View view){

        String size1 = spinnerSize.getSelectedItem().toString();
        String type1 = spinnerType.getSelectedItem().toString();
        String cost1 = "";
        cost1 = won.getText().toString();
        //String Latitude1 = longitude;
        //String Longitude1 = (String)latitude;

        Log.d("등록테스트", size1);
        Log.d("등록테스트", type1);
        Log.d("등록테스트", cost1);
        Log.d("등록테스트", strLati);
        if(size1 =="선택하세요" || type1 == "선택하세요" || cost1.equals("")) {

            Toast.makeText(shareInfoScreen.this,"정보를 모두 입력해 주세요.",Toast.LENGTH_LONG).show();
        }else {
            insertoToDatabase(LID, size1, type1, cost1,strLati, strLong);

            Log.d("코스트테스트",cost1);
            Intent intent = new Intent(this, mainScreen.class);
            startActivity(intent);
            finish();
        }
    }

}