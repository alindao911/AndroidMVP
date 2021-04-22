package com.example.training.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.training.realm.ProductModel;
import com.example.training.R;

import java.util.List;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private final OnItemClickListener listener;
    private List<ProductModel> data;
    private Context context;

    public HomeAdapter(Context context, List<ProductModel> data, OnItemClickListener listener) {
        this.data = data;
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public HomeAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_home, null);
        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT,
                RecyclerView.LayoutParams.WRAP_CONTENT));
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeAdapter.ViewHolder holder, int position) {
        holder.click(data.get(position), listener);
        holder.tvName.setText(data.get(position).getName());
        holder.tvStockNumber.setText(String.valueOf(data.get(position).getStockNumber()));
        holder.tvVariant.setText(data.get(position).getVariant());
        holder.tvPrice.setText(String.valueOf(data.get(position).getPrice()));
        holder.tvOnHandQuantity.setText(String.valueOf(data.get(position).getOnHandStockQuantity()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public interface OnItemClickListener {
        void onClick(ProductModel item);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        TextView tvStockNumber;
        TextView tvVariant;
        TextView tvPrice;
        TextView tvOnHandQuantity;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_name);
            tvStockNumber = itemView.findViewById(R.id.tv_stock_number);
            tvVariant = itemView.findViewById(R.id.tv_variant);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvOnHandQuantity = itemView.findViewById(R.id.tv_on_hand_quantity);
        }

        public void click(final ProductModel productModel, final OnItemClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(productModel);
                }
            });
        }
    }
}
