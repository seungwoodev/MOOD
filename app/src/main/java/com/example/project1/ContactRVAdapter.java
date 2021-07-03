package com.example.project1;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import java.util.ArrayList;


public class ContactRVAdapter extends RecyclerView.Adapter<ContactRVAdapter.ViewHolder> {
    private Context context;
    private ArrayList<PhoneBook> mList = null ;
    public MainActivity activity;
    int position;

    public void onAttach(Activity activity){
        this.activity= (MainActivity) activity;
    }
    // 아이템 뷰를 저장하는 뷰홀더 클래스.
    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView name ;
        TextView phnum ;

        public ViewHolder(View itemView) {
            super(itemView) ;

            // 뷰 객체에 대한 참조. (hold strong reference)
            this.img = itemView.findViewById(R.id.item_image) ;
            this.name = itemView.findViewById(R.id.item_name) ;
            this.phnum = itemView.findViewById(R.id.item_phonenum);
        }
    }

    // 생성자에서 데이터 리스트 객체를 전달받음.
    public ContactRVAdapter(ArrayList<PhoneBook> list) {
        this.mList = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public ContactRVAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.f1_layout_complex, parent, false) ;
        ContactRVAdapter.ViewHolder vh = new ContactRVAdapter.ViewHolder(view) ;

        return vh ;
    }
    public void filterList(ArrayList<PhoneBook> filterllist) {
        //on below line we are passing filtered array list in our original array list
        mList = filterllist;
        notifyDataSetChanged();
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(ContactRVAdapter.ViewHolder holder, int position) {
        this.position=position;
        PhoneBook modal = mList.get(position);
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        holder.phnum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        holder.name.setGravity(Gravity.CENTER);
        holder.phnum.setGravity(Gravity.LEFT);

        ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
        // generate random color
        int color = generator.getRandomColor();

        //below text drawable is a circular.
        TextDrawable drawable2 = TextDrawable.builder().beginConfig()
                .width(100)  // width in px
                .height(100) // height in px
                .endConfig()
                //as we are building a circular drawable we are calling a build round method.
                //in that method we are passing our text and color.
                .buildRound(modal.getName().substring(0, 1), color);
        //setting image to our image view on below line.
        holder.img.setImageDrawable(drawable2);


        //holder.img.setImageResource(R.drawable.per);
        holder.name.setText(mList.get(position).getName()) ;
        holder.phnum.setText(mList.get(position).getNum()) ;
        
        //여기 클릭 리스너 넣으면 라이프 사이클 때문에 안 됨\
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //on below line we are opening a new activity and passing data to it.
                Log.d("ContactRVAdapter", "리사이클러뷰 아이템 누름"+position);
                Intent i = new Intent(holder.itemView.getContext(), ContactDetailActivity.class);
                i.putExtra("name", modal.getName());
                i.putExtra("contact", modal.getNum());
                //on below line we are starting a new activity,
                holder.itemView.getContext().startActivity(i);
            }
        });

    }

    // getItemCount() - 전체 데이터 갯수 리턴.
    @Override
    public int getItemCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }
}