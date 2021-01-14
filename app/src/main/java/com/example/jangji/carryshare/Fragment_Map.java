package com.example.jangji.carryshare;

import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

import static com.example.jangji.carryshare.Fragment_List.registerID;
import static com.example.jangji.carryshare.loginScreen.LID;
import static com.example.jangji.carryshare.selectSuitcaseScreen.rentSize;
import static com.example.jangji.carryshare.selectSuitcaseScreen.rentType;

public class Fragment_Map extends Fragment implements OnMapReadyCallback {
    final private String url = "http://192.168.43.249/getSuitcase.php";
    ArrayList<String> list = new ArrayList<>();
    private Double[] La = new Double[20];
    private Double[] Lo = new Double[20];
    private String[] mSize = new String[20];
    private String[] mType = new String[20];
    private String[] mCost = new String[20];
    private String[] regi = new String[20];
    private MapView mapView = null;
    private LatLng SEOUL;

    int arrLength;

    int count=0;
    GoogleMap googleMap;

    private Marker marker;

    public Fragment_Map(){

    }

    public void onCreate(@Nullable Bundle savedInstaceState){
        super.onCreate(savedInstaceState);
        new JSONTask().execute(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.fragment_map,container,false);


        mapView = (MapView)layout.findViewById(R.id.map);
        mapView.getMapAsync(this);




        return layout;
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //액티비티가 처음 생성될 때 실행되는 함수
        if(mapView != null)
        {
            mapView.onCreate(savedInstanceState);
        }
    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
         mapView.onLowMemory();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onLowMemory();
    }
// 될거같은디 아까 그캐하면 ㅇㅇ그래도됨 근데 언래 저렇게 포문만돌려도 떠야함 되면 되는 방법으로 하면됨 시간이 없음
@Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        Log.d("카운트", String.valueOf(count));

    // 왜 반복문 1번밖에 안도냥? 설마 저 마커가 3번째 마커인가 그러네 3
        // 야 킴이쓰니 저거 마커 위치를 저장하는 배열을 만들어서 에드를 반복하면 안뎀?

        for(int j=0; j<(count);j++) {

            Log.d("aaa인덱스", String.valueOf(j));
            Log.d("aaa위도", String.valueOf(La[j]));
            Log.d("aaa경도", String.valueOf(Lo[j]));
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(new LatLng(La[j],Lo[j])).title(mSize[j] + "/" +mCost[j]+ "원" ).snippet(mType[j]);

            final int finalJ = j;

            googleMap.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    registerID = regi[finalJ];
                    Log.d("아이디인덱스2",registerID);
                    Intent intent = new Intent(getActivity(), shareRequestScreen.class);
                    startActivity(intent);
                }
            });
 //                   Marker marker = googleMap.addMarker(new MarkerOptions()
 //                   .position(new LatLng(La[ii], Lo[ii]))
 //                   .title("San Francisco")
 //                   .snippet("Population: 776733"));
            googleMap.addMarker(markerOptions);
        }
/*
        for (int i=0; i<1; i++) {
            Log.d("La["+i+"]",La[i]);
            Double dLa = Double.parseDouble(La[i]);
            Double dLo = Double.parseDouble(Lo[i]);
            SEOUL = new LatLng(dLa, dLo);
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(SEOUL);
            markerOptions.title(mSize[i]);
            markerOptions.snippet(mCost[i] + "," + mType[i]);
            googleMap.addMarker(markerOptions);
여긴 잘해줬네임뫄
        }
        */


    SEOUL = new LatLng( 35.095707,129.005275);
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(SEOUL));
        googleMap.animateCamera(CameraUpdateFactory.zoomTo(13));


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

            Log.d("resData", result);
            try {
                JSONArray resData = new JSONArray(result);
                arrLength = resData.length();
                Log.d("resData.length", String.valueOf(resData.length()));
                count=0;
                for(int i = 0; i <= resData.length(); i++){
                    JSONObject data = new JSONObject(resData.get(i).toString());
//                    JSONObject data = resData.getJSONObject(i);
                    if (data == null) {
                        continue;
                    }

                    if (rentSize.equals("모두보기") && rentType.equals("모두보기")) {
                        count = resData.length();
                        regi[i] = data.getString("userID");
                        La[i] = data.getDouble("Latitude");
                        Lo[i] = data.getDouble("Longitude");
                        mSize[i] = data.getString("size");
                        mType[i] = data.getString("type");
                        mCost[i] = data.getString("cost");
                    }
                    else if (rentSize.equals("모두보기"))  {
                        if (data.getString("type").equals(rentType)) {
                            regi[count] = data.getString("userID");
                            La[count] = data.getDouble("Latitude");
                            Lo[count] = data.getDouble("Longitude");
                            mSize[count] = data.getString("size");
                            mType[count] = data.getString("type");
                            mCost[count] = data.getString("cost");
                            Log.d("반복문확인", String.valueOf(i));
                            count++;
//                            if(regi[i] !=null ){
//                                count ++ ;
//                            }
                        }
                    } else if (rentType.equals("모두보기")) {
                        if (data.getString("size").equals(rentSize)) {
                            regi[count] = data.getString("userID");

                            La[count] = data.getDouble("Latitude");
                            Lo[count] = data.getDouble("Longitude");
                            mSize[count] = data.getString("size");
                            mType[count] = data.getString("type");
                            mCost[count] = data.getString("cost");
                            count++;
                            Log.d("반복문확인", String.valueOf(i));
//                            if(regi[i] !=null ){
//                                count ++ ;
//                            }
                        }
                    }else {
                        if (data.getString("size").equals(rentSize) && data.getString("type").equals(rentType)) {
                            regi[count] = data.getString("userID");

                            La[count] = data.getDouble("Latitude");
                            Lo[count] = data.getDouble("Longitude");
                            mSize[count] = data.getString("size");
                            mType[count] = data.getString("type");
                            mCost[count] = data.getString("cost");
                            count++;
                            Log.d("반복문확인", String.valueOf(i));
//                            if(regi[i] !=null ){
//                                count ++ ;
//                            }

                        }
                    }

                    //Log.d("레지아이디", regi[i]);
                    //rent_member= new String[]{String.valueOf(data.toString())};
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}