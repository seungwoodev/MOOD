package com.example.project1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class Fragment1 extends Fragment {
    ArrayList<Integer> imageList;
    LayoutInflater layoutInflater;
    View view;

    ArrayList<PhoneBook> phoneBookList;
    Context context;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 리사이클러뷰에 표시할 데이터 리스트 생성.
        phoneBookList = new ArrayList<>();
        //넣기


        //context = this;
        phoneBookList = new ArrayList<>();
        String sortOrder = ContactsContract.Contacts.DISPLAY_NAME + " COLLATE LOCALIZED ASC";

        Cursor c = getActivity().getContentResolver().query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                null, null, sortOrder);
        ArrayList<Integer> imageList=new ArrayList();
        while (c.moveToNext()) {
            String contactName = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phNumber = c
                    .getString(c
                            .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            PhoneBook phoneBook = new PhoneBook(0, contactName, phNumber);

            //야매
            imageList.add(R.drawable.per);
            if (phNumber.startsWith("01")) {
                phoneBookList.add(phoneBook);
            }
        }

        // 정렬
        c.close();
    }

    @Override
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup container, Bundle savedInstanceState) {
        view = layoutInflater.inflate(R.layout.fragment_1, container, false);

        // 리사이클러뷰에 LinearLayoutManager 객체 지정.
        RecyclerView recyclerView = view.findViewById(R.id.recycler1);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        // 리사이클러뷰에 SimpleTextAdapter 객체 지정.
        SimpleTextAdapter adapter = new SimpleTextAdapter(phoneBookList);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        return view;
    }

}