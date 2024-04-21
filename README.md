![duel of fates logo](./DuelOfFates/assets/logo.png)

# Duel of Fates

Duel of Fates is a cross-platform 1v1 multplayer card duel game, inspired by the popular game [Slay the Spire](https://store.steampowered.com/app/646570/Slay_the_Spire/). You can choose to be a brave knight, a wise mage or a devious skeleton. Check out the [trailer of Duel of Fates](https://www.youtube.com/watch?v=Edpo7qrH9JU), and when you're ready, join the arena and test your fate in a duel!

## Run the game
### Prerequisites
One of the following:
- Android device for the android application (>= Android 7.0)
- Android emulator for the android application (>= Android 7.0)
- Java for desktop application (>= Java 8)

### Installation 

#### APK
1. Clone the repository.
2. Transfer the APK file to your compatible mobile device.
3. Install the application.
4. Enjoy the game!

#### Project Source
1. Clone the repository.
2. Import the [project folder](./DuelOfFates/) in Android Studio.
3. Run the project on your device or emulator.
4. Enjoy the game!

## Structure
Our project follows the Model-View-Controller design pattern. The project is divided into three main folders: `controller`, `model`, and `view`. Each of these folders contains subfolders that are responsible for different parts of the game.

### Main folders of the project:

    .
    ├── ...
    ├── android         # Code for Android specific features
    ├── core            # Main implementation of the game
    ├── desktop         # Code for Desktop specific features

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
