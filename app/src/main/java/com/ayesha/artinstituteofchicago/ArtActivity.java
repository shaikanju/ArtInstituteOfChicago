
package com.ayesha.artinstituteofchicago;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

public class ArtActivity extends AppCompatActivity {

    private TextView titleDisplay, dateDisplay, artistName, artistDate, depTitle, galleryTitle, origin, artworkType, dimensions, creditLine;
    private ImageView ArtImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.artwork_activity);

        // Initialize the UI elements
        titleDisplay = findViewById(R.id.titleDisplay);
        dateDisplay = findViewById(R.id.dateDisplay);
        artistName = findViewById(R.id.artistName);
        artistDate = findViewById(R.id.artistDate);
        ArtImage = findViewById(R.id.ArtImage);
        depTitle = findViewById(R.id.depTitle);
        galleryTitle = findViewById(R.id.galleryTitle);
        origin = findViewById(R.id.origin);
        artworkType = findViewById(R.id.artworkType);
        dimensions = findViewById(R.id.dimensions);
        creditLine = findViewById(R.id.creditLine);
        ImageView imageView = findViewById(R.id.logoback);
        ImageView externalLink = findViewById(R.id.imageView3);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArtActivity.this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            }
        });

        // Get the data passed from the adapter
        Intent intent = getIntent();
        String title = checkForNull(intent.getStringExtra("title"));
        String dateDisplayText = checkForNull(intent.getStringExtra("date_display"));
        String artistDisplay = checkForNull(intent.getStringExtra("artist_display"));
        String artistNames = checkForNull(intent.getStringExtra("artist"));
        String mediumDisplay = checkForNull(intent.getStringExtra("medium_display"));
        String artworkTypeTitle = checkForNull(intent.getStringExtra("artwork_type_title"));
        String imageId = intent.getStringExtra("image_id"); // No null check for imageId as per requirement
        String dimensionsText = checkForNull(intent.getStringExtra("dimensions"));
        String departmentTitle = checkForNull(intent.getStringExtra("department_title"));
        String creditLineText = checkForNull(intent.getStringExtra("credit_line"));
        String placeOfOrigin = checkForNull(intent.getStringExtra("place_of_origin"));
        String galleryTitleText = intent.getStringExtra("gallery_title"); // No null check for galleryTitleText as per requirement
        int galleryId = intent.getIntExtra("gallery_id", -1);

        Log.d("MainActivity", "Gallery ID: " + galleryId);
        Log.d("MainActivity", "GalleryText: " + galleryTitleText);

        // Setting up the gallery URL link if galleryTitleText and galleryId are valid
        if (!"null".equals(galleryTitleText) && galleryId != -1) {
            String galleryUrl = "https://www.artic.edu/galleries/" + galleryId;
            SpannableStringBuilder spannable = new SpannableStringBuilder(galleryTitleText);
            ClickableSpan clickableSpan = new ClickableSpan() {
                @Override
                public void onClick(View widget) {
                    Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(galleryUrl));
                    startActivity(browserIntent);
                }
            };
            spannable.setSpan(clickableSpan, 0, spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            galleryTitle.setText(spannable);
            externalLink.setVisibility(View.VISIBLE);
            galleryTitle.setMovementMethod(LinkMovementMethod.getInstance()); // Enable clicking
        } else {
            galleryTitle.setText("Not on Display");
            externalLink.setVisibility(View.GONE);
        }

        // Set the data to UI elements
        titleDisplay.setText(title);
        dateDisplay.setText(dateDisplayText);
        artistName.setText(artistNames);
        artistDate.setText(artistDisplay);
        artworkType.setText(artworkTypeTitle + " - " + mediumDisplay);
        dimensions.setText(dimensionsText);
        depTitle.setText(departmentTitle);
        creditLine.setText(creditLineText);
        origin.setText(placeOfOrigin);

        // Load the artwork image using Picasso
        if (imageId != null && !imageId.isEmpty()) {
            String imageUrl = "https://www.artic.edu/iiif/2/" + imageId + "/full/843,/0/default.jpg"; // Replace with the correct image URL pattern
            Picasso.get()
                    .load(imageUrl)
                    .error(R.drawable.not_available)
                    .into(ArtImage);

            ArtImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent imageIntent = new Intent(ArtActivity.this, ImageActivity.class);
                    imageIntent.putExtra("title", title);
                    imageIntent.putExtra("artist_name", artistNames);
                    imageIntent.putExtra("artist_details", artistDisplay);
                    imageIntent.putExtra("image_url", imageUrl);
                    startActivity(imageIntent);
                }
            });
        }
    }


    private String checkForNull(String value) {
        return "null".equals(value) ? "" : value;
    }
}
