package com.example.jangji.carryshare;

import android.app.FragmentManager;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;


public class registerLocationScreen extends AppCompatActivity implements OnMapReadyCallback {


    private GoogleMap mMap;
    public static Double latitude1,longitude1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_location_screen);

        latitude1 = null;
        longitude1 = null;

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
        mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(35.095707,129.005275)));
        mMap.animateCamera(CameraUpdateFactory.zoomTo(13));
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener(){
            @Override
            public void onMapClick(LatLng point) {
                map.clear();
                MarkerOptions mOptions = new MarkerOptions();
                // 마커 타이틀
                mOptions.title("마커 좌표");
                Double latitude2 = point.latitude; // 위도
                Double longitude2 = point.longitude; // 경도
                // 마커의 스니펫(간단한 텍스트) 설정
                mOptions.snippet(latitude2.toString() + ", " + longitude2.toString());

                LatLng location = new LatLng(latitude2, longitude2);

                Log.d("bbb위경도", String.valueOf(location));

                MarkerOptions markerOptions = new MarkerOptions();
                markerOptions.position(location);
                markerOptions.title("공유위치");
                map.addMarker(markerOptions);

                latitude1 = latitude2;
                longitude1 = longitude2;
                Log.d("등록테스트", String.valueOf(longitude2));
                Log.d("등록테스트", String.valueOf(latitude2));

            }
        });
    }



    public void showMain(View view){
        Intent intent = new Intent(this, mainScreen.class);
        startActivity(intent);
        finish();
    }
    public void showShareInfo(View view){
        if(latitude1 != null && longitude1 != null){
            Intent intent = new Intent(this, shareInfoScreen.class);
            startActivity(intent);
            finish();
        }else {

            Toast.makeText(registerLocationScreen.this,"공유위치를 지정해 주세요.",Toast.LENGTH_LONG).show();
        }
    }
}