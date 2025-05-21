package com.ayesha.artinstituteofchicago;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ArtworkViewHolder extends RecyclerView.ViewHolder {
    ImageView thumbnail;
    TextView title;

    public ArtworkViewHolder(@NonNull View itemView) {
        super(itemView);
        thumbnail = itemView.findViewById(R.id.imageView);
        title = itemView.findViewById(R.id.title);
    }
}
