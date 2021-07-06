package com.example.project1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.ArrayList;
import java.util.List;

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
        images.add(R.drawable.music);

        for(int i=0;i<3;i++) tab.getTabAt(i).setIcon(images.get(i));

    }

}