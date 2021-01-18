package com.example.gestionstock.adapters;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gestionstock.R;
import com.example.gestionstock.models.Client;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.List;

public class ClientListAdapter extends RecyclerView.Adapter<ClientListAdapter.ClientViewHolder> {
    private final LayoutInflater inflater;
    private final View.OnClickListener listener;
    private List<Client> clients;

    public ClientListAdapter(Context context, View.OnClickListener listener) {
        super();
        inflater = LayoutInflater.from(context);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ClientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = inflater.inflate(R.layout.client_recyclerview_layout, parent, false);
        return new ClientViewHolder(itemView, listener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClientViewHolder holder, int position) {
        if (clients != null) {
            Client client = clients.get(position);
            holder.setClientData(client);
        }
    }

    @Override
    public int getItemCount() {
        if (clients != null) return clients.size();
        return 0;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
        notifyDataSetChanged();
    }

    public class ClientViewHolder extends RecyclerView.ViewHolder {
        private final TextView id;
        private final TextView fullName;
        private final TextView email;
        private final TextView phoneNumber;
        private final MaterialCardView clientCard;

        public ClientViewHolder(@NonNull View itemView, View.OnClickListener listener) {
            super(itemView);
            id = itemView.findViewById(R.id.tv_id);
            fullName = itemView.findViewById(R.id.tv_fullname);
            email = itemView.findViewById(R.id.tv_email);
            phoneNumber = itemView.findViewById(R.id.tv_phone);
            clientCard = itemView.findViewById(R.id.client_card_view);
            clientCard.setOnClickListener(listener);
        }

        public void setClientData(Client client) {
            id.setText(client.getId().toString());
            fullName.setText(String.format("%s %s", client.getLastName(), client.getFirstName()));
            email.setText(client.getEmail());
            phoneNumber.setText(client.getPhoneNumber());
        }
    }
}
