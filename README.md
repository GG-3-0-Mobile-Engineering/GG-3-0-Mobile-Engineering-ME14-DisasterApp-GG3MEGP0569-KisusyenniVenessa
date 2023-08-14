# Disaster App

## Purpose

This repository contains the code for the Disaster App, which is the midterm submission of the Generasi GIGIH 3.0 program. The app is designed to provide information about disasters and their locations on a map, as well as offer various features for users to filter and view disaster data.

## Features

* Show disaster locations on the map.
* List disasters with filter options (flood, earthquake, fire, haze, wind, volcano).
* Filter disasters based on the area.
* Receive notification alerts based on water level.
* Support both light and dark themes.
* Include an animation loading feature.

## Getting Started

### Prerequisites

Make sure your Android device has API level 24 or higher.

### Clone Project

To get started with the project, clone it using the following command:

```
git clone "https://github.com/GG-3-0-Mobile-Engineering/ME14-DisasterApp-GG3MEGP0569-KisusyenniVenessa"
```

### Set Up Google Maps API Key

Before running the app, you need to obtain a Google Maps API Key from [here](https://developers.google.com/maps/documentation/android-sdk/get-api-key).

Add the obtained Google Maps API Key and Peta Bencana API URL to the `local.properties` file in the project.

```
MAPS_API_KEY={ADD_YOUR_API_KEY}
API_URL=https://data.petabencana.id
```

## Libraries Used

The following libraries are used in the project:
* [AndroidX Core-KTX](https://developer.android.com/kotlin/ktx) - AndroidX Core library with Kotlin extensions for writing concise, idiomatic Kotlin code.
* [AndroidX AppCompat](https://developer.android.com/jetpack/androidx/releases/appcompat) - AndroidX AppCompat library for providing backward compatibility for newer Android features on older devices.
* [Google Material Components](https://material.io/develop/android) - Material Design components by Google for creating a visually appealing and consistent user interface.
* [ConstraintLayout](https://developer.android.com/training/constraint-layout) - Android's ConstraintLayout for creating flexible and responsive layouts.
* [AndroidX Lifecycle](https://developer.android.com/jetpack/androidx/releases/lifecycle) - AndroidX Lifecycle components for handling lifecycle-aware data in the ViewModel.
* [AndroidX Legacy Support](https://developer.android.com/jetpack/androidx/releases/legacy) - AndroidX Legacy Support library to support older Android features.
* [RecyclerView](https://developer.android.com/jetpack/androidx/releases/recyclerview) - AndroidX RecyclerView for displaying large datasets efficiently.
* [DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) - Android DataStore library for storing preferences asynchronously and consistently.
* [WorkManager](https://developer.android.com/jetpack/androidx/releases/work) - AndroidX WorkManager for managing background tasks efficiently.
* [Google Play Services Maps](https://developers.google.com/maps/documentation/android-sdk/overview) - Google Play Services library for integrating Google Maps into the app.
* [Retrofit](https://square.github.io/retrofit/) - Retrofit library for handling network requests and responses with a type-safe approach.
* [OkHttp Logging Interceptor](https://square.github.io/okhttp/interceptors) - OkHttp Logging Interceptor for logging HTTP interactions.
* [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) - Retrofit GSON Converter for parsing JSON responses using GSON.
* [Glide](https://github.com/bumptech/glide) - Glide library for loading images efficiently and smoothly.
* [Lottie](https://airbnb.io/lottie/#/) - Lottie for displaying and rendering animations from After Effects in the app.
* [Hilt](https://dagger.dev/hilt/) - Hilt for dependency injection.
* [JUnit 4](https://junit.org/junit4/) - JUnit 4 for unit testing.
* [Mockito](https://site.mockito.org/) - Mockito for mocking dependencies in unit tests.
* [Espresso](https://developer.android.com/training/testing/espresso/) - Espresso for UI testing.

## Project Architecture and Techniques

The Disaster App follows the MVVM (Model-View-ViewModel) architectural pattern to clearly separate concerns. The ViewModel serves as a bridge between the UI (View) and the data (Model). Data operations are handled using a Repository pattern, which abstracts the data source from the ViewModel. Dependency Injection is implemented using Koin, allowing for easy management of dependencies and enabling better testability of classes. View Binding is used to bind UI components in layout files, providing type-safe access to these components. Coroutines Flow and State Flow are employed to handle asynchronous operations and data streams efficiently. This approach simplifies handling background tasks and data updates while providing a more reactive and responsive user experience.

## Testing Documentation

This section provides documentation for the testing of the disaster reporting application.

### UI Test/Instrumentation Test

* **MainActivityTest**
    * Check if disaster list recyclerview is showing, test passed

### Unit Testing

* **DisasterAppRepositoryImplTest**
    * Check if get reports response should not null
    * Test passed
* **MainViewModelTest**
    * Check if get reports response should not null
    * Test passed


## Download the App

You can download the Disaster App on your Android device by clicking [here](https://drive.google.com/drive/folders/1My6gArcT6OH_xVUeEP2CSKDkDJI8yFkZ?usp=drive_link).

---
Feel free to reach out if you have any questions or need further assistance with the project! Happy coding! 🚀
