# Pipeytube (Android + Kotlin + Compose)

Minimal runnable app skeleton with three feature areas:

- `search`: query input + results list with thumbnails.
- `player`: video playback surface placeholder + quality selection.
- `details`: description + comments list.

## Project structure

```text
Pipeytube/
├── build.gradle.kts
├── settings.gradle.kts
├── gradle.properties
└── app/
    ├── build.gradle.kts
    └── src/main/java/com/pipeytube/
        ├── MainActivity.kt
        ├── navigation/
        ├── search/
        ├── player/
        └── video/details/
```

## Requirements

- Android Studio Iguana+ (or latest stable)
- Android SDK 34
- JDK 17

## Run in Android Studio

1. Open this folder in Android Studio.
2. Let Gradle sync finish.
3. Run the `app` configuration on an emulator or connected device.

## Run from command line

If Gradle is installed locally:

```bash
gradle :app:assembleDebug
```

Then install using Android Studio or `adb install app/build/outputs/apk/debug/app-debug.apk`.

## Notes

- Navigation shell lives in `app/src/main/java/com/pipeytube/navigation/PipeytubeApp.kt`.
- Feature entry composables:
  - `SearchRoute` in `.../search/SearchRoute.kt`
  - `PlayerRoute` in `.../player/PlayerRoute.kt`
  - `VideoDetailsRoute` in `.../video/details/VideoDetailsRoute.kt`
- `media3` and `coil` dependencies are wired for future implementation details.
