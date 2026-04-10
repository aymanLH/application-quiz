# 📱 QuizApp

> A full-featured Android quiz application built entirely in **Java**, combining offline trivia with AI-generated questions.

![Android](https://img.shields.io/badge/Platform-Android%207.0+-3F51B5?style=flat-square&logo=android&logoColor=white)
![Java](https://img.shields.io/badge/Language-Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)
![SDK](https://img.shields.io/badge/SDK-35-3F51B5?style=flat-square)
![Material Design](https://img.shields.io/badge/UI-Material%20Design%203-757de8?style=flat-square)
![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)

---

## ✨ Features

- 🎮 **Offline Mode** — 50 hardcoded questions across 5 categories, shuffled each game
- 🤖 **AI Mode** — type any topic, pick 5 / 10 / 15 / 20 questions, get them generated on the fly via OpenRouter (Gemma 3 4B)
- ✅ **Instant visual feedback** — correct card turns green, wrong turns red, auto-advances after 1.2 s
- 🏆 **Persistent leaderboard** — scores saved to local SQLite, sorted by highest score
- 🎨 **Material Design 3** — indigo theme, CardView, smooth slide animations between all 5 screens

---

## 📸 Screens

| Home | Quiz | Result | Leaderboard | AI Setup |
|------|------|--------|-------------|----------|
| Enter name & choose mode | Answer 4-option cards | Score + motivational message | All-time rankings | Pick topic & question count |

---

## 🗂️ Project Structure

```
app/src/main/
├── AndroidManifest.xml          # 5 activities + INTERNET permission
├── java/com/example/quizapp/
│   ├── MainActivity.java        # Home screen
│   ├── QuizActivity.java        # Game engine (offline + AI)
│   ├── ResultActivity.java      # Score display + SQLite save
│   ├── LeaderboardActivity.java # Rankings screen
│   ├── AISetupActivity.java     # Topic input + question count
│   ├── Question.java            # Data model (POJO)
│   ├── QuestionBank.java        # 50 hardcoded questions
│   ├── Score.java               # Leaderboard entry model
│   ├── DBHandler.java           # SQLiteOpenHelper
│   └── ApiService.java          # OkHttp + OpenRouter API
└── res/
    ├── layout/                  # 5 XML layout files
    ├── drawable/                # Circles, chips, indigo accents
    ├── values/                  # colors.xml, strings.xml, themes.xml
    └── anim/                    # slide_in_right, slide_out_left
```

---

## 🚀 Getting Started

### Prerequisites

- Android Studio Hedgehog or newer
- JDK 17
- Android SDK 35 (AGP 8.7.3 / Gradle 9.x)

### Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/your-username/QuizApp.git
   cd QuizApp
   ```

2. **Add your OpenRouter API key**

   Open `ApiService.java` and replace the placeholder:
   ```java
   private static final String API_KEY = "your-openrouter-api-key";
   ```
   Get a free key at [openrouter.ai](https://openrouter.ai).

3. **Build & run**

   Open the project in Android Studio and hit **Run ▶**, or build via CLI:
   ```bash
   ./gradlew assembleDebug
   ```

---

## 🤖 AI Mode — How It Works

```
User types topic  →  AISetupActivity
       ↓
Picks question count (5 / 10 / 15 / 20)
       ↓
QuizActivity launches in MODE_AI
       ↓
ApiService sends POST to OpenRouter (background thread)
  prompt: "Generate N MCQ questions about <topic>.
           Return ONLY a valid JSON array. No markdown."
       ↓
parseResponse() strips any markdown fences → JSONArray
       ↓
Callback delivered on main thread → quiz starts normally
       ↓
On error (429 / timeout) → Toast + fallback to offline questions
```

**Model:** `google/gemma-3-4b-it:free` via `https://openrouter.ai/api/v1/chat/completions`

---

## 🗄️ Database Schema

```sql
CREATE TABLE scores (
    id          INTEGER PRIMARY KEY AUTOINCREMENT,
    player_name TEXT    NOT NULL,
    score       INTEGER NOT NULL,   -- correct answers
    total       INTEGER NOT NULL,   -- total questions in session
    date        TEXT    NOT NULL    -- "yyyy-MM-dd HH:mm"
);
```

Stored in `quizapp.db` — private to the app, no special permissions required.

---

## 🎨 Color Palette

| Name | Hex | Usage |
|------|-----|-------|
| Indigo Primary | `#3F51B5` | Header, toolbar, buttons, progress bar |
| Indigo Dark | `#283593` | Status bar, dark accents |
| Indigo Light | `#C5CAE9` | Progress bar background, chip borders |
| Correct Answer | `#4CAF50` | Correct card highlight |
| Wrong Answer | `#F44336` | Incorrect card highlight |
| Background | `#F0F0F5` | Page background |

---

## 📦 Dependencies

```kotlin
// app/build.gradle.kts
dependencies {
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // SQLite and org.json are built into Android — no extra deps needed
}
```

---

## 📋 Question Categories (Offline Mode)

| Category | Questions |
|----------|-----------|
| 🌍 Culture Générale | 10 |
| 🔬 Sciences | 10 |
| 🗺️ Géographie (incl. Maroc) | 10 |
| 📜 Histoire | 10 |
| 💻 Technologie | 10 |

---

## 🛠️ Known Challenges & Solutions

| Challenge | Solution |
|-----------|----------|
| View ID mismatch | Always download XML layout before writing Java code |
| API rate limit (429) | Switched from Llama 70B to free Gemma 3 4B model |
| Final variable in lambda | Declare a `final String copy` before entering the lambda |
| Network crash on main thread | `new Thread()` + `Handler(Looper.getMainLooper())` |
| Missing drawable crash | Used a `TextView` with Unicode arrow instead of `ImageButton` |

---

## 🔮 Possible Improvements

- ⏱️ Per-question countdown timer
- 🎉 Confetti animation on 100% score
- 🖼️ Splash screen with animated logo
- 🗂️ Selectable categories in offline mode
- 👥 Local multiplayer via Bluetooth / Wi-Fi Direct
- 📤 Export leaderboard to CSV

---


