# Space Invaders

## Welcome to Galaxy of Space Invaders: Pt 2

## Spaceship Engine Requirements
Before launching, ensure your spaceship has the following:
- Gradle 7.4.2 or higher
- JDK 17+ 
- Unix-based system 
- Shooting skills

## How to run into a new galaxy

- To launch your self into a new galaxy, open up terminal and enter the following command:`gradle clean build run`

## Whats new
V2.0.0 has the following significant improvements over V1.0.0!


### 1. ***Difficulty Level Selection***

![Game difficulty level selection](https://user-images.githubusercontent.com/119890460/279302931-f9da037d-895e-4fe8-8212-91fddfa2d92f.png)

-   Players can now choose between 3 levels of difficulty - Easy, Medium, or Hard - at the start of the game!
  - Players have 10 seconds to choose a difficulty level. 
  - If the timer runs out, the game immediately defaults to the Easy level!

### 2. ***Time & Score***

![Player time and score](https://user-images.githubusercontent.com/119890460/279302925-919b59a6-f437-45a6-9342-60a713710fcf.png)

- Players can monitor their game progress with the newly implemented timer and score!
- Players are granted the following points for each shot:
    - Fast Alien ------------- 4 Points
    - Slow Alien ------------- 3 Points 
    - Fast Projectile -------- 2 Points
    - Slow Projectile -------- 1 Point

### 3. ***Save & Undo***
- Want a second chance at making your shot? --- Now you can!
- Players now have the ability to press:
  - The **S** key at anytime to save the state of a game. 
    - This action saves the positions of the Enemy at the time of saving. 
  - The **U** key to restore from a saved stated and undo that shot you missed!
    - This action restores from the saved state and repositions the Enemy and their Projectiles!
- Things to note:
  - When you ***save*** + ***undo***, the time and player's score *also resets back to values of the saved state*.
    - For example, if you saved a game state at Time 00:30 and Score 15, if you play and revert back to the saved state, any new points will be reverted!
  - You can only recover from a saved state ***once***. 
    - For example, if you saved a game state and press "U" to undo, pressing it again will not revert back to the same state!

### 4. ***Cheat Codes***

![On screen shortcuts and cheatcodes](https://user-images.githubusercontent.com/119890460/279303713-4fba3dd5-f083-468a-9ad6-3436c8e0d832.png)

- What's a game without cheat codes?!
- Shortcut/Cheat Options are now displayed on screen right below the Timer!
- Players now have the ability to score some points with the following new commands: 
  - The **V** key will remove all **_SLOW PROJECTILES_** and add them to the player's score (@ 1 POINT EACH)!
  - The **B** key will remove all **_FAST PROJECTILES_** and add them to the player's score (@ 2 POINT EACH)!
  - The **N** key will remove all **_FAST ENEMIES_** and add them to the player's score (@ 3 POINT EACH)!
  - The **M** key will remove all **_FAST ENEMIES_** and add them to the player's score (@ 4 POINT EACH)!
- Only one cheat can occur at a time, so choose wisely!

## The Other Fun Stuff (Design Patterns)
This section lists the names of the design patterns used in V2.0.0, the corresponding class names, and participating roles in the patterns:

### Singleton Pattern 
- Singleton: 
  - EasyLevel.java
  - MediumLevel.java
  - HardLevel.java
- Other Helper Classes: 
  - GameLevel.java: interface defines an abstract getInstance method.
  - App.java: based on user input from GameWindow, hold the new instance of GameEngine returned by respective game levels. 

### Observer Pattern 
- Observer Interface: 
  - Observer.java
- Concrete Observers:
  - ScoreTimeKeeper.java
- Subject Interface: 
  - Subject.java
- Concrete Subjects: 
  - Enemy.java
  - EnemyProjectile.java
- Client: 
  - GameEngine.java

### Memento Pattern 
- Originator Interface: 
  - Originator.java
- Memento Interface: 
  - Memento.java
- Concrete Originators: 
  - GameEngine.java
  - ScoreTimeKeeper.java
- Concrete Mementos:
  - EnemyMemento.java
  - ScoreTimeMemento.java
- Caretaker:
  - MementoManager.java

### Command Pattern
- Command Interface: 
  - Command.java
- Concrete Commands:
  - RemoveFastAlien.java
  - RemoveSlowAlien.java
  - RemoveFastProjectile.java
  - RemoveSlowAlien.java
- Receiver: 
  - KeyboardInputHandler.java
- Invoker: 
  - GameWindow.java
- Client:
  - GameEngine.java
