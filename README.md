# Foodgo — Android (Kotlin + Jetpack Compose)

Foodgo is a native Android application configured to build a debug APK locally
or with GitHub Actions.

## GitHub Actions

The workflow is located at:

```text
.github/workflows/android.yml
```

It checks out the repository, configures Java 21 and Gradle 9.3.1, runs
`:app:assembleDebug`, and uploads the result as a workflow artifact.

### Download the APK

```text
Actions
→ Build Android APK
→ Successful run
→ Artifacts
→ foodgo-debug-apk
→ Foodgo-debug.apk
```

The artifact also contains `Foodgo-debug.apk.sha256` for checksum verification.

## Local build

Requirements:

- Android SDK platform 36.1
- Android SDK Build Tools 36.0.0 or newer
- JDK 17 or newer
- Gradle 9.3.1

Build command:

```bash
gradle :app:assembleDebug --stacktrace --no-daemon
```

Local output:

```text
app/build/outputs/apk/debug/app-debug.apk
```

The debug build uses Android's standard debug signing configuration. No custom
keystore, Firebase configuration, API key, or `.env` file is required to build
the current application.
