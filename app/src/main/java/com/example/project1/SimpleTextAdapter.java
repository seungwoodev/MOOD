package com.example.project1;

import androidx.recyclerview.widget.RecyclerView;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project1.PhoneBook;
import com.example.project1.R;

import java.util.ArrayList;


public class SimpleTextAdapter extends RecyclerView.Adapter<SimpleTextAdapter.ViewHolder> {

    private ArrayList<PhoneBook> mList = null ;

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
    public SimpleTextAdapter(ArrayList<PhoneBook> list) {
        this.mList = list ;
    }

    // onCreateViewHolder() - 아이템 뷰를 위한 뷰홀더 객체 생성하여 리턴.
    @Override
    public SimpleTextAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext() ;
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) ;

        View view = inflater.inflate(R.layout.f1_layout_complex, parent, false) ;
        SimpleTextAdapter.ViewHolder vh = new SimpleTextAdapter.ViewHolder(view) ;

        return vh ;
    }


    // onBindViewHolder() - position에 해당하는 데이터를 뷰홀더의 아이템뷰에 표시.
    @Override
    public void onBindViewHolder(SimpleTextAdapter.ViewHolder holder, int position) {
        holder.name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        holder.phnum.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

        holder.name.setGravity(Gravity.CENTER);
        holder.phnum.setGravity(Gravity.LEFT);


        //여기서 불러오면 되는데,,,
        holder.img.setImageResource(R.drawable.per);
        holder.name.setText(mList.get(position).getName()) ;
        holder.phnum.setText(mList.get(position).getNum()) ;
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