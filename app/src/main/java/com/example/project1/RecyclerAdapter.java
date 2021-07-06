package com.example.project1;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ItemViewHolder> {

    // adapter에 들어갈 list 입니다.
    private ArrayList<Uri> listData = new ArrayList<>();
    Context context;
    Activity activity;
    ArrayList<Integer> nameList;
    ArrayList<Integer> sizeList;

    TextView textView;
    ImageView iv;

    public RecyclerAdapter(Context context, Activity activity){
        this.context=context;
        this.activity=activity;
    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        // LayoutInflater를 이용하여 전 단계에서 만들었던 item.xml을 inflate 시킵니다.
        // return 인자는 ViewHolder 입니다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        // Item을 하나, 하나 보여주는(bind 되는) 함수입니다.
        holder.onBind(listData.get(position));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening a new activity and passing data to it.
                Log.d("ContactRVAdapter", "리사이클러뷰 아이템 누름"+position);

                //Toast.makeText(context, "fkfkf", Toast.LENGTH_SHORT).show();
                AlertDialog.Builder alertadd = new AlertDialog.Builder(context);
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();

                LayoutInflater factory = LayoutInflater.from(context);
                final View view = factory.inflate(R.layout.sample, null);
                alertadd.setView(view);
                iv=view.findViewById(R.id.dialog_imageview);
                //textView=view.findViewById(R.id.photo_name);

                //여기서 SET
                iv.setImageURI(listData.get(position));
                // null object reference
                //textView.setText(nameList.get(position));
                alertadd.show();

            }
        });


    }


    @Override
    public int getItemCount() {
        // RecyclerView의 총 개수 입니다.
        return listData.size();
    }

    void addItem(Uri uri) {
        // 외부에서 item을 추가시킬 함수입니다.
        listData.add(uri);
    }
    void addName(int name) {
        // 외부에서 item을 추가시킬 함수입니다.
        nameList.add(name);
    }

    // RecyclerView의 핵심인 ViewHolder 입니다.
    // 여기서 subView를 setting 해줍니다.

    class ItemViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;

        ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.recyclerImg);

        }

        void onBind(Uri uri) {

            imageView.setImageURI(uri);
        }


    }
}