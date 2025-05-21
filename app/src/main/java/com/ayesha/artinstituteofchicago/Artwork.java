package com.ayesha.artinstituteofchicago;

public class Artwork {
    private String title;                // Title of the artwork
    private String dateDisplay;          // Date of the artwork's creation
    private String artist;               // Name of the artist
    private String artistDetails;        // Additional details about the artist (e.g., nationality, lifespan)
    private String mediumDisplay;        // Medium used in the artwork (e.g., "Oil on canvas")
    private String artworkTypeTitle;     // Type of the artwork (e.g., "Painting")

    private Integer id;
    private String imageId;              // ID for retrieving the image
    private String dimensions;           // Artwork dimensions
    private String departmentTitle;      // Department where the artwork is housed
    private String creditLine;           // Credit line information
    private String placeOfOrigin;        // Place of origin of the artwork
    private String galleryTitle;         // Title of the gallery where the artwork is displayed
    private Integer galleryId;           // Gallery ID
    private String apiLink;              // API link for additional information
    private String thumbnailUrl;         // URL for thumbnail image
    private String fullImageUrl;         // URL for full-sized image

    // Getters and Setters for each field

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDateDisplay() {
        return dateDisplay;
    }

    public void setDateDisplay(String dateDisplay) {
        this.dateDisplay = dateDisplay;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getArtistDetails() {
        return artistDetails;
    }

    public void setArtistDetails(String artistDetails) {
        this.artistDetails = artistDetails;
    }

    public String getMediumDisplay() {
        return mediumDisplay;
    }

    public void setMediumDisplay(String mediumDisplay) {
        this.mediumDisplay = mediumDisplay;
    }

    public String getArtworkTypeTitle() {
        return artworkTypeTitle;
    }

    public void setArtworkTypeTitle(String artworkTypeTitle) {
        this.artworkTypeTitle = artworkTypeTitle;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getDimensions() {
        return dimensions;
    }

    public void setDimensions(String dimensions) {
        this.dimensions = dimensions;
    }

    public String getDepartmentTitle() {
        return departmentTitle;
    }

    public void setDepartmentTitle(String departmentTitle) {
        this.departmentTitle = departmentTitle;
    }

    public String getCreditLine() {
        return creditLine;
    }

    public void setCreditLine(String creditLine) {
        this.creditLine = creditLine;
    }

    public String getPlaceOfOrigin() {
        return placeOfOrigin;
    }

    public void setPlaceOfOrigin(String placeOfOrigin) {
        this.placeOfOrigin = placeOfOrigin;
    }

    public String getGalleryTitle() {
        return galleryTitle;
    }

    public void setGalleryTitle(String galleryTitle) {
        this.galleryTitle = galleryTitle;
    }

    public Integer getGalleryId() {
        return galleryId;
    }

    public void setGalleryId(Integer galleryId) {
        this.galleryId = galleryId;
    }

    public String getApiLink() {
        return apiLink;
    }

    public void setApiLink(String apiLink) {
        this.apiLink = apiLink;
    }

    public String getThumbnailUrl() {
        return thumbnailUrl;
    }

    public void setThumbnailUrl(String thumbnailUrl) {
        this.thumbnailUrl = thumbnailUrl;
    }

    public String getFullImageUrl() {
        return fullImageUrl;
    }

    public void setFullImageUrl(String fullImageUrl) {
        this.fullImageUrl = fullImageUrl;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
