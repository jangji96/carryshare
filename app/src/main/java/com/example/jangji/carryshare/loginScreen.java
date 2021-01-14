package com.example.jangji.carryshare;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

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


public class loginScreen extends Activity {

    // CONNECTION_TIMEOUT and READ_TIMEOUT are in milliseconds

    public static final int CONNECTION_TIMEOUT=10000;
    public static final int READ_TIMEOUT=15000;
    private EditText etEmail;
    private EditText etPassword;
    ArrayList<User> userList = new ArrayList<User>();
    ArrayList<User_id>user_ID = new ArrayList<User_id>();
    String loginId, loginPwd;
    public static String LID = "";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        // Get Reference to variables
        etEmail = (EditText) findViewById(R.id.ID);
        etPassword = (EditText) findViewById(R.id.PASSWORD);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //처음에는 SharedPreferences에 아무런 정보도 없으므로 값을 저장할 키들을 생성한다.
        // getString의 첫 번째 인자는 저장될 키, 두 번쨰 인자는 값입니다.
        // 첨엔 값이 없으므로 키값은 원하는 것으로 하시고 값을 null을 줍니다.
        loginId = auto.getString("inputId",null);
        loginPwd = auto.getString("inputPwd",null);

        etEmail.requestFocus();
    }


    public void join (View v) {
        Intent intent = new Intent(loginScreen.this, joinScreen.class);
        startActivity(intent);
        finish();
    }

    /*public void suit (View v) {
        Intent intent = new Intent(MainActivity.this, suitcaseInfo.class);
        startActivity(intent);
    }

    public void rent (View v) {
        Intent intent = new Intent(MainActivity.this, Rent.class);

        startActivity(intent);
    }*/


    // Triggers when LOGIN Button clicked
    public void login(View arg0) {

        // Get text from email and passord field

        final String userID = etEmail.getText().toString();
        final String userPassword = etPassword.getText().toString();
        Log.d("테스트", userID);
        SharedPreferences auto = getSharedPreferences("auto", Activity.MODE_PRIVATE);
        //auto의 loginId와 loginPwd에 값을 저장해 줍니다.
        SharedPreferences.Editor autoLogin = auto.edit();
        autoLogin.putString("inputId", userID);
        //꼭 commit()을 해줘야 값이 저장됩니다 ㅎㅎ
        autoLogin.commit();
        Log.d("로그인", autoLogin.toString());
        // Initialize  AsyncLogin() class with email and password
        new AsyncLogin().execute(userID,userPassword);

    }

    private class AsyncLogin extends AsyncTask<String, String, String>
    {
        ProgressDialog pdLoading = new ProgressDialog(loginScreen.this);
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
                url = new URL("http://192.168.43.249/login.php");

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
                        .appendQueryParameter("userID", params[0])
                        .appendQueryParameter("userPassword", params[1]);
                String query = builder.build().getEncodedQuery();
                LID = params[0];
                Log.d("테스트2",params[1]);
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
                    Log.d("test", result.toString());
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
                //Log.d("LID", LID);
                if(jsonObject.getString("res").equals("true"))
                {
                /* Here launching another activity when login successful. If you persist login state
                use sharedPreferences of Android. and logout button to clear sharedPreferences.
                 */

                    JSONArray jsonArray = new JSONArray(jsonObject.getString("data"));
                    Log.d("확인", String.valueOf(jsonArray.length()));
                    for (int i=0; i < jsonArray.length(); i++){

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        //JSONObject jsonObject2 = jsonArray.getJSONObject(i);
                        JSONObject data = new JSONObject(jsonArray.get(i).toString());
                        if (data.getString("userID").equals(LID)) {
//                        Log.d("json", jsonObject1.toString());
                            userList.add(new User(
//                                    jsonObject1.getString("userID"),
                                    jsonObject1.getString("userName"),
                                    jsonObject1.getString("userPhone")
                            ));

                            user_ID.add(new User_id(
                                    data.getString("userID")
                            ));
                            //Log.d("json[" + i + "]: ", userList.get(i).toString());
                            break;
                        } /*else {
                            continue;
                        }*/
                    }

                    //tv.setText(user_ID.toString());
                    Intent intent = new Intent(loginScreen.this, mainScreen.class);
                    intent.putExtra("name",etEmail.getText().toString());
                    startActivity(intent);
                    loginScreen.this.finish();
                    Log.d("테스트4", "테스트4");


                }else{ //result.equalsIgnoreCase("false")

                    // If username and password does not match display a error message
                    Toast.makeText(loginScreen.this, "Invalid email or password", Toast.LENGTH_LONG).show();
                    Log.d("테스트5", "테스트5");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

//            if(result.equals("true"))
//            {
//                /* Here launching another activity when login successful. If you persist login state
//                use sharedPreferences of Android. and logout button to clear sharedPreferences.
//                 */
//                Intent intent = new Intent(MainActivity.this, MenuPage.class);
//                startActivity(intent);
//                MainActivity.this.finish();
//                Log.d("테스트4", "테스트4");
//
//            }else{ //result.equalsIgnoreCase("false")
//
//                // If username and password does not match display a error message
//                Toast.makeText(MainActivity.this, "Invalid email or password", Toast.LENGTH_LONG).show();
//                Log.d("테스트5", "테스트5");
//            } /*else if (result.length() != 4 && result.length() != 5) {
//                Toast.makeText(MainActivity.this, "OOPs! Something went wrong. Connection Problem.", Toast.LENGTH_LONG).show();
//            }*/
        }
    }
}