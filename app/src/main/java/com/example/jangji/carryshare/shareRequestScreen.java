package com.example.jangji.carryshare;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Calendar;

import static com.example.jangji.carryshare.Fragment_List.registerID;
import static com.example.jangji.carryshare.loginScreen.LID;

public class shareRequestScreen extends AppCompatActivity {
    String sDate="";
    String eDate="";
    String request2;
    EditText etRequest1;
    boolean suc = false;
    //private String request;
    private  static  final String TAG = "shareRequestScreen";

    private TextView startDate;
    private TextView endDate;
    private DatePickerDialog.OnDateSetListener startDateListener;
    private DatePickerDialog.OnDateSetListener endDateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_request_screen);

        etRequest1 = (EditText)findViewById(R.id.etR);



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        startDate = (TextView)findViewById(R.id.tvStartDay);
        endDate = (TextView)findViewById(R.id.tvEndDay);

        startDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        shareRequestScreen.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        startDateListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        endDate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        shareRequestScreen.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        endDateListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        startDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year1, int month1, int day1) {

                Log.d(TAG,"onDateSet : date:" + year1 + "/" + (month1+1) + "/" +day1);

                sDate = year1 +"년 " + (month1+1) +"월 "+ day1 + "일";
                startDate.setText(sDate);
                Log.d("시작날", sDate);
            }
        };
        endDateListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year2, int month2, int day2) {

                Log.d(TAG,"onDateSet : date:" + year2 + "/" + (month2+1) + "/" +day2);

                eDate = year2 +"년 " + (month2+1) +"월 "+ day2 + "일";
                endDate.setText(eDate);
                Log.d("끝날", eDate);
            }
        };

    }
    public void showMain(View view){

        Log.d("리퀘스트1", "리퀘스트1");
        Log.d("리퀘스트2", String.valueOf(etRequest1));
        request2 = etRequest1.getText().toString();
        Log.d("리퀘스트", request2);
        if (sDate.equals("") || eDate.equals("")) {
            Toast.makeText(shareRequestScreen.this,"날짜를 선택해 주세요.",Toast.LENGTH_LONG).show();
        }else {
            insertoToDatabase(LID, registerID, sDate, eDate, request2);
            Intent intent = new Intent(this, mainScreen.class);
            startActivity(intent);
            finish();
        }
    }

    private void insertoToDatabase(String LID, String registerID, String stDate, String edDate, String request) {
        Log.d("로그인2", LID);

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(shareRequestScreen.this,
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
                    Intent registerIntent = new Intent(shareRequestScreen.this, mainScreen.class);
                    shareRequestScreen.this.startActivity(registerIntent);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    Log.d("로그인 파람", params[0]);
                    String LID = (String) params[0];
                    String registerID = (String) params[1];
                    String stDate = (String) params[2];
                    String edDate = (String) params[3];
                    String request = (String) params[4];


                    String link = "http://192.168.43.249/rentInfo.php";
                    String data = URLEncoder.encode("rentID", "UTF-8") + "=" + URLEncoder.encode(LID, "UTF-8");
                    data += "&" + URLEncoder.encode("registerID", "UTF-8") + "=" + URLEncoder.encode(registerID, "UTF-8");
                    data += "&" + URLEncoder.encode("startDate", "UTF-8") + "=" + URLEncoder.encode(stDate, "UTF-8");
                    data += "&" + URLEncoder.encode("endDate", "UTF-8") + "=" + URLEncoder.encode(edDate, "UTF-8");
                    data += "&" + URLEncoder.encode("request", "UTF-8") + "=" + URLEncoder.encode(request, "UTF-8");
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
        task.execute(LID, registerID, stDate, edDate, request);
    }

    public void showshareinfoSelect(View view){

        Intent intent = new Intent(this, shareInfoSelectScreen.class);
        startActivity(intent);
        finish();
    }

}