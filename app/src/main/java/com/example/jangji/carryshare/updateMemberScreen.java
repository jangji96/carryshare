package com.example.jangji.carryshare;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.jangji.carryshare.loginScreen.LID;

public class updateMemberScreen extends AppCompatActivity {

    private EditText mEditTextUserPassword;
    private EditText mEditTextUserName;
    private EditText mEditTextUserPhone;

    boolean suc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_member_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //mEditTextUserID = (EditText) findViewById(R.id.new_id);
        mEditTextUserPassword = (EditText) findViewById(R.id.new_pw);
        mEditTextUserName = (EditText) findViewById(R.id.new_name);
        mEditTextUserPhone = (EditText) findViewById(R.id.new_phone);
    }

    // 수정버튼
    public void updateMember(View view) {

        //String Id = mEditTextUserID.getText().toString();
        String Pw = mEditTextUserPassword.getText().toString();
        String Name = mEditTextUserName.getText().toString();
        String Phone = mEditTextUserPhone.getText().toString();

        updateoToDatabase(LID, Pw, Name, Phone);

    }
    private void updateoToDatabase(String Id, String Pw, String Name, String Phone){
        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(updateMemberScreen.this,
                        "Please Wait", null, true, true);
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                loading.dismiss();

                Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                Log.d("zzzzzzzz" , String.valueOf(s.length()));
//                if (s.length() == 7) {
//                    suc = false;
//                } else {
//                    suc = true;
//                }
//                if (suc) {
//                    Intent registerIntent = new Intent(updateMemberScreen.this, MenuPage.class);
//                    updateMemberScreen.this.startActivity(registerIntent);
//                }
            }


            @Override
            protected String doInBackground(String... params) {

                try {

                    String Id = (String)params[0];
                    String Pw = (String)params[1];
                    String Name = (String)params[2];
                    String Phone = (String)params[3];

                    String link = "http://192.168.43.249/userUpdate.php";
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
        if (!Pw.equals("") && !Name.equals("") && !Phone.equals("")) {
            InsertData task = new InsertData();
            Log.d("테스트 task", String.valueOf(task));
            task.execute(Id,Pw,Name,Phone);
            Intent registerIntent = new Intent(updateMemberScreen.this, myPageScreen.class);
            updateMemberScreen.this.startActivity(registerIntent);
        } else {
            Toast.makeText(updateMemberScreen.this, "다 입력하세요. 뒤지기 싫으면", Toast.LENGTH_LONG).show();
        }
    }


    public void showMyPage(View view){
        Intent intent = new Intent(this, myPageScreen.class);
        startActivity(intent);
        finish();
    }
}