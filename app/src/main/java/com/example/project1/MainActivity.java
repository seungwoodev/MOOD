package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private final int Fragment_1 = 1;
    private final int Fragment_2 = 2;
    private final int Fragment_3 = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager vp=findViewById(R.id.viewpager);
        VPAdapter adapter=new VPAdapter(getSupportFragmentManager());
        vp.setAdapter(adapter);

        // 연동
        TabLayout tab=findViewById(R.id.tab);
        tab.setupWithViewPager(vp);

        ArrayList<Integer> images=new ArrayList<>();
        images.add(R.drawable.profile);
        images.add(R.drawable.gallery);
        images.add(R.drawable.gallery);

        for(int i=0;i<3;i++) tab.getTabAt(i).setIcon(images.get(i));


//        // 화면 전환 프래그먼트 선언 및 초기 화면 설정
//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.add(R.id.viewpager, Fragment1.newInstance()).commit();
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.viewpager, fragment).commit();      // Fragment로 사용할 MainActivity내의 layout공간을 선택합니다.
    }


}