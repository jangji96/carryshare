package com.example.jangji.carryshare;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

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
import java.util.List;

import static com.example.jangji.carryshare.loginScreen.CONNECTION_TIMEOUT;
import static com.example.jangji.carryshare.loginScreen.LID;
import static com.example.jangji.carryshare.loginScreen.READ_TIMEOUT;

public class checkShareIfoScreen extends AppCompatActivity {

//    static final String[] rentMember = {"a","b","c","d","e"};

    ListView listView;
    ArrayList<registerRequest> reigsterMember = new ArrayList<registerRequest>();
    List<String> list = new ArrayList<>();
    ArrayAdapter<String> listViewAdapter;
    public static String renID;
    String[] ren = new String[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_share_info_screen);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        new JSONTask().execute(LID);

        listViewAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                list
        );

        listView = (ListView)findViewById(R.id.rentMemberList);

    }
    public void showMyPage(View view){
        Intent intent = new Intent(this, myPageScreen.class);
        startActivity(intent);
        finish();
    }
    private class JSONTask extends AsyncTask<String, String, String>
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
                    URL url = new URL("http://192.168.43.249/registerRequest.php");
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
                for(int i = 0; i <= resData.length(); i++){
//                    JSONObject data = new JSONObject(resData.get(i).toString());
                    JSONObject data = resData.getJSONObject(i);
                    Log.d("rent["+i+"]: ", data.toString());
                    //여기서 json i번째값 가져와지니까
                    //listview에 추가하고
                    //마지막에 setAdapter add???????????????????????????????????????

                    reigsterMember.add(new registerRequest(
                            data.getString("rentID"),
                            data.getString("startDate"),
                            data.getString("endDate"),
                            data.getString("request")
                    ));
                    list.add(reigsterMember.get(i).toString());
                        ren[i] = data.getString("rentID");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d("어뎁터테스트", String.valueOf(list));

            listView.setAdapter(listViewAdapter);
            Log.d("어뎁터테스트2", String.valueOf(list));

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("indextest", String.valueOf(position));
                    renID = ren[position];
                    Log.d("레지아이디1", renID);
                    Intent intent = new Intent(checkShareIfoScreen.this, checkRentMemberScreen.class);
//                    intent.putExtra("registerID",posts_number_ck);
                    startActivity(intent);
                }
            });
        }
    }

}
