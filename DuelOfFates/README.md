# Duel of Fates

## Run the game
### Prerequisites
One of the following:
- Android device with a minimum Android SDK version of 24 (Android 7.0)
- Android Emulator
- Android Studio "Desktop"-version

Note: If you are using an emulator, make sure to connect to Firebase to be able to play the game.

### Installation
1. Clone the repository
2. Open the project in Android Studio
3. Run the project on your device or emulator
4. Enjoy the game!

## Structure
Our project follows the Model-View-Controller design pattern. The project is divided into three main folders: `controller`, `model`, and `view`. Each of these folders contains subfolders that are responsible for different parts of the game.

### Main folders of the project:

    .
    ├── ...
    ├── android         # Code for Android specific features
    ├── core            # Main implementation of the game
    ├── desktop         # Code for Desktop specific features
    └── README.md

### Model
    .
    ├── core/main/no.ntnu.dof/model
    │   ├── communication
    │   ├── di
    │   └── gameplay
    └── ...   

### View
    .
    ├── core/main/no.ntnu.dof/view
    │   ├── di
    │   ├── gameplay
    │   └── screen
    └── ...

### Controller
    .
    ├── core/main/no.ntnu.dof/controller
    │   ├── application
    │   ├── gameplay
    │   ├── lobby
    │   ├── menu
    │   └── network
    └── ...


### Assets
Here you can find all the assets used in the game.

    .
    ├── assets
    │   ├── cardsIcons
    │       └── ...
    │   ├── ...
    │   ├── music.mp3
    │   └── UISkin.json
    └── ...
