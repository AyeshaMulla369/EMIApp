package com.example.myemi;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ClassViewHolder> {

    ArrayList<EmiItems> emiItems;
    Context context;

    private OnItemClickListener onItemClickListener;
    public interface OnItemClickListener{
        void onClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public ListAdapter(Context context , ArrayList<EmiItems> emiItems) {
        this.emiItems = emiItems;
        this.context = context;
    }

    public static class ClassViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{

        TextView id;
        TextView name;
        CardView cardView;
        public ClassViewHolder(@NonNull View itemView , OnItemClickListener onItemClickListener) {
            super(itemView);
            id = itemView.findViewById(R.id.dataid);
            name = itemView.findViewById(R.id.name);
            cardView = itemView.findViewById(R.id.cardView);
            itemView.setOnClickListener(v -> {
                onItemClickListener.onClick(getAdapterPosition());
            });
            itemView.setOnCreateContextMenuListener(this);
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(getAdapterPosition(),0,0,"EDIT");
            menu.add(getAdapterPosition(),1,0,"DELETE");
        }
    }

    @NonNull
    @Override
    public ClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_set , parent, false);
        return new ClassViewHolder(itemView,onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ClassViewHolder holder, int position) {

        holder.id.setText(String.valueOf(emiItems.get(position).getSid() ));
        holder.name.setText(emiItems.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return emiItems.size();
    }
}
