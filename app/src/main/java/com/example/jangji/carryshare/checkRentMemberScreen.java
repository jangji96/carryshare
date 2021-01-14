package com.example.jangji.carryshare;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.jangji.carryshare.checkShareIfoScreen.renID;
import static com.example.jangji.carryshare.loginScreen.CONNECTION_TIMEOUT;
import static com.example.jangji.carryshare.loginScreen.LID;
import static com.example.jangji.carryshare.loginScreen.READ_TIMEOUT;

public class checkRentMemberScreen extends AppCompatActivity {

    ArrayList<Suitcase> suitcaseList = new ArrayList<Suitcase>();
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<User_id> userName = new ArrayList<User_id>();
    ArrayList<String> userInfo = new ArrayList<>();
    ArrayList<getDate> startDateList = new ArrayList<getDate>();
    ArrayList<getEndDate> endDateList = new ArrayList<getEndDate>();
    ArrayList<registerRequest> registerRequestList = new ArrayList<registerRequest>();
    ArrayList<Request> RequestList = new ArrayList<Request>();

    TextView checkMember,checkStartDate,checkEndDate,checkRequest;
    String a,b,c,d;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_rent_member_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        checkMember = (TextView) findViewById(R.id.checkMember);
        checkStartDate = (TextView) findViewById(R.id.checkStartDate);
        checkEndDate = (TextView) findViewById(R.id.checkEndDate);
        checkRequest = (TextView) findViewById(R.id.checkRequest);

        new AsyncRent().execute(LID,renID);

    }
    private class AsyncRent extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL("http://192.168.43.249/updateShare.php");
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        con = (HttpURLConnection)url.openConnection();
                        con.setReadTimeout(READ_TIMEOUT);
                        con.setConnectTimeout(CONNECTION_TIMEOUT);
                        con.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        con.setDoInput(true);
                        con.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("registerID", params[0])
                                .appendQueryParameter("rentID", params[1]);
//                                .appendQueryParameter("check",params[2]);

                                String query = builder.build().getEncodedQuery();

                        // Open connection for sending data

                        OutputStream os = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));

                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();
                        con.connect();

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        return "exception";
                    }
                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    Log.d("확인", String.valueOf(buffer));
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("resData", result);

            try {
                JSONArray resData = new JSONArray(result);
                Log.d("resData1", String.valueOf(resData));
                Log.d("resData2", String.valueOf(resData.length()));
                //for(int i = 0; i <= resData.length(); i++){
                JSONObject data = resData.getJSONObject(0);
                Log.d("user[" + 0 + "]: ", data.toString());
                userName.add(new User_id(
                        a=data.getString("userName")
                ));
                data = resData.getJSONObject(1);
                Log.d("user[" + 0 + "]: ", data.toString());
                startDateList.add(new getDate(
                        b=data.getString("startDate")
                ));
                data = resData.getJSONObject(1);
                endDateList.add(new getEndDate(
                        c=data.getString("endDate")
                ));
                data = resData.getJSONObject(1);
                RequestList.add(new Request(
                        d=data.getString("request")
                ));

/*
                data = resData.getJSONObject(1);
                Log.d("user[" + 1 + "]: ", data.toString());
                startDateList.add(new getDate(
                        data.getString("startDate")
                        //이거지금 로그부분봐보 이렇게들어오면 되는거임? ㅇㅇㅇ
                ));
                Log.d("시발", "쉣기");

                data = resData.getJSONObject(2);
                Log.d("user[" + 2 + "]: ", data.toString());
                endDateList.add(new getEndDate(
                        data.getString("endDate")
                ));*/
//                    userInfo.add(String.valueOf(userList));
//                    startDateList.add(data.getString("startDate"));
//                    endDateList.add(data.getString("endDate"));
                // }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            //Log.d("startDate", String.valueOf(startDateList));


            checkMember.setText(" " + a);
            checkStartDate.setText(" " + b);
            checkEndDate.setText(" " + c);
            checkRequest.setText(" " + d);
        }
    }

    public void showCheckShareInfo(View view){
        Intent intent = new Intent(this, checkShareIfoScreen.class);
        startActivity(intent);
        finish();
    }
    public void refusal(View view){
        String stateCheck = "2";
        new AsyncCheck().execute(LID,renID,stateCheck);
        Intent intent = new Intent(this, checkShareIfoScreen.class);
        startActivity(intent);
        finish();
    }
    public void accept(View view){
        String stateCheck = "1";
        new AsyncCheck().execute(LID,renID,stateCheck);
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
        finish();
    }
    private class AsyncCheck extends AsyncTask<String, String, String>
    {
        @Override
        protected String doInBackground(String... params) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL("http://192.168.43.249/shareAccept.php");
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        con = (HttpURLConnection)url.openConnection();
                        con.setReadTimeout(READ_TIMEOUT);
                        con.setConnectTimeout(CONNECTION_TIMEOUT);
                        con.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        con.setDoInput(true);
                        con.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("registerID", params[0])
                                .appendQueryParameter("rentID", params[1])
                                .appendQueryParameter("check",params[2]);

                        String query = builder.build().getEncodedQuery();

                        // Open connection for sending data

                        OutputStream os = con.getOutputStream();
                        BufferedWriter writer = new BufferedWriter(
                                new OutputStreamWriter(os, "UTF-8"));

                        writer.write(query);
                        writer.flush();
                        writer.close();
                        os.close();
                        con.connect();

                    } catch (IOException e1) {
                        // TODO Auto-generated catch block
                        e1.printStackTrace();
                        return "exception";
                    }
                    //입력 스트림 생성
                    InputStream stream = con.getInputStream();

                    //속도를 향상시키고 부하를 줄이기 위한 버퍼를 선언한다.
                    reader = new BufferedReader(new InputStreamReader(stream));

                    //실제 데이터를 받는곳
                    StringBuffer buffer = new StringBuffer();

                    //line별 스트링을 받기 위한 temp 변수
                    String line = "";

                    //아래라인은 실제 reader에서 데이터를 가져오는 부분이다. 즉 node.js서버로부터 데이터를 가져온다.
                    while((line = reader.readLine()) != null){
                        buffer.append(line);
                    }
                    Log.d("확인", String.valueOf(buffer));
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e){
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if(con != null){
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if(reader != null){
                            reader.close();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }//finally 부분
            } catch (Exception e) {
                e.printStackTrace();
            }

            return null;
        }

        //doInBackground메소드가 끝나면 여기로 와서 텍스트뷰의 값을 바꿔준다.
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d("resData", result);
        }
    }

}