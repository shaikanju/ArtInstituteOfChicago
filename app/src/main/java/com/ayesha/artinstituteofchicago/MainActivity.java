package com.ayesha.artinstituteofchicago;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkCapabilities;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArtworkAdapter artworkAdapter;
    ImageView backgroundImage;
    EditText searchText;
    RequestQueue queue;
    boolean responseReceived = false;
    ProgressBar progressBar;
    private List<Artwork> ArtworkList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         progressBar = findViewById(R.id.progressBar);
        backgroundImage =findViewById(R.id.background);
        Button searchButton = findViewById(R.id.buttonsearch);
        searchText = findViewById(R.id.serachtext);
        recyclerView = findViewById(R.id.recyclerView);  // Link to the RecyclerView in the layout
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        artworkAdapter = new ArtworkAdapter(ArtworkList, this);  // Adapter initialization
        recyclerView.setAdapter(artworkAdapter);
        ImageView clearButton = findViewById(R.id.clear);
        Button randomButton = findViewById(R.id.buttonrandom);
        TextView copyrightlink = findViewById(R.id.copyrightTextView);

        copyrightlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start the CopyrightActivity
                Intent intent = new Intent(MainActivity.this, CopyrightActivity.class);
                startActivity(intent);
            }
        });
        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
            }
        });
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = searchText.getText().toString().trim();
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                boolean isConnected = capabilities != null && (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED));

                if (!isConnected) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("No Connection Error")
                            .setMessage("No network connection present - cannot contact Art Institute API server.")
                            .setPositiveButton("OK", null)
                            .setIcon(R.drawable.logo)
                            .show();
                }
                if (query.length() < 3 && !query.isEmpty() ) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("Search String Too Short")
                            .setIcon(R.drawable.logo)
                            .setMessage("Please try a longer search string.")
                            .setPositiveButton("OK", null)
                            .show();
                    return;
                }
                if (query.isEmpty()) {
                    return;
                }

                else  {
                    progressBar.setVisibility(View.VISIBLE);
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                    searchArtworks(query);
                }
            }
        });


        randomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(MainActivity.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(searchText.getWindowToken(), 0);
                ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkCapabilities capabilities = cm.getNetworkCapabilities(cm.getActiveNetwork());
                boolean isConnected = capabilities != null && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);

                if (!isConnected) {
                    new AlertDialog.Builder(v.getContext())
                            .setTitle("No Connection Error")
                            .setMessage("No network connection present - cannot contact Art Institute API server.")
                            .setPositiveButton("OK", null)
                            .show();
                } else {
                    progressBar.setVisibility(View.VISIBLE);
                    getAllGalleries();
                }
            }
        });

    }
    private void getAllGalleries() {
        String url = "https://api.artic.edu/api/v1/galleries?limit=100&fields=id&page=1";

        queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        ArrayList<String> galleryIds = parseGalleryResponse(response);
                        if (!galleryIds.isEmpty()) {
                            getRandomArtworkFromGallery(galleryIds);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        queue.add(jsonObjectRequest);
    }

    private ArrayList<String> parseGalleryResponse(JSONObject response) {
        ArrayList<String> galleryIds = new ArrayList<>();
        try {
            JSONArray dataArray = response.getJSONArray("data");
            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject galleryObj = dataArray.getJSONObject(i);
                galleryIds.add(galleryObj.getString("id"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return galleryIds;
    }
    private void getRandomArtworkFromGallery(ArrayList<String> galleryIds) {
        // Randomly select a gallery id
        String randomGalleryId = galleryIds.get(new Random().nextInt(galleryIds.size()));

        String artworkUrl = "https://api.artic.edu/api/v1/artworks/search?query[term][gallery_id]=" + randomGalleryId
                + "&limit=100&fields=title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, artworkUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            if (dataArray.length() > 0) {
                                ArtworkList.clear();
                                showRecyclerView();
                                hideBackgroundImage();
                                // Randomly select an artwork from the gallery
                                JSONObject artworkObj = dataArray.getJSONObject(new Random().nextInt(dataArray.length()));
                                Artwork randomArtwork = parseArtworkObject(artworkObj);
                                ArtworkList.add(randomArtwork);
                                // Notify the adapter that the data set has changed
                                artworkAdapter.notifyDataSetChanged();
                            } else {
                                // If no artwork found, try another gallery
                                getRandomArtworkFromGallery(galleryIds);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        progressBar.setVisibility(View.GONE);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        queue.add(jsonObjectRequest);
    }
    private Artwork parseArtworkObject(JSONObject artworkObj) throws JSONException {
        Artwork artwork = new Artwork();


        artwork.setTitle(artworkObj.getString("title"));
        artwork.setDateDisplay(artworkObj.getString("date_display"));

//
        String artistDisplay = artworkObj.getString("artist_display");
        String artistName = "";
        String artistDetails = "";

// Check for newline character; if it’s found, split into two parts.
        if (artistDisplay.contains("\n")) {
            String[] artistParts = artistDisplay.split("\n", 2);
            artistName = artistParts[0].trim();
            artistDetails = artistParts[1].trim();
        } else {
            // If no newline, use regex to check if it matches "Name (Country, Year-Year)"
            // Extract the part before "(" as the artist name and the rest as artist details.
            if (artistDisplay.contains("(")) {
                int startIndex = artistDisplay.indexOf("(");
                artistName = artistDisplay.substring(0, startIndex).trim();
                artistDetails = artistDisplay.substring(startIndex).trim(); // Keeps "(Country, Year-Year)"
            } else {
                // If format doesn't match, assign the entire string as artist name.
                artistName = artistDisplay.trim();
                artistDetails = ""; // No additional details available.
            }
        }

// Set artist and artist details to artwork
        artwork.setArtist(artistName);
        artwork.setArtistDetails(artistDetails);

// Log the values to confirm they’re correctly set




        artwork.setMediumDisplay(artworkObj.optString("medium_display", ""));
        artwork.setArtworkTypeTitle(artworkObj.optString("artwork_type_title", ""));
        artwork.setImageId(artworkObj.optString("image_id", ""));
        artwork.setDimensions(artworkObj.optString("dimensions", ""));
        artwork.setDepartmentTitle(artworkObj.optString("department_title", ""));
        artwork.setCreditLine(artworkObj.optString("credit_line", ""));
        artwork.setPlaceOfOrigin(artworkObj.optString("place_of_origin", ""));

        artwork.setGalleryTitle(artworkObj.optString("gallery_title", "Not on Display"));
        artwork.setGalleryId(artworkObj.isNull("gallery_id") ? null : artworkObj.getInt("gallery_id"));
        artwork.setApiLink(artworkObj.getString("api_link"));

        // Build image URLs
        String baseUrl = "https://www.artic.edu/iiif/2/";
        String thumbnailUrl = baseUrl + artwork.getImageId() + "/full/200,/0/default.jpg";
        String fullImageUrl = baseUrl + artwork.getImageId() + "/full/843,/0/default.jpg";

        artwork.setThumbnailUrl(thumbnailUrl);
        artwork.setFullImageUrl(fullImageUrl);

        return artwork;
    }


    private void searchArtworks(String query) {
        String url = Uri.parse("https://api.artic.edu/api/v1/artworks/search")
                .buildUpon()
                .appendQueryParameter("q", query)
                .appendQueryParameter("limit", "15")
                .appendQueryParameter("page", "1")
                .appendQueryParameter("fields", "title,date_display,artist_display,medium_display,artwork_type_title,image_id,dimensions,department_title,credit_line,place_of_origin,gallery_title,gallery_id,id,api_link")
                .toString();

         queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                         responseReceived = true;
                        progressBar.setVisibility(View.GONE);
                        parseArtworkResponse(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(jsonObjectRequest);
    }


    private void  parseArtworkResponse(JSONObject response) {
        try {
            JSONArray dataArray = response.getJSONArray("data");
            ArtworkList.clear(); // Clear previous results
            String query = searchText.getText().toString().trim();
            if (dataArray.length() == 0) {
                // Show alert if no results are found
                new AlertDialog.Builder(MainActivity.this)
                        .setTitle("No search Results Found")
                        .setIcon(R.drawable.logo)
                        .setMessage("No  results found for \"" + query + "\".\n Please try another search string")
                        .setPositiveButton("OK", null)
                        .show();
            }

            for (int i = 0; i < dataArray.length(); i++) {
                JSONObject artworkObj = dataArray.getJSONObject(i);

                Artwork artwork = new Artwork(); // Create a new Artwork object

                // Set the properties using setters
                artwork.setTitle(artworkObj.getString("title"));
                artwork.setDateDisplay(artworkObj.getString("date_display"));

//
                String artistDisplay = artworkObj.getString("artist_display");
                String artistName = "";
                String artistDetails = "";

// Check for newline character; if it’s found, split into two parts.
                if (artistDisplay.contains("\n")) {
                    String[] artistParts = artistDisplay.split("\n", 2);
                    artistName = artistParts[0].trim();
                    artistDetails = artistParts[1].trim();
                } else {
                    // If no newline, use regex to check if it matches "Name (Country, Year-Year)"
                    // Extract the part before "(" as the artist name and the rest as artist details.
                    if (artistDisplay.contains("(")) {
                        int startIndex = artistDisplay.indexOf("(");
                        artistName = artistDisplay.substring(0, startIndex).trim();
                        artistDetails = artistDisplay.substring(startIndex).trim(); // Keeps "(Country, Year-Year)"
                    } else {
                        // If format doesn't match, assign the entire string as artist name.
                        artistName = artistDisplay.trim();
                        artistDetails = ""; // No additional details available.
                    }
                }

// Set artist and artist details to artwork
                artwork.setArtist(artistName);
                artwork.setArtistDetails(artistDetails);

// Log the values to confirm they’re correctly set
                Log.d("ArtworkActivity", "Artist Name: " + artwork.getArtist());
                Log.d("ArtworkActivity", "Artist Details: " + artwork.getArtistDetails());



                artwork.setMediumDisplay(artworkObj.optString("medium_display", ""));
                artwork.setArtworkTypeTitle(artworkObj.optString("artwork_type_title", ""));
                artwork.setImageId(artworkObj.optString("image_id", ""));
                artwork.setDimensions(artworkObj.optString("dimensions", ""));
                artwork.setDepartmentTitle(artworkObj.optString("department_title", ""));
                artwork.setCreditLine(artworkObj.optString("credit_line", ""));
                artwork.setPlaceOfOrigin(artworkObj.optString("place_of_origin", ""));

                artwork.setGalleryTitle(artworkObj.optString("gallery_title", "Not on Display"));
                artwork.setGalleryId(artworkObj.isNull("gallery_id") ? null : artworkObj.getInt("gallery_id"));
                artwork.setApiLink(artworkObj.getString("api_link"));

                // Build image URLs
                String baseUrl = "https://www.artic.edu/iiif/2/";
                String thumbnailUrl = baseUrl + artwork.getImageId() + "/full/200,/0/default.jpg";
                String fullImageUrl = baseUrl + artwork.getImageId() + "/full/843,/0/default.jpg";

                artwork.setThumbnailUrl(thumbnailUrl);
                artwork.setFullImageUrl(fullImageUrl);

                // Add artwork to the list
                ArtworkList.add(artwork);

            }

            // Notify the adapter that the data set has changed
            artworkAdapter.notifyDataSetChanged();
            if (responseReceived) {
                showRecyclerView();
                hideBackgroundImage();
            } else {
                hideRecyclerView();
                showBackgroundImage();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void hideBackgroundImage() {
        backgroundImage.setVisibility(View.GONE); // Hide the background image
    }

    private void showBackgroundImage() {
        backgroundImage.setVisibility(View.VISIBLE); // Show the background image
    }

    private void hideRecyclerView() {
        recyclerView.setVisibility(View.GONE); // Hide the RecyclerView
    }

    private void showRecyclerView() {
        recyclerView.setVisibility(View.VISIBLE); // Show the RecyclerView
    }
}