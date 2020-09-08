package com.example.appbanhang.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.Quanlysanpham;
import com.example.appbanhang.activity.UpdateSanpham;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class QuanlysanphamAdapter extends BaseAdapter {

    Quanlysanpham context;
    ArrayList<Sanpham> arrayquanly;

    public QuanlysanphamAdapter(Quanlysanpham context, ArrayList<Sanpham> arrayquanly) {
        this.context = context;
        this.arrayquanly = arrayquanly;
    }


    @Override
    public int getCount() {
        return arrayquanly.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayquanly.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
    public class ViewHolder{
        public TextView txtquanlyten,txtquanlygia,txtquanlymota;
        public ImageView imgquanly;
        public Button btnxoasanpham,btnsuasanpham;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view ==null){
            viewHolder= new ViewHolder();
            LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view=layoutInflater.inflate(R.layout.dong_quanly,null);
            viewHolder.txtquanlyten=view.findViewById(R.id.textviewtenquanly);
            viewHolder.txtquanlygia=view.findViewById(R.id.textviewgiaquanly);
            viewHolder.txtquanlymota=view.findViewById(R.id.textviewmotaquanly);
            viewHolder.imgquanly=view.findViewById(R.id.imageviewquanly);
            viewHolder.btnxoasanpham=view.findViewById(R.id.buttonxoa);
            viewHolder.btnsuasanpham=view.findViewById(R.id.buttonsua);
            view.setTag(viewHolder);
        }
        else{
            viewHolder=(QuanlysanphamAdapter.ViewHolder)view.getTag();
        }
        Sanpham sanpham=(Sanpham) getItem(i);
        viewHolder.txtquanlyten.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtquanlygia.setText("Giá :"+decimalFormat.format(sanpham.getGiasanpham())+" VNĐ");
        viewHolder.txtquanlymota.setMaxLines(2);
        viewHolder.txtquanlymota.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtquanlymota.setText(sanpham.getMotasanpham());
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                .placeholder(R.drawable.noimage)
                .error(R.drawable.error)
                .into(viewHolder.imgquanly);
        viewHolder.btnxoasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                XacNhan(sanpham.getTensanpham(),sanpham.getID());
            }
        });
        viewHolder.btnsuasanpham.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context,sanpham.getTensanpham(),Toast.LENGTH_SHORT).show();
                Intent intent= new Intent(context, UpdateSanpham.class);
                intent.putExtra("sanpham",sanpham);
                context.startActivity(intent);
            }
        });
        return view;

    }
    private void XacNhan(String ten ,final int id){
        AlertDialog.Builder dialogxoa= new AlertDialog.Builder(context);
        {
            dialogxoa.setMessage("Bạn có muốn xóa "+ten+" không");
            dialogxoa.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    context.DeleteSanPham(id);

                }
            });
            dialogxoa.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            dialogxoa.show();
        }
    }
}


