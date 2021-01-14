package com.example.jangji.carryshare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.jangji.carryshare.loginScreen.CONNECTION_TIMEOUT;
import static com.example.jangji.carryshare.loginScreen.READ_TIMEOUT;


public class joinScreen extends Activity {

    private EditText mEditTextUserID;
    private EditText mEditTextUserPassword;
    private EditText mEditTextUserName;
    private EditText mEditTextUserPhone;
    boolean suc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_screen);


        mEditTextUserID = (EditText) findViewById(R.id.new_id);
        mEditTextUserPassword = (EditText) findViewById(R.id.new_pw);
        mEditTextUserName = (EditText) findViewById(R.id.new_name);
        mEditTextUserPhone = (EditText) findViewById(R.id.new_phone);


    }

    public void showLogin(View view) {
        Intent intent = new Intent(joinScreen.this, loginScreen.class);
        joinScreen.this.startActivity(intent);
    }

    //중복체크
    public void same(View view) {

        final String userID = mEditTextUserID.getText().toString();
        Log.d("테스트", userID);
        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(userID);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(joinScreen.this);
        HttpURLConnection conn;
        URL url = null;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            //this method will be running on UI thread
            pdLoading.setMessage("\tLoading...");
            pdLoading.setCancelable(false);
            pdLoading.show();

        }

        @Override
        protected String doInBackground(String... params) {
            try {

                // Enter URL address where your php file resides
                url = new URL("http://192.168.43.249/checkId.php");

            } catch (MalformedURLException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                return "exception";
            }
            try {
                // Setup HttpURLConnection class to send and receive data from php and mysql
                conn = (HttpURLConnection)url.openConnection();
                conn.setReadTimeout(READ_TIMEOUT);
                conn.setConnectTimeout(CONNECTION_TIMEOUT);
                conn.setRequestMethod("POST");

                // setDoInput and setDoOutput method depict handling of both send and receive
                conn.setDoInput(true);
                conn.setDoOutput(true);

                // Append parameters to URL
                Uri.Builder builder = new Uri.Builder()
                        .appendQueryParameter("userID", params[0]);
                String query = builder.build().getEncodedQuery();

                // Open connection for sending data
                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(
                        new OutputStreamWriter(os, "UTF-8"));
                writer.write(query);
                writer.flush();
                writer.close();
                os.close();
                conn.connect();

            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
                return "exception";
            }

            try {

                int response_code = conn.getResponseCode();

                // Check if successful connection made
                if (response_code == HttpURLConnection.HTTP_OK) {

                    // Read data sent from server
                    InputStream input = conn.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    StringBuilder result = new StringBuilder();
                    String line;

                    while ((line = reader.readLine()) != null) {
                        result.append(line);
                    }

                    // Pass data to onPostExecute method
                    //Log.d("test", result.toString());
                    return(result.toString());

                }else{

                    return("unsuccessful");
                }

            } catch (IOException e) {
                e.printStackTrace();
                return "exception";
            } finally {
                conn.disconnect();
            }
        }

        @Override
        protected void onPostExecute(String result) {

            //this method will be running on UI thread
            pdLoading.dismiss();
            Log.d("테스트3", result);
            Log.d("test false", String.valueOf(result.length()));
            try {
                JSONObject jsonObject = new JSONObject(result);
                if(jsonObject.getString("res").equals("true"))
                {
                    Toast.makeText(joinScreen.this, "중복입니다.", Toast.LENGTH_LONG).show();
                    Log.d("테스트4", "테스트4");

                } else {
                    Toast.makeText(joinScreen.this, "중복이 아닙니다.", Toast.LENGTH_LONG).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }


    // 회원가입
    public void join(View view) {

        String Id = mEditTextUserID.getText().toString();
        String Pw = mEditTextUserPassword.getText().toString();
        String Name = mEditTextUserName.getText().toString();
        String Phone = mEditTextUserPhone.getText().toString();


        insertoToDatabase(Id, Pw, Name, Phone);

    }

    private void insertoToDatabase(String Id, final String Pw, String Name, String Phone){
        class InsertData extends AsyncTask<String, Void, String>{
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(joinScreen.this,
                        "Please Wait", null, true, true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("zzzzzzzz" , String.valueOf(s.length()));
                if (s.length() == 7) {
                    suc = false;
                } else {
                    suc = true;
                }
                if (suc) {
                    if (Pw.equals("")) {
                        Toast.makeText(joinScreen.this, "비밀번호를 입력하세요.", Toast.LENGTH_LONG).show();
                    } else {
                        Intent registerIntent = new Intent(joinScreen.this, loginScreen.class);
                        joinScreen.this.startActivity(registerIntent);
                        finish();
                    }
                }
            }


            @Override
            protected String doInBackground(String... params) {

                try {

                    String Id = (String)params[0];
                    String Pw = (String)params[1];
                    String Name = (String)params[2];
                    String Phone = (String)params[3];

                    String link = "http://192.168.43.249/post.php";
                    String data = URLEncoder.encode("Id", "UTF-8") + "=" + URLEncoder.encode(Id, "UTF-8");
                    data += "&" + URLEncoder.encode("Pw", "UTF-8") + "=" + URLEncoder.encode(Pw, "UTF-8");
                    data += "&" + URLEncoder.encode("Name", "UTF-8") + "=" + URLEncoder.encode(Name, "UTF-8");
                    data += "&" + URLEncoder.encode("Phone", "UTF-8") + "=" + URLEncoder.encode(Phone, "UTF-8");
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

                    while((line = reader.readLine()) != null){
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
        if (!Id.equals("") && !Pw.equals("") && !Name.equals("") && !Phone.equals("")) {
            InsertData task = new InsertData();
            Log.d("테스트 task", String.valueOf(task));
            task.execute(Id,Pw,Name,Phone);
        } else {
            Toast.makeText(joinScreen.this, "모두 입력하세요.", Toast.LENGTH_LONG).show();
        }
    }
}
