package com.example.gestionstock.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Provider;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ProviderListAdapter extends RecyclerView.Adapter<ProviderListAdapter.ProviderViewHolder> {
    private final LayoutInflater inflater;
    private final View.OnClickListener listener;
    private List<Provider> providers;

    public ProviderListAdapter(Context context, View.OnClickListener listener) {
        super();
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProviderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.provider_recyclerview_layout, parent, false);
        return new ProviderViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ProviderViewHolder holder, int position) {
        if (providers != null) {
            Provider provider = providers.get(position);
            holder.setProviderData(provider);
        }
    }

    @Override
    public int getItemCount() {
        if (providers != null) return providers.size();
        return 0;
    }

    public void setProviders(List<Provider> providers) {
        this.providers = providers;
        notifyDataSetChanged();
    }

    public class ProviderViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView fullName;
        private final TextView email;
        private final TextView phoneNumber;
        private final MaterialCardView providerCard;

        public ProviderViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            fullName = itemView.findViewById(R.id.tv_fullname);
            email = itemView.findViewById(R.id.tv_email);
            phoneNumber = itemView.findViewById(R.id.tv_phone);
            providerCard = itemView.findViewById(R.id.provider_card_view);
            providerCard.setOnClickListener(listener);
        }

        public void setProviderData(Provider provider) {
            id.setText(provider.getId().toString());
            fullName.setText(String.format("%s %s", provider.getLastName(), provider.getFirstName()));
            email.setText(provider.getEmail());
            phoneNumber.setText(provider.getPhoneNumber());
        }
    }
}
