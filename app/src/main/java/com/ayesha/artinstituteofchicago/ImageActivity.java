package com.ayesha.artinstituteofchicago;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;
import com.github.chrisbanes.photoview.PhotoView;
public class ImageActivity extends AppCompatActivity {

    private TextView titleText, artistNameText, artistDetailsText, textView11;
    private PhotoView fullImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        Log.d("ImageActivity", "Activity created");

        // Initialize the UI elements
        titleText = findViewById(R.id.titleDisplay);
        artistNameText = findViewById(R.id.dateDisplay);
        artistDetailsText = findViewById(R.id.textView11);
        fullImage = findViewById(R.id.mainImage);
        textView11 = findViewById(R.id.zoomScale);
        ImageView imageView = findViewById(R.id.logoback);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ImageActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
            }
        });

        // Get the intent data
        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String artistName = intent.getStringExtra("artist_name");
        String artistDetails = intent.getStringExtra("artist_details");
        String imageUrl = intent.getStringExtra("image_url");

        // Set the data to UI elements
        titleText.setText(title);
        artistNameText.setText(artistName);
        artistDetailsText.setText(artistDetails);

        // Load the image
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Picasso.get()
                    .load(imageUrl)
                    .fit()
                    .centerCrop()
                    .into(fullImage, new com.squareup.picasso.Callback() {
                        @Override
                        public void onSuccess() {
                            Log.d("ImageActivity", "Image loaded successfully");
                        }

                        @Override
                        public void onError(Exception e) {
                            Log.e("ImageActivity", "Error loading image: " + e.getMessage());
                        }
                    });

            // Set PhotoView scaling options
            fullImage.setMaximumScale(10.0f); // Set this to a value greater than medium zoom
            fullImage.setMinimumScale(1f);
            fullImage.setMediumScale(3.5f); // Max zoom
            fullImage.setScaleType(ImageView.ScaleType.FIT_CENTER);
            fullImage.setScale(1.0f, true);
            fullImage.setZoomable(true); // Ensure zooming is enabled

            // Set on photo tap listener to update the zoom level percentage
            fullImage.setOnPhotoTapListener((view, x, y) -> {
                updateZoomPercentage();
            });

            // Update zoom percentage when the user zooms the image
            fullImage.setOnMatrixChangeListener(matrix -> {
                updateZoomPercentage();
            });
        }
    }

    //    private void updateZoomPercentage() {
//        float scale = fullImage.getScale();
//        float percentage = (scale / fullImage.getMaximumScale()) * 100;
//        textView11.setText(String.format("Zoom Level: %.2f%%", percentage));
//        Log.d("ImageActivity", "Current zoom level: " + String.format("%.2f%%", percentage));
//    }
    private void updateZoomPercentage() {
        float scale = fullImage.getScale();
        float maxScale = fullImage.getMaximumScale();
        float percentage = (scale / maxScale) * 1000; // Adjusted calculation for percentage
        textView11.setText(String.format("Zoom Level: %.2f%%", percentage));
        Log.d("ImageActivity", "Current zoom level: " + String.format("%.2f%%", percentage));
    }

}
