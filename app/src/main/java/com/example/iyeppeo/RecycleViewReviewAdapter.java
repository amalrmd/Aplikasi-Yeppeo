package com.example.iyeppeo;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class RecycleViewReviewAdapter extends RecyclerView.Adapter<RecycleViewReviewAdapter.MyViewHolder> {

    Context context;
    ArrayList<Helper3Class> list;

    public RecycleViewReviewAdapter(Context context, ArrayList<Helper3Class> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_review,parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Glide.with(context).load(list.get(position).getGambarreview()).into(holder.gambarreview);
        holder.namarreview.setText(list.get(position).getNamarreview());
        holder.namaprodukreview.setText(list.get(position).getNamaprodukreview());
        holder.komentarreview.setText(list.get(position).getKomentarreview());

        holder.recCardRev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailReview.class);
                intent.putExtra("GambarReview", list.get(holder.getAdapterPosition()).getGambarreview());
                intent.putExtra("NamaReview", list.get(holder.getAdapterPosition()).getNamarreview());
                intent.putExtra("NamaProdukReview", list.get(holder.getAdapterPosition()).getNamaprodukreview());
                intent.putExtra("KomentarReview", list.get(holder.getAdapterPosition()).getKomentarreview());
                intent.putExtra("Key", list.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void searchDataList(ArrayList<Helper3Class> searchList){
        list = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView namarreview, namaprodukreview, komentarreview;
        ImageView gambarreview;
        CardView recCardRev;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            namarreview = itemView.findViewById(R.id.recNamaRev);
            namaprodukreview = itemView.findViewById(R.id.recNamaSkincareRev);
            komentarreview = itemView.findViewById(R.id.recCommentRev);
            gambarreview = itemView.findViewById(R.id.recGambarRev);
            recCardRev = itemView.findViewById(R.id.recCardRev);
        }
    }
}
