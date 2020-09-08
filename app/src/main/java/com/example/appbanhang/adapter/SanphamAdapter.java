package com.example.appbanhang.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.activity.ChiTietSanPham;
import com.example.appbanhang.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arraysanpham;

    public SanphamAdapter(ArrayList<Sanpham> arraysanpham,Context context) {
        this.context = context;
        this.arraysanpham = arraysanpham;
    }

    @Override
    public ItemHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder= new ItemHolder(v);

        return itemHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
            Sanpham sanpham= arraysanpham.get(position);
            holder.txttensanpham.setText(sanpham.getTensanpham());
            DecimalFormat decimalFormat= new DecimalFormat("###,###,###");
            holder.txtgiasanpham.setText("Giá: " + decimalFormat.format(sanpham.getGiasanpham())+" VNĐ");
            Picasso.with(context).load(sanpham.getHinhanhsanpham())
                    .placeholder(R.drawable.error)
                    .error(R.drawable.error)
                    .into(holder.imghinhsanpham);
    }

    @Override
    public int getItemCount() {
        return arraysanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        public ImageView imghinhsanpham ;
        public TextView txttensanpham,txtgiasanpham;
        public ItemHolder(View itemView){
            super(itemView);
            imghinhsanpham=(ImageView) itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham= itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham= itemView.findViewById(R.id.textviewtensanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arraysanpham.get(getPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);

                }
            });
        }
    }
}
