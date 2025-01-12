# CineQuest

CineQuest is an Android application for managing and discovering movies. It integrates with the TMDb API to fetch movie data, and allows users to view, rate, and favorite movies. Additionally, the app provides a search feature to easily find movies by title, genre, or year.

## Features

- Browse movies from TMDb API
- Search for movies by title, genre, or year
- View detailed movie information (overview, release date, etc.)
- Rate and favorite movies
- User-friendly UI with responsive layouts
- Secure user authentication (if implemented)

## Technologies Used

- **Android**: The app is built for Android using Java and Android Studio.
- **TMDb API**: The app fetches movie data from the TMDb API.
- **Room Database**: If implemented, for storing local favorites and user preferences.
- **RecyclerView**: For displaying lists of movies.
- **ViewModel & LiveData**: For managing UI-related data in a lifecycle-conscious way.

## Setup

### Prerequisites

1. **Android Studio**: Ensure that you have Android Studio installed. You can download it from [here](https://developer.android.com/studio).
2. **TMDb API Key**: You will need an API key from [TMDb](https://www.themoviedb.org/documentation/api) to fetch movie data.

### Clone the Repository

Clone this repository to your local machine using:

```bash
git clone https://github.com/LimboWRLD/CineQuest.git
