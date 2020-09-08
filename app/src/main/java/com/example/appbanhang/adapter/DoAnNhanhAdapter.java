package com.example.appbanhang.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appbanhang.R;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class DoAnNhanhAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayDAN;

    public DoAnNhanhAdapter(Context context, ArrayList<Sanpham> arrayDAN) {
        this.context = context;
        this.arrayDAN = arrayDAN;
    }

    @Override
    public int getCount() {
        return arrayDAN.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayDAN.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtTenDAN,txtGiaDAN,txtMotaDAN;
        public ImageView imgDAN;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view ==null){
            viewHolder= new DoAnNhanhAdapter.ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_doannhanh,null);
            viewHolder.txtTenDAN=view.findViewById(R.id.txtTenDAN);
            viewHolder.txtGiaDAN=view.findViewById(R.id.txtGiaDAN);
            viewHolder.txtMotaDAN=view.findViewById(R.id.txtMotaDAN);
            viewHolder.imgDAN=view.findViewById(R.id.imgDAN);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(DoAnNhanhAdapter.ViewHolder)view.getTag();
        }
        Sanpham sanpham=(Sanpham) getItem(i);
        viewHolder.txtTenDAN.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaDAN.setText("Giá :"+decimalFormat.format(sanpham.getGiasanpham())+" VNĐ");
        viewHolder.txtMotaDAN.setMaxLines(2);
        viewHolder.txtMotaDAN.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaDAN.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgDAN);
        return view;
    }
}
