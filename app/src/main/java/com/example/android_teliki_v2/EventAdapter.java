package com.example.android_teliki_v2;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

public class EventAdapter extends FirestoreRecyclerAdapter<Event, EventAdapter.EventHolder>{


    public EventAdapter(@NonNull FirestoreRecyclerOptions<Event> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        holder.textViewEvent.setText(model.getEvent());
        holder.textViewUsername.setText(model.getUsername());
        holder.textViewComment.setText(model.getComment());

    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,
                parent, false);
        return new EventHolder(v);
    }

    class EventHolder extends RecyclerView.ViewHolder{
        TextView textViewEvent;
        TextView textViewUsername;
        TextView textViewComment;


        public EventHolder(@NonNull View itemView) {
            super(itemView);
            textViewEvent = itemView.findViewById(R.id.tveventType);
            textViewUsername = itemView.findViewById(R.id.tvusername);
            textViewComment = itemView.findViewById(R.id.tvcomment);
        }
    }
}
