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

import static com.example.jangji.carryshare.loginScreen.CONNECTION_TIMEOUT;
import static com.example.jangji.carryshare.loginScreen.LID;
import static com.example.jangji.carryshare.loginScreen.READ_TIMEOUT;

public class currentRentScreen extends AppCompatActivity {

    ArrayList<Suitcase> suitcaseList = new ArrayList<Suitcase>();
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<String> userInfo = new ArrayList<>();
    ArrayList<getDate> startDateList = new ArrayList<getDate>();
    ArrayList<getEndDate> endDateList = new ArrayList<getEndDate>();

    TextView user, startDate, endDate;

    //String loginId, loginPwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_current_rent_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //String query = "?tagID=32733";
        //new selectMySuitcaseInfo.JSONTask().execute(url);
        user = (TextView) findViewById(R.id.userInfo);
        startDate = (TextView) findViewById(R.id.start);
        endDate = (TextView) findViewById(R.id.end);
        /*SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        loginId = auto.getString("inputId",null);*/
    }

    public void rent(View v) {
        userList.clear();
        startDateList.clear();
        endDateList.clear();
        new AsyncRent().execute(LID);
    }

    private class AsyncRent extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL("http://192.168.43.249/checking.php");
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        con = (HttpURLConnection) url.openConnection();
                        con.setReadTimeout(READ_TIMEOUT);
                        con.setConnectTimeout(CONNECTION_TIMEOUT);
                        con.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        con.setDoInput(true);
                        con.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("rentID", params[0]);
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    Log.d("확인", String.valueOf(buffer));
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
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
                userList.add(new User(
//                            data.getString("userID"),
                        data.getString("userName"),
                        data.getString("userPhone")
                ));
                Log.d("시발", "쉣기");//야 내가 스타트데이트리스트 자바 코드로 만들었
                //거든 이거 틀린건가 좀 봐줘.잠시만

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
                ));
//                    userInfo.add(String.valueOf(userList));
//                    startDateList.add(data.getString("startDate"));
//                    endDateList.add(data.getString("endDate"));
                // }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("startDate", String.valueOf(startDateList));

            if (userList.equals("[]")) {
                user.setText("");
                startDate.setText("");
                endDate.setText("");
            } else {
                user.setText(" " + userList.toString());
                startDate.setText(" " + startDateList.toString());
                endDate.setText(" " + endDateList.toString());
            }

        }
    }

    public void regi(View v) {
        userList.clear();
        startDateList.clear();
        endDateList.clear();
        new AsyncRegi().execute(LID);

    }

    private class AsyncRegi extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL("http://192.168.43.249/checking2.php");
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        con = (HttpURLConnection) url.openConnection();
                        con.setReadTimeout(READ_TIMEOUT);
                        con.setConnectTimeout(CONNECTION_TIMEOUT);
                        con.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        con.setDoInput(true);
                        con.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("registerID", params[0]);
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    Log.d("확인", String.valueOf(buffer));
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
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
                userList.add(new User(
//                            data.getString("userID"),
                        data.getString("userName"),
                        data.getString("userPhone")
                ));
                Log.d("시발", "쉣기");//야 내가 스타트데이트리스트 자바 코드로 만들었
                //거든 이거 틀린건가 좀 봐줘.잠시만

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
                ));
//                    userInfo.add(String.valueOf(userList));
//                    startDateList.add(data.getString("startDate"));
//                    endDateList.add(data.getString("endDate"));
                // }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("startDate", String.valueOf(startDateList));

            if (userList.equals("false")) {
                user.setText("");
                startDate.setText("");
                endDate.setText("");
            } else {
                user.setText(" " + userList.toString());
                startDate.setText(" " + startDateList.toString());
                endDate.setText(" " + endDateList.toString());
            }

        }
    }

    public void showMyPage(View view) {
        Intent intent = new Intent(this, myPageScreen.class);
        startActivity(intent);
        finish();
    }

    public void finish(View view) {
        new AsyncDel().execute(LID);
        Intent intent = new Intent(this, myPageScreen.class);
        startActivity(intent);
        finish();
    }

    private class AsyncDel extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try {
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL("http://192.168.43.249/delete.php");
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

                    try {
                        // Setup HttpURLConnection class to send and receive data from php and mysql
                        con = (HttpURLConnection) url.openConnection();
                        con.setReadTimeout(READ_TIMEOUT);
                        con.setConnectTimeout(CONNECTION_TIMEOUT);
                        con.setRequestMethod("POST");

                        // setDoInput and setDoOutput method depict handling of both send and receive
                        con.setDoInput(true);
                        con.setDoOutput(true);

                        // Append parameters to URL
                        Uri.Builder builder = new Uri.Builder()
                                .appendQueryParameter("registerID", params[0]);
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
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    Log.d("확인", String.valueOf(buffer));
                    //다 가져오면 String 형변환을 수행한다. 이유는 protected String doInBackground(String... urls) 니까
                    return buffer.toString();

                    //아래는 예외처리 부분이다.
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    //종료가 되면 disconnect메소드를 호출한다.
                    if (con != null) {
                        con.disconnect();
                    }
                    try {
                        //버퍼를 닫아준다.
                        if (reader != null) {
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
    }
}