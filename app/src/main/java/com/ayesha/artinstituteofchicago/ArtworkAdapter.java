package com.ayesha.artinstituteofchicago;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class ArtworkAdapter extends RecyclerView.Adapter<ArtworkViewHolder> {

    private List<Artwork> artworkList;
    private Context context;

    public ArtworkAdapter(List<Artwork> artworkList, Context context) {
        this.artworkList = artworkList;
        this.context = context;
    }

    @NonNull
    @Override
    public ArtworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new ArtworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ArtworkViewHolder holder, int position) {
        Artwork artwork = artworkList.get(position);
        holder.title.setText(artwork.getTitle());

        // Use Picasso to load the thumbnail image
        Picasso.get()
                .load(artwork.getThumbnailUrl())

                .error(R.drawable.not_available) // Optional: Error image if loading fails
                .into(holder.thumbnail);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ArtActivity.class);
                intent.putExtra("title", artwork.getTitle());
                intent.putExtra("date_display", artwork.getDateDisplay());
                intent.putExtra("artist_display", artwork.getArtistDetails());
                intent.putExtra("artist", artwork.getArtist());
                intent.putExtra("medium_display", artwork.getMediumDisplay());
                intent.putExtra("artwork_type_title", artwork.getArtworkTypeTitle());
                intent.putExtra("image_id", artwork.getImageId());
                intent.putExtra("dimensions", artwork.getDimensions());
                intent.putExtra("department_title", artwork.getDepartmentTitle());
                intent.putExtra("credit_line", artwork.getCreditLine());
                intent.putExtra("place_of_origin", artwork.getPlaceOfOrigin());
                intent.putExtra("gallery_title", artwork.getGalleryTitle());
                intent.putExtra("gallery_id", artwork.getGalleryId());
                intent.putExtra("id", artwork.getId());
                intent.putExtra("api_link", artwork.getApiLink());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return artworkList.size();
    }
}

