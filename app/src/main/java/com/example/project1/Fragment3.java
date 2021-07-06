package com.example.project1;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;


//import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Locale;

import static android.content.Context.LOCATION_SERVICE;


public class Fragment3 extends Fragment implements View.OnClickListener{
    Button ShowLocationButton;
    Button url_button;

    private GpsTracker gpsTracker;

    private static final int GPS_ENABLE_REQUEST_CODE = 2001;
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    String[] REQUIRED_PERMISSIONS  = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_3, container, false);
//        youTubePlayerView = view.findViewById(R.id.playerView);
//        getLifecycle().addObserver(youTubePlayerView);

        ShowLocationButton = view.findViewById(R.id.button3);

        url_button=view.findViewById(R.id.url_btn);


        Log.d("Fragment3", "success"+"\n");


        if (!checkLocationServicesStatus()) {

            showDialogForLocationServiceSetting();
        }else {

//            checkRunTimePermission();
        }

        Log.d("SetOnClickListener", "processed"+"\n");
        ShowLocationButton.setOnClickListener(this);

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
            //your codes here

        }

        return view;
    }


// json 받아오는 method
    private String jsonReadAll(Reader reader) throws IOException {

        StringBuilder sb = new StringBuilder();

        int cp;

        while ((cp = reader.read()) != -1) {

            sb.append((char) cp);

        }

        return sb.toString();

    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {

        InputStream is = new URL(url).openStream();

        try {

            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));

            String jsonText = jsonReadAll(rd);

            JSONObject json = new JSONObject(jsonText);

            return json;

        } finally {

            is.close();

        }

    }



    @Override
    public void onClick(View arg0)
    {

        Log.d("Address", "processing..."+"\n");

        gpsTracker = new GpsTracker(getActivity().getApplicationContext());

        double latitude = gpsTracker.getLatitude();
        double longitude = gpsTracker.getLongitude();

        Log.d("geometry ", "latititude: "+Double.toString(latitude)+"longitude: "+Double.toString(longitude)+"\n");

        String api = "http://api.openweathermap.org/data/2.5/weather?lat="+Double.toString(latitude)+"&lon="+Double.toString(longitude)+"&appid=b29145d4cc29b36a94df3052c568bfb3";
        try {
            Log.d("try: ", "start");
            JSONObject json = readJsonFromUrl(api);
            Log.d("weatherAPI: ", "start");

            JSONArray weather = (JSONArray) json.get("weather");
            Log.d("weather: ", weather+"\n");
            String description;

            for (int i = 0; i < weather.length(); i++){
                JSONObject jsonObj = (JSONObject)weather.get(i);
                try {
                    jsonObj.getString("description");
                    description = "현재 날씨: " + jsonObj.getString("description");
                    String icon = jsonObj.getString("icon");
                    Log.d("JSON Object: ", jsonObj + "\n");

                    String address = "현재 위치: " + getCurrentAddress(latitude, longitude);
                    TextView gpstext_address = getActivity().findViewById(R.id.gpstext);
                    TextView weather_current = getActivity().findViewById(R.id.weathertext);
                    gpstext_address.setText(address);
                    weather_current.setText(description);

                    String youtubeAPIkey = "AIzaSyACoaOud7qz-X_H2TYGINAm4q_WDLxGvrk";
                    Log.d("videoId2: ", "process\n");

                    String youtubeAPI = "https://www.googleapis.com/youtube/v3/search?q="+description+" weather music&part=snippet&key="+youtubeAPIkey+"&maxResults=1";
                    JSONObject json2 = readJsonFromUrl(youtubeAPI);
                    Log.d("videoId3: ", "process\n");

                    JSONArray jArray = json2.getJSONArray("items");
                    Log.d("videoId4: ", jArray+"\n");
                    JSONObject json3 = jArray.getJSONObject(0);
                    Log.d("videoId5: ", json3+"\n");


                    JSONObject json4 = json3.getJSONObject("id");
                    Log.d("videoId6: ", json3+"\n");
                    String videoId = json4.getString("videoId");
                    Log.d("videoId7: ", videoId+"\n");

                    JSONObject json5 = json3.getJSONObject("snippet");
                    Log.d("videoId8: ", json5+"\n");
                    JSONObject json6 = json5.getJSONObject("thumbnails");
                    Log.d("videoId9: ", json6+"\n");
                    JSONObject json7 = json6.getJSONObject("high");
                    Log.d("videoId10: ", json7+"\n");
                    String thumbnailUrl = json7.getString("url");
                    Log.d("videoId11: ", thumbnailUrl+"\n");

                    Glide.with(this).load(thumbnailUrl).into((ImageView) getActivity().findViewById(R.id.thumbnail));

                    String title = "Title: " + json5.getString("title");
                    String videoDescription = json5.getString("description");
                    String videoURL = "http://www.youtube.com/watch?v="+videoId;

                    url_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent=new Intent(Intent.ACTION_VIEW, Uri.parse(videoURL));
                            startActivity(intent);
                        }
                    });

                    videoDescription = "\n" + "Description: " + videoDescription + "\n" +"Link: " + videoURL;

                    Toast.makeText(getActivity().getApplicationContext(), "현재위치 \n위도 " + latitude + "\n경도 " + longitude, Toast.LENGTH_LONG).show();

                    TextView video_title = getActivity().findViewById(R.id.video_title);
                    video_title.setText(title);
                    Log.d("videoId: ", "videoId process\n");

                    TextView video_description = getActivity().findViewById(R.id.video_description);
                    video_description.setText(videoDescription);
                    Log.d("videoId: ", "videoId process\n");

                    String icon_url = "http://openweathermap.org/img/w/" + icon +".png";
                    Glide.with(this).load(icon_url).into((ImageView) getActivity().findViewById(R.id.weather_icon));

//                    ImageView thumbnail_image = getActivity().findViewById(R.id.thumbnail);
//                    thumbnail_image.set(videoId);
//                    Log.d("videoId: ", "thumbnail process\n");
                    // Youtube video play
//                    YouTubePlayer youTubePlayer = new YouTubePlayer();
//                    YouTubePlayer.cueVideo(videoId, 0);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }



            Log.d("Address", "process finished"+"\n");


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }





    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(Bundle savedInstanceState)
    {
//        setContentView(R.layout.activity_main);



        super.onActivityCreated(savedInstanceState);
    }




    /*
     * ActivityCompat.requestPermissions를 사용한 퍼미션 요청의 결과를 리턴받는 메소드입니다.
     */
    @Override
    public void onRequestPermissionsResult(int permsRequestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grandResults) {

        if ( permsRequestCode == PERMISSIONS_REQUEST_CODE && grandResults.length == REQUIRED_PERMISSIONS.length) {

            // 요청 코드가 PERMISSIONS_REQUEST_CODE 이고, 요청한 퍼미션 개수만큼 수신되었다면

            boolean check_result = true;


            // 모든 퍼미션을 허용했는지 체크합니다.

            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }


            if ( check_result ) {

                //위치 값을 가져올 수 있음
                ;
            }
            else {
                // 거부한 퍼미션이 있다면 앱을 사용할 수 없는 이유를 설명해주고 앱을 종료합니다.2 가지 경우가 있습니다.

                if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])
                        || ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[1])) {

                    Toast.makeText(getActivity().getApplicationContext(), "퍼미션이 거부되었습니다. 앱을 다시 실행하여 퍼미션을 허용해주세요.", Toast.LENGTH_LONG).show();
                    getActivity().finish();


                }else {

                    Toast.makeText(getActivity().getApplicationContext(), "퍼미션이 거부되었습니다. 설정(앱 정보)에서 퍼미션을 허용해야 합니다. ", Toast.LENGTH_LONG).show();

                }
            }

        }
    }

    void checkRunTimePermission(){

        //런타임 퍼미션 처리
        // 1. 위치 퍼미션을 가지고 있는지 체크합니다.
        int hasFineLocationPermission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION);
        int hasCoarseLocationPermission = ContextCompat.checkSelfPermission(getActivity().getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION);


        if (hasFineLocationPermission == PackageManager.PERMISSION_GRANTED &&
                hasCoarseLocationPermission == PackageManager.PERMISSION_GRANTED) {

            // 2. 이미 퍼미션을 가지고 있다면
            // ( 안드로이드 6.0 이하 버전은 런타임 퍼미션이 필요없기 때문에 이미 허용된 걸로 인식합니다.)


            // 3.  위치 값을 가져올 수 있음



        } else {  //2. 퍼미션 요청을 허용한 적이 없다면 퍼미션 요청이 필요합니다. 2가지 경우(3-1, 4-1)가 있습니다.

            // 3-1. 사용자가 퍼미션 거부를 한 적이 있는 경우에는
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), REQUIRED_PERMISSIONS[0])) {

                // 3-2. 요청을 진행하기 전에 사용자가에게 퍼미션이 필요한 이유를 설명해줄 필요가 있습니다.
                Toast.makeText(getActivity().getApplicationContext(), "이 앱을 실행하려면 위치 접근 권한이 필요합니다.", Toast.LENGTH_LONG).show();
                // 3-3. 사용자게에 퍼미션 요청을 합니다. 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);


            } else {
                // 4-1. 사용자가 퍼미션 거부를 한 적이 없는 경우에는 퍼미션 요청을 바로 합니다.
                // 요청 결과는 onRequestPermissionResult에서 수신됩니다.
                ActivityCompat.requestPermissions(getActivity(), REQUIRED_PERMISSIONS,
                        PERMISSIONS_REQUEST_CODE);
            }

        }

    }


    public String getCurrentAddress( double latitude, double longitude) {

        //지오코더... GPS를 주소로 변환
        Geocoder geocoder = new Geocoder(getActivity().getApplicationContext(), Locale.getDefault());

        List<Address> addresses;

        try {

            addresses = geocoder.getFromLocation(
                    latitude,
                    longitude,
                    7);
        } catch (IOException ioException) {
            //네트워크 문제
            Toast.makeText(getActivity().getApplicationContext(), "지오코더 서비스 사용불가", Toast.LENGTH_LONG).show();
            return "지오코더 서비스 사용불가";
        } catch (IllegalArgumentException illegalArgumentException) {
            Toast.makeText(getActivity().getApplicationContext(), "잘못된 GPS 좌표", Toast.LENGTH_LONG).show();
            return "잘못된 GPS 좌표";

        }



        if (addresses == null || addresses.size() == 0) {
            Toast.makeText(getActivity().getApplicationContext(), "주소 미발견", Toast.LENGTH_LONG).show();
            return "주소 미발견";

        }

        Address address = addresses.get(0);
        return address.getAddressLine(0).toString()+"\n";

    }


    //여기부터는 GPS 활성화를 위한 메소드들
    private void showDialogForLocationServiceSetting() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setTitle("위치 서비스 비활성화");
        builder.setMessage("앱을 사용하기 위해서는 위치 서비스가 필요합니다.\n"
                + "위치 설정을 수정하실래요?");
        builder.setCancelable(true);
        builder.setPositiveButton("설정", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent callGPSSettingIntent
                        = new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivityForResult(callGPSSettingIntent, GPS_ENABLE_REQUEST_CODE);
            }
        });
        builder.setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        builder.create().show();
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case GPS_ENABLE_REQUEST_CODE:

                //사용자가 GPS 활성 시켰는지 검사
                if (checkLocationServicesStatus()) {
                    if (checkLocationServicesStatus()) {

                        Log.d("@@@", "onActivityResult : GPS 활성화 되있음");
//                        checkRunTimePermission();
                        return;
                    }
                }

                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean checkLocationServicesStatus() {
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

}
