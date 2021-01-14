package com.example.jangji.carryshare;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import static com.example.jangji.carryshare.selectSuitcaseScreen.rentSize;
import static com.example.jangji.carryshare.selectSuitcaseScreen.rentType;

public class Fragment_List extends Fragment{

    final private String url = "http://192.168.43.249/getSuitcase.php";
    ArrayList<Suitcase> suitcaseList = new ArrayList<Suitcase>();
    public static String registerID;
    String[] regi = new String[10];
    ListView listView;

    ArrayList<String> list = new ArrayList<>();

    @Override
    public void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);
        new JSONTask().execute(url);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        RelativeLayout layout = (RelativeLayout) inflater.inflate(R.layout.fragment_list,container,false);

//        String[] rent_member= new String[]{"a", "b", "c"};
//
        listView = (ListView) layout.findViewById(R.id.distanceSuitcaseList);
// 임뫄 add 할라면 어뎁터에 에드 해야되는거 아님?
//        ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
//                getActivity(),
//                android.R.layout.simple_list_item_1,
//                rent_member
//        );
//        listView.setAdapter(listViewAdapter);
//
//
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(getActivity(), shareRequestScreen.class);
//                startActivity(intent);
//            }
//        });

        return layout;

    }

    class JSONTask extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... urls) {
            try {
                //JSONObject를 만들고 key value 형식으로 값을 저장해준다.
//                    JSONObject jsonObject = new JSONObject();
//                    jsonObject.accumulate("user_id", "androidTest");
//                    jsonObject.accumulate("name", "yun");

                HttpURLConnection con = null;
                BufferedReader reader = null;

                try{
                    //URL url = new URL("http://192.168.25.16:3000/users");
                    URL url = new URL(urls[0]);//url을 가져온다.
                    con = (HttpURLConnection) url.openConnection();
                    con.connect();//연결 수행

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

            String rent_member[] = new String[0];

            Log.d("resData", result);
            try {
                JSONArray resData = new JSONArray(result);
                for(int i = 0; i <= resData.length(); i++){
//                    JSONObject data = new JSONObject(resData.get(i).toString());
                    JSONObject data = resData.getJSONObject(i);
                    Log.d("Suitcase["+i+"]: ", data.toString());
                    //여기서 json i번째값 가져와지니까
                    //listview에 추가하고
                    //마지막에 setAdapter add???????????????????????????????????????

                    suitcaseList.add(new Suitcase(
                            data.getString("userID"),
                            data.getString("size"),
                            data.getString("type"),
                            data.getString("cost")
/*                                data.getString("Latitude"),
                                data.getString("Longitude")*/
                    ));
                    Log.d("렌트사이즈",rentSize);
                    if (rentSize.equals("모두보기") && rentType.equals("모두보기")) {
                        list.add(suitcaseList.get(i).toString());
                        regi[i] = data.getString("userID");
                    } else if (rentSize.equals("모두보기"))  {
                        if (data.getString("type").equals(rentType)) {
                            list.add(suitcaseList.get(i).toString());
                            regi[i] = data.getString("userID");
                        }
                    } else if (rentType.equals("모두보기")) {
                        if (data.getString("size").equals(rentSize)) {
                            list.add(suitcaseList.get(i).toString());
                            regi[i] = data.getString("userID");
                        }
                    }else {
                        if (data.getString("size").equals(rentSize) && data.getString("type").equals(rentType)) {
                            list.add(suitcaseList.get(i).toString());
                            regi[i] = data.getString("userID");
                        }
                    }

                    //Log.d("레지아이디", regi[i]);
                    //rent_member= new String[]{String.valueOf(data.toString())};
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            final ArrayAdapter<String> listViewAdapter = new ArrayAdapter<String>(
                    getActivity(),
                    android.R.layout.simple_list_item_1,
                    list
            );


            listView.setAdapter(listViewAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("indextest", String.valueOf(position));
                    registerID = regi[position];
                    //Log.d("레지아이디1", registerID);
                    Intent intent = new Intent(getActivity(), shareRequestScreen.class);
//                    intent.putExtra("registerID",posts_number_ck);
                    startActivity(intent);
                }
            });
        }
    }
}