package com.example.gestionstock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Product;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ProductViewHolder> {
    private final LayoutInflater inflater;
    private final View.OnClickListener listener;
    private List<Product> products;

    public ProductListAdapter(Context context, View.OnClickListener listener) {
        super();
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.product_recyclerview_layout, parent, false);
        return new ProductViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        if (products != null) {
            Product product = products.get(position);
            holder.setProductData(product);
        }
    }

    @Override
    public int getItemCount() {
        if (products != null) return products.size();
        return 0;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    public class ProductViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView name;
        private final TextView reference;
        private final TextView qty;
        private final MaterialCardView productCard;

        public ProductViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            name = itemView.findViewById(R.id.tv_name);
            reference = itemView.findViewById(R.id.tv_reference);
            qty = itemView.findViewById(R.id.tv_qty);
            productCard = itemView.findViewById(R.id.product_card_view);
            productCard.setOnClickListener(listener);
        }

        public void setProductData(Product product) {
            id.setText(product.getId().toString());
            name.setText(product.getName());
            qty.setText(product.getQty().toString());
            reference.setText(product.getReference());
        }
    }
}
