# PokéCard: An Android Pokémon Explorer 📱

PokéCard is a simple yet powerful Android application built to demonstrate modern Android development practices. It uses the PokéAPI to display a comprehensive list of Pokémon, allowing users to browse, view details, and enjoy a clean, user-friendly interface.

## ✨ Features

* **Infinite Scrolling List**: Seamlessly scroll through the entire list of Pokémon, with new creatures loading automatically as you reach the bottom.
* **Pull-to-Refresh**: Swipe down from the top of the list to refresh the data and get the latest updates.
* **Detailed Pokémon View**: Tap on any Pokémon to navigate to a detailed screen showing its name, ID, height, weight, base stats, and abilities.
* **High-Quality Images**: View beautiful, high-resolution artwork for each Pokémon using the Glide image loading library.
* **Clean, Modern UI**: Built with Material Design components for an intuitive and visually appealing experience.

## 📸 Screenshots

| Pokémon List | Pokémon Detail |
| :----------: | :------------: |
| <img src="https://github.com/user-attachments/assets/0e255820-6dd1-4de1-ad7c-5019c271a8ce" alt="Pokémon List Screen" width="250"/> | <img src="https://github.com/user-attachments/assets/473edb3a-d78b-43f7-afd9-e7ff3f126826" alt="Pokémon Detail Screen" width="250"/> |

## 🛠️ Tech Stack & Architecture

This project follows modern Android architecture and utilizes a range of popular libraries.

* **Language**: **Kotlin**
* **Architecture**: **MVVM (Model-View-ViewModel)** - A robust architecture that separates the UI from the business logic, making the code cleaner and easier to test.
* **Asynchronous Operations**: **Coroutines** - For managing background threads and making API calls without freezing the UI.
* **Networking**: **Retrofit & OkHttp** - For making efficient and clean HTTP requests to the PokéAPI.
* **Image Loading**: **Glide** - For loading, caching, and displaying images efficiently.
* **UI**: **Android XML** with **RecyclerView**, **CardView**, and **Material Design** components.

## API Reference

This application relies on the free and open-source **[PokéAPI](https://pokeapi.co/)**. A huge thank you to the community for maintaining this incredible resource.

## 🚀 Setup and Installation

To get this project running on your local machine, follow these simple steps:

1.  **Clone the repository:**
    ```bash
    git clone [https://your-repository-url.git](https://your-repository-url.git)
    ```
2.  **Open in Android Studio:**
    * Open Android Studio.
    * Click on **File > Open** and navigate to the cloned project folder.
3.  **Build the project:**
    * Let Android Studio sync and download the required Gradle dependencies.
    * Click the **Run 'app'** button (▶️) to build and install the application on an emulator or a physical device.
