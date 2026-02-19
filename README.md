# TattAI ☕🥗
AI-powered, mobile-first nutrition assistant for the Tatte Bakery menu; built to help health-conscious customers find meals that match fitness goals, dietary restrictions, and macro preferences.

## What this repo is
This is a **Jetpack Compose Android** app scaffold with:
- A clean, modular architecture (UI, domain, data)
- Firebase Authentication + Firestore hooks (optional but wired)
- An OpenAI-backed chat/recommendation service (pluggable)
- A minimal menu data model and recommendation flow

It is intentionally opinionated, simple to run, and easy to extend.

## Tech stack
- **Android**: Kotlin, Jetpack Compose, Material 3
- **Architecture**: MVVM + Repository pattern
- **Async**: Kotlin Coroutines + Flow
- **Networking**: OkHttp + kotlinx.serialization
- **Backend**: Firebase Auth + Firestore (optional)
- **LLM**: OpenAI API (via HTTPS)

## Quickstart
### 1) Configure secrets
For easiest local runs, create:

`app/src/main/resources/secrets.properties`

```properties
OPENAI_API_KEY=your_key_here
OPENAI_MODEL=gpt-4o-mini
FIREBASE_ENABLED=false
```

This path is gitignored by default.

### 2) Firebase (optional)
If you want Firebase:
1. Create a Firebase project and add an Android app
2. Download `google-services.json` into `app/`
3. Set `FIREBASE_ENABLED=true` in your secrets

### 3) Run
Open the project in Android Studio and hit Run.

## Project structure
```
app/src/main/java/com/tattai/
  ui/                Compose screens + navigation + viewmodels
  domain/            Models
  data/              Repositories, Firebase, OpenAI client
  di/                Simple dependency wiring
  util/              Helpers (secrets loader)
docs/
scripts/
```

## Disclaimer
This project is not affiliated with or endorsed by Tatte Bakery & Cafe.
