package com.example.project1;

import android.content.ClipData;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

import static android.app.Activity.RESULT_OK;

public class Fragment2 extends Fragment{

    private static final String TAG = "Fragment2";

    ImageView imageView;
    TextView textView;
    String imgName = "osz.png";    // 이미지 이름
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private RecyclerAdapter adapter;



    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment2() {
        // Required empty public constructor
    }

    public static Fragment2 newInstance(String param1, String param2) {
        Fragment2 fragment = new Fragment2();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_2, container, false);
    }


    private void init() {
        RecyclerView recyclerView = getView().findViewById(R.id.recyclerView);

        int numberOfColumns = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity().getApplicationContext(), numberOfColumns));
        recyclerView.addItemDecoration(new RecyclerViewDecoration(1));

        adapter = new RecyclerAdapter(getContext(), getActivity());
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.v(TAG, "Gallery process...");

//        imageView = getView().findViewById(R.id.imageView);

        try {
            String imgpath = getActivity().getCacheDir() + "/" + imgName;   // 내부 저장소에 저장되어 있는 이미지 경로
            Bitmap bm = BitmapFactory.decodeFile(imgpath);
//            imageView.setImageBitmap(bm);   // 내부 저장소에 저장된 이미지를 이미지뷰에 셋
            Toast.makeText(getActivity().getApplicationContext(), "파일 로드 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "파일 로드 실패", Toast.LENGTH_SHORT).show();
        }


        Button bt1 = getView().findViewById(R.id.button);
        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO : click event
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, 1);
            }
        });
//
//        Button bt2 = getView().findViewById(R.id.button2);
//        bt2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                // TODO : click event
//                try {
//                    File file = getActivity().getCacheDir();  // 내부저장소 캐시 경로를 받아오기
//                    File[] flist = file.listFiles();
//                    for (int i = 0; i < flist.length; i++) {    // 배열의 크기만큼 반복
//                        if (flist[i].getName().equals(imgName)) {   // 삭제하고자 하는 이름과 같은 파일명이 있으면 실행
//                            flist[i].delete();  // 파일 삭제
//                            Toast.makeText(getActivity().getApplicationContext(), "파일 삭제 성공", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                } catch (Exception e) {
//                    Toast.makeText(getActivity().getApplicationContext(), "파일 삭제 실패", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });

        Log.v(TAG, "gallery process finished...");
    }



    public void bt1(View view) {    // 이미지 선택 누르면 실행됨 이미지 고를 갤러리 오픈
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        Log.d("intent type: ", intent+"\n");
        startActivityForResult(intent, 1);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) { // 갤러리

        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                init();
                ClipData clip = data.getClipData();

                for (int i=clip.getItemCount()-1; i >= 0; i--) {
                    Uri fileUri = clip.getItemAt(i).getUri();
                    adapter.addItem(fileUri);
                }
                ContentResolver resolver = getActivity().getContentResolver();
                try {
                    for (int i=clip.getItemCount()-1; i >= 0; i--){
                        Uri fileUri = clip.getItemAt(i).getUri();
                        InputStream instream = resolver.openInputStream(fileUri);
                        Bitmap imgBitmap = BitmapFactory.decodeStream(instream);
//                    imageView.setImageBitmap(imgBitmap);    // 선택한 이미지 이미지뷰에 셋
                        instream.close();   // 스트림 닫아주기
                        saveBitmapToJpeg(imgBitmap);    // 내부 저장소에 저장

//                        adapter.addName(resolver.query(fileUri, null, null, null, null).getColumnIndex(OpenableColumns.DISPLAY_NAME));
//                        Log.d("이름 : ", String.valueOf(resolver.query(fileUri, null, null, null, null).getColumnIndex(OpenableColumns.DISPLAY_NAME)));
//
                    }
                    Toast.makeText(getActivity().getApplicationContext(), "파일 불러오기 성공", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    Toast.makeText(getActivity().getApplicationContext(), "파일 불러오기 실패", Toast.LENGTH_SHORT).show();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void saveBitmapToJpeg(Bitmap bitmap) {   // 선택한 이미지 내부 저장소에 저장
        File tempFile = new File(getActivity().getCacheDir(), imgName);    // 파일 경로와 이름 넣기
        try {
            tempFile.createNewFile();   // 자동으로 빈 파일을 생성하기
            FileOutputStream out = new FileOutputStream(tempFile);  // 파일을 쓸 수 있는 스트림을 준비하기
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);   // compress 함수를 사용해 스트림에 비트맵을 저장하기
            out.close();    // 스트림 닫아주기
            Toast.makeText(getActivity().getApplicationContext(), "파일 저장 성공", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Toast.makeText(getActivity().getApplicationContext(), "파일 저장 실패", Toast.LENGTH_SHORT).show();
        }
    }


}