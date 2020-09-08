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

public class MonLauAdapter extends BaseAdapter {
    Context context;
    ArrayList<Sanpham> arrayLau;

    public MonLauAdapter(Context context, ArrayList<Sanpham> arrayLau) {
        this.context = context;
        this.arrayLau = arrayLau;
    }

    @Override
    public int getCount() {
        return arrayLau.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayLau.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtTenLau,txtGiaLau,txtMotaLau;
        public ImageView imgLau;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view ==null){
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_monlau,null);
            viewHolder.txtTenLau=view.findViewById(R.id.txtTenLau);
            viewHolder.txtGiaLau=view.findViewById(R.id.txtGiaLau);
            viewHolder.txtMotaLau=view.findViewById(R.id.txtMotaLau);
            viewHolder.imgLau=view.findViewById(R.id.imgLau);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(ViewHolder)view.getTag();
        }
        Sanpham sanpham=(Sanpham) getItem(i);
        viewHolder.txtTenLau.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtGiaLau.setText("Giá :"+decimalFormat.format(sanpham.getGiasanpham())+" VNĐ");
        viewHolder.txtMotaLau.setMaxLines(2);
        viewHolder.txtMotaLau.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMotaLau.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgLau);

        return view;
    }
}
