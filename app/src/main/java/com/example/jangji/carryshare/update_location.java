package com.example.jangji.carryshare;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import android.app.FragmentManager;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

import static com.example.jangji.carryshare.loginScreen.LID;

public class update_location extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public static String latitude2,longitude2;
    boolean suc = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_location);

        FragmentManager fragmentManager = getFragmentManager();
        MapFragment mapFragment = (MapFragment)fragmentManager
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }
    @Override
    public void onMapReady(final GoogleMap map) {

        mMap = map;

        map.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.145713,129.007351)));
        map.animateCamera(CameraUpdateFactory.zoomTo(17));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                map.clear();
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude = point.latitude; // 위도
                Double longitude = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude.toString() + ", " + longitude.toString());

                LatLng location = new LatLng(latitude, longitude);

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location);
                markerOptions.title("공유위치");
                map.addMarker(markerOptions);

                latitude2 = Double.toString(latitude);
                longitude2 = Double.toString(longitude);
                Log.d("등록테스트", String.valueOf(longitude));
                Log.d("등록테스트", String.valueOf(latitude));

            }
        });
    }
    public void updateLocation(View view){

        updateToDatabase(LID, latitude2, longitude2);
        Intent intent = new Intent(this, myPageScreen .class);
        startActivity(intent);
        finish();
    }
    private void updateToDatabase(String LID, String latitude, String longitude) {
        Log.d("로그인2", LID);

        class InsertData extends AsyncTask<String, Void, String> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();

                loading = ProgressDialog.show(update_location.this,
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
                    Intent registerIntent = new Intent(update_location.this, mainScreen.class);
                    update_location.this.startActivity(registerIntent);
                }
            }

            @Override
            protected String doInBackground(String... params) {

                try {
                    Log.d("로그인 파람", params[0]);
                    String LID = (String) params[0];
                    String latitude = (String) params[1];
                    String longitude = (String) params[2];

                    String link = "http://192.168.43.249/locationUpdate.php";
                    String data = URLEncoder.encode("registerID", "UTF-8") + "=" + URLEncoder.encode(LID, "UTF-8");
                    data += "&" + URLEncoder.encode("Latitude", "UTF-8") + "=" + URLEncoder.encode(latitude, "UTF-8");
                    data += "&" + URLEncoder.encode("Longitude", "UTF-8") + "=" + URLEncoder.encode(longitude, "UTF-8");
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
        task.execute(LID, latitude, longitude);
    }
    public void showUpdateInfo(View view){
        Intent intent = new Intent(this, updateInfoScreen.class);
        startActivity(intent);
        finish();
    }
}