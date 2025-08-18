# PinterestStyleGridDemo

A sample Android application demonstrating how to build a Pinterest-style (staggered) grid of images using Jetpack Compose. The app showcases dynamic image heights, infinite scrolling with placeholders, and modern Android development practices. This project evolves through several stages, each documented in a corresponding Medium article.

## 📸 Features (in the `main` branch)

*   **Pinterest-Style Staggered Grid:** Displays images in a staggered grid layout where items can have variable heights.
*   **Infinite Scrolling:** Images are loaded progressively as the user scrolls, providing a seamless browsing experience.
*   **Dynamic Image Heights:** Image cards adjust their height based on the actual image dimensions fetched from the API.
*   **Loading Placeholders:** Utilizes shimmer effects (via Accompanist Placeholder) while images and initial data are being loaded.
*   **Modern Android Tech Stack:** Built entirely with Kotlin and leverages modern Jetpack libraries.
*   **Clean Architecture (MVVM-like):** Follows a structured architecture with distinct layers for UI, ViewModel, Domain (Use Cases), and Data (Repository, PagingSource, API Service).
*   **Dependency Injection with Hilt.**

## 📖 Branch Guide & Medium Articles

This project was built incrementally. Each significant stage corresponds to a branch and a detailed Medium article:

*   **`part-1-setup`**
    *   **Focus:** Setting Up a Pinterest-Style Grid App with Clean Architecture in Jetpack Compose
    *   **Read the Article:** [Building a Production-Ready Pinterest-Style Grid with Jetpack Compose (Part 1)](https://medium.com/@sayvortana.itc/building-a-production-ready-pinterest-style-grid-with-jetpack-compose-part-1-efd7053fc160)

*   **`part-2-grid`**
    *   **Focus:** Implementing the Pinterest Grid Layout + Loading Images with Coil
    *   **Read the Article:** [Building a Production-Ready Pinterest-Style Grid with Jetpack Compose (Part 2)](https://medium.com/@sayvortana.itc/building-a-production-ready-pinterest-style-grid-with-jetpack-compose-part-2-7c9401d3e093)

*   **`part-3-network`**
    *   **Focus:** Integrating a Real API with Retrofit + Error Handling + Paging Prep
    *   **Read the Article:** [Building a Production-Ready Pinterest-Style Grid with Jetpack Compose (Part 3)](https://medium.com/@sayvortana.itc/building-a-production-ready-pinterest-style-grid-with-jetpack-compose-part-3-ff6e8730101b)

*   **`main` / `part-4-pagination`** (The `main` branch reflects the completed Part 4)
    *   **Focus:** Adding loading placeholders with Accompanist, refining the architecture (MVVM, Use Cases, Repository), and introducing Dependency Injection with Hilt, and infinte scrolling
    *   **Read the Article:** [Building a Production-Ready Pinterest-Style Grid with Jetpack Compose (Part 4)](https://medium.com/@sayvortana.itc/building-a-production-ready-pinterest-style-grid-with-jetpack-compose-part-4-5e9cfe618217)

To explore the code at a specific stage, check out the corresponding branch. The `main` branch contains the latest, most complete version of the project.

## 🛠️ Tech Stack & Libraries (as of `main` branch)

*   **Kotlin:** First-party programming language for Android development.
*   **Jetpack Compose:** Modern toolkit for building native Android UI.
    *   `androidx.compose.ui`: Core UI elements.
    *   `androidx.compose.material`: Material Design components.
    *   `androidx.compose.foundation`: Layouts, gestures, etc. (used for `LazyVerticalStaggeredGrid`).
    *   `androidx.compose.ui.tooling`: For previews and UI inspection.
*   **Jetpack ViewModel:** Manages UI-related data in a lifecycle-conscious way.
*   **Jetpack Paging 3:** Facilitates loading and displaying large datasets in chunks (for infinite scrolling).
    *   `androidx.paging.runtime`
    *   `androidx.paging.compose`
*   **Hilt:** For dependency injection.
    *   `com.google.dagger:hilt-android`
    *   `androidx.hilt:hilt-navigation-compose`
*   **Coroutines & Flow:** For asynchronous programming and reactive data streams.
*   **Retrofit 2:** Type-safe HTTP client for Android and Java (for networking).
*   **Moshi:** Modern JSON library for Android, Kotlin, and Java.
*   **OkHttp 3:** HTTP client (used by Retrofit) with an interceptor for logging.
*   **Coil:** Image loading library for Android backed by Kotlin Coroutines.
    *   `io.coil-kt:coil-compose`
*   **Accompanist Libraries:**
    *   `com.google.accompanist:accompanist-placeholder-material`: For shimmer loading effects.
    *   `com.google.accompanist:accompanist-systemuicontroller`: To control system UI (status bar, navigation bar).
*   **Material Components:** For some theme aspects and compatibility.

## 🏗️ Architecture (as of `main` branch)

The project follows an MVVM-like architecture pattern, with distinct layers:

*   **UI (Presentation) Layer:**
    *   Jetpack Compose screens (`HomeScreen`, `ImageCard`).
    *   Observes data from ViewModels.
    *   Located in `presentation.ui`.
*   **ViewModel Layer:**
    *   `HomeViewModel` manages the state for `HomeScreen` and interacts with Use Cases.
    *   Located in `presentation.viewmodel`.
*   **Domain Layer:**
    *   Contains business logic and use cases (`GetImagesUseCase`).
    *   Defines core data models (`Image`).
    *   Located in `domain.model` and `domain.usecase`.
*   **Data Layer:**
    *   Responsible for providing data to the domain layer.
    *   `ImageRepository` and `ImageRepositoryImpl`: Abstract and concrete implementations for data operations.
    *   `ImagePagingSource`: Implements the Paging 3 `PagingSource` to load paginated image data.
    *   `ApiService`: Retrofit interface defining API endpoints.
    *   Located in `data.remote`, `data.repository`, and `data.paging`.

Dependency Injection is managed by **Hilt**.


## 🚀 Setup & Build

1.  **Clone the repository:**
2.  **Open in Android Studio:**
    *   Open Android Studio (latest stable version recommended).
    *   Select "Open an Existing Project".
    *   Navigate to the cloned repository folder and open it.
3.  **Build the project:**
    *   Android Studio should automatically sync the Gradle project.
    *   Click on "Build" > "Make Project" or run the app on an emulator or physical device.

The app fetches images from the [Picsum Photos API](https://picsum.photos/).

## 💡 Possible Future Improvements

*   Replace Accompanist Placeholder with a Compose-native shimmer or placeholder solution as Accompanist libraries are being deprecated.
*   Implement detailed error handling and display more specific error messages to the user.
*   Add image detail screen when an image is tapped.
*   Implement unit tests for ViewModels, Use Cases, and Repositories.
*   Add UI tests with Jetpack Compose testing APIs.
*   Cache network responses using OkHttp or Room for better offline support.

---
