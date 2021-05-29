# CISC191-SPRING2021-B
Intermediate Java Programming Group B

Final Project: "Battle X Armada" arcade-style shoot-em-up game
## Prerequisites
1. Maven
2. Git
3. JDK14
## Optional Prerequisites
1. 1920 x 1080 screen resolution
2. "Gameplay" Font

Our game's UI was designed around 1920 x 1080 screen dimensions, so this
resolution ensures the best user experience. Having the "Gameplay" font
is purely optional, if you'd like more retro-looking text.
## Building
Through Maven, run clean. Then, run install.
## Running
The game can be run by running the Server class's main method, then running the Starter class's main method.

Alternatively, the game can be run through two command terminals, one for each jar:

java -jar Server/target/Server-1.0.0.jar  
java -jar Client/target/Client-1.0.0.jar

In both cases, make sure the Server has fully started before running the Starter (Client).
## Common Module
Shared classes between client and server modules in order to handle requests.
### Contains
1. UserProfileRequest
2. UserProfileResponse
3. UserScoreRequest
4. UserScoreResponse
## Server Module
The server application that handles multiple clients.
### Contains
1. Server
2. User
3. UserRepository
## Client Module
The client application used to connect to the server.
### Contains
1. Bullet
2. EnemyShip
3. GameView
4. PlayerShip
5. Ship
6. Starter
7. User
