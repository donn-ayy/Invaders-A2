# Cannot Use
* Factory
* Strategy
* Pattern
* State

# Changes made to existing code
- App
  - refactored to include initial window for choosing level difficulty. 
  - window closes after selection is made
  - GameEngine accepts configLevel.getConfig() which returns a string of game difficulty 

# Part 2 : Time & Score 
- Time and Score would be a good candidate for Observer. 
- Time is independent and does not to be involved in the design pattern. 
- Score can be implented using the Observer pattern. 

- Since we are only keeping track of the Player's score and we have 4 different entities that the player missile can shoot,
each entity will be the subject or the player's missile can be the subject. The subjects hold the data that will inform 
the observer about the state change. 

- We can integrate the observer into an exising class, such as GameEngine, which handles the logic and gets the information 
about changes in the publisher state. 
