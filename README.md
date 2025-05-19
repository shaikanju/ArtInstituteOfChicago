# 🖼️ Art Institute of Chicago Explorer App

A mobile application that allows users to explore artworks from the **Art Institute of Chicago** using their public API. Users can search for artwork using keywords or view a random piece from the museum's collection.

---

## 📱 Features

### 🔍 Artwork Search
- Search the Art Institute collection by keyword.
- View results in a scrollable **RecyclerView**.
- Each result shows a thumbnail image and title.
- Tapping a result opens a detailed view.

### 🖼️ Artwork Details View
- Displays:
  - Title
  - Artist & biography
  - Artwork date
  - Gallery & department
  - Medium, type, dimensions
  - Image (with zoom support)
- Includes a link to the gallery’s webpage (if on display).

### 🔎 Zoomable Image View
- Tapping the artwork image opens a new activity.
- Full-screen zoomable image using pinch-to-zoom and double-tap (via PhotoView).

### 🎲 Random Artwork (Extra Credit Feature)
- Tap "Random" to view a random artwork from a random gallery.
- All details shown as in search results.

### © Copyright
- Dedicated activity showing copyright info.
- Tappable links to Art Institute API docs and Google Fonts.

---

## 🌐 API Integration

Powered by the [Art Institute of Chicago API](https://api.artic.edu/docs), this app fetches:
- Search results
- Artwork metadata
- Image URLs
- Gallery links

Example API endpoint:
https://api.artic.edu/api/v1/artworks

---

## 🛠️ Technologies Used

| Category         | Tools & Libraries                                   |
|------------------|-----------------------------------------------------|
| Language         | Java                                                |
| IDE              | Android Studio                                      |
| Network          | Android Volley                                      |
| Image Loading    | Picasso                                             |
| UI Components    | RecyclerView, ScrollView, AlertDialog               |
| Image Zooming    | PhotoView Library                                   |
| Fonts            | Google Fonts (Cinzel)                               |
| API Source       | [Art Institute of Chicago](https://api.artic.edu/)  |

---

## 📂 App Structure

- **MainActivity**: Search bar, RecyclerView, Random button
- **ArtworkActivity**: Detailed view of artwork info
- **ImageActivity**: Full-screen zoomable image
- **CopyrightActivity**: App legal and resource info

---

## 📦 Installation

1. Clone the repository
2. Open in Android Studio
3. Run on device or emulator (API Level 25+)
4. Internet permission is required

---

## 💡 Development Notes

- Handles no internet, no results, and short search input errors gracefully
- RecyclerView is dynamically updated with results
- Uses `Uri.Builder` to construct API calls
- Artwork info is encapsulated in a custom `Artwork` class
- Handles null values (e.g., gallery info, artist bio) cleanly

---

## ✨ Screenshots (Optional)
> ![image](https://github.com/user-attachments/assets/d5a66181-04b3-4229-8884-90211e18fad0)
> ![image](https://github.com/user-attachments/assets/e0215f4c-5c9d-480a-8695-a68aa2c988eb)
> ![image](https://github.com/user-attachments/assets/e1109368-975b-48ca-bb84-0f0a3d9e1250)
> ![image](https://github.com/user-attachments/assets/e504c517-1eb9-4b8f-a331-ec352bc73b9d)
> ![image](https://github.com/user-attachments/assets/b9ff4736-9728-44ec-92f6-b15593562c76)
![image](https://github.com/user-attachments/assets/423fdf5b-4c47-470b-9204-39525c056653)
> ![image](https://github.com/user-attachments/assets/06b623b6-4257-4373-9de4-b50826e6e8c6)
> ![image](https://github.com/user-attachments/assets/476d0bec-f200-421b-96d4-c18019a3332c)
> ![image](https://github.com/user-attachments/assets/941943f2-945d-4f7f-b73b-9e9ae4e850ad)
> ![image](https://github.com/user-attachments/assets/4c2539f1-606d-4f02-8e37-b8a33aa570e6)
> ![image](https://github.com/user-attachments/assets/70829f1b-290e-4b68-b5fa-34f5bfbc3a96)
## 👩‍💻 Author

**Anju Shaik**  
Mobile Application Developer | Android Enthusiast  
GitHub: [@shaikanju](https://github.com/shaikanju)

---

## 📜 License

This app was developed as part of the **CSC 372/472: Mobile Applications Development for Android** course at the **College of Computing and Digital Media**.

---

